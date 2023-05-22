package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

@Data
public class ReturnNowQuantityModel {
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

    //剩余工程量
    private Double shen_shitong;  //管道试通
    private Double shen_ruhu_mi;  //入户线敷设米数
    private Double shen_ruhu_hu;  //入户线敷设户数
    private Double shen_jiexu;  //光缆接续
    private Double shen_xiangti;  //箱体安装
    private Double shen_guanglan_4D;  //4D光缆
    private Double shen_guanglan_8D;  //8D光缆
    private Double shen_guanglan_12D;  //12D光缆
    private Double shen_guanglan_24D;  //24D光缆
    private Double shen_guanglan_48D;  //48D光缆
    private Double shen_guanglan_72D;  //72D光缆
    private Double shen_guanglan_96D;  //96D光缆
    private Double shen_guanglan_144D;  //144D光缆
    private Double shen_guanglan_288D;  //288D光缆
    private Double shen_zhimai;  //子管直埋
    private Double shen_kaiwa;  //开挖路面
    private Double shen_dingguan;  //顶管

    //构造函数
    public ReturnNowQuantityModel() {
    }

    public ReturnNowQuantityModel(String coding, String name, String town, String xiang_man, String construction,
                                  String team, String supervision, String sup_man, String ruhu_type, Double shitong,
                                  Double ruhu_mi, Double ruhu_hu, Double jiexu, Double xiangti, Double guanglan_4D,
                                  Double guanglan_8D, Double guanglan_12D, Double guanglan_24D, Double guanglan_48D,
                                  Double guanglan_72D, Double guanglan_96D, Double guanglan_144D, Double guanglan_288D,
                                  Double zhimai, Double kaiwa, Double dingguan, Double shen_shitong,
                                  Double shen_ruhu_mi, Double shen_ruhu_hu,
                                  Double shen_jiexu, Double shen_xiangti, Double shen_guanglan_4D,
                                  Double shen_guanglan_8D, Double shen_guanglan_12D, Double shen_guanglan_24D,
                                  Double shen_guanglan_48D, Double shen_guanglan_72D, Double shen_guanglan_96D,
                                  Double shen_guanglan_144D, Double shen_guanglan_288D,
                                  Double shen_zhimai, Double shen_kaiwa, Double shen_dingguan) {
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
        this.shen_shitong = shen_shitong;
        this.shen_ruhu_mi = shen_ruhu_mi;
        this.shen_ruhu_hu = shen_ruhu_hu;
        this.shen_jiexu = shen_jiexu;
        this.shen_xiangti = shen_xiangti;
        this.shen_guanglan_4D = shen_guanglan_4D;
        this.shen_guanglan_8D = shen_guanglan_8D;
        this.shen_guanglan_12D = shen_guanglan_12D;
        this.shen_guanglan_24D = shen_guanglan_24D;
        this.shen_guanglan_48D = shen_guanglan_48D;
        this.shen_guanglan_72D = shen_guanglan_72D;
        this.shen_guanglan_96D = shen_guanglan_96D;
        this.shen_guanglan_144D = shen_guanglan_144D;
        this.shen_guanglan_288D = shen_guanglan_288D;
        this.shen_zhimai = shen_zhimai;
        this.shen_kaiwa = shen_kaiwa;
        this.shen_dingguan = shen_dingguan;
    }
}
