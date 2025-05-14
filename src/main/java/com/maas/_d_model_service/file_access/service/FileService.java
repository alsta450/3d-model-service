package com.maas._d_model_service.file_access.service;

import com.maas._d_model_service.file_access.model.File;
import com.maas._d_model_service.file_access.repository.FileRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class FileService {

    private final FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public Resource loadFileById(Long id) throws IOException {
        File file = repository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("STL file not found"));

        Path filePath = Paths.get("storage", file.getBlobPath());
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("File not accessible: " + filePath);
        }

        return resource;
    }


    public Long saveFile(MultipartFile multipartFile, Long customerId) throws IOException {
        // Create storage directory if it doesn't exist
        Path storageDir = Paths.get("storage");
        if (!Files.exists(storageDir)) {
            Files.createDirectories(storageDir);
        }

        // Save the file with a unique name (you can use UUIDs or timestamps)
        String originalFilename = multipartFile.getOriginalFilename();
        String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = storageDir.resolve(uniqueFilename);
        Files.copy(multipartFile.getInputStream(), filePath);

        // Save metadata to DB
        File stlFile = new File();
        stlFile.setFilename(originalFilename);
        stlFile.setBlobPath(uniqueFilename); // relative path
        stlFile.setUploadedAt(LocalDateTime.now());
        stlFile.setCustomerId(customerId);
        stlFile.setType(multipartFile.getContentType());

        File saved = repository.save(stlFile);
        return saved.getId();
    }

}
