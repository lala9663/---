package com.example.wantedpreonboardingbackend.user.entity;

import com.example.wantedpreonboardingbackend.post.entity.Post;
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
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "id", targetEntity = Post.class,
            cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> postList = new ArrayList<>();
}