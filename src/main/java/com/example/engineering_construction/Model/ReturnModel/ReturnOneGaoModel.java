package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class ReturnOneGaoModel {
    private String coding;
    private String name;
    private String mu;
    private Double yuan;
    private Double xian;

    //构造函数
    public ReturnOneGaoModel() {
    }

    public ReturnOneGaoModel(String coding, String name, String mu, Double yuan, Double xian) {
        this.coding = coding;
        this.name = name;
        this.mu = mu;
        this.yuan = yuan;
        this.xian = xian;
    }

}
