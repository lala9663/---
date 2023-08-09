package wanted.project.wantedpreonboardingbackend.member.service;

import org.springframework.http.ResponseEntity;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.member.dto.request.*;
import wanted.project.wantedpreonboardingbackend.member.dto.response.MemberDto;

import java.util.List;

public interface MemberService {
    ResponseEntity<?> signup(SignUpRequestDto singUp);
    ResponseEntity<?> login(LoginRequestDto login);
    ResponseEntity<?> reissue(ReissueRequestDto reissue);
    ResponseEntity<?> authority();
    List<BoardDto> getBoardsForMember(String email);
    String findPassword(FindPasswordRequestDto find);
    String changePassword(String email, ChangePasswordRequestDto request);

}
