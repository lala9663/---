package com.example.wantedpreonboardingbackend.post.dto;

import com.example.wantedpreonboardingbackend.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String companyName;
    private String position;
    private int reward;
    private String content;
    private Set<String> stacks;

    public static PostResponseDto fromEntity(Post post) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .companyName(post.getCompany().getCompanyName())
                .position(post.getPosition())
                .reward(post.getReward())
                .content(post.getContent())
                .stacks(post.getStacks())
                .build();
    }
}
