package put.appsec.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewPasswordRequestDto {
    @NotBlank(message = "New password is required")
    @Size(min = 7)
    private String newPassword;
}
