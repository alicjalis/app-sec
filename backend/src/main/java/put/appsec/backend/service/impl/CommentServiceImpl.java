package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.dto.comment.CreateCommentRequestDto;
import put.appsec.backend.dto.comment.UpdateCommentRequestDto;
import put.appsec.backend.entity.Comment;
import put.appsec.backend.entity.Post;
import put.appsec.backend.entity.User;
import put.appsec.backend.exceptions.ResourceNotFoundException;
import put.appsec.backend.mapper.CommentMapper;
import put.appsec.backend.repository.CommentRepository;
import put.appsec.backend.repository.PostRepository;
import put.appsec.backend.repository.UserRepository;
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
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> getAllComments(String viewerUsername) {
        return List.of();
    }

    @Override
    public CommentDto getCommentById(Integer id, String viewerUsername) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found id: " + id));

        return commentMapper.toDto(comment, viewerUsername);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Integer postId, String viewerUsername) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found id: " + postId);
        }

        return commentRepository.findByPostId(postId).stream()
                .map(comment -> commentMapper.toDto(comment, viewerUsername))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getCommentsByUsername(String username, String viewerUsername) {
        if (!userRepository.existsByUsername(username)) {
            throw new ResourceNotFoundException("User not found: " + username);
        }

        return commentRepository.findByUserUsername(username).stream()
                .map(comment -> commentMapper.toDto(comment, viewerUsername))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto createComment(CreateCommentRequestDto request, String authorUsername) {
        User author = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setUser(author);
        comment.setPost(post);
        comment.setContent(request.getContent());
        comment.setUploadDate(LocalDateTime.now());
        comment.setIsDeleted(false);
        comment.setCommentReactions(new ArrayList<>());

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toDto(savedComment, authorUsername);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Integer id, UpdateCommentRequestDto request, String editorUsername) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getUser().getUsername().equals(editorUsername)) {
            throw new AccessDeniedException("You are not the author of this comment");
        }

        comment.setContent(request.getContent());

        Comment updatedComment = commentRepository.save(comment);

        return commentMapper.toDto(updatedComment, editorUsername);
    }

    @Override
    @Transactional
    public void deleteComment(Integer id, String requesterUsername) {
        User requester = userRepository.findByUsername(requesterUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");

        boolean isAdmin = requester.getAuthorities().contains(adminAuthority);

        if (isAdmin || comment.getUser().getUsername().equals(requesterUsername)) {
            comment.setIsDeleted(true);
            commentRepository.save(comment);
        } else {
            throw new AccessDeniedException("You do not have permission to delete this comment.");
        }
    }
}
