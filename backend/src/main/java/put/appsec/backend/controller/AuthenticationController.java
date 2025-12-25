package put.appsec.backend.controller;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.LoginDto;
import put.appsec.backend.dto.LoginResponseDto;
import put.appsec.backend.dto.UserDto;
import put.appsec.backend.entity.User;
import put.appsec.backend.enums.UserType;
import put.appsec.backend.security.AuthenticationService;
import put.appsec.backend.security.JwtService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody LoginDto loginDto) {
        UserDto createdUser = authenticationService.register(loginDto);
        if (createdUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<Boolean> confirmAccount(@RequestParam("token") String token) {
        Boolean accountConfirmed = authenticationService.checkToken(token);
        if (accountConfirmed) {
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginDto loginUserDto) {
        User user = authenticationService.authenticatePerson(loginUserDto);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        String jwtToken = jwtService.generateToken(user);
        UserType userType = user.getUserType();

        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setUserType(userType);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/request-reset")
    public ResponseEntity<Boolean> requestReset(@RequestBody LoginDto loginUserDto) {
        Boolean result = authenticationService.sendResetPasswordEmail(loginUserDto.getEmail());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<UserDto> resetPassword(@RequestParam("token") String token, @RequestBody LoginDto loginUserDto) {
        UserDto savedUser = authenticationService.changePassword(loginUserDto, token);
        if (savedUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedUser);
    }
}
