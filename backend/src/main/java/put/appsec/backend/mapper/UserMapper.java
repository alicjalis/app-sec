package put.appsec.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.dto.post.PostDto;
import put.appsec.backend.dto.user.UserDto;
import put.appsec.backend.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    public UserDto toDto(User user, String viewerUsername) {
        if (user == null) return null;

        List<PostDto> posts = null;
        if (user.getPosts() != null) {
            posts = user.getPosts().stream()
                    .map(post -> postMapper.toDto(post, viewerUsername))
                    .collect(Collectors.toList());
        }

        List<CommentDto> comments = null;
        if (user.getComments() != null) {
            comments = user.getComments().stream()
                    .map(comment -> commentMapper.toDto(comment, viewerUsername))
                    .collect(Collectors.toList());
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .registrationDate(user.getRegistrationDate())
                .userType(user.getUserType())
                .posts(posts)
                .comments(comments)
                .build();
    }
}