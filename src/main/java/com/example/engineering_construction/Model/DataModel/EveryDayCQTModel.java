package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

@Data
public class EveryDayCQTModel {
    private String id;
    private String date;
    private String name;
    private String phone;
    private String sheng;
    private String shi;
    private String address;
    private String xuhao;
    private String dili;
    private String wangge;
    private String type;
    private String latitude;
    private String longitude;

    private String gd5xinhao;
    private String gd5nr;
    private String gd5pinlv;
    private String gd5pci;
    private String gd5ecl;
    private String gd5rsrp;
    private String gd5sinr;
    private String gd5vonr;
    private String gd510099;

    private String yd5xinhao;
    private String yd5nr;
    private String yd5pinlv;
    private String yd5pci;
    private String yd5ecl;
    private String yd5rsrp;
    private String yd5sinr;

    private String gd4xinhao;
    private String gd4lte;
    private String gd4pinlv;
    private String gd4pci;
    private String gd4ecl;
    private String gd4rsrp;
    private String gd4sinr;
    private String gd4volte;
    private String gd410099;

    private String yd4xinhao;
    private String yd4lte;
    private String yd4pinlv;
    private String yd4pci;
    private String yd4ecl;
    private String yd4rsrp;
    private String yd4sinr;

    private Boolean youxiao;

    //构造函数
    public EveryDayCQTModel() {
    }
}
