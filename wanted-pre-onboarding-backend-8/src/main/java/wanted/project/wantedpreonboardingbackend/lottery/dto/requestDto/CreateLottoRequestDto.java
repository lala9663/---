package wanted.project.wantedpreonboardingbackend.lottery.dto.requestDto;

import lombok.*;
import wanted.project.wantedpreonboardingbackend.lottery.entity.Lotto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateLottoRequestDto {
    private Long memberId;
    private int round;
    private int ball1;
    private int ball2;
    private int ball3;
    private int ball4;
    private int ball5;
    private int ball6;
    private int price;
    private int quantity;
    public Lotto toEntity() {
        return Lotto.builder()
                .round(round)
                .ball1(ball1)
                .ball2(ball2)
                .ball3(ball3)
                .ball4(ball4)
                .ball5(ball5)
                .ball6(ball6)
                .price(price)
                .build();
    }
}
