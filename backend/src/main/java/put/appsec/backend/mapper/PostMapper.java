package put.appsec.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.dto.post.PostDto;
import put.appsec.backend.entity.Post;
import put.appsec.backend.entity.PostReaction;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentMapper commentMapper;

    public PostDto toDto(Post post, String viewerUsername) {
        if (post == null) return null;

        int score = post.getReactionScore() != null ? post.getReactionScore() : 0;

        Short userReaction = null;
        if (viewerUsername != null && post.getPostReactions() != null) {
            userReaction = post.getPostReactions().stream()
                    .filter(r -> r.getUser().getUsername().equals(viewerUsername))
                    .map(PostReaction::getReactionValue)
                    .findFirst()
                    .orElse(null);
        }

        List<CommentDto> comments = null;
        if (post.getComments() != null) {
            comments = post.getComments().stream()
                    .map(comment -> commentMapper.toDto(comment, viewerUsername))
                    .collect(Collectors.toList());
        }

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contentUri(post.getContentUri())
                .uploadDate(post.getUploadDate())
                .isDeleted(post.getIsDeleted())
                .userReaction(userReaction)
                .reactionScore(score)
                .userId(post.getUser() != null ? post.getUser().getId() : null)
                .author(post.getUser() != null ? post.getUser().getUsername() : null)
                .comments(comments)
                .build();
    }
}