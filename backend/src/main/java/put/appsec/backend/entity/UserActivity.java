package put.appsec.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user_activity")
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime lastLoginDate;
    @Column(length = 50)
    private String lastLoginIp;
    private String lastUserAgent;

    private LocalDateTime lastPostDate;

    private Integer failedLoginAttempts;

    private LocalDateTime passwordLastChangedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}