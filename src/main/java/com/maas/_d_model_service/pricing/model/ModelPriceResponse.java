package com.maas._d_model_service.pricing.model;

import java.util.List;

public class ModelPriceResponse {
    private List<ModelPrice> modelPrices;

    public List<ModelPrice> getModelPrices() {
        return modelPrices;
    }

    public void setModelPrices(List<ModelPrice> modelPrices) {
        this.modelPrices = modelPrices;
    }
}
