package put.appsec.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    private String username;
    private String email;
    private String password;
}
