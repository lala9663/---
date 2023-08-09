package wanted.project.wantedpreonboardingbackend.board.service;

import io.jsonwebtoken.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.request.UpdateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;

import java.util.List;

public interface BoardService {
    void createBoard(CreateBoardDto create, String email);
    Long updateBoard(Long boardId, UpdateBoardDto update) throws IOException;
    Long deleteBoard(Long boardId) throws IOException;
    List<BoardDto> getAllBoards();
    BoardDto findBoardById(Long boardId);
    Page<BoardDto> getAllBoardsWithPagination(int page, int size);

}
