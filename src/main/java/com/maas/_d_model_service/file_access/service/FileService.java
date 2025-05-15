package com.maas._d_model_service.file_access.service;

import com.maas._d_model_service.file_access.model.File;
import com.maas._d_model_service.file_access.model.MultipartInputStreamFileResource;
import com.maas._d_model_service.file_access.repository.FileRepository;
import com.maas._d_model_service.pricing.model.ModelPrice;
import com.maas._d_model_service.pricing.model.ModelPriceResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<File> loadFilesByCustomerId(Long customerId) throws IOException {
        return repository.findByCustomerId(customerId);
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

        List<ModelPrice> modelPrice = sendPriceRequest(multipartFile);

        // Save metadata to DB
        File stlFile = new File();
        stlFile.setFilename(originalFilename);
        stlFile.setBlobPath(uniqueFilename); // relative path
        stlFile.setUploadedAt(LocalDateTime.now());
        stlFile.setCustomerId(customerId);
        stlFile.setType(multipartFile.getContentType());
        modelPrice.forEach(price -> price.setFile(stlFile));

        stlFile.setModelPrices(modelPrice);

        File saved = repository.save(stlFile);
        return saved.getId();
    }

    private List<ModelPrice> sendPriceRequest(MultipartFile multipartFile) throws IOException {
        String pythonServerUrl = "http://localhost:5000/compute-price"; // adjust if needed

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Build multipart body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(
                multipartFile.getInputStream(),
                multipartFile.getOriginalFilename()
        ));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ModelPriceResponse> response = restTemplate.exchange(
                pythonServerUrl,
                HttpMethod.POST,
                requestEntity,
                ModelPriceResponse.class
        );

        return response.getBody().getModelPrices();
    }

}
