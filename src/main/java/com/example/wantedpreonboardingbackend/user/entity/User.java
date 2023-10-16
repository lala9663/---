package com.example.wantedpreonboardingbackend.user.entity;

import com.example.wantedpreonboardingbackend.apply.entity.Apply;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Apply> posts = new ArrayList<>();
}