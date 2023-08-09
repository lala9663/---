package wanted.project.wantedpreonboardingbackend.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDto {
    private String email;
    private String currentPassword;
    private String newPassword;

}
