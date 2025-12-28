package put.appsec.backend.service;

import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.dto.comment.CreateCommentRequestDto;
import put.appsec.backend.dto.comment.UpdateCommentRequestDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllComments(String viewerUsername);
    CommentDto getCommentById(Integer id, String viewerUsername);
    List<CommentDto> getCommentsByPostId(Integer id, String viewerUsername);
    List<CommentDto> getCommentsByUsername(String username, String viewerUsername);
    CommentDto createComment(CreateCommentRequestDto request, String viewerUsername);
    CommentDto updateComment(Integer id, UpdateCommentRequestDto request, String editorUsername);
    void deleteComment(Integer id,  String requesterUsername);
}
