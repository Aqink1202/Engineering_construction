package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class CenterModel {
    private Double lat;
    private Double lng;

    //构造函数
    public CenterModel() {
    }

    public CenterModel(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
