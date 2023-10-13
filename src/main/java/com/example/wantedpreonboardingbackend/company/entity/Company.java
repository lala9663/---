package com.example.wantedpreonboardingbackend.company.entity;

import lombok.*;

import javax.persistence.*;

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
    private Long companyId;
    @Column(name = "name", columnDefinition = "VARCHAR(30)")
    private String name;
    // 다른 회사 정보 필드들 추가
}

