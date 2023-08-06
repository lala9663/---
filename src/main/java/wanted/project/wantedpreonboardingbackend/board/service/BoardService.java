package wanted.project.wantedpreonboardingbackend.board.service;

import org.springframework.http.ResponseEntity;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;

public interface BoardService {
    void create(CreateBoardDto create, String eamil);
}
