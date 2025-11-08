package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.PostDto;
import put.appsec.backend.repository.PostRepository;
import put.appsec.backend.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(PostDto::new).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Integer id) {
        return postRepository.findById(id).map(PostDto::new).orElse(null);
    }

    @Override
    public List<PostDto> getPostsByUserId(Integer id) {
        return postRepository.findAllByUserId(id).stream().map(PostDto::new).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUsername(String username) {
        return postRepository.findAllByUserUsername(username).stream().map(PostDto::new).collect(Collectors.toList());
    }
}
