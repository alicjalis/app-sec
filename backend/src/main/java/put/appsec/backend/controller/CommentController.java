package put.appsec.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.CommentDto;
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

    @GetMapping("/id/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer id, @RequestParam(value = "username", defaultValue = "") String viewerUsername){
        CommentDto comment = commentService.getCommentById(id, viewerUsername);
        if(comment == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable Integer id, @RequestParam(value = "username", defaultValue = "") String viewerUsername){
        return ResponseEntity.ok(commentService.getAllCommentsByPostId(id, viewerUsername));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUsername(@PathVariable String username, @RequestParam(value = "username", defaultValue = "") String viewerUsername){
        return ResponseEntity.ok(commentService.getAllCommentsByUsername(username, viewerUsername));
    }

    @PostMapping("/create")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        CommentDto createdComment = commentService.createComment(commentDto);
        if(createdComment == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdComment);
    }

    @PostMapping("/update")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto){
        CommentDto updatedComment = commentService.updateComment(commentDto);
        if(updatedComment == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedComment);
    }
}
