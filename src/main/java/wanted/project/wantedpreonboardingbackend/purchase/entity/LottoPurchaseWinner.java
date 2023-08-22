package wanted.project.wantedpreonboardingbackend.purchase.entity;

import lombok.*;
import wanted.project.wantedpreonboardingbackend.winner.entity.Winner;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "lotto_purchase_winner") // 브릿지 테이블 이름 지정
public class LottoPurchaseWinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lotto_purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Winner winner;

}
