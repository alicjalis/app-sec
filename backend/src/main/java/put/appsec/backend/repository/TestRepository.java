package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import put.appsec.backend.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
}
