package com.example.engineering_construction.Service.ProcessService;

import com.example.engineering_construction.Dao.MySQLDao.InformationDao;
import com.example.engineering_construction.Dao.MySQLDao.ManReportDao;
import com.example.engineering_construction.Dao.MySQLDao.OperatingDao;
import com.example.engineering_construction.Model.DataModel.InformationModel;
import com.example.engineering_construction.Model.DataModel.ManReportModel;
import com.example.engineering_construction.Model.DataModel.OperatingModel;
import com.example.engineering_construction.Service.DataService.InformationService;
import com.example.engineering_construction.Service.DataService.ManReportService;
import com.example.engineering_construction.Service.DataService.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
@Component
public class SchedulService {

    @Autowired
    ManReportDao manreportdao;
    @Autowired
    ManReportService manreportservice;
    @Autowired
    InformationDao informationdao;
    @Autowired
    InformationService informationservice;
    @Autowired
    StopService stopservice;
    @Autowired
    OperatingDao operatingdao;

    /**
     * 定时线程，定时晚上23点59分自动触发
     * <p>
     * 用以确认每日工作量是否上报
     */
    @Scheduled(cron = "0 59 23 * * ?")
    public void time1() throws ParseException {
        //生成今日日期
        Date da = new Date();
        SimpleDateFormat date_sdf1 = new SimpleDateFormat("yyyy年MM月dd日 00:00:00");
        SimpleDateFormat date_sdf2 = new SimpleDateFormat("yyyy年MM月dd日 23:59:59");
        SimpleDateFormat date_sdf3 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date FindDate1 = date_sdf3.parse(date_sdf1.format(da));
        Date FindDate2 = date_sdf3.parse(date_sdf2.format(da));

        //查询今日上报信息
        List<ManReportModel> li_re = manreportdao.Find(null, FindDate1, FindDate2, null, null,
                null, null, null, null);

        //查询除已完工外所有项目
        List<InformationModel> li_in = informationservice.FindTeam(null, null);

        //删去已上报的项目
        List<InformationModel> all = new ArrayList<>();
        for (InformationModel informationModel : li_in) {
            boolean isin = false;
            for (ManReportModel manReportModel : li_re) {
                if (Objects.equals(manReportModel.getName(), informationModel.getName())) {
                    isin = true;
                    break;
                }
            }

            if (!isin) {
                all.add(informationModel);
            }
        }

        //开始自动上报
        for (InformationModel informationModel : all) {
            try {
                manreportservice.Add(informationModel.getName(), "否", null, null, null,
                        informationModel.getTown(), informationModel.getConstruction(),
                        informationModel.getTeam(), "0", "系统自动上报！！！", "Aqink", null);
            } catch (Exception ignored) {
            }

        }
    }

    /**
     * 定时线程，定时中午12点自动触发
     * <p>
     * 用以更新各项目状态
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void time2() {
        //查询除已完工外所有项目
        List<InformationModel> li_in = informationservice.FindTeam(null, null);

        //寻找status并修改
        for (InformationModel informationModel : li_in) {
            try {
                String status = stopservice.FindStatus(informationModel.getCoding());
                informationservice.UpdateStatus(informationModel.getCoding(), status, "Aqink");
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * 定时线程，定时晚上23点59分自动触发
     * <p>
     * 用以得到每日开工率
     */
    @Scheduled(cron = "0 59 23 * * ?")
    public void Operatinglv() throws Exception {
        //分公司列表
        String[] com = {"金厚田", "长讯", "中通三局", "天诚伟业", "中邮建", "中通服", "金一百", "鼎熙国讯"};

        //时间
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String date1 = sdf1.format(new Date());
        String date2 = sdf2.format(new Date());
        Date date = new Date();

        //置入值
        double jin = 0.0, chang = 0.0, san = 0.0, tian = 0.0, you = 0.0, yi = 0.0, fu = 0.0, ding = 0.0;

        //分次计算
        for (String construction : com) {
            //查询开工中的项目数量
            List<InformationModel> li_in = informationservice.Find(null, null, null, null,
                    construction, null, null, null, "Aqink", null);
            List<InformationModel> res_in = new ArrayList<>();
            for (InformationModel informationModel : li_in) {
                if (Objects.equals(informationModel.getStatus(), "开工中") ||
                        Objects.equals(informationModel.getStatus(), "逾期未完工")) {
                    res_in.add(informationModel);
                }
            }
            double fenmu = res_in.size();

            //查询每日上报开工的项目数量
            List<ManReportModel> li_man = manreportservice.Find(null, date1, date2, null,
                    construction, null, "Aqink", null, "是");
            List<ManReportModel> res_man = new ArrayList<>();
            for (ManReportModel m1 : li_man) {
                boolean isin = true;
                for (ManReportModel m2 : res_man) {
                    if (Objects.equals(m2.getName(), m1.getName())) {
                        isin = false;
                        break;
                    }
                }

                if (isin) {
                    res_man.add(m1);
                }
            }
            double fenzi = res_man.size();

            //防止分母为0
            if (fenmu == 0) {
                fenmu = 1;
                fenzi = 0;
            }
            switch (construction) {
                case "金厚田" -> jin = fenzi / fenmu;
                case "长讯" -> chang = fenzi / fenmu;
                case "中通三局" -> san = fenzi / fenmu;
                case "天诚伟业" -> tian = fenzi / fenmu;
                case "中邮建" -> you = fenzi / fenmu;
                case "中通服" -> fu = fenzi / fenmu;
                case "金一百" -> yi = fenzi / fenmu;
                case "鼎熙国讯" -> ding = fenzi / fenmu;
            }
        }

        OperatingModel om = new OperatingModel(date, jin, chang, san, tian, you, yi, fu, ding);
        operatingdao.Add(om);
    }
}
