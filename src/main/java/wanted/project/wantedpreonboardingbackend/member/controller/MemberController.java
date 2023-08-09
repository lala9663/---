package wanted.project.wantedpreonboardingbackend.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.*;
import wanted.project.wantedpreonboardingbackend.member.dto.response.Response;
import wanted.project.wantedpreonboardingbackend.member.exception.MemberException;
import wanted.project.wantedpreonboardingbackend.member.service.MemberService;
import wanted.project.wantedpreonboardingbackend.security.jwt.JwtTokenProvider;
import wanted.project.wantedpreonboardingbackend.security.lib.Helper;

import java.util.List;

@RestController
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Response response;

    @ApiOperation(value = "회원가입", notes = "회원가입을 진행한다.")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Validated SignUpRequestDto signUp, Errors errors) {

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }

        return memberService.signup(signUp);
    }

    @ApiOperation(value = "로그인", notes = "로그인을 진행한다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequestDto login, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return memberService.login(login);
    }

//    @ApiOperation(value = "로그아웃", notes = "로그아웃을 진행한다.")
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestBody LogoutRequestDto logoutRequest) {
//        boolean logoutResult = memberService.logout(logoutRequest);
//
//        if (logoutResult) {
//            return ResponseEntity.ok("Logout successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed");
//        }
//    }

    @ApiOperation(value = "비밀번호 재발급", notes = "아이디와 번호를 통한 비밀번호 재발급")
    @PostMapping("/findMember")
    public ResponseEntity<String> findPassword(@RequestBody FindPasswordRequestDto find) {
        try {
            String temporaryPassword = memberService.findPassword(find);
            return ResponseEntity.ok(temporaryPassword);
        } catch (MemberException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }


    @ApiOperation(value = "해당 멤버의 게시글", notes = "로그인한 멤버의 게시글 목록")
    @GetMapping("/my-boards")
    public ResponseEntity<List<BoardDto>> getMyBoards(@RequestHeader(name = "Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String loggedInEmail = jwtTokenProvider.getMemberEmailFromToken(token);

        List<BoardDto> memberBoards = memberService.getBoardsForMember(loggedInEmail);

        return ResponseEntity.ok(memberBoards);
    }


    @ApiOperation(value = "재발급", notes = "토큰을 재발급한다.")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody ReissueRequestDto reissue, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return memberService.reissue(reissue);
    }

    @ApiOperation(value = "권한 변경", notes = "권한을 변경한다.")
    @GetMapping("/authority")
    public ResponseEntity<?> authority() {
        log.info("ADD ROLE_ADMIN");
        return memberService.authority();
    }
}
