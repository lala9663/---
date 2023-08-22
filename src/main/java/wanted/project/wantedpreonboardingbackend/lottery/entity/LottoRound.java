package wanted.project.wantedpreonboardingbackend.lottery.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
public class LottoRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lottoRoundId;

    @Column
    private LocalDate roundDate; // 추첨 회차 날짜
    @Column
    private int winningBall1; // 1등 번호 1
    @Column
    private int winningBall2; // 1등 번호 2
    @Column
    private int winningBall3; // 1등 번호 3
    @Column
    private int winningBall4; // 1등 번호 4
    @Column
    private int winningBall5; // 1등 번호 5
    @Column
    private int winningBall6; // 1등 번호 6
    @Column
    private int firstPrizeAmount; // 1등 당첨금
    @Column
    private int secondPrizeAmount; // 2등 당첨금
    @Column
    private int thirdPrizeAmount; // 3등 당첨금
    @Column
    private int fourthPrizeAmount; // 4등 당첨금
    @Column
    private int fifthPrizeAmount; // 5등 당첨금
    @OneToMany(mappedBy = "lottoRound")
    private List<LottoPurchase> lottoPurchases;

}
