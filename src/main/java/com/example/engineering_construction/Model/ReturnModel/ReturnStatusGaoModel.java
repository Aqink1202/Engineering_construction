package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class ReturnStatusGaoModel {
    private String coding;
    private String name;
    private String status;

    //构造函数
    public ReturnStatusGaoModel() {
    }

    public ReturnStatusGaoModel(String coding, String name, String status) {
        this.coding = coding;
        this.name = name;
        this.status = status;
    }
}
