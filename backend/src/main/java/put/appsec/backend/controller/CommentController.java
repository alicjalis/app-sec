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
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer id){
        CommentDto comment = commentService.getCommentById(id);
        if(comment == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/post/id/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable Integer id){
        return ResponseEntity.ok(commentService.getAllCommentsByPostId(id));
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUserId(@PathVariable Integer id){
        return ResponseEntity.ok(commentService.getAllCommentsByUserId(id));
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUsername(@PathVariable String username){
        return ResponseEntity.ok(commentService.getAllCommentsByUsername(username));
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
