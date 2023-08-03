package wanted.project.wantedpreonboardingbackend.member.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String email;
    private String accessToken;
    private String refreshToken;

}
