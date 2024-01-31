package wanted.project.wantedpreonboardingbackend.board.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        if (!isValidTitle(create.getTitle())) {
            throw new BoardException("게시글 제목은 최대 100자까지 입력 가능합니다.");
        }
        if (!isValidContent(create.getContent())) {
            throw new BoardException("게시글 내용은 최대 1000자까지 입력 가능합니다.");
        }

        boardRepository.save(create.toEntity(member));
    }

    @Override
    @Transactional
    public Long updateBoard(UpdateBoardDto update, Long boardId, String loggedInEmail) {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        if (optBoard.isEmpty()) {
            throw new BoardException.BoardNotFoundException();
        }

        Board board = optBoard.get();

        if (board.isDeleted()) {
            throw new BoardException.BoardDeletedException();
        }

        if (!board.getMember().getEmail().equals(loggedInEmail)) {
            throw new BoardException.BoardNoPermissionException();
        }

        board.setTitle(update.getTitle());
        board.setContent(update.getContent());

        boardRepository.save(board);

        return board.getBoardId();
    }


    @Override
    @Transactional
    public Long deleteBoard(Long boardId, String loggedInEmail) {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        if (optBoard.isEmpty()) {
            throw new BoardException.BoardNotFoundException();
        }

        Board board = optBoard.get();

        if (board.isDeleted()) {
            throw new BoardException.BoardDeletedException();
        }

        if (!board.getMember().getEmail().equals(loggedInEmail)) {
            throw new BoardException.BoardNoPermissionException();
        }

        board.setBoardDeleted(true); // 논리적 삭제 상태로 변경
        boardRepository.save(board);

        return board.getBoardId();
    }

    @Override
    public List<BoardDto> getAllBoards() {
        List<Board> boards = boardRepository.findAllByBoardDeletedFalse();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board board : boards) {
            BoardDto boardDto = convertToBoardDto(board);
            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    @Override
    public BoardDto findBoardById(Long boardId) {
        Board board = boardRepository.findByBoardIdAndBoardDeletedFalse(boardId);

        if (board == null) {
            throw new BoardException("해당 게시판이 없습니다.");
        }
        if (board.isBoardDeleted()) {
            throw new BoardException("삭제된 게시판입니다.");
        }

        return convertToBoardDto(board);
    }


    @Override
    @Transactional
    public Page<BoardDto> getAllBoardsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardRepository.findAllByBoardDeletedFalse(pageable);
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
    private boolean isValidTitle(String title) {
        return title.length() <= 100;
    }
    private boolean isValidContent(String content) {
        return content.length() <= 1000;
    }


}
