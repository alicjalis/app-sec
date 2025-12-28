package put.appsec.backend.service;

import put.appsec.backend.dto.user.UpdateUserRequestDto;
import put.appsec.backend.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers(String viewerUsername);
    UserDto getUserById(Integer id, String viewerUsername);
    UserDto getUserByUsername(String username, String viewerUsername);
    UserDto updateUser(Integer id, UpdateUserRequestDto request, String requesterUsername);
}
