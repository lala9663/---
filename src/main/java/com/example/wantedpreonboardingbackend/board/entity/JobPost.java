package com.example.wantedpreonboardingbackend.board.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_post_id", columnDefinition = "BIGINT")
    private Long companyPostId;
    @Column(name = "company_name", columnDefinition = "VARCHAR(50)")
    private String companyName;
    @Column(name = "position", columnDefinition = "VARCHAR(30)")
    private String position;
    @Column(name = "reward", columnDefinition = "INT")
    private int reward;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @ElementCollection
    private Set<String> stacks = new HashSet<>();
}
