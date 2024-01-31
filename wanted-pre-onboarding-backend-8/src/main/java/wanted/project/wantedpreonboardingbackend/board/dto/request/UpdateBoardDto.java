package wanted.project.wantedpreonboardingbackend.board.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBoardDto {
    private String title;
    private String content;
}
