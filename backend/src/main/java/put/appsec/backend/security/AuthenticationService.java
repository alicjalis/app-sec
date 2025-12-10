package put.appsec.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import put.appsec.backend.dto.LoginDto;
import put.appsec.backend.dto.UserDto;
import put.appsec.backend.entity.ConfirmationToken;
import put.appsec.backend.entity.User;
import put.appsec.backend.enums.UserType;
import put.appsec.backend.repository.ConfirmationTokenRepository;
import put.appsec.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final PasswordEncoder encoder;
    private final JavaMailSender javaMailSender;

    public UserDto register(LoginDto loginDto) {
        User user = new User();
        user.setId(null);
        user.setUsername(loginDto.getUsername());
        user.setEmail(loginDto.getEmail());
        user.setPassword(encoder.encode(loginDto.getPassword()));
        user.setUserType(UserType.USER);
        user.setPosts(new ArrayList<>());
        user.setComments(new ArrayList<>());
        user.setRegistrationDate(LocalDateTime.now());
        user.setEnabled(false);

        User savedEntity = userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        ConfirmationToken savedToken = confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("noreply@appsecmemes.com");
        mailMessage.setSubject("Confirm email address");
        mailMessage.setText("Hi, " + user.getUsername() + "!\nClick here to confirm your email: "
                + "http://localhost:8080/confirm-account?token=" + savedToken.getToken());
        javaMailSender.send(mailMessage);

        return new UserDto(savedEntity);
    }

    public Boolean checkToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        if(confirmationToken != null) {
            User user = confirmationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
