package put.appsec.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import put.appsec.backend.enums.ConfirmationRequestType;
import put.appsec.backend.enums.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "confirmation_tokens")
@NoArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private ConfirmationRequestType ConfirmationRequestType;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public ConfirmationToken(User user, ConfirmationRequestType confirmationRequestType) {
        this.user = user;
        this.ConfirmationRequestType = confirmationRequestType;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }
}