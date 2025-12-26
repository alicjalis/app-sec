package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.PostDto;
import put.appsec.backend.entity.Post;
import put.appsec.backend.repository.PostRepository;
import put.appsec.backend.service.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public List<PostDto> getAllPosts( String viewerUsername ) {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "uploadDate")).stream().map(post -> new PostDto(post, viewerUsername)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Integer id, String viewerUsername) {
        return postRepository.findById(id).map(post -> new PostDto(post, viewerUsername)).orElse(null);
    }


    @Override
    public List<PostDto> getPostsByUsername(String username, String viewerUsername) {
        return postRepository.findAllByUserUsername(username).stream().map(post -> new PostDto(post, viewerUsername)).collect(Collectors.toList());
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        postDto.setId(null);
        postDto.setComments(new ArrayList<>());
        postDto.setUploadDate(LocalDateTime.now());
        postDto.setIsDeleted(false);
        Post entity = postDto.toEntity();
        entity.setPostReactions(new ArrayList<>());
        Post savedEntity = postRepository.save(entity);
        return new PostDto(savedEntity);
    }

    @Override
    public PostDto updatePost(PostDto updatedPostDto) {
        PostDto postToEditDto = getPostById(updatedPostDto.getId(), null);
        Post savedEntity;
        if (postToEditDto == null) {
            return null;
        }
        postToEditDto.setTitle(updatedPostDto.getTitle()!=null?updatedPostDto.getTitle():postToEditDto.getTitle());
        postToEditDto.setContentUri(updatedPostDto.getContentUri()!=null?updatedPostDto.getContentUri():postToEditDto.getContentUri());
        postToEditDto.setIsDeleted(updatedPostDto.getIsDeleted()!=null?updatedPostDto.getIsDeleted():postToEditDto.getIsDeleted());

        savedEntity = postRepository.save(postToEditDto.toEntity());
        return new PostDto(savedEntity);
    }
}
