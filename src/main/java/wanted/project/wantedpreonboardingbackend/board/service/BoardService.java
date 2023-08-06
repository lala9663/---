package wanted.project.wantedpreonboardingbackend.board.service;

import io.jsonwebtoken.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.request.UpdateBoardDto;

public interface BoardService {
    Long createBoard(CreateBoardDto create, Long memberId, Authentication authentication) throws IOException;
    Long updateBoard(Long boardId, UpdateBoardDto update) throws IOException;

}
