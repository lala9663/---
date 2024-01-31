package wanted.project.wantedpreonboardingbackend.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.project.wantedpreonboardingbackend.purchase.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
