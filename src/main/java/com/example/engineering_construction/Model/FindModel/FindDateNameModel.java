package com.example.engineering_construction.Model.FindModel;

import lombok.Data;

import java.util.Date;

@Data
public class FindDateNameModel {
    private String name;
    private Date date;

    //构造函数
    public FindDateNameModel() {
    }

    public FindDateNameModel(String name, Date date) {
        this.name = name;
        this.date = date;
    }
}
