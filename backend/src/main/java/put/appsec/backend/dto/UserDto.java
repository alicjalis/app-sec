package put.appsec.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import put.appsec.backend.entity.User;
import put.appsec.backend.enums.UserType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private LocalDateTime registrationDate;
    private UserType userType;
    private List<PostDto> posts;
    private List<CommentDto> comments;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.userType = user.getUserType();
        this.registrationDate = user.getRegistrationDate();
        this.userType = user.getUserType();

        this.posts = user.getPosts().stream().map(PostDto::new).collect(Collectors.toList());
        this.comments = user.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
    }

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setUserType(this.userType);
        user.setRegistrationDate(this.registrationDate);

        user.setPosts(this.posts.stream().map(PostDto::toEntity).collect(Collectors.toList()));
        user.setComments(this.comments.stream().map(CommentDto::toEntity).collect(Collectors.toList()));

        return user;
    }

}
