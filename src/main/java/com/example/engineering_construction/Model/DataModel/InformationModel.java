package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

import java.util.Date;

@Data
public class InformationModel {
    private Integer id;  //主键

    //基本信息
    private String coding;  //工程编码
    private String town;  //镇区
    private String type;  //项目类型
    private String name;  //项目名称
    private Date li_date;  //立项时间
    private String pi_name;  //批复名称
    private Date pi_date;  //批复日期
    private Date chou_date;  //抽签日期
    private String he_year;  //合同年份
    private String build_year;  //建设年份
    private String xiang_man;  //项目负责人
    private String construction;  //施工单位
    private String team;  //施工队伍
    private String supervision;  //监理单位
    private String sup_man;  //监理员
    private String electronic_version;  //文件电子版
    private Date shen_date;  //会审日期
    private Date wei_date;  //委托日期
    private Date wan_date;  //委托完工日期
    private String bei;  //备注
    private String status;  //项目状态

    //设计金额
    private Double jiagong;  //甲供材料费
    private Double rengong;  //人工费
    private Double zhanlie;  //暂列金
    private Double jianli;  //监理费
    private Double qita;  //其他费用
    private Double allmoney;  //总金额
    private String guimo;  //建设规模
    private Double hujun;  //户均投资

    //工程量
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

    //构造函数
    public InformationModel() {
    }

    public InformationModel(String coding, String town, String type, String name, Date li_date, String pi_name,
                            Date pi_date, Date chou_date, String he_year, String build_year, String xiang_man,
                            String construction, String team, String supervision, String sup_man,
                            String electronic_version, Date shen_date, Date wei_date, Date wan_date,
                            String bei, String status, Double jiagong, Double rengong, Double zhanlie,
                            Double jianli, Double qita, Double allmoney, String guimo, Double hujun, String ruhu_type,
                            Double shitong, Double ruhu_mi, Double ruhu_hu, Double jiexu, Double xiangti,
                            Double guanglan_4D, Double guanglan_8D, Double guanglan_12D, Double guanglan_24D,
                            Double guanglan_48D, Double guanglan_72D, Double guanglan_96D, Double guanglan_144D,
                            Double guanglan_288D, Double zhimai, Double kaiwa, Double dingguan) {
        this.coding = coding;
        this.town = town;
        this.type = type;
        this.name = name;
        this.li_date = li_date;
        this.pi_name = pi_name;
        this.pi_date = pi_date;
        this.chou_date = chou_date;
        this.he_year = he_year;
        this.build_year = build_year;
        this.xiang_man = xiang_man;
        this.construction = construction;
        this.team = team;
        this.supervision = supervision;
        this.sup_man = sup_man;
        this.electronic_version = electronic_version;
        this.shen_date = shen_date;
        this.wei_date = wei_date;
        this.wan_date = wan_date;
        this.bei = bei;
        this.status = status;
        this.jiagong = jiagong;
        this.rengong = rengong;
        this.zhanlie = zhanlie;
        this.jianli = jianli;
        this.qita = qita;
        this.allmoney = allmoney;
        this.guimo = guimo;
        this.hujun = hujun;
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
    }
}
