package put.appsec.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import put.appsec.backend.dto.LoginDto;
import put.appsec.backend.dto.UserDto;
import put.appsec.backend.entity.ConfirmationToken;
import put.appsec.backend.entity.User;
import put.appsec.backend.enums.ConfirmationRequestType;
import put.appsec.backend.enums.UserType;
import put.appsec.backend.repository.ConfirmationTokenRepository;
import put.appsec.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final PasswordEncoder encoder;
    private final JavaMailSender javaMailSender;

    public Boolean checkMatch (CharSequence rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }

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

        ConfirmationToken confirmationToken = new ConfirmationToken(user, ConfirmationRequestType.EMAIL);
        ConfirmationToken savedToken = confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("jakub.hologa@student.put.poznan.pl");
        mailMessage.setSubject("Confirm email address");
        mailMessage.setText("Hi, " + user.getUsername() + "!\nClick here to confirm your email: "
                + "http://localhost:8080/auth/confirm-account?token=" + savedToken.getToken());
        javaMailSender.send(mailMessage);

        return new UserDto(savedEntity);
    }

    public Boolean checkToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        if(confirmationToken != null) {
            User user = confirmationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
            confirmationTokenRepository.delete(confirmationToken);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public User authenticatePerson(LoginDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }

    public Boolean sendResetPasswordEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            return Boolean.FALSE;
        }
        ConfirmationToken confirmationToken = new ConfirmationToken(user, ConfirmationRequestType.PASSWORD);
        ConfirmationToken savedToken = confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom("jakub.hologa@student.put.poznan.pl");
        mailMessage.setSubject("Reset Password");
        mailMessage.setText("Hi " + user.getUsername() + "! ≽^•⩊•^≼ \nClick here to reset your password: "
                + "http://localhost:5173/reset-password?token=" + savedToken.getToken());
        javaMailSender.send(mailMessage);
        return Boolean.TRUE;
    }

    public UserDto changePassword(LoginDto loginDto, String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        if(confirmationToken == null) {
            return null;
        }
        User user = confirmationToken.getUser();
        user.setPassword(encoder.encode(loginDto.getPassword()));

        User savedUser = userRepository.save(user);
        confirmationTokenRepository.delete(confirmationToken);

        return new UserDto(savedUser);
    }
}
