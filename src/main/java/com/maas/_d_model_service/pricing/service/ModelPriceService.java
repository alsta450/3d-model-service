package com.maas._d_model_service.pricing.service;

import com.maas._d_model_service.pricing.model.ModelPrice;
import com.maas._d_model_service.pricing.repository.ModelPriceRepository;
import org.springframework.stereotype.Service;

@Service
public class ModelPriceService {

    private final ModelPriceRepository modelPriceRepository;

    public ModelPriceService(ModelPriceRepository modelPriceRepository) {
        this.modelPriceRepository = modelPriceRepository;
    }

    public ModelPrice getModelPriceByMaterialAndModelId(Long modelId, String material) {
        return modelPriceRepository.findByFileIdAndMaterial(modelId, material)
                .orElseThrow(() -> new RuntimeException("ModelPrice not found for modelId " + modelId + " and material " + material));
    }
}
