package wanted.project.wantedpreonboardingbackend.member.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {
    private String email;
    private String password;
}
