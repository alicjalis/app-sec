package put.appsec.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.auth.*;
import put.appsec.backend.dto.user.UserDto;
import put.appsec.backend.security.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterRequestDto request) {
        UserDto createdUser = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<Void> confirmAccount(@RequestParam("token") String token) {
        authenticationService.confirmAccount(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request, HttpServletRequest httpRequest) {
        String ipAddress = httpRequest.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = httpRequest.getRemoteAddr();
        } else {
            ipAddress = ipAddress.split(",")[0].trim();
        }
        String userAgent = httpRequest.getHeader("User-Agent");

        LoginResponseDto response = authenticationService.login(request, ipAddress, userAgent);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/request-reset")
    public ResponseEntity<Void> requestReset(@RequestBody @Valid PasswordResetRequest request) {
        authenticationService.sendResetPasswordEmail(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestParam("token") String token, @RequestBody @Valid NewPasswordRequestDto request) {
        authenticationService.changePassword(token, request.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/whoami")
    public ResponseEntity<UserDto> whoami(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = userDetails.getUsername();

        return ResponseEntity.ok(authenticationService.getUserByUsername(username));
    }
}
