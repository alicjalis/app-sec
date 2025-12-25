package put.appsec.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.CommentDto;
import put.appsec.backend.dto.PostDto;
import put.appsec.backend.dto.ReactionDto;
import put.appsec.backend.service.ReactionsService;

@RestController
@RequestMapping("/reaction")
@RequiredArgsConstructor
public class ReactionsController {
    private final ReactionsService reactionsService;

    @PostMapping("/post/set")
    public ResponseEntity<PostDto> setPostReaction(@RequestBody ReactionDto reactionDto) {
        PostDto postDto = reactionsService.setPostReaction(reactionDto);
        if (postDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(postDto);
    }

    @PostMapping("/post/remove")
    public ResponseEntity<PostDto> removePostReaction(@RequestBody ReactionDto reactionDto) {
        PostDto postDto = reactionsService.removePostReaction(reactionDto);
        if (postDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(postDto);
    }

    @PostMapping("/comment/set")
    public ResponseEntity<CommentDto> setCommentReaction(@RequestBody ReactionDto reactionDto) {
        CommentDto commentDto = reactionsService.setCommentReaction(reactionDto);
        if (commentDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(commentDto);
    }

    @PostMapping("/comment/remove")
    public ResponseEntity<CommentDto> removeCommentReaction(@RequestBody ReactionDto reactionDto) {
        CommentDto commentDto = reactionsService.removeCommentReaction(reactionDto);
        if (commentDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(commentDto);
    }

}
