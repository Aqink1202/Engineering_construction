package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

@Data
public class ZhanModel {
    private Integer id;
    private String name;
    private String coding;
    private String address;
    private String latitude;
    private String longitude;

    //构造函数
    public ZhanModel() {
    }

    public ZhanModel(String name, String coding, String address, String latitude, String longitude) {
        this.name = name;
        this.coding = coding;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
