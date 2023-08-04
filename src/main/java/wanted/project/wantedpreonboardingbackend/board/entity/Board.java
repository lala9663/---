package wanted.project.wantedpreonboardingbackend.board.entity;

import lombok.*;
import wanted.project.wantedpreonboardingbackend.member.entity.BaseTime;
import wanted.project.wantedpreonboardingbackend.member.entity.Member;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;


}