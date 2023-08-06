package wanted.project.wantedpreonboardingbackend.board.service.impl;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.request.UpdateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.board.repository.BoardRepository;
import wanted.project.wantedpreonboardingbackend.board.service.BoardService;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;
import wanted.project.wantedpreonboardingbackend.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public Long createBoard(CreateBoardDto create, Long id, Authentication authentication) throws IOException {

        Member loginMember = memberRepository.findByMemberId(id).get();

        Board savedBoard = boardRepository.save(create.toEntity(loginMember));


        if (create.getTitle().length() > 30) {
            throw new IllegalArgumentException("게시글 제목은 최대 100자까지 입력 가능합니다.");
        }

        if (create.getContent().length() > 1000) {
            throw new IllegalArgumentException("게시글 내용은 최대 1000자까지 입력 가능합니다.");
        }

        return savedBoard.getBoardId();
    }

    @Override
    @Transactional
    public Long updateBoard(Long boardId, UpdateBoardDto update) throws IOException {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        if (optBoard.isEmpty()) {
            return null;
        }

        Board board = optBoard.get();
        board.setTitle(update.getTitle());
        board.setContent(update.getContent());

        boardRepository.save(board);

        return board.getBoardId();
    }

    @Override
    public Long deleteBoard(Long boardId) throws IOException {
        Optional<Board> delBoard = boardRepository.findById(boardId);

        if (delBoard.isEmpty()) {
            return null;
        }
        Member member = delBoard.get().getMember();
        boardRepository.deleteById(boardId);
        return boardId;
    }

    @Override
    public List<BoardDto> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boards) {
            BoardDto boardDto = new BoardDto();
            boardDto.setBoardId(board.getBoardId());
            boardDto.setTitle(board.getTitle());
            boardDto.setContent(board.getContent());
            boardDtos.add(boardDto);
        }
        return boardDtos;
    }

}
