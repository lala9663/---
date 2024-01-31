package wanted.project.wantedpreonboardingbackend.board.dto.request;

import lombok.*;
import wanted.project.wantedpreonboardingbackend.board.entity.Board;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardDto {
    private String title;
    private String content;

    public Board toEntity(Member member) {
        return Board.builder()
                .member(member)
                .title(title)
                .content(content)
                .build();
    }

}
