package wanted.project.wantedpreonboardingbackend.member.service;

import org.springframework.http.ResponseEntity;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LoginRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LogoutRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.SignUpRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.response.LoginResponseDto;
import wanted.project.wantedpreonboardingbackend.member.dto.response.SignUpResponseDto;

public interface MemberService {
    ResponseEntity<?> signup(SignUpRequestDto singUp);
    ResponseEntity<?> login(LoginRequestDto login);
    ResponseEntity<?> logout(LogoutRequestDto logout);
}
