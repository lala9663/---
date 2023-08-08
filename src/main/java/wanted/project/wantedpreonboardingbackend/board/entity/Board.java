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
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private boolean boardDeleted = false;

    public void delete() {
        this.boardDeleted = true;
    }

    public boolean isDeleted() {
        return this.boardDeleted;
    }

}