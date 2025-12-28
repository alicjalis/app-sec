package put.appsec.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import put.appsec.backend.security.validation.StrongPassword;

@Data
public class RegisterRequestDto {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @StrongPassword
    @NotBlank(message = "Password is required")
    private String password;
}