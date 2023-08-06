package wanted.project.wantedpreonboardingbackend.board.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.service.BoardService;
import wanted.project.wantedpreonboardingbackend.member.dto.response.Response;

@RestController
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final Response response;

    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성한다.")
    @PostMapping("/new")
    public ResponseEntity<Void> create(@RequestBody CreateBoardDto create, Authentication authentication) {
        boardService.create(create, authentication.getName());
        return ResponseEntity.ok(null);
    }

}
