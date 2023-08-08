package wanted.project.wantedpreonboardingbackend.member.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class LogoutRequestDto {
    private String accessToken;
    public LogoutRequestDto(String accessToken) {

    }
}