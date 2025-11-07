package put.appsec.backend.service;

import put.appsec.backend.dto.TestDto;

import java.util.List;

public interface TestService {
    List<TestDto> getAllUsers();
    TestDto getUserById(int id);
    TestDto createUser(TestDto testDto);
}
