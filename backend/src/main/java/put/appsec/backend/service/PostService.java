package put.appsec.backend.service;

import put.appsec.backend.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();
    PostDto getPostById(Integer id);
    List<PostDto> getPostsByUserId(Integer id);
    List<PostDto> getPostsByUsername(String username);
}
