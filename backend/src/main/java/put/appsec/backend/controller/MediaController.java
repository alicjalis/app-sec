package put.appsec.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import put.appsec.backend.exceptions.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMedia(@RequestParam("file") MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if (contentType == null || (!contentType.startsWith("image/") && !contentType.startsWith("video/"))) {
                throw new FileStorageException("Invalid file type. Only images and videos allowed.");
            }

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(fileName);

        } catch (IOException e) {
            throw new FileStorageException("Could not store file. " + e.getMessage());
        }
    }
}
