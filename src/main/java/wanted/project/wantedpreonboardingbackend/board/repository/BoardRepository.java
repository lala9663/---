package wanted.project.wantedpreonboardingbackend.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByWriter(Member writer);

}
