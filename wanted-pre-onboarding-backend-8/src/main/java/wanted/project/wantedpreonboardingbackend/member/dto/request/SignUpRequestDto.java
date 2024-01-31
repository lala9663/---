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
    private String phone;
    private String name;
    private int age;
}
