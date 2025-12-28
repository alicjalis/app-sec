package put.appsec.backend.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequestDto {
    @NotNull(message = "Post ID is required")
    private Integer postId;

    @NotBlank(message = "Content cannot be empty")
    private String content;
}