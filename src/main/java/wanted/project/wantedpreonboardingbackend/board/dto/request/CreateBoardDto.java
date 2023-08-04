package wanted.project.wantedpreonboardingbackend.board.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBoardDto {
    private String title;
    private String content;
    private Long writer;
}
