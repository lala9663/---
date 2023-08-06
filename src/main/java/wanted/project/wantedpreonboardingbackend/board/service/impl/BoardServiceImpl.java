package wanted.project.wantedpreonboardingbackend.board.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.project.wantedpreonboardingbackend.board.dto.request.CreateBoardDto;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.board.repository.BoardRepository;
import wanted.project.wantedpreonboardingbackend.board.service.BoardService;
import wanted.project.wantedpreonboardingbackend.member.dto.response.Response;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;
import wanted.project.wantedpreonboardingbackend.member.exception.MemberException;
import wanted.project.wantedpreonboardingbackend.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public void create(CreateBoardDto create, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("유효하지 않은 작성자 Id입니다."));

        if (create.getTitle().length() > 30) {
            throw new IllegalArgumentException("게시글 제목은 최대 100자까지 입력 가능합니다.");
        }

        if (create.getContent().length() > 1000) {
            throw new IllegalArgumentException("게시글 내용은 최대 1000자까지 입력 가능합니다.");
        }

        Board board = Board.builder()
                .title(create.getTitle())
                .content(create.getContent())
                .member(member)
                .build();
        boardRepository.save(board);
    }


}
