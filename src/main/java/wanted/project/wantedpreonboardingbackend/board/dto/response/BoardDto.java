package wanted.project.wantedpreonboardingbackend.board.dto.response;

import lombok.*;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private Long boardId;
    private String title;
    private String content;

    public BoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }


}
