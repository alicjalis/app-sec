package put.appsec.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.dto.post.PostDto;
import put.appsec.backend.dto.ReactionDto;
import put.appsec.backend.service.ReactionsService;

@RestController
@RequestMapping("/reaction")
@RequiredArgsConstructor
public class ReactionsController {
    private final ReactionsService reactionsService;
    @PostMapping("/post")
    public ResponseEntity<PostDto> setPostReaction(@RequestBody @Valid ReactionDto reactionDto, @AuthenticationPrincipal UserDetails currentUser) {
        reactionDto.setUsername(currentUser.getUsername());
        return ResponseEntity.ok(reactionsService.setPostReaction(reactionDto));
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentDto> setCommentReaction(@RequestBody @Valid ReactionDto reactionDto, @AuthenticationPrincipal UserDetails currentUser) {
        reactionDto.setUsername(currentUser.getUsername());
        return ResponseEntity.ok(reactionsService.setCommentReaction(reactionDto));
    }

}
