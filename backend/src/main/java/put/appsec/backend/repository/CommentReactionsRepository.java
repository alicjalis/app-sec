package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import put.appsec.backend.entity.CommentReaction;

import java.util.Optional;

public interface CommentReactionsRepository extends JpaRepository<CommentReaction,Integer> {
    Optional<CommentReaction> findByCommentIdAndUserUsername(Integer commentId, String username);
}
