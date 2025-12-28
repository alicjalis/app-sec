package put.appsec.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.auth.LoginRequestDto;
import put.appsec.backend.dto.auth.LoginResponseDto;
import put.appsec.backend.dto.auth.RegisterRequestDto;
import put.appsec.backend.dto.user.UserDto;
import put.appsec.backend.entity.ConfirmationToken;
import put.appsec.backend.entity.User;
import put.appsec.backend.enums.ConfirmationRequestType;
import put.appsec.backend.enums.UserType;
import put.appsec.backend.exceptions.AccountNotEnabledException;
import put.appsec.backend.exceptions.InvalidTokenException;
import put.appsec.backend.exceptions.UserAlreadyExistsException;
import put.appsec.backend.mapper.UserMapper;
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

    private final JwtService jwtService;

    private final PasswordEncoder encoder;
    private final JavaMailSender javaMailSender;
    private final UserMapper userMapper;

    @Value("${application.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Transactional
    public UserDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already in use");
        }

        User user = new User();
        user.setId(null);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setUserType(UserType.USER);
        user.setPosts(new ArrayList<>());
        user.setComments(new ArrayList<>());
        user.setRegistrationDate(LocalDateTime.now());
        user.setEnabled(false);

        User savedEntity = userRepository.save(user);

        ConfirmationToken confirmationToken = createToken(user, ConfirmationRequestType.EMAIL);

        String link = frontendUrl + "/confirm-email?token=" + confirmationToken.getToken();
        sendEmail(user.getEmail(), "Confirm your email", "Hi " + user.getUsername() + ",\nClick here: " + link);

        return userMapper.toDto(savedEntity, savedEntity.getUsername());
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!user.isEnabled()) {
            throw new AccountNotEnabledException("Please confirm your email address first.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String jwtToken = jwtService.generateToken(user);

        return LoginResponseDto.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .username(user.getUsername())
                .userType(user.getUserType())
                .build();
    }

    @Transactional
    public void confirmAccount(String tokenString) {
        ConfirmationToken token = getTokenOrThrow(tokenString);

        if (token.getConfirmationRequestType() != ConfirmationRequestType.EMAIL) {
            throw new InvalidTokenException("Invalid token type");
        }

        User user = token.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        //confirmationTokenRepository.delete(token);
    }

    @Transactional
    public void sendResetPasswordEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            ConfirmationToken token = createToken(user, ConfirmationRequestType.PASSWORD);
            String link = frontendUrl + "/reset-password?token=" + token.getToken();
            sendEmail(user.getEmail(), "Reset Password", "Hi " + user.getUsername() + ",\nClick here to reset: " + link);
        });
    }

    @Transactional
    public void changePassword(String tokenString, String newPassword) {
        ConfirmationToken token = getTokenOrThrow(tokenString);

        if (token.getConfirmationRequestType() != ConfirmationRequestType.PASSWORD) {
            throw new InvalidTokenException("Invalid token type");
        }

        User user = token.getUser();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        confirmationTokenRepository.delete(token);
    }

    private ConfirmationToken createToken(User user, ConfirmationRequestType type) {
        ConfirmationToken token = new ConfirmationToken(user, type);
        return confirmationTokenRepository.save(token);
    }

    private ConfirmationToken getTokenOrThrow(String tokenString) {
        ConfirmationToken token = confirmationTokenRepository.findByToken(tokenString);
        if (token == null) {
            throw new InvalidTokenException("Invalid token");
        }

        int tokenExpirationTime = 1800; // seconds
        LocalDateTime expirationDate = token.getCreatedDate().plusSeconds(tokenExpirationTime);

        if (expirationDate.isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token expired");
        }

        return token;
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(senderEmail);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email");
        }
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.toDto(user, username);
    }
}
