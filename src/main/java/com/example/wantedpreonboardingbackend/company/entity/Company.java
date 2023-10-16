package com.example.wantedpreonboardingbackend.company.entity;

import com.example.wantedpreonboardingbackend.post.entity.Post;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", columnDefinition = "BIGINT")
    private Long id;
    @Column(name = "company_name", columnDefinition = "VARCHAR(30)")
    private String companyName;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> postList;
}

