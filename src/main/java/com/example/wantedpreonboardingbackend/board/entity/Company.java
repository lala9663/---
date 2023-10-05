package com.example.wantedpreonboardingbackend.board.entity;

import com.example.wantedpreonboardingbackend.global.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", columnDefinition = "BIGINT")
    private Long companyId;
    @Column(name = "company_name", columnDefinition = "VARCHAR(50)")
    private String companyName;
    @Column(name = "position", columnDefinition = "VARCHAR(30)")
    private String position;
    @Column(name = "reward", columnDefinition = "INT")
    private int reward;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @OneToMany(mappedBy = "company")
    @Column(name = "stack", columnDefinition = "VARCHAR(30)")
    private List<Stack> stacks = new ArrayList<>();
}
