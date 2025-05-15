package com.maas._d_model_service.pricing.controller;

import com.maas._d_model_service.pricing.model.ModelPrice;
import com.maas._d_model_service.pricing.service.ModelPriceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelPriceController {

    private final ModelPriceService modelPriceService;

    public ModelPriceController(ModelPriceService modelPriceService) {
        this.modelPriceService = modelPriceService;
    }

    @GetMapping("/customers/{customerId}/models/{modelId}")
    public ResponseEntity<ModelPrice> getModelPriceByMaterialAndModelId(
            @PathVariable Long customerId,
            @PathVariable Long modelId,
            @RequestParam(defaultValue = "PLA") String material) {

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(modelPriceService.getModelPriceByMaterialAndModelId(modelId, material));
    }
}
