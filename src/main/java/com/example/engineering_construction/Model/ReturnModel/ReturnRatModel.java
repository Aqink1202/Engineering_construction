package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

import java.util.List;

@Data
public class ReturnRatModel {
    private List<RadiationModel> list_5g;
    private List<RadiationModel> list_4g;

    //构造函数
    public ReturnRatModel() {
    }

    public ReturnRatModel(List<RadiationModel> list_5g, List<RadiationModel> list_4g) {
        this.list_5g = list_5g;
        this.list_4g = list_4g;
    }
}
