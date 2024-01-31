package wanted.project.wantedpreonboardingbackend.board.service;

import org.springframework.data.domain.Page;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.request.UpdateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;

import java.util.List;

public interface BoardService {
    void createBoard(CreateBoardDto create, String email);
    Long updateBoard(UpdateBoardDto update, Long boardId, String loggedInEmail);
    Long deleteBoard(Long boardId, String loggedInEmail);
    List<BoardDto> getAllBoards();
    BoardDto findBoardById(Long boardId);
    Page<BoardDto> getAllBoardsWithPagination(int page, int size);

}
