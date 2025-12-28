package put.appsec.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.dto.post.PostDto;
import put.appsec.backend.enums.UserType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private LocalDateTime registrationDate;
    private UserType userType;

    private List<PostDto> posts;
    private List<CommentDto> comments;
}