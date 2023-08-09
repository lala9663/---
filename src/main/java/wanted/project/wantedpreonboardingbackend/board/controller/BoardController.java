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
    public ResponseEntity<Long> updateBoard(@PathVariable Long boardId, @RequestBody UpdateBoardDto update) {
        try {
            Long updatedBoardId = boardService.updateBoard(update, boardId);
            if (updatedBoardId != null) {
                return ResponseEntity.ok(updatedBoardId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Long> deleteBoard(@PathVariable Long boardId) {
        try {
            Long deletedBoardId = boardService.deleteBoard(boardId);
            if (deletedBoardId != null) {
                return ResponseEntity.ok(deletedBoardId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    @GetMapping("/my-boards")
    public ResponseEntity<List<BoardDto>> getMyBoards(@RequestHeader(name = "Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String loggedInEmail = jwtTokenProvider.getMemberEmailFromToken(token);

        List<BoardDto> memberBoards = memberService.getBoardsForMember(loggedInEmail);

        return ResponseEntity.ok(memberBoards);
    }


}
