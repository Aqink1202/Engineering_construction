package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

import java.util.Date;

@Data
public class ZhongDianModel {
    private Integer id;
    private String name;
    private String danwei;
    private Date shijian;
    private String neirong;
    private String zengti;
    private String shangzhou;
    private String zhongdian;

    //构造函数
    public ZhongDianModel() {
    }

    public ZhongDianModel(String name, String danwei, Date shijian, String neirong, String zengti,
                          String shangzhou, String zhongdian) {
        this.name = name;
        this.danwei = danwei;
        this.shijian = shijian;
        this.neirong = neirong;
        this.zengti = zengti;
        this.shangzhou = shangzhou;
        this.zhongdian = zhongdian;
    }

}
