package wanted.project.wantedpreonboardingbackend.board.service;

import io.jsonwebtoken.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.request.UpdateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;

import java.util.List;

public interface BoardService {
    Long createBoard(CreateBoardDto create, Long memberId, Authentication authentication) throws IOException;
    Long updateBoard(Long boardId, UpdateBoardDto update) throws IOException;
    Long deleteBoard(Long boardId) throws IOException;
    List<BoardDto> getAllBoards();
    BoardDto findBoardById(Long boardId);
}
