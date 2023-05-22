package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

import java.util.Date;


@Data
public class ManReportModel {
    private Integer id;  //主键
    private String name;  //项目名称
    private Date date;  //上报日期
    private String open;  //是否开工
    private String address;  //地址信息
    private String longitude;  //经度
    private String latitude;  //维度
    private String town;  //镇区
    private String construction;  //施工单位
    private String team;  //施工队伍
    private Integer man;  //人数
    private String content;  //内容
    private String picture;  //图片地址

    //构造函数
    public ManReportModel() {
    }

    public ManReportModel(String name, Date date, String open, String address, String longitude, String latitude,
                          String town, String construction, String team, Integer man, String content,
                          String picture) {
        this.name = name;
        this.date = date;
        this.open = open;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.town = town;
        this.construction = construction;
        this.team = team;
        this.man = man;
        this.content = content;
        this.picture = picture;
    }
}
