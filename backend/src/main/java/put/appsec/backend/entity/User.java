package put.appsec.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import put.appsec.backend.enums.UserType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;
    private Boolean enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;
}
