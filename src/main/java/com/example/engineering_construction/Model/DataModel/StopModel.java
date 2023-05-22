package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

import java.util.Date;

@Data
public class StopModel {
    private String id;  //主键
    private String coding;  //项目编码
    private String name;  //项目名称
    private Integer time;  //停工次数
    private Date stat_date;  //停工日期
    private Date end_date;  //复工日期

    //构造函数
    public StopModel() {
    }

    public StopModel(String coding, String name, Integer time, Date stat_date, Date end_date) {
        this.coding = coding;
        this.name = name;
        this.time = time;
        this.stat_date = stat_date;
        this.end_date = end_date;
    }
}
