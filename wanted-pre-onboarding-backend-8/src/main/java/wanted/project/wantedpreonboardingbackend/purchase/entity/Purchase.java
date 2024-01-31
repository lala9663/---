package wanted.project.wantedpreonboardingbackend.purchase.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import wanted.project.wantedpreonboardingbackend.lottery.entity.Lotto;
import wanted.project.wantedpreonboardingbackend.winner.entity.Winner;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
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
    private Member member;
    @ManyToOne
    @JoinColumn(name = "lotto_id")
    private Lotto lotto;
    @OneToMany(mappedBy = "purchase")
    private List<Winner> lottoWinners = new ArrayList<>();
    @CreatedDate
    private LocalDateTime purchaseDate = LocalDateTime.now();
}
