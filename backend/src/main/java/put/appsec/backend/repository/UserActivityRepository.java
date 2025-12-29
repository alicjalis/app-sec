package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import put.appsec.backend.entity.UserActivity;

import java.util.Optional;


@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity,Integer> {
    Optional<UserActivity> findByUserId(Integer id);
}
