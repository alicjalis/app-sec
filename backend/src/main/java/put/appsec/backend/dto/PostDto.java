package put.appsec.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import put.appsec.backend.entity.Post;
import put.appsec.backend.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostDto {
    private Integer id;
    private String title;
    private String contentUri;
    private Integer likesNumber;
    private Integer dislikesNumber;
    private LocalDateTime uploadDate;
    private Boolean isDeleted;

    private Integer userId;
    private String author;
    private List<CommentDto> comments;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contentUri = post.getContentUri();
        this.likesNumber = post.getLikesNumber();
        this.dislikesNumber = post.getDislikesNumber();
        this.uploadDate = post.getUploadDate();
        this.isDeleted = post.getIsDeleted();

        this.userId = post.getUser().getId();
        this.author = post.getUser().getUsername();

        List<CommentDto> comments = new ArrayList<>();
        post.getComments().forEach(comment -> comments.add(new CommentDto(comment)));
        this.comments = comments;
    }

    public Post toEntity() {
        Post entity = new Post();
        entity.setId(this.id);
        entity.setTitle(this.title);
        entity.setContentUri(this.contentUri);
        entity.setLikesNumber(this.likesNumber);
        entity.setDislikesNumber(this.dislikesNumber);
        entity.setUploadDate(this.uploadDate);
        entity.setIsDeleted(this.isDeleted);

        User user = new User();
        user.setId(this.userId);
        entity.setUser(user);

        entity.setComments(comments.stream().map(CommentDto::toEntity).collect(Collectors.toList()));

        return entity;
    }
}
