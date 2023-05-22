package com.example.engineering_construction.Model.FindModel;

import lombok.Data;

@Data
public class FindUserBaoModel {
    private String bao;
    private Integer deng;

    //构造函数
    public FindUserBaoModel() {
    }

    public FindUserBaoModel(String bao, Integer deng) {
        this.bao = bao;
        this.deng = deng;
    }
}
