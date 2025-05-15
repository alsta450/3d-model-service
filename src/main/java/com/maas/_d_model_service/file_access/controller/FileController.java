package com.maas._d_model_service.file_access.controller;

import com.maas._d_model_service.file_access.model.File;
import com.maas._d_model_service.file_access.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/customers/{customerId}/models")
    public ResponseEntity<List<File>> getFile(@PathVariable String customerId) throws IOException {

        List<File> files = fileService.loadFilesByCustomerId(Long.valueOf(customerId));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/json"))
                .body(files);
    }

    @GetMapping("/models/{fileId}")
    public ResponseEntity<Resource> getSTLFile(@PathVariable String fileId) throws IOException {

        Resource resource = fileService.loadFileById(Long.valueOf(fileId));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/sla")) // or "application/octet-stream"
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"model.stl\"")
                .body(resource);
    }

    @PostMapping("/models")
    public ResponseEntity<Long> uploadSTLFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("customerId") Long customerId) throws IOException {

        Long fileId = fileService.saveFile(file, customerId);
        return ResponseEntity.ok(fileId);
    }
}
