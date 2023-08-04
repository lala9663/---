package wanted.project.wantedpreonboardingbackend.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);


    boolean existsByEmail(String email);
}
