package put.appsec.backend.dto.auth;

import lombok.*;
import put.appsec.backend.enums.UserType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private long expiresIn;
    private String username;
    private UserType userType;
}