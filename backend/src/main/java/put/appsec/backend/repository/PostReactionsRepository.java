package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import put.appsec.backend.entity.PostReaction;

import java.util.Optional;

public interface PostReactionsRepository extends JpaRepository<PostReaction,Integer> {
    Optional<PostReaction> findByPostIdAndUserUsername(Integer commentId, String username);
}
