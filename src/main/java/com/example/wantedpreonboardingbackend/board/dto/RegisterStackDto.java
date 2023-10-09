package com.example.wantedpreonboardingbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStackDto {
    @NotBlank(message = "기술 이름은 필수 항목입니다.")
    private String stackName;

}
