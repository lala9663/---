package com.example.wantedpreonboardingbackend.post.dto;

import com.example.wantedpreonboardingbackend.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPostDto {
    @NotBlank(message = "포지션")
    @Size(max = 30, message = "최대 30자")
    private String position;
    private int reward;
    @NotBlank(message = "채용 내용")
    @Size(max = 3000, message = "최대 3000자")
    private String content;
    @NotBlank(message = "사용 기술")
    private Set<String> stacks;

    public Post toEntity() {
        return Post.builder()
                .position(this.position)
                .reward(this.reward)
                .content(this.content)
                .stacks(this.stacks)
                .build();
    }
}
