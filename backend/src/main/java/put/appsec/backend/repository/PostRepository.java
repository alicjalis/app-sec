package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import put.appsec.backend.dto.PostDto;
import put.appsec.backend.entity.Post;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findAllByUserId(Integer id);
    List<Post> findAllByUserUsername(String userUsername);
}
