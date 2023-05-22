package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class ReturnOperatingModel {
    private String name;
    private Double lv;

    //构造函数
    public ReturnOperatingModel() {
    }

    public ReturnOperatingModel(String name, Double lv) {
        this.name = name;
        this.lv = lv;
    }
}
