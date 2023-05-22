package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class RadiationModel {
    private CenterModel center;
    private Integer radius;
    private String styleId;

    //构造函数
    public RadiationModel() {
    }

    public RadiationModel(CenterModel center, Integer radius, String styleId) {
        this.center = center;
        this.radius = radius;
        this.styleId = styleId;
    }
}
