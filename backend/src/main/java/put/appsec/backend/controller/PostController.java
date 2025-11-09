package put.appsec.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.PostDto;
import put.appsec.backend.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id) {
        PostDto post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Integer id) {
        List<PostDto> posts = postService.getPostsByUserId(id);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<List<PostDto>> getPostsByUsername(@PathVariable String username) {
        List<PostDto> posts = postService.getPostsByUsername(username);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto post = postService.createPost(postDto);
        if (post == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping("/update")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto) {
        PostDto post = postService.updatePost(postDto);
        if (post == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(post);
    }
}
