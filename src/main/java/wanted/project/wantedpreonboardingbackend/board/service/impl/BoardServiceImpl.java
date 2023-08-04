package wanted.project.wantedpreonboardingbackend.board.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.board.repository.BoardRepository;
import wanted.project.wantedpreonboardingbackend.board.service.BoardService;
import wanted.project.wantedpreonboardingbackend.member.dto.response.Response;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;
import wanted.project.wantedpreonboardingbackend.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final Response response;
    @Override
    @Transactional
    public ResponseEntity<?> create(CreateBoardDto create) {

        Member writer = memberRepository.findById(create.getWriter())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 작성자 Id입니다."));

        Board board = Board.builder()
                .title(create.getTitle())
                .content(create.getContent())
                .writer(writer)
                .build();
        boardRepository.save(board);

        return response.success("게시글이 작성되었습니다.");
    }
}
