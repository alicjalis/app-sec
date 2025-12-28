package put.appsec.backend.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCommentRequestDto {
    @NotBlank
    private String content;
}