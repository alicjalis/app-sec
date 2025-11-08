package put.appsec.backend.service;

import put.appsec.backend.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllComments();
    CommentDto getCommentById(Integer id);
    List<CommentDto> getAllCommentsByPostId(Integer id);
    List<CommentDto> getAllCommentsByUserId(Integer id);
    List<CommentDto> getAllCommentsByUsername(String username);
}
