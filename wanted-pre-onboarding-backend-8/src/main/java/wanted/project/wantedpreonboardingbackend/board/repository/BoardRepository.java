package wanted.project.wantedpreonboardingbackend.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByBoardDeletedFalse();
    Board findByBoardIdAndBoardDeletedFalse(Long id);
    Page<Board> findAllByBoardDeletedFalse(Pageable pageable);



}
