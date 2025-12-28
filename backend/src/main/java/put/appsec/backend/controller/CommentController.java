package put.appsec.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.dto.comment.CreateCommentRequestDto;
import put.appsec.backend.dto.comment.UpdateCommentRequestDto;
import put.appsec.backend.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> getAllComments(@RequestParam(value = "username", defaultValue = "") String viewerUsername) {
        return ResponseEntity.ok(commentService.getAllComments(viewerUsername));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer id, @AuthenticationPrincipal UserDetails currentUser){
        String username = (currentUser != null) ? currentUser.getUsername() : null;
        return ResponseEntity.ok(commentService.getCommentById(id, username));
    }

    @GetMapping(params = "postId")
        public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@RequestParam Integer postId, @AuthenticationPrincipal UserDetails currentUser){
        String username = (currentUser != null) ? currentUser.getUsername() : null;
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId, username));
    }

    @GetMapping(params = "username")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUsername(@RequestParam String username, @AuthenticationPrincipal UserDetails currentUser){
        String viewer = (currentUser != null) ? currentUser.getUsername() : null;
        return ResponseEntity.ok(commentService.getCommentsByUsername(username, viewer));
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody @Valid CreateCommentRequestDto request, @AuthenticationPrincipal UserDetails currentUser){
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CommentDto createdComment = commentService.createComment(request, currentUser.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer id, @RequestBody @Valid UpdateCommentRequestDto request, @AuthenticationPrincipal UserDetails currentUser){
        CommentDto updatedComment = commentService.updateComment(id, request, currentUser.getUsername());
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id, @AuthenticationPrincipal UserDetails currentUser) {
        commentService.deleteComment(id, currentUser.getUsername());
        return ResponseEntity.noContent().build();
    }
}
