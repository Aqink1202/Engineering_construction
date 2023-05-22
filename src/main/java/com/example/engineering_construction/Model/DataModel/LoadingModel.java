package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

@Data
public class LoadingModel {
    private Integer id;  //主键
    private String username;  //账号
    private String password;  //密码
    private String type;  //项目类型
    private String company;  //公司
    private String team;  //队伍
    private String bao;  //密保
    private Integer deng; //等级
    private String name;  //人名

    //构造函数
    public LoadingModel() {
    }

    public LoadingModel(String username, String password, String type, String company, String team,
                        String bao, Integer deng, String name) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.company = company;
        this.team = team;
        this.bao = bao;
        this.deng = deng;
        this.name = name;
    }
}
