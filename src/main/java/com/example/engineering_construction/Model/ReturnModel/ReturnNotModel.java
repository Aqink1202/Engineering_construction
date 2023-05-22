package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

import java.util.Date;

@Data
public class ReturnNotModel {
    private String coding;
    private String name;
    private String time;
    private String construction;
    private Date date;
    private String man;
    private String status;

    //构造函数
    public ReturnNotModel() {
    }

    public ReturnNotModel(String coding, String name, String time, String construction,
                          Date date, String man, String status) {
        this.coding = coding;
        this.name = name;
        this.time = time;
        this.construction = construction;
        this.date = date;
        this.man = man;
        this.status = status;
    }
}
