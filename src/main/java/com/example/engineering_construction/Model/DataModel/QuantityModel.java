package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

import java.util.Date;

@Data
public class QuantityModel {
    private Integer id;  //主键
    private String coding;  //项目编码
    private String name;  //项目名称
    private String time;  //上报次序
    private String construction;  //施工单位
    private String team;  //施工队伍
    private Date date;  //上报时间

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

    private String type;  //业务节点
    private String link;  //审批环节
    private String sup_man;  //监理员
    private String sup_status;  //监理审核状态
    private String com_man;  //分公司人员
    private String com_status;  //分公司审核状态
    private String sup_bei;  //监理反馈意见
    private String com_bei;  //分公司反馈意见
    private String picture;  //证明图片

    //构造函数
    public QuantityModel() {
    }

    public QuantityModel(String coding, String name, String time, String construction, String team, Date date,
                         String ruhu_type, Double shitong, Double ruhu_mi, Double ruhu_hu, Double jiexu,
                         Double xiangti, Double guanglan_4D, Double guanglan_8D, Double guanglan_12D,
                         Double guanglan_24D, Double guanglan_48D, Double guanglan_72D, Double guanglan_96D,
                         Double guanglan_144D, Double guanglan_288D, Double zhimai, Double kaiwa, Double dingguan,
                         String type, String link, String sup_man, String sup_status, String com_man,
                         String com_status, String sup_bei, String com_bei, String picture) {
        this.coding = coding;
        this.name = name;
        this.time = time;
        this.construction = construction;
        this.team = team;
        this.date = date;
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
        this.type = type;
        this.link = link;
        this.sup_man = sup_man;
        this.sup_status = sup_status;
        this.com_man = com_man;
        this.com_status = com_status;
        this.sup_bei = sup_bei;
        this.com_bei = com_bei;
        this.picture = picture;
    }
}
