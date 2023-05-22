package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class ReturnCQTModel {
    private Double lat;
    private Double lng;
    private String styleId;

    public ReturnCQTModel() {
    }

    public ReturnCQTModel(Double lat, Double lng, String styleId) {
        this.lat = lat;
        this.lng = lng;
        this.styleId = styleId;
    }
}
