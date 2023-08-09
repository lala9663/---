package wanted.project.wantedpreonboardingbackend.member.service;

import org.springframework.http.ResponseEntity;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LoginRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.LogoutRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.ReissueRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.request.SignUpRequestDto;
import wanted.project.wantedpreonboardingbackend.member.dto.response.MemberDto;

import java.util.List;

public interface MemberService {
    ResponseEntity<?> signup(SignUpRequestDto singUp);
    ResponseEntity<?> login(LoginRequestDto login);
    ResponseEntity<?> reissue(ReissueRequestDto reissue);
    ResponseEntity<?> authority();
    List<BoardDto> getBoardsForMember(String email);


}
