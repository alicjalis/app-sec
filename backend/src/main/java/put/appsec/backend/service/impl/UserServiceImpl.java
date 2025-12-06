package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.UserDto;
import put.appsec.backend.entity.User;
import put.appsec.backend.repository.UserRepository;
import put.appsec.backend.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Integer id) {
        return userRepository.findById(id).map(UserDto::new).orElse(null);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(UserDto::new).orElse(null);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(null);
        userDto.setPosts(new ArrayList<>());
        userDto.setComments(new ArrayList<>());
        userDto.setRegistrationDate(LocalDateTime.now());
        User entity = userDto.toEntity();
        User savedEntity = userRepository.save(entity);
        return new UserDto(savedEntity);
    }

    @Override
    public UserDto updateUser(UserDto updatedUserDto) {
        UserDto userToEditDto = getUserById(updatedUserDto.getId());
        User savedEntity;
        if (userToEditDto == null) {
            return null;
        }
        userToEditDto.setUsername(updatedUserDto.getUsername()!=null?updatedUserDto.getUsername():userToEditDto.getUsername());
        userToEditDto.setUserType(updatedUserDto.getUserType()!=null?updatedUserDto.getUserType():userToEditDto.getUserType());

        savedEntity = userRepository.save(userToEditDto.toEntity());
        return new UserDto(savedEntity);
    }
}
