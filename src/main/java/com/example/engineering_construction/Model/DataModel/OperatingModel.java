package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

import java.util.Date;

@Data
public class OperatingModel {
    private Date date;
    private Double jinhoutian;
    private Double changxun;
    private Double zhongtongsan;
    private Double tiancheng;
    private Double zhongyoujian;
    private Double jinyibai;
    private Double zhongtongfu;
    private Double dingxiguoxun;

    //构造函数
    public OperatingModel() {
    }

    public OperatingModel(Date date, Double jinhoutian, Double changxun, Double zhongtongsan, Double tiancheng,
                          Double zhongyoujian, Double jinyibai, Double zhongtongfu, Double dingxiguoxun) {
        this.date = date;
        this.jinhoutian = jinhoutian;
        this.changxun = changxun;
        this.zhongtongsan = zhongtongsan;
        this.tiancheng = tiancheng;
        this.zhongyoujian = zhongyoujian;
        this.jinyibai = jinyibai;
        this.zhongtongfu = zhongtongfu;
        this.dingxiguoxun = dingxiguoxun;
    }
}
