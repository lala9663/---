package com.example.wantedpreonboardingbackend.board.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_post_id", columnDefinition = "BIGINT")
    private Long companyPostId;
    @Column(name = "company", columnDefinition = "VARCHAR(30)")
    private String companyName;
    @Column(name = "position", columnDefinition = "VARCHAR(30)")
    private String position;
    @Column(name = "reward", columnDefinition = "INT")
    private int reward;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> stacks = new HashSet<>();


    public void updateFrom(JobPost other) {
        if (other.getCompanyName() != null) {
            this.companyName = other.getCompanyName();
        }
        if (other.getPosition() != null) {
            this.position = other.getPosition();
        }
        if (other.getReward() != 0) {
            this.reward = other.getReward();
        }
        if (other.getContent() != null) {
            this.content = other.getContent();
        }
        if (other.getStacks() != null) {
            this.stacks = other.getStacks();
        }
    }
}
