package put.appsec.backend.service;

import put.appsec.backend.dto.CommentDto;
import put.appsec.backend.dto.PostDto;
import put.appsec.backend.dto.ReactionDto;

public interface ReactionsService {
    CommentDto setCommentReaction(ReactionDto reactionDto);
    CommentDto removeCommentReaction(ReactionDto reactionDto);
    PostDto setPostReaction(ReactionDto reactionDto);
    PostDto removePostReaction(ReactionDto reactionDto);
}
