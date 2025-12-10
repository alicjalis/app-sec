package put.appsec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import put.appsec.backend.entity.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Integer> {
    ConfirmationToken findByToken(String token);
}
