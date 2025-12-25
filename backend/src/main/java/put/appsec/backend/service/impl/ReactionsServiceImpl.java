package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.CommentDto;
import put.appsec.backend.dto.PostDto;
import put.appsec.backend.dto.ReactionDto;
import put.appsec.backend.entity.*;
import put.appsec.backend.repository.*;
import put.appsec.backend.service.ReactionsService;

@Service
@Transactional
@RequiredArgsConstructor
public class ReactionsServiceImpl implements ReactionsService {
    private final CommentReactionsRepository commentReactionsRepository;
    private final PostReactionsRepository postReactionsRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto setCommentReaction(ReactionDto reactionDto) {
        CommentReaction commentReaction = commentReactionsRepository.findByCommentIdAndUserUsername(reactionDto.getTargetId(), reactionDto.getUsername()).orElse(null);

        if (commentReaction != null) {
            commentReaction.setReactionValue(reactionDto.getReaction());
            CommentReaction savedCommentReaction = commentReactionsRepository.save(commentReaction);
            return new CommentDto(savedCommentReaction.getComment(), reactionDto.getUsername());
        }

        User user = userRepository.findByUsername(reactionDto.getUsername()).orElse(null);
        Comment comment = commentRepository.findById(reactionDto.getTargetId()).orElse(null);
        if (user == null  || comment == null) {
            return null;
        }

        commentReaction = new CommentReaction();
        commentReaction.setUser(user);
        commentReaction.setComment(comment);
        commentReaction.setReactionValue(reactionDto.getReaction());

        CommentReaction savedCommentReaction = commentReactionsRepository.save(commentReaction);
        return new CommentDto(savedCommentReaction.getComment(), reactionDto.getUsername());
    }

    @Override
    public CommentDto removeCommentReaction(ReactionDto reactionDto) {
        CommentReaction commentReaction = commentReactionsRepository.findByCommentIdAndUserUsername(reactionDto.getTargetId(), reactionDto.getUsername()).orElse(null);
        if (commentReaction == null) {
            return null;
        }

        commentReaction.setReactionValue(reactionDto.getReaction());
        commentReactionsRepository.delete(commentReaction);

        Comment updatedComment =  commentRepository.findById(reactionDto.getTargetId()).orElse(null);
        if (updatedComment == null) {
            return null;
        }

        return new CommentDto(updatedComment, reactionDto.getUsername());
    }

    @Override
    public PostDto setPostReaction(ReactionDto reactionDto) {
        PostReaction postReaction = postReactionsRepository.findByPostIdAndUserUsername(reactionDto.getTargetId(), reactionDto.getUsername()).orElse(null);

        if (postReaction != null) {
            postReaction.setReactionValue(reactionDto.getReaction());
            PostReaction savedPostReaction = postReactionsRepository.save(postReaction);
            return new PostDto(savedPostReaction.getPost(), reactionDto.getUsername());
        }

        User user = userRepository.findByUsername(reactionDto.getUsername()).orElse(null);
        Post post = postRepository.findById(reactionDto.getTargetId()).orElse(null);
        if (user == null  || post == null) {
            return null;
        }

        postReaction = new PostReaction();
        postReaction.setUser(user);
        postReaction.setPost(post);
        postReaction.setReactionValue(reactionDto.getReaction());

        PostReaction savedPostReaction = postReactionsRepository.save(postReaction);
        return new PostDto(savedPostReaction.getPost(), reactionDto.getUsername());
    }

    @Override
    public PostDto removePostReaction(ReactionDto reactionDto) {
        PostReaction postReaction = postReactionsRepository.findByPostIdAndUserUsername(reactionDto.getTargetId(), reactionDto.getUsername()).orElse(null);
        if (postReaction == null) {
            return null;
        }

        postReaction.setReactionValue(reactionDto.getReaction());
        postReactionsRepository.delete(postReaction);

        Post updatedPost =  postRepository.findById(reactionDto.getTargetId()).orElse(null);
        if (updatedPost == null) {
            return null;
        }
        return new PostDto(updatedPost, reactionDto.getUsername());
    }
}
