package wanted.project.wantedpreonboardingbackend.member.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponseDto {
    private Long memberId;
    private String email;
}
