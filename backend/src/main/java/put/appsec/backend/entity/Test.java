package put.appsec.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "person")
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "person_id_seq_gen")
    @SequenceGenerator(name = "person_id_seq_gen", sequenceName = "person_id_seq", allocationSize = 1)
    private Integer id;
    private String username;
    private String password;
}
