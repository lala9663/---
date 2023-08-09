package wanted.project.wantedpreonboardingbackend.board.controller;

import io.jsonwebtoken.io.IOException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.request.UpdateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.board.exception.BoardException;
import wanted.project.wantedpreonboardingbackend.board.service.BoardService;
import wanted.project.wantedpreonboardingbackend.member.dto.response.MemberDto;
import wanted.project.wantedpreonboardingbackend.member.dto.response.Response;
import wanted.project.wantedpreonboardingbackend.member.service.MemberService;
import wanted.project.wantedpreonboardingbackend.security.jwt.JwtTokenProvider;

import java.util.List;

@RestController
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Response response;

    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성한다.")
    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody CreateBoardDto create,
                                            Authentication authentication) {
        boardService.createBoard(create, authentication.getName());

        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    @PutMapping("/{boardId}")
    public ResponseEntity<Long> updateBoard(
            @PathVariable Long boardId,
            @RequestBody UpdateBoardDto update,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ) {
        try {
            String token = authorizationHeader.substring(7);
            String loggedInEmail = jwtTokenProvider.getMemberEmailFromToken(token);

            Long updatedBoardId = boardService.updateBoard(update, boardId, loggedInEmail);

            if (updatedBoardId != null) {
                return ResponseEntity.ok(updatedBoardId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "게시글 논리적 삭제", notes = "게시글을 논리적으로 삭제한다.")
    @PutMapping("/{boardId}/delete")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring(7);
            String loggedInEmail = jwtTokenProvider.getMemberEmailFromToken(token);

            Long deletedBoardId = boardService.deleteBoard(boardId, loggedInEmail);
            return ResponseEntity.ok("게시글이 성공적으로 논리적으로 삭제되었습니다.");
        } catch (BoardException.BoardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
        } catch (BoardException.BoardDeletedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 삭제된 게시글입니다.");
        } catch (BoardException.BoardNoPermissionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
        }
    }


    @ApiOperation(value = "게시글 전체 조회", notes = "게시글을 조회한다.")
    @GetMapping("/all")
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        List<BoardDto> boardDtos = boardService.getAllBoards();
        return new ResponseEntity<>(boardDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "특정 게시글 조회", notes = "게시글을 조회한다.")
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long boardId) {
        BoardDto boardDto = boardService.findBoardById(boardId);
        if (boardDto != null) {
            return new ResponseEntity<>(boardDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "페이징", notes = "1~10번 게시글 보여준다")
    @GetMapping("/paged")
    public ResponseEntity<Page<BoardDto>> getAllBoardsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BoardDto> boardPage = boardService.getAllBoardsWithPagination(page, size);
        return new ResponseEntity<>(boardPage, HttpStatus.OK);
    }

}
