package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.user.UpdateUserRequestDto;
import put.appsec.backend.dto.user.UserDto;
import put.appsec.backend.entity.User;
import put.appsec.backend.enums.UserType;
import put.appsec.backend.exceptions.ResourceNotFoundException;
import put.appsec.backend.mapper.UserMapper;
import put.appsec.backend.repository.UserRepository;
import put.appsec.backend.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers(String viewerUsername) {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toDto(user, viewerUsername))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Integer id, String viewerUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found id: " + id));
        return userMapper.toDto(user, viewerUsername);
    }

    @Override
    public UserDto getUserByUsername(String username, String viewerUsername) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        return userMapper.toDto(user, viewerUsername);
    }

    @Override
    public UserDto updateUser(Integer id, UpdateUserRequestDto request, String requesterUsername) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User requester = userRepository.findByUsername(requesterUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found"));

        boolean isAdmin = requester.getUserType() == UserType.ADMIN;
        boolean isOwner = userToUpdate.getUsername().equals(requesterUsername);

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You are not allowed to modify this profile.");
        }

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            if (!userToUpdate.getUsername().equals(request.getUsername()) &&
                    userRepository.existsByUsername(request.getUsername())) {
                throw new IllegalArgumentException("Username already in use");
            }
            userToUpdate.setUsername(request.getUsername());
        }

        //TODO: resend confirmation email
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!userToUpdate.getEmail().equals(request.getEmail()) &&
                    userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
            userToUpdate.setEmail(request.getEmail());
        }

        if (request.getUserType() != null) {
            if (isAdmin) {
                userToUpdate.setUserType(request.getUserType());
            } else {
                throw new AccessDeniedException("Only admins can change user roles.");
            }
        }

        User savedUser = userRepository.save(userToUpdate);
        return userMapper.toDto(savedUser, requesterUsername);
    }
}
