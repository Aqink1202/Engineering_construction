package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Dao.MySQLDao.CQTDao;
import com.example.engineering_construction.Dao.MySQLDao.GuangDianDao;
import com.example.engineering_construction.Dao.MySQLDao.ZhanAndCQTDao;
import com.example.engineering_construction.Dao.MySQLDao.ZhanDao;
import com.example.engineering_construction.Model.DataModel.*;
import com.example.engineering_construction.Model.ReturnModel.*;
import com.example.engineering_construction.Service.ProcessService.LALService;
import com.example.engineering_construction.Service.ProcessService.LatAndLngService;
import com.example.engineering_construction.Service.ProcessService.XianSiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class CQTService {

    @Autowired
    CQTDao cqtdao;
    @Autowired
    ZhanDao zhandao;
    @Autowired
    ZhanAndCQTDao zhanandcqtdao;
    @Autowired
    LALService lalservice;
    @Autowired
    XianSiService xiansiservice;
    @Autowired
    GuangDianDao guangdiandao;

    /**
     * 先像清远那样，搞一个覆盖图出来
     */
    public List<ReturnCQTModel> FindFu() {
        //先查数据
        List<CQTModel> li = cqtdao.Find();

        //声明返回值
        List<ReturnCQTModel> res = new ArrayList<>();

        //对数据进行处理
        for (CQTModel cqtModel : li) {
            GPSModel add = LatAndLngService.wgs84_To_Gcj02(Double.parseDouble(cqtModel.getLatitude()),
                    Double.parseDouble(cqtModel.getLongitude()));

            //生成返回实例
            ReturnCQTModel re = new ReturnCQTModel(add.getLat(), add.getLon(), "");

            if (Objects.equals(cqtModel.getGd_5g_if(), "有信号") &&
                    Objects.equals(cqtModel.getGd_4g_if(), "有信号")) {
                re.setStyleId("all");
            } else if (Objects.equals(cqtModel.getGd_5g_if(), "有信号") &&
                    Objects.equals(cqtModel.getGd_4g_if(), "无信号")) {
                re.setStyleId("five");
            } else if (Objects.equals(cqtModel.getGd_5g_if(), "无信号") &&
                    Objects.equals(cqtModel.getGd_4g_if(), "有信号")) {
                re.setStyleId("four");
            } else if (Objects.equals(cqtModel.getGd_5g_if(), "无信号") &&
                    Objects.equals(cqtModel.getGd_4g_if(), "无信号")) {
                re.setStyleId("wu");
            }

            if (!Objects.equals(re.getStyleId(), "")) {
                res.add(re);
            }
        }

        return res;
    }


    /**
     * 搞出这点位信息
     */
    public List<ReturnCQTModel> FindZhan() {
        //先查数据
        List<ZhanModel> li = zhandao.Find();

        //声明返回值
        List<ReturnCQTModel> res = new ArrayList<>();

        //数据处理
        for (ZhanModel zhanModel : li) {
            GPSModel add = LatAndLngService.wgs84_To_Gcj02(Double.parseDouble(zhanModel.getLatitude()),
                    Double.parseDouble(zhanModel.getLongitude()));

            //生成返回实例
            ReturnCQTModel re = new ReturnCQTModel(add.getLat(), add.getLon(), "zhan");
            res.add(re);
        }

        return res;
    }

    /**
     * 搞个点位跟xx覆盖图
     */
    public List<ReturnCQTModel> Find700() {
        //先查数据
        List<CQTModel> li = cqtdao.Find();

        //声明返回值
        List<ReturnCQTModel> res = new ArrayList<>();

        for (CQTModel cqtModel : li) {
            if (cqtModel.getGd_5g_freq().contains("7")) {
                GPSModel add = LatAndLngService.wgs84_To_Gcj02(Double.parseDouble(cqtModel.getLatitude()),
                        Double.parseDouble(cqtModel.getLongitude()));

                //生成返回实例
                ReturnCQTModel re = new ReturnCQTModel(add.getLat(), add.getLon(), "zhan700");
                res.add(re);
            }
        }

        return res;
    }


    /**
     * 搞个点位跟CQT覆盖图
     */
    public List<ReturnZhanAndCQTModel> FindZhanAndCQT() {
        //先查数据
        List<CQTModel> li_cqt = cqtdao.Find();
        List<ZhanAndCQTModel> li_zhan = zhanandcqtdao.Find();

        //声明返回值
        List<ReturnZhanAndCQTModel> res = new ArrayList<>();
        ReturnZhanAndCQTModel re = new ReturnZhanAndCQTModel();

        //先循环站点
        List<ReturnCQTModel> zhan_5G = new ArrayList<>();
        List<ReturnCQTModel> zhan_4G = new ArrayList<>();

        String quchong_5g = "";
        for (ZhanAndCQTModel zz : li_zhan) {
            GPSModel add = LatAndLngService.wgs84_To_Gcj02(Double.parseDouble(zz.getLatitude()),
                    Double.parseDouble(zz.getLongitude()));
            ReturnCQTModel rc = new ReturnCQTModel(add.getLat(), add.getLon(), null);

            switch (zz.getWhat()) {
                case "5G" -> {
                    float du = xiansiservice.getSimilarityRatio(quchong_5g, zz.getName());

                    if (du < 70) {
                        rc.setStyleId("zhan_5g");
                        zhan_5G.add(rc);
                    }

                    quchong_5g = zz.getName();
                }
                case "4G" -> {
                    rc.setStyleId("zhan_4g");
                    zhan_4G.add(rc);
                }
            }
        }
        re.setZhan_5G(zhan_5G);
        re.setZhan_4G(zhan_4G);

        //再循环数据
        List<ReturnCQTModel> ruo_5G = new ArrayList<>();
        List<ReturnCQTModel> ruo_4G = new ArrayList<>();
        List<ReturnCQTModel> ruo_all = new ArrayList<>();
        List<ReturnCQTModel> wu_5G = new ArrayList<>();
        List<ReturnCQTModel> wu_4G = new ArrayList<>();
        List<ReturnCQTModel> wu_all = new ArrayList<>();
        for (CQTModel cc : li_cqt) {
            GPSModel add = LatAndLngService.wgs84_To_Gcj02(Double.parseDouble(cc.getLatitude()),
                    Double.parseDouble(cc.getLongitude()));
            ReturnCQTModel rc = new ReturnCQTModel(add.getLat(), add.getLon(), null);

            if (Objects.equals(cc.getGd_5g_if(), "无信号") && Objects.equals(cc.getGd_4g_if(), "无信号")) {
                rc.setStyleId("wu_all");
                wu_all.add(rc);
            } else if (!Objects.equals(cc.getGd_5g_if(), "无信号") && Objects.equals(cc.getGd_4g_if(), "无信号")) {
                rc.setStyleId("wu_4G");
                wu_4G.add(rc);
            } else if (Objects.equals(cc.getGd_5g_if(), "无信号") && !Objects.equals(cc.getGd_4g_if(), "无信号")) {
                rc.setStyleId("wu_5G");
                wu_5G.add(rc);
            }

            if ((Objects.equals(cc.getGd_5g_if(), "有信号") && Double.parseDouble(cc.getGd_5g_rsrp()) <= -105)
                    && (Objects.equals(cc.getGd_4g_if(), "有信号") &&
                    Double.parseDouble(cc.getGd_4g_rsrp()) <= -105)) {
                rc.setStyleId("ruo_all");
                ruo_all.add(rc);
            }
            if ((Objects.equals(cc.getGd_5g_if(), "有信号") && Double.parseDouble(cc.getGd_5g_rsrp()) > -105)
                    && (Objects.equals(cc.getGd_4g_if(), "有信号") &&
                    Double.parseDouble(cc.getGd_4g_rsrp()) <= -105)) {
                rc.setStyleId("ruo_4G");
                ruo_4G.add(rc);
            }
            if ((Objects.equals(cc.getGd_5g_if(), "有信号") && Double.parseDouble(cc.getGd_5g_rsrp()) <= -105)
                    && (Objects.equals(cc.getGd_4g_if(), "有信号") &&
                    Double.parseDouble(cc.getGd_4g_rsrp()) > -105)) {
                rc.setStyleId("ruo_5G");
                ruo_5G.add(rc);
            }
        }
        re.setRuo_5G(ruo_5G);
        re.setRuo_4G(ruo_4G);
        re.setRuo_all(ruo_all);
        re.setWu_5G(wu_5G);
        re.setWu_4G(wu_4G);
        re.setWu_all(wu_all);

        res.add(re);
        return res;
    }


    /**
     * 计算减去距离少于30cm后的点
     */
    public void Find30() {
        //先查数据
        List<CQTModel> li = cqtdao.Find();
        List<CQTModel> li2 = cqtdao.Find();

        int num = 0;
        int i = 0;

        for (CQTModel cc : li) {
            double lat = Double.parseDouble(cc.getLatitude());
            double lng = Double.parseDouble(cc.getLongitude());

            for (CQTModel qq : li) {
                if (lalservice.getDistance(lng, lat, Double.parseDouble(qq.getLongitude()),
                        Double.parseDouble(qq.getLatitude())) <= 30) {
                    i++;
                }
            }

            System.out.println("第" + num++ + "个点" + lat + " " + lng + " 有：" + (i - 1) + "个点与其相重复");
            i = 0;
        }
    }

    /**
     * 把站点按辐射圈的格式弄好
     */
    public List<ReturnRatModel> FindRad() {
        //先查数据
        List<ZhanAndCQTModel> li_zhan = zhanandcqtdao.Find();

        //声明返回值
        List<ReturnRatModel> res = new ArrayList<>();
        List<RadiationModel> li_5g = new ArrayList<>();
        List<RadiationModel> li_4g = new ArrayList<>();

        String quchong_5g = "";
        for (ZhanAndCQTModel zz : li_zhan) {
            GPSModel add = LatAndLngService.wgs84_To_Gcj02(Double.parseDouble(zz.getLatitude()),
                    Double.parseDouble(zz.getLongitude()));
            CenterModel cm = new CenterModel(add.getLat(), add.getLon());

            switch (zz.getWhat()) {
                case "5G" -> {
                    float du = xiansiservice.getSimilarityRatio(quchong_5g, zz.getName());

                    if (du < 70) {
                        RadiationModel re = new RadiationModel(cm, 100, "zhan_5g");
                        li_5g.add(re);
                    }

                    quchong_5g = zz.getName();
                }
                case "4G" -> {
                    RadiationModel re = new RadiationModel(cm, 200, "zhan_4g");
                    li_4g.add(re);
                }
            }
        }

        ReturnRatModel rrm = new ReturnRatModel(li_5g, li_4g);
        res.add(rrm);
        return res;
    }

    /**
     * 输出光电信息的点位
     */
    public List<ReturnCQTModel> FindGuangDian() {
        //先查数据
        List<GuangDianModel> li = guangdiandao.Find();

        //声明返回值
        List<ReturnCQTModel> res = new ArrayList<>();

        //数据处理
        for (GuangDianModel gd : li) {
            if ((!Objects.equals(gd.getLatitude(), "") && gd.getLatitude() != null) &&
                    (!Objects.equals(gd.getLongitude(), "") && gd.getLongitude() != null)) {
                GPSModel add = LatAndLngService.wgs84_To_Gcj02(Double.parseDouble(gd.getLatitude()),
                        Double.parseDouble(gd.getLongitude()));

                //生成返回实例
                ReturnCQTModel re = new ReturnCQTModel(add.getLat(), add.getLon(), "dian");
                res.add(re);
            }
        }
        return res;
    }

}
