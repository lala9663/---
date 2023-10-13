package com.example.wantedpreonboardingbackend.post.entity;

import com.example.wantedpreonboardingbackend.apply.entity.Apply;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @OneToMany(mappedBy = "jobPost")
    private List<Apply> userJobPosts = new ArrayList<>();

    public JobPost(long companyPostId, String companyName, String position, int reward, String content, Set<String> stacks) {
        this.companyPostId = companyPostId;
        this.companyName = companyName;
        this.position = position;
        this.reward = reward;
        this.stacks = stacks;
    }

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
