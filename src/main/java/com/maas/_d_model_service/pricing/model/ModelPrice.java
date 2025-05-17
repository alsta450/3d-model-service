package com.maas._d_model_service.pricing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maas._d_model_service.file_access.model.File;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "model_prices")
public class ModelPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String material;

    private Double volume;

    private BigDecimal materialCost;

    private Double printTime;

    private BigDecimal energyCost;

    private BigDecimal operatorMargin;

    private BigDecimal producerMargin;

    private BigDecimal totalCost;

    @ManyToOne
    @JoinColumn(name = "file_id")
    @JsonIgnore
    private File file;

    public ModelPrice() {}

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public Double getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Double printTime) {
        this.printTime = printTime;
    }

    public BigDecimal getEnergyCost() {
        return energyCost;
    }

    public void setEnergyCost(BigDecimal energyCost) {
        this.energyCost = energyCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getOperatorMargin() {
        return operatorMargin;
    }

    public void setOperatorMargin(BigDecimal operatorMargin) {
        this.operatorMargin = operatorMargin;
    }

    public BigDecimal getProducerMargin() {
        return producerMargin;
    }

    public void setProducerMargin(BigDecimal producerMargin) {
        this.producerMargin = producerMargin;
    }
}
