package put.appsec.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import put.appsec.backend.entity.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
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

    public CommentDto(Comment comment, String viewerUsername) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.uploadDate = comment.getUploadDate();
        this.isDeleted = comment.getIsDeleted();

        Optional<CommentReaction> userReaction = comment.getCommentReactions().stream()
                .filter(reaction -> reaction.getUser().getUsername().equals(viewerUsername))
                .findFirst();
        this.userReaction = userReaction.map(CommentReaction::getReactionValue).orElse(null);

        this.reactionScore = comment.getCommentReactions().stream().mapToInt(CommentReaction::getReactionValue).sum();
        this.userId = comment.getUser().getId();
        this.author = comment.getUser().getUsername();
        this.postId = comment.getPost().getId();
    }

    public CommentDto(Comment comment) {
        this(comment, null);
    }

    public Comment toEntity() {
        Comment entity = new Comment();
        entity.setId(this.id);
        entity.setContent(this.content);
        entity.setUploadDate(this.uploadDate);
        entity.setIsDeleted(this.isDeleted);

        Post post = new Post();
        post.setId(this.postId);
        entity.setPost(post);

        User user = new User();
        user.setId(this.userId);
        entity.setUser(user);

        return entity;
    }
}

