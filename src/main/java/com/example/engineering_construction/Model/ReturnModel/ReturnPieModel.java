package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class ReturnPieModel {
    public Integer value;
    private String name;

    //构造函数
    public ReturnPieModel() {
    }

    public ReturnPieModel(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
