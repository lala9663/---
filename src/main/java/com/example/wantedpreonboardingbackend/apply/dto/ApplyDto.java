package com.example.wantedpreonboardingbackend.apply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyDto {
    @NotNull(message = "입력해주세요")
    private Long postId;
    @NotNull(message = "필수 입력")
    private Long memberId;
}
