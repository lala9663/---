package wanted.project.wantedpreonboardingbackend.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LoginRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LogoutRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.ReissueRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.SignUpRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.response.Response;
import wanted.project.wantedpreonboardingbackend.member.service.MemberService;
import wanted.project.wantedpreonboardingbackend.security.lib.Helper;

@RestController
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
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
//    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
//        String accessToken = authorizationHeader.replace("Bearer ", "");
//
//        memberService.logout(new LogoutRequestDto(accessToken));
//    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃을 진행한다.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDto logoutRequest) {
        boolean logoutResult = memberService.logout(logoutRequest);

        if (logoutResult) {
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed");
        }
    }



//    @PostMapping("/logout")
//    public ResponseEntity<LogoutResponseDto> logout(LogoutRequestDto logoutRequestDto) {
//        LogoutResponseDto responseDto = memberService.logout(logoutRequestDto);
//        return ResponseEntity.ok(responseDto);
//    }

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
