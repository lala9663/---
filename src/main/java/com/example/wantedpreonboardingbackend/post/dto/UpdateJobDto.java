package com.example.wantedpreonboardingbackend.post.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateJobDto {

    @NotBlank(message = "회사 이름")
    @Size(max = 50, message = "최대 50자")
    private String companyName;

    @NotBlank(message = "포지션")
    @Size(max = 30, message = "최대 30자")
    private String position;

    private int reward;

    @NotBlank(message = "채용 내용")
    @Size(max = 3000, message = "최대 3000자")
    private String content;

    @NotBlank(message = "사용 기술")
    private Set<String> stacks;

}
