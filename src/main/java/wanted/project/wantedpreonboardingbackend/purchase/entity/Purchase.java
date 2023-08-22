package wanted.project.wantedpreonboardingbackend.purchase.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 회원 정보

    @OneToMany(mappedBy = "purchase")
    private List<LottoPurchaseWinner> lottoPurchaseWinners; // 브릿지 테이블과 연결

    @Column
    private int totalPrice; // 총 구매 가격

    @CreatedDate
    private LocalDateTime purchaseDate = LocalDateTime.now();
}
