package put.appsec.backend.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Integer id;
    private String content;
    private LocalDateTime uploadDate;
    private Boolean isDeleted;

    private Short userReaction;
    private Integer reactionScore;

    private Integer userId;
    private String author;
    private Integer postId;
}
