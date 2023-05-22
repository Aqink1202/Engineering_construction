package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

import java.util.List;

@Data
public class ReturnZhanAndCQTModel {
    private List<ReturnCQTModel> zhan_5G;
    private List<ReturnCQTModel> zhan_4G;
    private List<ReturnCQTModel> ruo_5G;
    private List<ReturnCQTModel> ruo_4G;
    private List<ReturnCQTModel> ruo_all;
    private List<ReturnCQTModel> wu_5G;
    private List<ReturnCQTModel> wu_4G;
    private List<ReturnCQTModel> wu_all;

    //构造函数
    public ReturnZhanAndCQTModel() {
    }

    public ReturnZhanAndCQTModel(List<ReturnCQTModel> zhan_5G, List<ReturnCQTModel> zhan_4G,
                                 List<ReturnCQTModel> ruo_5G, List<ReturnCQTModel> ruo_4G,
                                 List<ReturnCQTModel> ruo_all, List<ReturnCQTModel> wu_5G,
                                 List<ReturnCQTModel> wu_4G, List<ReturnCQTModel> wu_all) {
        this.zhan_5G = zhan_5G;
        this.zhan_4G = zhan_4G;
        this.ruo_5G = ruo_5G;
        this.ruo_4G = ruo_4G;
        this.ruo_all = ruo_all;
        this.wu_5G = wu_5G;
        this.wu_4G = wu_4G;
        this.wu_all = wu_all;
    }
}
