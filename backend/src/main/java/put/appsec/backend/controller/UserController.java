package put.appsec.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.user.UpdateUserRequestDto;
import put.appsec.backend.dto.user.UserDto;
import put.appsec.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@AuthenticationPrincipal UserDetails currentUser) {
        String viewer = (currentUser != null) ? currentUser.getUsername() : null;
        return ResponseEntity.ok(userService.getAllUsers(viewer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id, @AuthenticationPrincipal UserDetails currentUser) {
        String viewer = (currentUser != null) ? currentUser.getUsername() : null;
        return ResponseEntity.ok(userService.getUserById(id, viewer));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username, @AuthenticationPrincipal UserDetails currentUser) {
        String viewer = (currentUser != null) ? currentUser.getUsername() : null;
        return ResponseEntity.ok(userService.getUserByUsername(username, viewer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody @Valid UpdateUserRequestDto request, @AuthenticationPrincipal UserDetails currentUser) {
        UserDto updatedUser = userService.updateUser(id, request, currentUser.getUsername());
        return ResponseEntity.ok(updatedUser);
    }
}
