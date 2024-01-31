package wanted.project.wantedpreonboardingbackend.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequestDto {
    private String email;
    private String phone;
}
