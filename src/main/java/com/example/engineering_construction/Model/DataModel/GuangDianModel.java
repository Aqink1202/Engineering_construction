package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

@Data
public class GuangDianModel {
    private Integer id;
    private String town;
    private String name;
    private String latitude;
    private String longitude;

    //构造函数
    public GuangDianModel() {
    }

    public GuangDianModel(String town, String name, String latitude, String longitude) {
        this.town = town;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
