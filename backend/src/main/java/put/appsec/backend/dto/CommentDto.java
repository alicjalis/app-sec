package put.appsec.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import put.appsec.backend.entity.Comment;
import put.appsec.backend.entity.Post;
import put.appsec.backend.entity.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private String content;
    private Integer likesNumber;
    private Integer dislikesNumber;
    private LocalDateTime uploadDate;
    private Boolean isDeleted;

    private Integer userId;
    private Integer postId;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.likesNumber = comment.getLikesNumber();
        this.dislikesNumber = comment.getDislikesNumber();
        this.uploadDate = comment.getUploadDate();
        this.isDeleted = comment.getIsDeleted();

        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
    }

    public Comment toEntity() {
        Comment entity = new Comment();
        entity.setId(this.id);
        entity.setContent(this.content);
        entity.setLikesNumber(this.likesNumber);
        entity.setDislikesNumber(this.dislikesNumber);
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

