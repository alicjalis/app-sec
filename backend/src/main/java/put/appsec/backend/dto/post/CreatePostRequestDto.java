package put.appsec.backend.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String contentUri;

    @NotBlank
    private String recaptchaToken;
}