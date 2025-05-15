package com.maas._d_model_service.file_access.model;

import com.maas._d_model_service.pricing.model.ModelPrice;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stl_files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String filename;

    private String blobPath;

    private LocalDateTime uploadedAt;

    private Long customerId;

    private String type;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    private List<ModelPrice> modelPrices;

    public List<ModelPrice> getModelPrices() {
        return modelPrices;
    }

    public void setModelPrices(List<ModelPrice> modelPrices) {
        this.modelPrices = modelPrices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBlobPath() {
        return blobPath;
    }

    public void setBlobPath(String blobPath) {
        this.blobPath = blobPath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}