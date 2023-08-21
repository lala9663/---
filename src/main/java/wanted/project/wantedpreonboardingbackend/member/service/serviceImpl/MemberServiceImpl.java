package wanted.project.wantedpreonboardingbackend.member.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.board.repository.BoardRepository;
import wanted.project.wantedpreonboardingbackend.member.dto.request.*;
import wanted.project.wantedpreonboardingbackend.member.dto.response.Response;
import wanted.project.wantedpreonboardingbackend.member.entity.Authority;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;
import wanted.project.wantedpreonboardingbackend.member.exception.MemberException;
import wanted.project.wantedpreonboardingbackend.member.repository.MemberRepository;
import wanted.project.wantedpreonboardingbackend.member.service.MemberService;
import wanted.project.wantedpreonboardingbackend.security.dto.TokenResponseDto;
import wanted.project.wantedpreonboardingbackend.security.jwt.JwtTokenProvider;
import wanted.project.wantedpreonboardingbackend.security.util.SecurityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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


    /**
     * 회원가입
     * @param signUp
     * @return
     */
    @Override
    @Transactional
    public void signup(SignUpRequestDto signUp) {

        if (isDuplicatedEmail(signUp.getEmail())) {
            throw new MemberException("이미 존재하는 회원입니다.");
        }
        if (!isValidEmail(signUp.getEmail())) {
            throw new MemberException("유효하지 않은 이메일 형식입니다.");
        }
        if (!isValidPassword(signUp.getPassword())) {
            throw new MemberException("비밀번호는 숫자와 영문자를 포함한 8자 이상이어야 합니다.");
        }
        Member member = Member.builder()
                        .email(signUp.getEmail())
                        .password(passwordEncoder.encode(signUp.getPassword()))
                        .phone(signUp.getPhone())
                        .name(signUp.getName())
                        .age(signUp.getAge())
                        .roles(Collections.singletonList(Authority.ROLE_USER.name()))
                        .build();
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public TokenResponseDto login(LoginRequestDto login) {
        if (!isEmailExists(login.getEmail())) {
            throw new MemberException("존재하지 않는 아이디입니다.");
        }
        Member member = memberRepository.findByEmail(login.getEmail())
                .orElseThrow(MemberException::new);
        if (!isMatchesPassword(login.getPassword(), member.getPassword())) {
            throw new MemberException("비밀번호가 일치하지 않습니다.");
        }
        // 인증 성공 시 토큰 생성 및 반환
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword(), member.getAuthorities());
        TokenResponseDto tokenResponse = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenResponse.getRefreshToken(), tokenResponse.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return tokenResponse; // 토큰 정보만 반환
    }


//    @Override
//    public ResponseEntity<?> logout(LogoutRequestDto logout) {
//        // 1. Access Token 검증
//        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
//            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
//        }
//
//        // 2. Access Token 에서 email 을 가져옴
//        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
//
//        // 3. Redis 에서 해당 email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제
//        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
//            // Refresh Token 삭제
//            redisTemplate.delete("RT:" + authentication.getName());
//        }
//
//        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장
//        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
//        redisTemplate.opsForValue()
//                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
//
//        return response.success("로그아웃 되었습니다.");
//    }

    @Override
    public ResponseEntity<?> reissue(ReissueRequestDto reissue) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 email 을 기반으로 저장된 Refresh Token 값을 가져옴
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());
        // 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        if(!refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 4. 새로운 토큰 생성
        TokenResponseDto tokenInfo = jwtTokenProvider.renewToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> authority() {
        // SecurityContext에 담겨 있는 authentication email 정보
        String memberEmail = SecurityUtil.getCurrentUserEmail();

        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        // add ROLE_ADMIN
        member.getRoles().add(Authority.ROLE_ADMIN.name());
        memberRepository.save(member);

        return response.success();
    }

    @Override
    @Transactional
    public List<BoardDto> getBoardsForMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        List<Board> memberBoards = member.getBoards();
        List<BoardDto> boardDTOs = new ArrayList<>();

        for (Board board : memberBoards) {
            if (!board.isBoardDeleted()){
                BoardDto boardDto = new BoardDto();
                boardDto.setBoardId(board.getBoardId());
                boardDto.setTitle(board.getTitle());
                boardDto.setContent(board.getContent());

                boardDTOs.add(boardDto);
            }
        }

        return boardDTOs;
    }

    @Transactional
    @Override
    public String findPassword(FindPasswordRequestDto find) {
        Member member = memberRepository.findByEmailAndPhone(find.getEmail(), find.getPhone());
        if (member == null) {
            throw new MemberException("해당하는 회원이 없습니다.");
        }

        String temporaryPassword = generateTemporaryPassword();

        member.setPassword(passwordEncoder.encode(temporaryPassword));
        memberRepository.save(member);

        return temporaryPassword;
    }

    @Override
    @Transactional
    public String changePassword(String email, ChangePasswordRequestDto request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
            throw new MemberException("현재 비밀번호가 일치하지 않습니다.");
        }

        String newPassword = passwordEncoder.encode(request.getNewPassword());
        member.setPassword(newPassword);
        memberRepository.save(member);

        return newPassword;
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

    // 아이디 확인 검사
    @Override
    public boolean isEmailExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 비밀번호 일치 검사
    private boolean isMatchesPassword(String password, String encryptedPassword) {
        // 비밀번호를 복호화하여 일치 여부 확인
        return passwordEncoder.matches(password, encryptedPassword);
    }

    private String generateTemporaryPassword() {
        // 임시 비밀번호 생성 로직을 구현 (예: 랜덤 문자열 생성)
        // 이 예시에서는 8자의 랜덤 문자열을 생성하는 방법을 사용
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newPassword = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            newPassword.append(characters.charAt(index));
        }
        return newPassword.toString();
    }


}
