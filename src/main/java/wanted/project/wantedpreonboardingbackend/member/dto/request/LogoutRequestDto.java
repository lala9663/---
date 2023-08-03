package wanted.project.wantedpreonboardingbackend.member.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogoutRequestDto {
    private String accessToken;
    private String refreshToken;
}