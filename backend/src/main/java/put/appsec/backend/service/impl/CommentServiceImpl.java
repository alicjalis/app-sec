package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.CommentDto;
import put.appsec.backend.repository.CommentRepository;
import put.appsec.backend.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream().map(CommentDto::new).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Integer id) {
        return commentRepository.findById(id).map(CommentDto::new).orElse(null);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Integer id) {
        return commentRepository.findAllByPostId(id).stream().map(CommentDto::new).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentsByUserId(Integer id) {
        return commentRepository.findAllByUserId(id).stream().map(CommentDto::new).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentsByUsername(String username) {
        return commentRepository.findAllByUserUsername(username).stream().map(CommentDto::new).collect(Collectors.toList());
    }
}
