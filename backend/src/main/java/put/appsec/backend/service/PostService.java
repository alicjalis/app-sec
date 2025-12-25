package put.appsec.backend.service;

import put.appsec.backend.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts(String viewerUsername);
    PostDto getPostById(Integer id, String viewerUsername);
    List<PostDto> getPostsByUsername(String username, String viewerUsername);
    PostDto createPost(PostDto postDto);
    PostDto updatePost(PostDto postDto);
}
