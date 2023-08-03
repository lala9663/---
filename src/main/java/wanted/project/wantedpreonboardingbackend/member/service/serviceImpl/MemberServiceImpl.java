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
    public SignUpResponseDto signup(SignUpRequestDto signUp) {

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
        return new SignUpResponseDto(member.getMemberId(), member.getEmail());
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto login) {
        Member member = memberRepository.findByEmail(login.getEmail())
                .orElseThrow(MemberException::new);
        if (!isMatchesPassword(login.getPassword(), member.getPassword())) {
            throw new MemberException("비밀번호가 일치하지 않습니다.");
        }
        // 인증 성공 시 토큰 생성 및 반환
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword(), member.getAuthorities());
        TokenResponseDto tokenResponse = jwtTokenProvider.generateToken(authentication);

        // 로그인 성공 응답에 토큰 정보 추가하여 반환
        return new LoginResponseDto(member.getEmail(), tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
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
