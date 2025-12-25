package put.appsec.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReactionDto {
    private Integer targetId;
    private String username;
    private Short reaction;
}
