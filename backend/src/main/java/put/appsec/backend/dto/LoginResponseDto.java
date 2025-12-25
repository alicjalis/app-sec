package put.appsec.backend.dto;

import lombok.Getter;
import lombok.Setter;
import put.appsec.backend.enums.UserType;

@Setter
@Getter
public class LoginResponseDto {
    private String token;
    private long expiresIn;
    private String username;
    private UserType userType;
}