package put.appsec.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import put.appsec.backend.dto.LoginDto;
import put.appsec.backend.dto.UserDto;
import put.appsec.backend.entity.User;
import put.appsec.backend.enums.UserType;
import put.appsec.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserDto register(LoginDto loginDto) {
        User user = new User();
        user.setId(null);
        user.setUsername(loginDto.getUsername());
        user.setPassword(encoder.encode(loginDto.getPassword()));
        user.setUserType(UserType.USER);
        user.setPosts(new ArrayList<>());
        user.setComments(new ArrayList<>());
        user.setRegistrationDate(LocalDateTime.now());

        User savedEntity = userRepository.save(user);

        return new UserDto(savedEntity);
    }
}
