package put.appsec.backend.mapper;

import org.springframework.stereotype.Component;
import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.entity.Comment;
import put.appsec.backend.entity.CommentReaction;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment comment, String viewerUsername) {
        if (comment == null) return null;

        int score = 0;
        Short myReaction = null;
        String finalContent = comment.getContent();

        if (Boolean.TRUE.equals(comment.getIsDeleted())) {
            finalContent = "[Comment Deleted]";
        }

        if (comment.getCommentReactions() != null) {
            score = comment.getCommentReactions().stream()
                    .mapToInt(CommentReaction::getReactionValue)
                    .sum();

            if (viewerUsername != null) {
                myReaction = comment.getCommentReactions().stream()
                        .filter(r -> r.getUser().getUsername().equals(viewerUsername))
                        .map(CommentReaction::getReactionValue)
                        .findFirst()
                        .orElse(null);
            }
        }

        return CommentDto.builder()
                .id(comment.getId())
                .content(finalContent)
                .uploadDate(comment.getUploadDate())
                .isDeleted(comment.getIsDeleted())
                .userReaction(myReaction)
                .reactionScore(score)
                .userId(comment.getUser().getId())
                .author(comment.getUser().getUsername())
                .postId(comment.getPost().getId())
                .build();
    }
}