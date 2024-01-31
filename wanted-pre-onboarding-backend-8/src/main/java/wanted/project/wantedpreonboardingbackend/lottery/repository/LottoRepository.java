package wanted.project.wantedpreonboardingbackend.lottery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wanted.project.wantedpreonboardingbackend.lottery.entity.Lotto;

@Repository
public interface LottoRepository extends JpaRepository<Lotto, Long> {

    @Query("SELECT MAX(l.round) FROM Lotto l")
    Integer findMaxRound();
}
