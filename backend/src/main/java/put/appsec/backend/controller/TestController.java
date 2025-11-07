package put.appsec.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import put.appsec.backend.dto.TestDto;
import put.appsec.backend.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
class TestController {
    private final TestService testService;

    @GetMapping("/all")
    public ResponseEntity<List<TestDto>> getAllUsers() {
        List<TestDto> allUsers = testService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getUserById(@PathVariable int id) {
        TestDto user = testService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<TestDto> createUser(@RequestBody TestDto testDto) {
        TestDto createdUser = testService.createUser(testDto);
        if (createdUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
