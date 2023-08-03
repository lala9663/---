package wanted.project.wantedpreonboardingbackend.member.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LoginRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LogoutRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.SignUpRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.response.LoginResponseDto;
import wanted.project.wantedpreonboardingbackend.member.dto.response.Response;
import wanted.project.wantedpreonboardingbackend.member.dto.response.SignUpResponseDto;
import wanted.project.wantedpreonboardingbackend.member.entity.Authority;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;
import wanted.project.wantedpreonboardingbackend.member.exception.MemberException;
import wanted.project.wantedpreonboardingbackend.member.repository.MemberRepository;
import wanted.project.wantedpreonboardingbackend.member.service.MemberService;
import wanted.project.wantedpreonboardingbackend.security.dto.TokenResponseDto;
import wanted.project.wantedpreonboardingbackend.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final Response response;
    private final RedisTemplate redisTemplate;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;




    /**
     * 회원가입
     * @param signUp
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> signup(SignUpRequestDto signUp) {

        if (!isValidEmail(signUp.getEmail())) {
            throw new MemberException("유효하지 않은 이메일 형식입니다.");
        }
        if (!isValidPassword(signUp.getPassword())) {
            throw new MemberException("비밀번호는 숫자와 영문자를 포함한 8자 이상이어야 합니다.");
        }
        if (isDuplicatedEmail(signUp.getEmail())) {
            throw new MemberException("이미 존재하는 회원입니다.");
        }
        Member member = memberRepository.save(
                Member.builder()
                        .email(signUp.getEmail())
                        .password(passwordEncoder.encode(signUp.getPassword()))
                        .roles(Collections.singletonList(Authority.ROLE_USER.name()))
                        .build());
        return response.success("회원가입 성공했습니다.");
    }

    @Override
    @Transactional
    public ResponseEntity<?> login(LoginRequestDto login) {
        // 이메일 확인
        Member member = memberRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new MemberException("해당 유저가 존재하지 않습니다."));

        // 비밀번호 확인
        if (!isMatchesPassword(login.getPassword(), member.getPassword())) {
            throw new MemberException("비밀번호가 일치하지 않습니다.");
        }

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponseDto tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

//        // 인증 성공 시 토큰 생성 및 반환
//        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword(), member.getAuthorities());
//        TokenResponseDto tokenResponse = jwtTokenProvider.generateToken(authentication);

        // 로그인 성공 응답에 토큰 정보 추가하여 반환
        return response.success(tokenInfo, "로그인에 성공했습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> logout(LogoutRequestDto logout) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User email 을 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success("로그아웃 되었습니다.");
    }


    // 이메일 유효성 검사
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regex);
    }

    // 비밀번호 유효성 검사
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        return password.matches(regex);
    }

    // 이메일 중복 검사
    private boolean isDuplicatedEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    // 비밀번호 일치 검사
    private boolean isMatchesPassword(String password, String encryptedPassword) {
        // 비밀번호를 복호화하여 일치 여부 확인
        return passwordEncoder.matches(password, encryptedPassword);
    }


}
