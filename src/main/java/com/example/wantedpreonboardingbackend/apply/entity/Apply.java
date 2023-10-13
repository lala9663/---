package com.example.wantedpreonboardingbackend.apply.entity;

import com.example.wantedpreonboardingbackend.post.entity.JobPost;
import com.example.wantedpreonboardingbackend.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Apply {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_post_id")
    private JobPost jobPost;

}