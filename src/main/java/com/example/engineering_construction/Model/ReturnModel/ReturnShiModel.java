package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

import java.util.Date;

@Data
public class ReturnShiModel {
    public String coding;
    public String time;
    public String name;
    public Date date;
    public String link;

    //构造函数
    public ReturnShiModel() {
    }

    public ReturnShiModel(String coding, String time, String name, Date date, String link) {
        this.coding = coding;
        this.time = time;
        this.name = name;
        this.date = date;
        this.link = link;
    }

}
