package wanted.project.wantedpreonboardingbackend.lottery.entity;

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
public class LottoPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 회원 정보
    @ManyToOne
    @JoinColumn(name = "lotto_round_id")
    private LottoRound lottoRound; // 추첨 회차 정보
    @Column
    private int totalPrice; // 총 구매 가격
    @OneToMany(mappedBy = "lottoPurchase")
    private List<Lotto> lottos; // 구매한 로또 리스트

    @CreatedDate
    private LocalDateTime purchaseDate = LocalDateTime.now();
}
