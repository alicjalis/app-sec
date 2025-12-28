package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.comment.CommentDto;
import put.appsec.backend.dto.post.PostDto;
import put.appsec.backend.dto.ReactionDto;
import put.appsec.backend.entity.*;
import put.appsec.backend.exceptions.ResourceNotFoundException;
import put.appsec.backend.mapper.CommentMapper;
import put.appsec.backend.mapper.PostMapper;
import put.appsec.backend.repository.*;
import put.appsec.backend.service.ReactionsService;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReactionsServiceImpl implements ReactionsService {
    private final CommentReactionsRepository commentReactionsRepository;
    private final PostReactionsRepository postReactionsRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final CommentMapper commentMapper;
    private final PostMapper postMapper;

    @Override
    public CommentDto setCommentReaction(ReactionDto reactionDto) {
        User user = userRepository.findByUsername(reactionDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + reactionDto.getUsername()));

        Comment comment = commentRepository.findById(reactionDto.getTargetId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found id: " + reactionDto.getTargetId()));

        Optional<CommentReaction> existingReaction = commentReactionsRepository
                .findByCommentIdAndUserUsername(reactionDto.getTargetId(), reactionDto.getUsername());

        CommentReaction savedReaction;

        if (existingReaction.isPresent()) {
            CommentReaction reaction = existingReaction.get();
            reaction.setReactionValue(reactionDto.getReaction());
            savedReaction = commentReactionsRepository.save(reaction);
        } else {
            CommentReaction newReaction = new CommentReaction();
            newReaction.setUser(user);
            newReaction.setComment(comment);
            newReaction.setReactionValue(reactionDto.getReaction());
            savedReaction = commentReactionsRepository.save(newReaction);
        }

        return commentMapper.toDto(savedReaction.getComment(), reactionDto.getUsername());
    }

    @Override
    public PostDto setPostReaction(ReactionDto reactionDto) {
        User user = userRepository.findByUsername(reactionDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + reactionDto.getUsername()));

        Post post = postRepository.findById(reactionDto.getTargetId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found id: " + reactionDto.getTargetId()));

        Optional<PostReaction> existingReaction = postReactionsRepository
                .findByPostIdAndUserUsername(reactionDto.getTargetId(), reactionDto.getUsername());

        PostReaction savedReaction;

        if (existingReaction.isPresent()) {
            PostReaction reaction = existingReaction.get();
            reaction.setReactionValue(reactionDto.getReaction());
            savedReaction = postReactionsRepository.save(reaction);
        } else {
            PostReaction newReaction = new PostReaction();
            newReaction.setUser(user);
            newReaction.setPost(post);
            newReaction.setReactionValue(reactionDto.getReaction());
            savedReaction = postReactionsRepository.save(newReaction);
        }

        return postMapper.toDto(savedReaction.getPost(), reactionDto.getUsername());
    }
}
