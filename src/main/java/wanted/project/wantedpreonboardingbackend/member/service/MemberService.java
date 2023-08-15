package wanted.project.wantedpreonboardingbackend.member.service;

import org.springframework.http.ResponseEntity;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.member.dto.request.*;
import wanted.project.wantedpreonboardingbackend.member.dto.response.MemberDto;
import wanted.project.wantedpreonboardingbackend.security.dto.TokenResponseDto;

import java.util.List;

public interface MemberService {
    void signup(SignUpRequestDto singUp);
    TokenResponseDto login(LoginRequestDto login);
    ResponseEntity<?> reissue(ReissueRequestDto reissue);
    ResponseEntity<?> authority();
    List<BoardDto> getBoardsForMember(String email);
    String findPassword(FindPasswordRequestDto find);
    String changePassword(String email, ChangePasswordRequestDto request);
    boolean isEmailExists(String email);


}
