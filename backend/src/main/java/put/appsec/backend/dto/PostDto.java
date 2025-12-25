package put.appsec.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import put.appsec.backend.entity.Post;
import put.appsec.backend.entity.PostReaction;
import put.appsec.backend.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Long.sum;

@Data
@NoArgsConstructor
public class PostDto {
    private Integer id;
    private String title;
    private String contentUri;
    private Short userReaction;
    private Integer reactionScore;
    private LocalDateTime uploadDate;
    private Boolean isDeleted;

    private Integer userId;
    private String author;
    private List<CommentDto> comments;

    public PostDto(Post post, String viewerUsername) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contentUri = post.getContentUri();
        this.uploadDate = post.getUploadDate();
        this.isDeleted = post.getIsDeleted();

        Optional<PostReaction> userReaction = post.getPostReactions().stream()
                .filter(reaction -> reaction.getUser().getUsername().equals(viewerUsername))
                .findFirst();
        this.userReaction = userReaction.map(PostReaction::getReactionValue).orElse(null);

        this.reactionScore = post.getPostReactions().stream().mapToInt(PostReaction::getReactionValue).sum();
        this.userId = post.getUser().getId();
        this.author = post.getUser().getUsername();

        List<CommentDto> comments = new ArrayList<>();
        post.getComments().forEach(comment -> comments.add(new CommentDto(comment, viewerUsername)));
        this.comments = comments;
    }

    public PostDto(Post post) {
        this(post, null);
    }

    public Post toEntity() {
        Post entity = new Post();
        entity.setId(this.id);
        entity.setTitle(this.title);
        entity.setContentUri(this.contentUri);
        entity.setUploadDate(this.uploadDate);
        entity.setIsDeleted(this.isDeleted);

        User user = new User();
        user.setId(this.userId);
        entity.setUser(user);

        entity.setComments(comments.stream().map(CommentDto::toEntity).collect(Collectors.toList()));

        return entity;
    }
}
