package put.appsec.backend.dto.post;

import lombok.Data;

@Data
public class UpdatePostRequestDto {
    private String title;
    private String contentUri;
}