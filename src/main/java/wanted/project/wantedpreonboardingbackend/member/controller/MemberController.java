package wanted.project.wantedpreonboardingbackend.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LoginRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LogoutRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.SignUpRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.response.LoginResponseDto;
import wanted.project.wantedpreonboardingbackend.member.dto.response.Response;
import wanted.project.wantedpreonboardingbackend.member.dto.response.SignUpResponseDto;
import wanted.project.wantedpreonboardingbackend.member.service.MemberService;
import wanted.project.wantedpreonboardingbackend.security.lib.Helper;

@RestController
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Response response;

//    @PostMapping("/signup")
//    public ResponseEntity<?> signUp(@Validated SignUpRequestDto signUp, Errors errors) {
//
//        if (errors.hasErrors()) {
//            return response.invalidFields(Helper.refineErrors(errors));
//        }
//
//        return memberService.signup(signUp);
//    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Validated @RequestBody SignUpRequestDto signUpRequestDto, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        SignUpResponseDto responseDto = memberService.signup(signUpRequestDto);

        return ResponseEntity.ok(responseDto);
    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Validated LoginRequestDto login, Errors errors) {
//        if (errors.hasErrors()) {
//            return response.invalidFields(Helper.refineErrors(errors));
//        }
//        return memberService.login(login);
//    }
@PostMapping("/login")
public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
    LoginResponseDto responseDto = memberService.login(loginRequestDto);
    return ResponseEntity.ok(responseDto);
}

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@Validated LogoutRequestDto logout, Errors errors) {
//        if (errors.hasErrors()) {
//            return response.invalidFields(Helper.refineErrors(errors));
//        }
//        return memberService.logout(logout);
//    }
    @GetMapping("/authority")
    public ResponseEntity<?> authority() {
        log.info("ADD ROLE_ADMIN");
        return memberService.authority();
    }
}
