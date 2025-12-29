package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.post.CreatePostRequestDto;
import put.appsec.backend.dto.post.PostDto;
import put.appsec.backend.dto.post.UpdatePostRequestDto;
import put.appsec.backend.entity.Post;
import put.appsec.backend.entity.User;
import put.appsec.backend.entity.UserActivity;
import put.appsec.backend.exceptions.ResourceNotFoundException;
import put.appsec.backend.mapper.PostMapper;
import put.appsec.backend.repository.PostRepository;
import put.appsec.backend.repository.UserActivityRepository;
import put.appsec.backend.repository.UserRepository;
import put.appsec.backend.service.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserActivityRepository userActivityRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public List<PostDto> getAllPosts(String viewerUsername) {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "uploadDate")).stream()
                .map(post -> postMapper.toDto(post, viewerUsername))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Integer id, String viewerUsername) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found id: " + id));
        return postMapper.toDto(post, viewerUsername);
    }

    @Override
    public List<PostDto> getPostsByUsername(String username, String viewerUsername) {
        if (!userRepository.existsByUsername(username)) {
            throw new ResourceNotFoundException("User not found: " + username);
        }
        return postRepository.findAllByUserUsername(username).stream()
                .map(post -> postMapper.toDto(post, viewerUsername))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createPost(CreatePostRequestDto request, String authorUsername) {
        User author = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContentUri(request.getContentUri());
        post.setUser(author);
        post.setUploadDate(LocalDateTime.now());
        post.setIsDeleted(false);
        post.setComments(new ArrayList<>());
        post.setPostReactions(new ArrayList<>());

        Post savedPost = postRepository.save(post);

        updateLastPostActivity(author);

        return postMapper.toDto(savedPost, authorUsername);
    }

    @Override
    public PostDto updatePost(Integer id, UpdatePostRequestDto request, String editorUsername) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getUser().getUsername().equals(editorUsername)) {
            throw new AccessDeniedException("You are not the author of this post");
        }

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            post.setTitle(request.getTitle());
        }
        if (request.getContentUri() != null && !request.getContentUri().isBlank()) {
            post.setContentUri(request.getContentUri());
        }

        Post updatedPost = postRepository.save(post);

        return postMapper.toDto(updatedPost, editorUsername);
    }

    @Override
    public void deletePost(Integer id, String requesterUsername) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        User requester = userRepository.findByUsername(requesterUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = requester.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("You do not have permission to delete this post");
        }

        post.setIsDeleted(true);
        postRepository.save(post);
    }

    private void updateLastPostActivity(User author) {
        UserActivity activity = userActivityRepository.findByUserId(author.getId()).orElse(new UserActivity());
        if (activity.getUser() == null) activity.setUser(author);

        activity.setLastPostDate(LocalDateTime.now());
        userActivityRepository.save(activity);
    }
}
