package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import put.appsec.backend.dto.CommentDto;
import put.appsec.backend.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findAllByPostId(Integer postId);
    List<Comment> findAllByUserId(Integer userId);
    List<Comment> findAllByUserUsername(String userUsername);
}
