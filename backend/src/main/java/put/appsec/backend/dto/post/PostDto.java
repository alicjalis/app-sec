package put.appsec.backend.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.entity.PostReaction;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer id;
    private String title;
    private String contentUri;
    private LocalDateTime uploadDate;
    private Boolean isDeleted;

    private Short userReaction;
    private Integer reactionScore;
    private Integer userId;
    private String author;

    private List<CommentDto> comments;
    private List<PostReaction> reactions;
}