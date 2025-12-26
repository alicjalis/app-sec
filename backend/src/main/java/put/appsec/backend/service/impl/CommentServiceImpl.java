package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.CommentDto;
import put.appsec.backend.entity.Comment;
import put.appsec.backend.repository.CommentRepository;
import put.appsec.backend.service.CommentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> getAllComments(String viewerUsername) {
        return commentRepository.findAll().stream().map(comment -> new CommentDto(comment, viewerUsername)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Integer id, String viewerUsername) {
        return commentRepository.findById(id).map(comment -> new CommentDto(comment, viewerUsername)).orElse(null);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Integer id, String viewerUsername) {
        return commentRepository.findAllByPostId(id).stream().map(comment -> new CommentDto(comment, viewerUsername)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentsByUsername(String username, String viewerUsername) {
        return commentRepository.findAllByUserUsername(username).stream().map(comment -> new CommentDto(comment, viewerUsername)).collect(Collectors.toList());
    }

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        commentDto.setId(null);
        commentDto.setUploadDate(LocalDateTime.now());
        commentDto.setIsDeleted(false);
        Comment entity = commentDto.toEntity();
        entity.setCommentReactions(new ArrayList<>());
        Comment savedEntity = commentRepository.save(entity);
        return new CommentDto(savedEntity);
    }

    @Override
    public CommentDto updateComment(CommentDto updatedCommentDto) {
        CommentDto commentToEdit = getCommentById(updatedCommentDto.getId(), null);
        Comment savedEntity;
        if (commentToEdit == null) {
            return null;
        }
        commentToEdit.setContent(updatedCommentDto.getContent()!=null?updatedCommentDto.getContent():commentToEdit.getContent());
        commentToEdit.setIsDeleted(updatedCommentDto.getIsDeleted());

        savedEntity = commentRepository.save(commentToEdit.toEntity());
        return new CommentDto(savedEntity);
    }
}
