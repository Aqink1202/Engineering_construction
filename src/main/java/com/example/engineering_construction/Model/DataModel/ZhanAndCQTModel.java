package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

@Data
public class ZhanAndCQTModel {
    private Integer id;
    private String name;
    private String latitude;
    private String longitude;
    private String what;

    public ZhanAndCQTModel() {
    }

    public ZhanAndCQTModel(String name, String latitude, String longitude, String what) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.what = what;
    }
}
