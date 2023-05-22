package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class ReturnGaoModel {
    private String coding;
    private String name;
    private Double jindu;

    //构造函数
    public ReturnGaoModel() {
    }

    public ReturnGaoModel(String coding, String name, Double jindu) {
        this.coding = coding;
        this.name = name;
        this.jindu = jindu;
    }
}
