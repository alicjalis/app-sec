package put.appsec.backend.dto.user;

import lombok.Data;
import put.appsec.backend.enums.UserType;

@Data
public class UpdateUserRequestDto {
    private String username;
    private String email;

    private UserType userType;
}
