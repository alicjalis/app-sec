package put.appsec.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import put.appsec.backend.entity.Test;

@Data
@NoArgsConstructor
public class TestDto {
    private Integer id;
    private String username;
    private String password;

    public TestDto(Test test) {
        this.id = test.getId();
        this.username = test.getUsername();
        this.password = test.getPassword();
    }

    public Test toEntity() {
        Test entity = new Test();
        entity.setId(this.id);
        entity.setUsername(this.username);
        entity.setPassword(this.password);
        return entity;
    }
}
