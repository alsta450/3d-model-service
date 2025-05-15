package com.maas._d_model_service.pricing.repository;


import com.maas._d_model_service.pricing.model.ModelPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelPriceRepository extends JpaRepository<ModelPrice, Long> {
    Optional<ModelPrice> findByFileIdAndMaterial(Long fileId, String material);
}
