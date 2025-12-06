package put.appsec.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import put.appsec.backend.dto.LoginDto;
import put.appsec.backend.dto.UserDto;
import put.appsec.backend.security.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody LoginDto loginDto) {
        UserDto createdUser = authenticationService.register(loginDto);
        if (createdUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdUser);
    }
}
