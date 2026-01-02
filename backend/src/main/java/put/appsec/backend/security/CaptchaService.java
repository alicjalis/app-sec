package put.appsec.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CaptchaService {
    private static final String GOOGLE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    @Value("${google.recaptcha.secret}")
    private String SECRET_KEY;

    public boolean verify(String token) {
        if (token == null || token.isEmpty()) return false;

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", SECRET_KEY);
        requestMap.add("response", token);

        var response = restTemplate.postForObject(GOOGLE_VERIFY_URL, requestMap, Map.class);

        assert response != null;
        return Boolean.TRUE.equals(response.get("success"));
    }
}
