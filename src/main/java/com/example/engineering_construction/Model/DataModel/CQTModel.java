package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

@Data
public class CQTModel {
    private Integer id;
    private String name;
    private String latitude;
    private String longitude;
    private String gd_5g_if;
    private String gd_5g_freq;
    private String gd_5g_rsrp;
    private String gd_4g_if;
    private String gd_4g_freq;
    private String gd_4g_rsrp;
    private String yd_5g_if;
    private String yd_5g_freq;
    private String yd_5g_rsrp;
    private String yd_4g_if;
    private String yd_4g_freq;
    private String yd_4g_rsrp;

    //构造函数
    public CQTModel() {
    }

    public CQTModel(String name, String latitude, String longitude, String gd_5g_if,String gd_5g_freq,
                    String gd_5g_rsrp, String gd_4g_if,String gd_4g_freq, String gd_4g_rsrp, String yd_5g_if,
                    String yd_5g_freq,String yd_5g_rsrp, String yd_4g_if, String yd_4g_freq,
                    String yd_4g_rsrp) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gd_5g_if = gd_5g_if;
        this.gd_5g_freq = gd_5g_freq;
        this.gd_5g_rsrp = gd_5g_rsrp;
        this.gd_4g_if = gd_4g_if;
        this.gd_4g_freq = gd_4g_freq;
        this.gd_4g_rsrp = gd_4g_rsrp;
        this.yd_5g_if = yd_5g_if;
        this.yd_5g_freq = yd_5g_freq;
        this.yd_5g_rsrp = yd_5g_rsrp;
        this.yd_4g_if = yd_4g_if;
        this.yd_4g_freq = yd_4g_freq;
        this.yd_4g_rsrp = yd_4g_rsrp;
    }
}
