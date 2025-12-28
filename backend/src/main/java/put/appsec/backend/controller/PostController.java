package put.appsec.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.post.CreatePostRequestDto;
import put.appsec.backend.dto.post.PostDto;
import put.appsec.backend.dto.post.UpdatePostRequestDto;
import put.appsec.backend.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@AuthenticationPrincipal UserDetails currentUser) {
        String viewer = (currentUser != null) ? currentUser.getUsername() : null;
        return ResponseEntity.ok(postService.getAllPosts(viewer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id, @AuthenticationPrincipal UserDetails currentUser) {
        String viewer = (currentUser != null) ? currentUser.getUsername() : null;
        return ResponseEntity.ok(postService.getPostById(id, viewer));
    }

    @GetMapping(params = "username")
    public ResponseEntity<List<PostDto>> getPostsByUsername(@RequestParam String username, @AuthenticationPrincipal UserDetails currentUser) {
        String viewer = (currentUser != null) ? currentUser.getUsername() : null;
        return ResponseEntity.ok(postService.getPostsByUsername(username, viewer));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid CreatePostRequestDto request, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        PostDto createdPost = postService.createPost(request, currentUser.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Integer id, @RequestBody @Valid UpdatePostRequestDto request, @AuthenticationPrincipal UserDetails currentUser) {
        PostDto updatedPost = postService.updatePost(id, request, currentUser.getUsername());
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id, @AuthenticationPrincipal UserDetails currentUser) {
        postService.deletePost(id, currentUser.getUsername());
        return ResponseEntity.noContent().build();
    }
}
