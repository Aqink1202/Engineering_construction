package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class ReturnWeekQuantityModel {
    private String coding;  //工程编码
    private String name;  //项目名称
    private String town;  //镇区
    private String xiang_man;  //项目负责人
    private String construction;  //施工单位
    private String team;  //施工队伍
    private String supervision;  //监理单位
    private String sup_man;  //监理员

    //已做工程量
    private String ruhu_type;  //入户线类型
    private Double shitong;  //管道试通
    private Double ruhu_mi;  //入户线敷设米数
    private Double ruhu_hu;  //入户线敷设户数
    private Double jiexu;  //光缆接续
    private Double xiangti;  //箱体安装
    private Double guanglan_4D;  //4D光缆
    private Double guanglan_8D;  //8D光缆
    private Double guanglan_12D;  //12D光缆
    private Double guanglan_24D;  //24D光缆
    private Double guanglan_48D;  //48D光缆
    private Double guanglan_72D;  //72D光缆
    private Double guanglan_96D;  //96D光缆
    private Double guanglan_144D;  //144D光缆
    private Double guanglan_288D;  //288D光缆
    private Double zhimai;  //子管直埋
    private Double kaiwa;  //开挖路面
    private Double dingguan;  //顶管
    private Double jindu;  //项目进度

    //构造函数
    public ReturnWeekQuantityModel() {
    }

    public ReturnWeekQuantityModel(String coding, String name, String town, String xiang_man, String construction,
                                   String team, String supervision, String sup_man, String ruhu_type, Double shitong,
                                   Double ruhu_mi, Double ruhu_hu, Double jiexu, Double xiangti, Double guanglan_4D,
                                   Double guanglan_8D, Double guanglan_12D, Double guanglan_24D, Double guanglan_48D,
                                   Double guanglan_72D, Double guanglan_96D, Double guanglan_144D, Double guanglan_288D,
                                   Double zhimai, Double kaiwa, Double dingguan, Double jindu) {
        this.coding = coding;
        this.name = name;
        this.town = town;
        this.xiang_man = xiang_man;
        this.construction = construction;
        this.team = team;
        this.supervision = supervision;
        this.sup_man = sup_man;
        this.ruhu_type = ruhu_type;
        this.shitong = shitong;
        this.ruhu_mi = ruhu_mi;
        this.ruhu_hu = ruhu_hu;
        this.jiexu = jiexu;
        this.xiangti = xiangti;
        this.guanglan_4D = guanglan_4D;
        this.guanglan_8D = guanglan_8D;
        this.guanglan_12D = guanglan_12D;
        this.guanglan_24D = guanglan_24D;
        this.guanglan_48D = guanglan_48D;
        this.guanglan_72D = guanglan_72D;
        this.guanglan_96D = guanglan_96D;
        this.guanglan_144D = guanglan_144D;
        this.guanglan_288D = guanglan_288D;
        this.zhimai = zhimai;
        this.kaiwa = kaiwa;
        this.dingguan = dingguan;
        this.jindu = jindu;
    }
}
