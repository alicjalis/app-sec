package put.appsec.backend.service;

import put.appsec.backend.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllComments(String viewerUsername);
    CommentDto getCommentById(Integer id, String viewerUsername);
    List<CommentDto> getAllCommentsByPostId(Integer id, String viewerUsername);
    List<CommentDto> getAllCommentsByUsername(String username, String viewerUsername);
    CommentDto createComment(CommentDto commentDto);
    CommentDto updateComment(CommentDto commentDto);
}
