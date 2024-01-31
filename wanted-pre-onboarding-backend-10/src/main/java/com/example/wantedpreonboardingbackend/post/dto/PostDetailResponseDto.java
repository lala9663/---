package com.example.wantedpreonboardingbackend.post.dto;

import com.example.wantedpreonboardingbackend.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponseDto {
    private Long postId;
    private String companyName;
    private String position;
    private int reward;
    private String content;
    private Set<String> stacks;
    private List<String> otherList;

    public static PostDetailResponseDto fromEntity(Post post) {
        List<String> list = post.getCompany().getPostList().stream()
                .filter(other -> !other.getPosition().equals(post.getPosition()))
                .map(Post::getPosition)
                .collect(Collectors.toList());

        return builder()
                .postId(post.getId())
                .companyName(post.getCompany().getCompanyName())
                .reward(post.getReward())
                .content(post.getContent())
                .stacks(post.getStacks())
                .otherList(list)
                .build();
    }
}
