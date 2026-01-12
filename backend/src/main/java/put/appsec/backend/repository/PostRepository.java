package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import put.appsec.backend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findAllByUserUsername(String userUsername);
    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
