package put.appsec.backend.service;

import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.dto.post.PostDto;
import put.appsec.backend.dto.ReactionDto;

public interface ReactionsService {
    CommentDto setCommentReaction(ReactionDto reactionDto);
    PostDto setPostReaction(ReactionDto reactionDto);
}
