package put.appsec.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import put.appsec.backend.entity.Post;
import put.appsec.backend.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostDto {
    private Integer id;
    private String title;
    private String content_uri;
    private Integer likesNumber;
    private Integer dislikesNumber;
    private LocalDateTime uploadDate;
    private Boolean isDeleted;

    private Integer userId;
    private List<CommentDto> comments;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content_uri = post.getContentUri();
        this.likesNumber = post.getLikesNumber();
        this.dislikesNumber = post.getDislikesNumber();
        this.uploadDate = post.getUploadDate();
        this.isDeleted = post.getIsDeleted();

        this.userId = post.getUser().getId();

        List<CommentDto> comments = new ArrayList<>();
        post.getComments().forEach(comment -> comments.add(new CommentDto(comment)));
        this.comments = comments;
    }

    public Post toEntity() {
        Post entity = new Post();
        entity.setId(this.id);
        entity.setTitle(this.title);
        entity.setContentUri(this.content_uri);
        entity.setLikesNumber(this.likesNumber);
        entity.setDislikesNumber(this.dislikesNumber);
        entity.setUploadDate(this.uploadDate);
        entity.setIsDeleted(this.isDeleted);

        entity.setComments(comments.stream().map(CommentDto::toEntity).collect(Collectors.toList()));

        return entity;
    }
}
