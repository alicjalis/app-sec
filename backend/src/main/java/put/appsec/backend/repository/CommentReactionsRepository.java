package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import put.appsec.backend.entity.CommentReaction;

public interface CommentReactionsRepository extends JpaRepository<CommentReaction,Integer> {
}
