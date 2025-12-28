package put.appsec.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReactionDto {
    @NotNull
    private Integer targetId;
    private String username;

    @NotNull
    @Min(value = -1, message = "Reaction must be -1, 0, or 1")
    @Max(value = 1, message = "Reaction must be -1, 0, or 1")
    private Short reaction;
}
