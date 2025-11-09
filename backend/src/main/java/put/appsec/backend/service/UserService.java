package put.appsec.backend.service;

import put.appsec.backend.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Integer id);
    UserDto getUserByUsername(String username);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
}
