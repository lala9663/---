package wanted.project.wantedpreonboardingbackend.board.service.impl;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.request.UpdateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.dto.response.BoardDto;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.board.exception.BoardException;
import wanted.project.wantedpreonboardingbackend.board.repository.BoardRepository;
import wanted.project.wantedpreonboardingbackend.board.service.BoardService;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;
import wanted.project.wantedpreonboardingbackend.member.exception.MemberException;
import wanted.project.wantedpreonboardingbackend.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void createBoard(CreateBoardDto create, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("회원을 찾을 수 없습니다."));



        if (create.getTitle().length() > 30) {
            throw new IllegalArgumentException("게시글 제목은 최대 100자까지 입력 가능합니다.");
        }

        if (create.getContent().length() > 1000) {
            throw new IllegalArgumentException("게시글 내용은 최대 1000자까지 입력 가능합니다.");
        }

        Board savedBoard = boardRepository.save(create.toEntity(member));
    }

    @Override
    @Transactional
    public Long updateBoard(Long boardId, UpdateBoardDto update) throws IOException {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        if (optBoard.isEmpty()) {
            throw new BoardException.BoardNotFoundException();
        }

        Board board = optBoard.get();

        if (board.isDeleted()) {
            throw new BoardException.BoardDeletedException();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();

        // 현재 로그인한 사용자가 작성한 모든 게시글을 조회
        List<Board> memberBoards = boardRepository.findByMemberEmail(loggedInEmail);

        // 수정하려는 게시글의 작성자와 비교
        if (!memberBoards.contains(board)) {
            throw new BoardException.BoardNoPermissionException();
        }

        board.setTitle(update.getTitle());
        board.setContent(update.getContent());

        boardRepository.save(board);

        return board.getBoardId();
    }

    @Override
    @Transactional
    public Long deleteBoard(Long boardId) throws IOException {
        Optional<Board> deleteBoard = boardRepository.findById(boardId);

        if (deleteBoard.isEmpty()) {
            throw new BoardException.BoardDeletedException();
        }

        Board board = deleteBoard.get();
        Member member = board.getMember();
        board.delete();

        try {
            boardRepository.save(board);
        } catch (Exception e) {
            throw new BoardException.FailedException("삭제 후 저장 실패");
        }
        return boardId;
    }

    @Override
    public List<BoardDto> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boards) {
            boardDtoList.add(convertToBoardDto(board));
        }
        return boardDtoList;
    }

    @Override
    public BoardDto findBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);

        if (board != null) {
            return convertToBoardDto(board);
        } else {
            return null;
        }
    }

    @Override
    public Page<BoardDto> getAllBoardsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardRepository.findAll(pageable);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boardPage.getContent()) {
            boardDtoList.add(convertToBoardDto(board));
        }
        return new PageImpl<>(boardDtoList, pageable, boardPage.getTotalElements());
    }

    private BoardDto convertToBoardDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        return boardDto;
    }


}
