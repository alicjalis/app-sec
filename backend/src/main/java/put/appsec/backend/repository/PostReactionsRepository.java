package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import put.appsec.backend.entity.PostReaction;

public interface PostReactionsRepository extends JpaRepository<PostReaction,Integer> {
}
