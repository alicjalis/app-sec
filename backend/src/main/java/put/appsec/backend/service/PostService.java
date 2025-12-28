package put.appsec.backend.service;

import put.appsec.backend.dto.post.CreatePostRequestDto;
import put.appsec.backend.dto.post.PostDto;
import put.appsec.backend.dto.post.UpdatePostRequestDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts(String viewerUsername);
    PostDto getPostById(Integer id, String viewerUsername);
    List<PostDto> getPostsByUsername(String username, String viewerUsername);
    PostDto createPost(CreatePostRequestDto request, String authorUsername);
    PostDto updatePost(Integer id, UpdatePostRequestDto request, String editorUsername);
    void deletePost(Integer id, String requesterUsername);
}
