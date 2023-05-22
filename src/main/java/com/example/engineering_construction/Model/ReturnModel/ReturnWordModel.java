package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class ReturnWordModel {
    //需要输入的
    private String name;
    private String dong;
    private String hu;
    private String gaoceng;
    private String shangpu;
    private String kehu;
    private String hetong_time;
    private String hetong_money;
    private String yusuan;
    private String cailiaofei;
    private String jianlifei;
    private String street;
    private String type;

    //自动生成的
    private String company;
    private String address;
    private String year;
    private String hetong_name;
    private String date;
    private String date_start;
    private String date_end;
    private String bili;
    private String bili_money;
    private String xiaoyi;
    private String hujun;
    private String maoli;
    private String hu_xin;
    private String hu_ru;
    private String hu_tao;
    private String hu_yi;
    private String hu_cheng;
    private String rengongfei;
    private String date_zunbei;
    private String date_shigong;
    private String date_yanshou;

    //构造函数
    public ReturnWordModel() {
    }

    public ReturnWordModel(String name, String dong, String hu, String gaoceng, String shangpu, String kehu,
                           String hetong_time, String hetong_money, String yusuan, String cailiaofei,
                           String jianlifei, String street, String type, String company, String address,
                           String year, String hetong_name, String date, String date_start, String date_end,
                           String bili, String bili_money, String xiaoyi, String hujun, String maoli,
                           String hu_xin, String hu_ru, String hu_tao, String hu_yi,
                           String hu_cheng, String rengongfei, String date_zunbei, String date_shigong,
                           String date_yanshou) {
        this.name = name;
        this.dong = dong;
        this.hu = hu;
        this.gaoceng = gaoceng;
        this.shangpu = shangpu;
        this.kehu = kehu;
        this.hetong_time = hetong_time;
        this.hetong_money = hetong_money;
        this.yusuan = yusuan;
        this.cailiaofei = cailiaofei;
        this.jianlifei = jianlifei;
        this.street = street;
        this.type = type;
        this.company = company;
        this.address = address;
        this.year = year;
        this.hetong_name = hetong_name;
        this.date = date;
        this.date_start = date_start;
        this.date_end = date_end;
        this.bili = bili;
        this.bili_money = bili_money;
        this.xiaoyi = xiaoyi;
        this.hujun = hujun;
        this.maoli = maoli;
        this.hu_xin = hu_xin;
        this.hu_ru = hu_ru;
        this.hu_tao = hu_tao;
        this.hu_yi = hu_yi;
        this.hu_cheng = hu_cheng;
        this.rengongfei = rengongfei;
        this.date_zunbei = date_zunbei;
        this.date_shigong = date_shigong;
        this.date_yanshou = date_yanshou;
    }


}
