package put.appsec.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import put.appsec.backend.dto.TestDto;
import put.appsec.backend.entity.Test;
import put.appsec.backend.repository.TestRepository;
import put.appsec.backend.service.TestService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    @Override
    public List<TestDto> getAllUsers() {
        return testRepository.findAll().stream().map(TestDto::new).collect(Collectors.toList());
    }

    @Override
    public TestDto getUserById(int id) {
        return testRepository.findById(id).map(TestDto::new).orElse(null);
    }

    @Override
    public TestDto createUser(TestDto testDto) {
        testDto.setId(null);
        Test entity = testDto.toEntity();
        Test savedEntity = testRepository.save(entity);
        return new TestDto(savedEntity);
    }
}
