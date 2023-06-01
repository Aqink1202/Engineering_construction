package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Dao.MySQLDao.LoadingDao;
import com.example.engineering_construction.Dao.MySQLDao.ManReportDao;
import com.example.engineering_construction.Dao.MySQLDao.QuantityDao;
import com.example.engineering_construction.Model.DataModel.InformationModel;
import com.example.engineering_construction.Model.DataModel.LoadingModel;
import com.example.engineering_construction.Model.DataModel.ManReportModel;
import com.example.engineering_construction.Model.DataModel.QuantityModel;
import com.example.engineering_construction.Model.ReturnModel.*;
import com.example.engineering_construction.Service.ProcessService.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class QuantityService {

    @Autowired
    QuantityDao quantitydao;
    @Autowired
    StrToAnythingService strtoanythingservice;
    @Autowired
    InformationService informationservice;
    @Autowired
    MiService miservice;
    @Autowired
    LoadingDao loadingdao;
    @Autowired
    WeekService weekservice;
    @Autowired
    ManReportDao manreportdao;
    @Autowired
    ImageService imageservice;

    /**
     * 工程量表添加
     * <p>
     * 用以施工队每日上报工程量
     * <p>
     * 只有1级权限所有人可执行该操作
     */
    public void Add(String coding, String name, String construction, String team,
                    String ruhu_type, String st, String rh_mi, String rh_hu, String jx, String xt,
                    String gl_4D, String gl_8D, String gl_12D, String gl_24D, String gl_48D,
                    String gl_72D, String gl_96D, String gl_144D, String gl_288D, String zm, String kw,
                    String dg, String type, String sup_man, String com_man, String zhiname,
                    MultipartFile[] files) throws Exception {
        //生成日期格式基准
        SimpleDateFormat date_sdf1 = new SimpleDateFormat("yyyy年MM月dd日 00:00:00");
        SimpleDateFormat date_sdf2 = new SimpleDateFormat("yyyy年MM月dd日 23:59:59");
        SimpleDateFormat date_sdf3 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        SimpleDateFormat date_sdf4 = new SimpleDateFormat("yyyy年MM月dd日 12:00:00");

        //生成上报时间
        Date date = new Date();

        //生成当天中午
        Date da3 = date_sdf3.parse(date_sdf4.format(date));  //当天中午
        Date da1, da2;

        //判断当天是上午还是下午
        List<ManReportModel> yos;
        if (date.before(da3)) {
            da1 = date_sdf3.parse(date_sdf1.format(new Date(date.getTime() -
                    (long) 2 * 24 * 60 * 60 * 1000)));  //前两天
            da2 = date_sdf3.parse(date_sdf2.format(new Date(date.getTime() -
                    (long) 24 * 60 * 60 * 1000)));  //前一天

            //寻找是否有上报
            yos = manreportdao.Find(name, da1, da2, null, construction, null, null, "是",
                    null);

            if (yos.size() == 0) {
                throw new Exception("由于您前天与昨天并未上报该项目开工，现不可上报工程量！！！");
            }
        } else {
            da1 = date_sdf3.parse(date_sdf1.format(new Date(date.getTime() -
                    (long) 24 * 60 * 60 * 1000)));  //前一天
            //这里存在当天下午上报工程量前上报一个开工即可上报的bug
            //但不影响使用，就先不改了，问题不大
            da2 = date_sdf3.parse(date_sdf2.format(date));  //当天

            //寻找是否有上报
            yos = manreportdao.Find(name, da1, da2, null, construction, null, null, "是",
                    null);

            if (yos.size() == 0) {
                throw new Exception("由于您昨天与今天并未上报该项目开工，现不可上报工程量！！！");
            }
        }

        //字符串转double
        //Double shitong = strtoanythingservice.StrToDou(st);
        Double ruhu_mi = strtoanythingservice.StrToDou(rh_mi);
        //Double ruhu_hu = strtoanythingservice.StrToDou(rh_hu);
        Double jiexu = strtoanythingservice.StrToDou(jx);
        Double xiangti = strtoanythingservice.StrToDou(xt);
        Double guanglan_4D = strtoanythingservice.StrToDou(gl_4D);
        Double guanglan_8D = strtoanythingservice.StrToDou(gl_8D);
        Double guanglan_12D = strtoanythingservice.StrToDou(gl_12D);
        Double guanglan_24D = strtoanythingservice.StrToDou(gl_24D);
        Double guanglan_48D = strtoanythingservice.StrToDou(gl_48D);
        Double guanglan_72D = strtoanythingservice.StrToDou(gl_72D);
        Double guanglan_96D = strtoanythingservice.StrToDou(gl_96D);
        Double guanglan_144D = strtoanythingservice.StrToDou(gl_144D);
        Double guanglan_288D = strtoanythingservice.StrToDou(gl_288D);
        Double zhimai = strtoanythingservice.StrToDou(zm);
        Double kaiwa = strtoanythingservice.StrToDou(kw);
        Double dingguan = strtoanythingservice.StrToDou(dg);

        //次数time生成,寻找最大值
        List<QuantityModel> li = quantitydao.Find(coding, null, null, null, null,
                null, null, null, null, null, null);
        int max = 0;
        for (QuantityModel quantityModel : li) {
            if (strtoanythingservice.StrToInt(quantityModel.getTime()) > max) {
                max = strtoanythingservice.StrToInt(quantityModel.getTime());
            }
        }
        String time = String.valueOf(max + 1);

        //生成审批环节
        String link = "监理审批";

        //记录图片
        //生成日期名称
        Date da = new Date();
        SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat time_sdf = new SimpleDateFormat("HH时mm分");

        //生成施工队名称
        String con_team = construction + team;

        //开始生成文件夹
        Path path = Paths.get("C://工建系统/工程量上报/" + con_team + "/" +
                name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da));
        Files.createDirectories(path);

        //开始存入图片并生成对应路径
        int i = 0;
        StringBuilder picture = new StringBuilder();
        String[] filePath = new String[9];
        int filePath_num = 0;
        for (MultipartFile multipartFile : files) {
            //生成数据库存储路径
            if (i != 0) {
                picture.append(";");
            }

            //生成三位数的编码
            String num = "00" + i;
            i++;

            //获取图片名称
            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;
            String[] filename = originalFilename.split("\\.");

            //生成防撞编码
            int fz = 0;
            String fznum = getfz(fz);

            //生成图片路径
            filePath[filePath_num] = "C://工建系统/工程量上报/" + con_team + "/" +
                    name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                    "." + filename[1];
            File file = new File(filePath[filePath_num]);

            while (file.exists()) {
                fz++;
                fznum = getfz(fz);

                filePath[filePath_num] = "C://工建系统/工程量上报/" + con_team + "/" +
                        name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                        "." + filename[1];
                file = new File(filePath[filePath_num]);
            }
            filePath_num++;

            //生成路径
            String callPath = "https://www.dgcatv.top:8787/Img/Quantity/" + con_team + "/" +
                    name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                    "." + filename[1];
            picture.append(callPath);
        }

        //生成model实例
        QuantityModel qm = new QuantityModel(coding, name, time, construction, team, date, ruhu_type, (double) 0,
                ruhu_mi, (double) 0, jiexu, xiangti, guanglan_4D, guanglan_8D, guanglan_12D, guanglan_24D,
                guanglan_48D, guanglan_72D, guanglan_96D, guanglan_144D, guanglan_288D, zhimai, kaiwa, dingguan,
                type, link, sup_man, null, com_man, null, null, null,
                String.valueOf(picture));

        //给定权限范围
        Integer[] quan = {1, 5};

        if (miservice.PiPei(zhiname, quan)) {
            quantitydao.Add(qm);
            //存入图片
            int j = 0;
            for (MultipartFile multipartFile : files) {
                multipartFile.transferTo(new File(filePath[j]));
                j++;
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表添加——小程序专用
     * <p>
     * 用以施工单位上报工程量
     * <p>
     * 只有1级权限所有人可执行该操作
     */
    public void AddXiao(String coding, String name, String construction, String team,
                        String ruhu_type, String st, String rh_mi, String rh_hu, String jx, String xt,
                        String gl_4D, String gl_8D, String gl_12D, String gl_24D, String gl_48D,
                        String gl_72D, String gl_96D, String gl_144D, String gl_288D, String zm, String kw,
                        String dg, String type, String sup_man, String com_man, String zhiname,
                        MultipartFile[] files) throws Exception {
        //生成日期格式基准
        SimpleDateFormat date_sdf1 = new SimpleDateFormat("yyyy年MM月dd日 00:00:00");
        SimpleDateFormat date_sdf2 = new SimpleDateFormat("yyyy年MM月dd日 23:59:59");
        SimpleDateFormat date_sdf3 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        SimpleDateFormat date_sdf4 = new SimpleDateFormat("yyyy年MM月dd日 12:00:00");

        //生成上报时间
        Date date = new Date();

        //生成当天中午
        Date da3 = date_sdf3.parse(date_sdf4.format(date));  //当天中午
        Date da1, da2;

        //判断当天是上午还是下午
        List<ManReportModel> yos;
        if (date.before(da3)) {
            da1 = date_sdf3.parse(date_sdf1.format(new Date(date.getTime() -
                    (long) 2 * 24 * 60 * 60 * 1000)));  //前两天
            da2 = date_sdf3.parse(date_sdf2.format(new Date(date.getTime() -
                    (long) 24 * 60 * 60 * 1000)));  //前一天

            //寻找是否有上报
            yos = manreportdao.Find(name, da1, da2, null, construction, null, null, "是",
                    null);

            if (yos.size() == 0) {
                throw new Exception("由于您前天与昨天并未上报该项目开工，现不可上报工程量！！！");
            }
        } else {
            da1 = date_sdf3.parse(date_sdf1.format(new Date(date.getTime() -
                    (long) 24 * 60 * 60 * 1000)));  //前一天
            da2 = date_sdf3.parse(date_sdf2.format(date));  //当天

            //寻找是否有上报
            yos = manreportdao.Find(name, da1, da2, null, construction, null, null, "是",
                    null);

            if (yos.size() == 0) {
                throw new Exception("由于您昨天与今天并未上报该项目开工，现不可上报工程量！！！");
            }
        }

        //字符串转double
        //Double shitong = strtoanythingservice.StrToDou(st);
        Double ruhu_mi = strtoanythingservice.StrToDou(rh_mi);
        //Double ruhu_hu = strtoanythingservice.StrToDou(rh_hu);
        Double jiexu = strtoanythingservice.StrToDou(jx);
        Double xiangti = strtoanythingservice.StrToDou(xt);
        Double guanglan_4D = strtoanythingservice.StrToDou(gl_4D);
        Double guanglan_8D = strtoanythingservice.StrToDou(gl_8D);
        Double guanglan_12D = strtoanythingservice.StrToDou(gl_12D);
        Double guanglan_24D = strtoanythingservice.StrToDou(gl_24D);
        Double guanglan_48D = strtoanythingservice.StrToDou(gl_48D);
        Double guanglan_72D = strtoanythingservice.StrToDou(gl_72D);
        Double guanglan_96D = strtoanythingservice.StrToDou(gl_96D);
        Double guanglan_144D = strtoanythingservice.StrToDou(gl_144D);
        Double guanglan_288D = strtoanythingservice.StrToDou(gl_288D);
        Double zhimai = strtoanythingservice.StrToDou(zm);
        Double kaiwa = strtoanythingservice.StrToDou(kw);
        Double dingguan = strtoanythingservice.StrToDou(dg);

        //次数time生成,寻找最大值
        List<QuantityModel> li = quantitydao.Find(coding, null, null, null, null,
                null, null, null, null, null, null);
        int max = 0;
        for (QuantityModel quantityModel : li) {
            if (strtoanythingservice.StrToInt(quantityModel.getTime()) > max) {
                max = strtoanythingservice.StrToInt(quantityModel.getTime());
            }
        }
        String time = String.valueOf(max + 1);

        //生成审批环节
        String link = "监理审批";

        //记录图片
        //生成日期名称
        Date da = new Date();
        SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat time_sdf = new SimpleDateFormat("HH时mm分");

        //生成施工队名称
        String con_team = construction + team;

        //开始生成文件夹
        Path path = Paths.get("C://工建系统/工程量上报/" + con_team + "/" +
                name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da));
        Files.createDirectories(path);

        //开始存入图片并生成对应路径
        int i = 0;
        StringBuilder picture = new StringBuilder();
        String[] filePath = new String[9];
        int filePath_num = 0;
        for (MultipartFile multipartFile : files) {
            //生成数据库存储路径
            if (i != 0) {
                picture.append(";");
            }

            //生成三位数的编码
            String num = "00" + i;
            i++;

            //获取图片名称
            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;
            String[] filename = originalFilename.split("\\.");

            //生成防撞编码
            int fz = 0;
            String fznum = getfz(fz);

            //生成图片路径
            filePath[filePath_num] = "C://工建系统/工程量上报/" + con_team + "/" +
                    name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                    "." + filename[1];
            File file = new File(filePath[filePath_num]);

            while (file.exists()) {
                fz++;
                fznum = getfz(fz);

                filePath[filePath_num] = "C://工建系统/工程量上报/" + con_team + "/" +
                        name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                        "." + filename[1];
                file = new File(filePath[filePath_num]);
            }
            filePath_num++;

            //生成路径
            String callPath = "https://www.dgcatv.top:8787/Img/Quantity/" + con_team + "/" +
                    name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                    "." + filename[1];
            picture.append(callPath);
        }

        //生成model实例
        QuantityModel qm = new QuantityModel(coding, name, time, construction, team, date, ruhu_type, (double) 0,
                ruhu_mi, (double) 0, jiexu, xiangti, guanglan_4D, guanglan_8D, guanglan_12D, guanglan_24D,
                guanglan_48D, guanglan_72D, guanglan_96D, guanglan_144D, guanglan_288D, zhimai, kaiwa, dingguan, type,
                link, sup_man, null, com_man, null, null, null,
                String.valueOf(picture));

        //给定权限范围
        Integer[] quan = {1, 5};

        if (miservice.PiPei(zhiname, quan)) {
            quantitydao.Add(qm);
            //存入图片
            int j = 0;
            for (MultipartFile multipartFile : files) {
                multipartFile.transferTo(new File(filePath[j]));
                j++;
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以施工队及规划建设部查看工程量走到哪一步
     * <p>
     * 只有1、4、5级权限所有人可执行改操作
     */
    public List<QuantityModel> FindBu(String zhiname, String name, String construction, String pa) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先查操作者的用户信息
            List<LoadingModel> li = loadingdao.Find(zhiname, null);

            //查询改用户信息下的所有数据
            List<QuantityModel> res;
            if (!Objects.equals(li.get(0).getType(), "规划建设部")) {
                res = quantitydao.Find(null, name, null, null,
                        li.get(0).getCompany(), li.get(0).getTeam(), null, null, null,
                        null, null);
            } else {
                res = quantitydao.Find(null, name, null, null,
                        construction, null, null, null, null,
                        null, null);
            }
            res.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (res.size() < min) {
                    return new ArrayList<>();
                } else if (res.size() < max) {
                    max = res.size();
                }

                //最终分页
                res = res.subList(min, max);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以施工单位查询找有什么项目是没有审核的，以便驳回及进一步修改
     * <p>
     * 只有1、5级权限所有人可执行该操作
     */
    public List<ReturnShiModel> FindShi(String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //声明返回值
            List<ReturnShiModel> res = new ArrayList<>();

            //通过FindNot得到所有自身未审核量
            List<ReturnNotModel> fn = FindNot(zhiname, null, null, null);
            for (ReturnNotModel returnNotModel : fn) {
                ReturnShiModel rsm = new ReturnShiModel(returnNotModel.getCoding(),
                        returnNotModel.getTime(), returnNotModel.getName(),
                        returnNotModel.getDate(), returnNotModel.getStatus());
                res.add(rsm);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以管理员显示有什么项目是没有审核的，卡在哪一步
     * <p>
     * 只有1、4、5级权限所有人可执行该操作
     */
    public List<ReturnNotModel> FindNot(String zhiname, String pa, String name, String status) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先查操作者的用户信息
            List<LoadingModel> us = loadingdao.Find(zhiname, null);
            Integer deng = miservice.DengJi(us.get(0).getBao());

            String construction = null;
            if (deng == 1) {
                construction = us.get(0).getCompany();
            }

            //先查出所有项目
            List<QuantityModel> li = quantitydao.Find(null, name, null, null, construction,
                    null, null, null, null, null, null);
            //声明返回值
            List<ReturnNotModel> res = new ArrayList<>();

            //数据处理
            for (QuantityModel quantityModel : li) {
                if (Objects.equals(quantityModel.getSup_status(), "") || quantityModel.getSup_status() == null) {
                    if (Objects.equals(quantityModel.getLink(), "监理审批")) {
                        ReturnNotModel rnm = new ReturnNotModel(quantityModel.getCoding(), quantityModel.getName(),
                                quantityModel.getTime(), quantityModel.getConstruction(),
                                quantityModel.getDate(), quantityModel.getSup_man(),
                                "待监理审核");
                        res.add(rnm);
                    }
                } else if (Objects.equals(quantityModel.getCom_status(), "") ||
                        quantityModel.getCom_status() == null) {
                    if (Objects.equals(quantityModel.getLink(), "镇公司审核")) {
                        ReturnNotModel rnm = new ReturnNotModel(quantityModel.getCoding(), quantityModel.getName(),
                                quantityModel.getTime(), quantityModel.getConstruction(),
                                quantityModel.getDate(), quantityModel.getCom_man(),
                                "待镇公司审核");
                        res.add(rnm);
                    }
                }
            }

            //做状态区分
            if (!Objects.equals(status, "") && status != null) {
                if (status.equals("监理")) {
                    res.removeIf(x -> Objects.equals(x.getStatus(), "待镇公司审核"));
                } else if (status.equals("镇公司")) {
                    res.removeIf(x -> Objects.equals(x.getStatus(), "待监理审核"));
                }
            }

            //按时间排序
            res.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (res.size() < min) {
                    return new ArrayList<>();
                } else if (res.size() < max) {
                    max = res.size();
                }

                //最终分页
                res = res.subList(min, max);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以返回每周每个项目所作的工程量信息
     * <p>
     * 只有1、4、5级权限所有人可执行该操作
     */
    public List<ReturnWeekQuantityModel> FindWeek(String coding, String name, String town, String construction,
                                                  String da1, String da2, String zhiname, String pa)
            throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //生成日期
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            Date monday, sunday;
            if ((!Objects.equals(da1, "") && da1 != null) && (!Objects.equals(da2, "") && da2 != null)) {
                monday = sf1.parse(da1);
                sunday = sf2.parse(da2);
            } else {
                //得到本周的日期
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, 0);
                monday = sf.parse(sf1.format(weekservice.getFirstDayOfWeek(c.getTime())));
                sunday = sf.parse(sf2.format(weekservice.getLastDayOfWeek(c.getTime())));
            }

            //先查操作者的用户信息
            List<LoadingModel> us = loadingdao.Find(zhiname, null);
            Integer deng = miservice.DengJi(us.get(0).getBao());
            String supervision = null, company = null;

            switch (deng) {
                case 1 -> construction = us.get(0).getCompany();
                case 2 -> supervision = us.get(0).getCompany();
                case 3 -> company = us.get(0).getCompany();
            }

            //先根据条件查基础信息表得到对应项目信息
            List<InformationModel> li_first = informationservice.Find(town, coding, name, company, construction,
                    supervision, null, null, zhiname, null);
            List<InformationModel> li = new ArrayList<>();
            for (InformationModel informationModel : li_first) {
                if (!Objects.equals(informationModel.getStatus(), "已完工") &&
                        !Objects.equals(informationModel.getStatus(), "暂未更新，每日12点将自动更新！！")) {
                    li.add(informationModel);
                }
            }

            //声明返回值
            List<ReturnWeekQuantityModel> res = new ArrayList<>();

            //数据处理
            for (InformationModel informationModel : li) {
                //先查数据
                List<QuantityModel> qm = quantitydao.Find(informationModel.getCoding(), null, null,
                        null, null, null, monday, sunday, "审核通过",
                        "审核通过", null);
                qm.sort((o1, o2) -> (Integer.parseInt(o2.getTime()) - Integer.parseInt(o1.getTime())));  //降序排序

                //声明返回值-先置入基础信息
                ReturnWeekQuantityModel rwqm = new ReturnWeekQuantityModel(informationModel.getCoding(),
                        informationModel.getName(), informationModel.getTown(), informationModel.getXiang_man(),
                        informationModel.getConstruction(), informationModel.getTeam(),
                        informationModel.getSupervision(), informationModel.getSup_man(),
                        informationModel.getRuhu_type(), (double) 0, (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0);

                //置入已完成工程量
                for (QuantityModel quantityModel : qm) {
                    rwqm.setShitong(rwqm.getShitong() + quantityModel.getShitong());
                    rwqm.setRuhu_mi(rwqm.getRuhu_mi() + quantityModel.getRuhu_mi());
                    rwqm.setRuhu_hu(rwqm.getRuhu_hu() + quantityModel.getRuhu_hu());
                    rwqm.setJiexu(rwqm.getJiexu() + quantityModel.getJiexu());
                    rwqm.setXiangti(rwqm.getXiangti() + quantityModel.getXiangti());
                    rwqm.setGuanglan_4D(rwqm.getGuanglan_4D() + quantityModel.getGuanglan_4D());
                    rwqm.setGuanglan_8D(rwqm.getGuanglan_8D() + quantityModel.getGuanglan_8D());
                    rwqm.setGuanglan_12D(rwqm.getGuanglan_12D() + quantityModel.getGuanglan_12D());
                    rwqm.setGuanglan_24D(rwqm.getGuanglan_24D() + quantityModel.getGuanglan_24D());
                    rwqm.setGuanglan_48D(rwqm.getGuanglan_48D() + quantityModel.getGuanglan_48D());
                    rwqm.setGuanglan_72D(rwqm.getGuanglan_72D() + quantityModel.getGuanglan_72D());
                    rwqm.setGuanglan_96D(rwqm.getGuanglan_96D() + quantityModel.getGuanglan_96D());
                    rwqm.setGuanglan_144D(rwqm.getGuanglan_144D() + quantityModel.getGuanglan_144D());
                    rwqm.setGuanglan_288D(rwqm.getGuanglan_288D() + quantityModel.getGuanglan_288D());
                    rwqm.setZhimai(rwqm.getZhimai() + quantityModel.getZhimai());
                    rwqm.setKaiwa(rwqm.getKaiwa() + quantityModel.getKaiwa());
                    rwqm.setDingguan(rwqm.getDingguan() + quantityModel.getDingguan());
                }

                //根据现有工程量计算整体工程进度
                int num = 0;  //个数
                double all = 0;  //总进度
                if (informationModel.getShitong() != 0) {
                    all += rwqm.getShitong() / informationModel.getShitong();
                    num++;
                }
                if (informationModel.getRuhu_mi() != 0) {
                    all += rwqm.getRuhu_mi() / informationModel.getRuhu_mi();
                    num++;
                }
                if (informationModel.getRuhu_hu() != 0) {
                    all += rwqm.getRuhu_hu() / informationModel.getRuhu_hu();
                    num++;
                }
                if (informationModel.getJiexu() != 0) {
                    all += rwqm.getJiexu() / informationModel.getJiexu();
                    num++;
                }
                if (informationModel.getXiangti() != 0) {
                    all += rwqm.getXiangti() / informationModel.getXiangti();
                    num++;
                }
                if (informationModel.getGuanglan_4D() != 0) {
                    all += rwqm.getGuanglan_4D() / informationModel.getGuanglan_4D();
                    num++;
                }
                if (informationModel.getGuanglan_8D() != 0) {
                    all += rwqm.getGuanglan_8D() / informationModel.getGuanglan_8D();
                    num++;
                }
                if (informationModel.getGuanglan_12D() != 0) {
                    all += rwqm.getGuanglan_12D() / informationModel.getGuanglan_12D();
                    num++;
                }
                if (informationModel.getGuanglan_24D() != 0) {
                    all += rwqm.getGuanglan_24D() / informationModel.getGuanglan_24D();
                    num++;
                }
                if (informationModel.getGuanglan_48D() != 0) {
                    all += rwqm.getGuanglan_48D() / informationModel.getGuanglan_48D();
                    num++;
                }
                if (informationModel.getGuanglan_72D() != 0) {
                    all += rwqm.getGuanglan_72D() / informationModel.getGuanglan_72D();
                    num++;
                }
                if (informationModel.getGuanglan_96D() != 0) {
                    all += rwqm.getGuanglan_96D() / informationModel.getGuanglan_96D();
                    num++;
                }
                if (informationModel.getGuanglan_144D() != 0) {
                    all += rwqm.getGuanglan_144D() / informationModel.getGuanglan_144D();
                    num++;
                }
                if (informationModel.getGuanglan_288D() != 0) {
                    all += rwqm.getGuanglan_288D() / informationModel.getGuanglan_288D();
                    num++;
                }
                if (informationModel.getZhimai() != 0) {
                    all += rwqm.getZhimai() / informationModel.getZhimai();
                    num++;
                }
                if (informationModel.getKaiwa() != 0) {
                    all += rwqm.getKaiwa() / informationModel.getKaiwa();
                    num++;
                }
                if (informationModel.getDingguan() != 0) {
                    all += rwqm.getDingguan() / informationModel.getDingguan();
                    num++;
                }

                if (all == 0) {
                    num = 1;
                }
                rwqm.setJindu(all / num * 100);
                BigDecimal bg = BigDecimal.valueOf(rwqm.getJindu());
                rwqm.setJindu(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());

                res.add(rwqm);
            }

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (res.size() < min) {
                    return new ArrayList<>();
                } else if (res.size() < max) {
                    max = res.size();
                }

                //最终分页
                res = res.subList(min, max);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以回显基本信息及工程量
     * <p>
     * 任何级别权限都可以操作,需根据权限等级做相应限制
     */
    public List<ReturnNowQuantityModel> Find(String coding, String name, String zhiname, String pa)
            throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先查操作者的用户信息
            List<LoadingModel> li = loadingdao.Find(zhiname, null);
            Integer deng = miservice.DengJi(li.get(0).getBao());
            String construction = null, team = null, company = null, supervision = null;

            //根据权限等级做限制
            switch (deng) {
                case 1 -> {
                    construction = li.get(0).getCompany();
                    team = li.get(0).getTeam();
                }
                case 2 -> supervision = li.get(0).getCompany();
                case 3 -> company = li.get(0).getCompany();
            }

            //查项目基础信息表得到基础信息
            List<InformationModel> im = informationservice.Find(null, coding, name, company,
                    construction, supervision, team, null, zhiname, null);

            if (im.size() == 0) {
                throw new Exception("找不到对应项目，请确认该项目是否自身所有！！！");
            }

            //数据处理-去除完工、已验收项目
            List<InformationModel> im1 = new ArrayList<>();
            if (coding == null && name == null) {
                for (InformationModel informationModel : im) {
                    if (!Objects.equals(informationModel.getStatus(), "已完工") &&
                            !Objects.equals(informationModel.getStatus(), "已验收")) {
                        im1.add(informationModel);
                    }
                }
            } else {
                im1 = im;
            }

            //返回值
            List<ReturnNowQuantityModel> li_res = new ArrayList<>();
            for (InformationModel informationModel : im1) {
                //查工程量表得到工程量信息
                List<QuantityModel> qm = quantitydao.Find(informationModel.getCoding(), informationModel.getName(),
                        null, null, construction, team,
                        null, null, "审核通过", "审核通过", null);
                qm.sort((o1, o2) -> (Integer.parseInt(o2.getTime()) - Integer.parseInt(o1.getTime())));  //降序排序

                //声明返回值-先置入基础信息
                ReturnNowQuantityModel res = new ReturnNowQuantityModel(informationModel.getCoding(),
                        informationModel.getName(), informationModel.getTown(), informationModel.getXiang_man(),
                        informationModel.getConstruction(), informationModel.getTeam(),
                        informationModel.getSupervision(), informationModel.getSup_man(),
                        informationModel.getRuhu_type(), (double) 0, (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0);

                //置入已完成工程量
                for (QuantityModel quantityModel : qm) {
                    res.setShitong(res.getShitong() + quantityModel.getShitong());
                    res.setRuhu_mi(res.getRuhu_mi() + quantityModel.getRuhu_mi());
                    res.setRuhu_hu(res.getRuhu_hu() + quantityModel.getRuhu_hu());
                    res.setJiexu(res.getJiexu() + quantityModel.getJiexu());
                    res.setXiangti(res.getXiangti() + quantityModel.getXiangti());
                    res.setGuanglan_4D(res.getGuanglan_4D() + quantityModel.getGuanglan_4D());
                    res.setGuanglan_8D(res.getGuanglan_8D() + quantityModel.getGuanglan_8D());
                    res.setGuanglan_12D(res.getGuanglan_12D() + quantityModel.getGuanglan_12D());
                    res.setGuanglan_24D(res.getGuanglan_24D() + quantityModel.getGuanglan_24D());
                    res.setGuanglan_48D(res.getGuanglan_48D() + quantityModel.getGuanglan_48D());
                    res.setGuanglan_72D(res.getGuanglan_72D() + quantityModel.getGuanglan_72D());
                    res.setGuanglan_96D(res.getGuanglan_96D() + quantityModel.getGuanglan_96D());
                    res.setGuanglan_144D(res.getGuanglan_144D() + quantityModel.getGuanglan_144D());
                    res.setGuanglan_288D(res.getGuanglan_288D() + quantityModel.getGuanglan_288D());
                    res.setZhimai(res.getZhimai() + quantityModel.getZhimai());
                    res.setKaiwa(res.getKaiwa() + quantityModel.getKaiwa());
                    res.setDingguan(res.getDingguan() + quantityModel.getDingguan());
                }

                //置入剩余工作量
                res.setShen_shitong(informationModel.getShitong() - res.getShitong());
                res.setShen_ruhu_mi(informationModel.getRuhu_mi() - res.getRuhu_mi());
                res.setShen_ruhu_hu(informationModel.getRuhu_hu() - res.getRuhu_hu());
                res.setShen_jiexu(informationModel.getJiexu() - res.getJiexu());
                res.setShen_xiangti(informationModel.getXiangti() - res.getXiangti());
                res.setShen_guanglan_4D(informationModel.getGuanglan_4D() - res.getGuanglan_4D());
                res.setShen_guanglan_8D(informationModel.getGuanglan_8D() - res.getGuanglan_8D());
                res.setShen_guanglan_12D(informationModel.getGuanglan_12D() - res.getGuanglan_12D());
                res.setShen_guanglan_24D(informationModel.getGuanglan_24D() - res.getGuanglan_24D());
                res.setShen_guanglan_48D(informationModel.getGuanglan_48D() - res.getGuanglan_48D());
                res.setShen_guanglan_72D(informationModel.getGuanglan_72D() - res.getGuanglan_72D());
                res.setShen_guanglan_96D(informationModel.getGuanglan_96D() - res.getGuanglan_96D());
                res.setShen_guanglan_144D(informationModel.getGuanglan_144D() - res.getGuanglan_144D());
                res.setShen_guanglan_288D(informationModel.getGuanglan_288D() - res.getGuanglan_288D());
                res.setShen_zhimai(informationModel.getZhimai() - res.getZhimai());
                res.setShen_kaiwa(informationModel.getKaiwa() - res.getKaiwa());
                res.setShen_dingguan(informationModel.getDingguan() - res.getDingguan());

                li_res.add(res);
            }

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (li_res.size() < min) {
                    return new ArrayList<>();
                } else if (li_res.size() < max) {
                    max = li_res.size();
                }

                //最终分页
                li_res = li_res.subList(min, max);
            }

            return li_res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以监理查询自身未审核的项目
     * <p>
     * 只有2级权限所有者可执行该操作
     */
    public List<QuantityModel> FindSup(String sup_man, String zhiname, String name,
                                       String link, String construction, String isready, String pa)
            throws Exception {
        //给定权限范围
        Integer[] quan = {2, 5};

        if (miservice.PiPei(zhiname, quan)) {
            List<QuantityModel> li = quantitydao.FindSup(sup_man, name, link, construction);

            //声明返回变量
            List<QuantityModel> res = new ArrayList<>();

            for (QuantityModel quantityModel : li) {
                if (Objects.equals(isready, "false")) {
                    if (Objects.equals(quantityModel.getSup_status(), "") ||
                            quantityModel.getSup_status() == null) {
                        res.add(quantityModel);
                    }
                } else if (Objects.equals(isready, "true")) {
                    if (!Objects.equals(quantityModel.getSup_status(), "") &&
                            quantityModel.getSup_status() != null) {
                        res.add(quantityModel);
                    }
                }
            }
            //按时间排序
            res.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (res.size() < min) {
                    return new ArrayList<>();
                } else if (res.size() < max) {
                    max = res.size();
                }

                //最终分页
                res = res.subList(min, max);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以监理、镇公司查询单个项目数据
     * <p>
     * 只有1、2、3、4、5级权限所有人才可执行该操作
     */
    public List<QuantityModel> FindOne(String coding, String time, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            return quantitydao.FindOne(coding, time);
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表修改
     * <p>
     * 用以监理审核完后置入监理审核结果
     * <p>
     * 只有2级权限所有人才可执行该操作
     */
    public void UpdateSup(String coding, String time, String sup_status, String sup_man,
                          String bei, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {2, 5};

        if (miservice.PiPei(zhiname, quan)) {
            List<QuantityModel> li = quantitydao.Find(coding, null, null, null, null,
                    null, null, null, null, null, time);
            String link = null;

            if (Objects.equals(sup_man, li.get(0).getSup_man())) {
                if (Objects.equals(sup_status, "true")) {
                    sup_status = "审核通过";
                    link = "镇公司审核";
                } else {
                    sup_status = "审核不通过";
                }

                quantitydao.Update(coding, time, sup_status, null, bei, null, link);
            } else {
                throw new Exception("非自身项目，请勿审核！！！");
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以镇公司查询自身未审核的项目
     * <p>
     * 只有3级权限所有人才可进行该操作
     */
    public List<QuantityModel> FindCom(String com_man, String zhiname, String name,
                                       String link, String construction, String isready, String pa)
            throws Exception {
        //给定权限范围
        Integer[] quan = {3, 5};

        if (miservice.PiPei(zhiname, quan)) {
            List<QuantityModel> li = quantitydao.FindCom(com_man, "审核通过", name, link, construction);

            //声明返回变量
            List<QuantityModel> res = new ArrayList<>();

            for (QuantityModel quantityModel : li) {
                if (Objects.equals(isready, "false")) {
                    if (Objects.equals(quantityModel.getCom_status(), "") ||
                            quantityModel.getCom_status() == null) {
                        res.add(quantityModel);
                    }
                } else if (Objects.equals(isready, "true")) {
                    if (!Objects.equals(quantityModel.getCom_status(), "") &&
                            quantityModel.getCom_status() != null) {
                        res.add(quantityModel);
                    }
                }
            }
            //按时间排序
            res.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (res.size() < min) {
                    return new ArrayList<>();
                } else if (res.size() < max) {
                    max = res.size();
                }

                //最终分页
                res = res.subList(min, max);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表修改
     * <p>
     * 用以将镇公司审核结果置入
     * <p>
     * 只有3级权限所有人才可执行该操作
     */
    public void UpdateCom(String coding, String time, String com_status, String com_man,
                          String bei, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {3, 5};

        if (miservice.PiPei(zhiname, quan)) {
            List<QuantityModel> li = quantitydao.Find(coding, null, null, null, null,
                    null, null, null, null, null, time);
            String link = "";

            if (Objects.equals(com_man, li.get(0).getCom_man())) {
                if (Objects.equals(com_status, "true")) {
                    com_status = "审核通过";
                    link = "审核完成";
                } else {
                    com_status = "审核不通过";
                }

                quantitydao.Update(coding, time, null, com_status, null, bei, link);
            } else {
                throw new Exception("非自身项目，请勿审核！！!");
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表修改
     * <p>
     * 用以施工单位修改其未经过审核的工程量信息
     * <p>
     * 只有1，5级权限所有人可执行该操作
     */
    public void UpdateShi(String zhiname, String coding, String time, String st, String rh_mi, String rh_hu, String jx,
                          String xt, String gl_4D, String gl_8D, String gl_12D, String gl_24D, String gl_48D,
                          String gl_72D, String gl_96D, String gl_144D, String gl_288D, String zm, String kw,
                          String dg, MultipartFile[] files) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //做限制
            //先查出要改的这个东西
            List<QuantityModel> ff = quantitydao.FindOne(coding, time);
            if (Objects.equals(ff.get(0).getLink(), "监理审批") &&
                    (Objects.equals(ff.get(0).getSup_status(), "") || ff.get(0).getSup_status() == null)) {
                //字符串转double
                //Double shitong = strtoanythingservice.StrToDou(st);
                Double ruhu_mi = strtoanythingservice.StrToDou(rh_mi);
                //Double ruhu_hu = strtoanythingservice.StrToDou(rh_hu);
                Double jiexu = strtoanythingservice.StrToDou(jx);
                Double xiangti = strtoanythingservice.StrToDou(xt);
                Double guanglan_4D = strtoanythingservice.StrToDou(gl_4D);
                Double guanglan_8D = strtoanythingservice.StrToDou(gl_8D);
                Double guanglan_12D = strtoanythingservice.StrToDou(gl_12D);
                Double guanglan_24D = strtoanythingservice.StrToDou(gl_24D);
                Double guanglan_48D = strtoanythingservice.StrToDou(gl_48D);
                Double guanglan_72D = strtoanythingservice.StrToDou(gl_72D);
                Double guanglan_96D = strtoanythingservice.StrToDou(gl_96D);
                Double guanglan_144D = strtoanythingservice.StrToDou(gl_144D);
                Double guanglan_288D = strtoanythingservice.StrToDou(gl_288D);
                Double zhimai = strtoanythingservice.StrToDou(zm);
                Double kaiwa = strtoanythingservice.StrToDou(kw);
                Double dingguan = strtoanythingservice.StrToDou(dg);

                //删除原有图片
                imageservice.DeleteImg(ff.get(0).getPicture());

                String construction = ff.get(0).getConstruction();
                String team = ff.get(0).getTeam();
                String name = ff.get(0).getName();
                //记录图片
                //生成日期名称
                Date da = new Date();
                SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy年MM月dd日");
                SimpleDateFormat time_sdf = new SimpleDateFormat("HH时mm分");

                //生成施工队名称
                String con_team = construction + team;

                //开始生成文件夹
                Path path = Paths.get("C://工建系统/工程量上报/" + con_team + "/" +
                        name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da));
                Files.createDirectories(path);

                //开始存入图片并生成对应路径
                int i = 0;
                StringBuilder picture = new StringBuilder();
                String[] filePath = new String[9];
                int filePath_num = 0;
                for (MultipartFile multipartFile : files) {
                    //生成数据库存储路径
                    if (i != 0) {
                        picture.append(";");
                    }

                    //生成三位数的编码
                    String num = "00" + i;
                    i++;

                    //获取图片名称
                    String originalFilename = multipartFile.getOriginalFilename();
                    assert originalFilename != null;
                    String[] filename = originalFilename.split("\\.");

                    //生成防撞编码
                    int fz = 0;
                    String fznum = getfz(fz);

                    //生成图片路径
                    filePath[filePath_num] = "C://工建系统/工程量上报/" + con_team + "/" +
                            name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                            "." + filename[1];
                    File file = new File(filePath[filePath_num]);

                    while (file.exists()) {
                        fz++;
                        fznum = getfz(fz);

                        filePath[filePath_num] = "C://工建系统/工程量上报/" + con_team + "/" +
                                name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                                "." + filename[1];
                        file = new File(filePath[filePath_num]);
                    }
                    filePath_num++;

                    //生成路径
                    String callPath = "https://www.dgcatv.top:8787/Img/Quantity/" + con_team + "/" +
                            name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                            "." + filename[1];
                    picture.append(callPath);
                }

                //生成model实例
                QuantityModel qm = new QuantityModel(coding, null, time, null, null,
                        new Date(), null, (double) 0, ruhu_mi, (double) 0, jiexu, xiangti, guanglan_4D,
                        guanglan_8D, guanglan_12D, guanglan_24D, guanglan_48D, guanglan_72D, guanglan_96D,
                        guanglan_144D, guanglan_288D, zhimai, kaiwa, dingguan, null, null, null,
                        null, null, null, null, null,
                        String.valueOf(picture));

                //修改
                quantitydao.UpdateAll(qm);
                //存入图片
                int j = 0;
                for (MultipartFile multipartFile : files) {
                    multipartFile.transferTo(new File(filePath[j]));
                    j++;
                }
            } else {
                throw new Exception("您无法修改已被审批的工程量信息！如果有误，请联系管理员修改！！");
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以返回对应工程的工程量进度，该部分涉及到计算
     * <p>
     * 只有1、2、3、4、5级权限所有人才可执行该操作
     */
    public List<ReturnYiQuantityModel> CalculateQuantity(String coding, String zhiname,
                                                         String name, String town,
                                                         String construction, String pa) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //查基础信息表，得到基础数据
            List<InformationModel> im = informationservice.Find(town, coding, name, null,
                    construction, null, null, null, zhiname, null);

            //返回值
            List<ReturnYiQuantityModel> li = new ArrayList<>();
            for (InformationModel informationModel : im) {
                //查工程量表，得到工程量信息
                List<QuantityModel> qm = quantitydao.Find(informationModel.getCoding(), null, null,
                        null, null, null, null, null, "审核通过",
                        "审核通过", null);

                //声明返回值
                ReturnYiQuantityModel ryqm = new ReturnYiQuantityModel(informationModel.getCoding(),
                        informationModel.getTown(), informationModel.getName(), informationModel.getXiang_man(),
                        informationModel.getConstruction(), informationModel.getTeam(), informationModel.getStatus(),
                        informationModel.getRuhu_type(), (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0,
                        (double) 0, (double) 0, (double) 0);

                if (informationModel.getWei_date() == null) {
                    //返回值
                    li.add(ryqm);
                } else if (Objects.equals(informationModel.getStatus(), "已完工")) {
                    ryqm.setJindu((double) 100);
                    li.add(ryqm);
                } else {
                    //置入已完成工程量
                    for (QuantityModel quantityModel : qm) {
                        ryqm.setShitong(ryqm.getShitong() + quantityModel.getShitong());
                        ryqm.setRuhu_mi(ryqm.getRuhu_mi() + quantityModel.getRuhu_mi());
                        ryqm.setRuhu_hu(ryqm.getRuhu_hu() + quantityModel.getRuhu_hu());
                        ryqm.setJiexu(ryqm.getJiexu() + quantityModel.getJiexu());
                        ryqm.setXiangti(ryqm.getXiangti() + quantityModel.getXiangti());
                        ryqm.setGuanglan_4D(ryqm.getGuanglan_4D() + quantityModel.getGuanglan_4D());
                        ryqm.setGuanglan_8D(ryqm.getGuanglan_8D() + quantityModel.getGuanglan_8D());
                        ryqm.setGuanglan_12D(ryqm.getGuanglan_12D() + quantityModel.getGuanglan_12D());
                        ryqm.setGuanglan_24D(ryqm.getGuanglan_24D() + quantityModel.getGuanglan_24D());
                        ryqm.setGuanglan_48D(ryqm.getGuanglan_48D() + quantityModel.getGuanglan_48D());
                        ryqm.setGuanglan_72D(ryqm.getGuanglan_72D() + quantityModel.getGuanglan_72D());
                        ryqm.setGuanglan_96D(ryqm.getGuanglan_96D() + quantityModel.getGuanglan_96D());
                        ryqm.setGuanglan_144D(ryqm.getGuanglan_144D() + quantityModel.getGuanglan_144D());
                        ryqm.setGuanglan_288D(ryqm.getGuanglan_288D() + quantityModel.getGuanglan_288D());
                        ryqm.setZhimai(ryqm.getZhimai() + quantityModel.getZhimai());
                        ryqm.setKaiwa(ryqm.getKaiwa() + quantityModel.getKaiwa());
                        ryqm.setDingguan(ryqm.getDingguan() + quantityModel.getDingguan());
                    }

                    //算出总体工程总量
                    double all = informationModel.getShitong() + informationModel.getRuhu_mi() +
                            informationModel.getRuhu_hu() + informationModel.getJiexu() +
                            informationModel.getXiangti() + informationModel.getGuanglan_4D() +
                            informationModel.getGuanglan_8D() + informationModel.getGuanglan_12D() +
                            informationModel.getGuanglan_24D() + informationModel.getGuanglan_48D() +
                            informationModel.getGuanglan_72D() + informationModel.getGuanglan_96D() +
                            informationModel.getGuanglan_144D() + informationModel.getGuanglan_288D() +
                            informationModel.getZhimai() + informationModel.getKaiwa() +
                            informationModel.getDingguan();

                    if (all == 0) {
                        ryqm.setJindu((double) 0);
                    } else {
                        //根据现有工程量计算整体工程进度
                        double lv = 0; //完成进度百分比
                        if (informationModel.getShitong() != 0 || ryqm.getShitong() != 0) {
                            //计算权重值
                            double zhong = informationModel.getShitong() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getShitong() != 0) {
                                bi = ryqm.getShitong() / informationModel.getShitong();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getRuhu_mi() != 0 || ryqm.getRuhu_mi() != 0) {
                            //计算权重值
                            double zhong = informationModel.getRuhu_mi() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getRuhu_mi() != 0) {
                                bi = ryqm.getRuhu_mi() / informationModel.getRuhu_mi();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getRuhu_hu() != 0 || ryqm.getRuhu_hu() != 0) {
                            //计算权重值
                            double zhong = informationModel.getRuhu_hu() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getRuhu_hu() != 0) {
                                bi = ryqm.getRuhu_hu() / informationModel.getRuhu_hu();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getJiexu() != 0 || ryqm.getJiexu() != 0) {
                            //计算权重值
                            double zhong = informationModel.getJiexu() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getJiexu() != 0) {
                                bi = ryqm.getJiexu() / informationModel.getJiexu();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getXiangti() != 0 || ryqm.getXiangti() != 0) {
                            //计算权重值
                            double zhong = informationModel.getXiangti() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getXiangti() != 0) {
                                bi = ryqm.getXiangti() / informationModel.getXiangti();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getGuanglan_4D() != 0 || ryqm.getGuanglan_4D() != 0) {
                            //计算权重值
                            double zhong = informationModel.getGuanglan_4D() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getGuanglan_4D() != 0) {
                                bi = ryqm.getGuanglan_4D() / informationModel.getGuanglan_4D();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getGuanglan_8D() != 0 || ryqm.getGuanglan_8D() != 0) {
                            //计算权重值
                            double zhong = informationModel.getGuanglan_8D() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getGuanglan_8D() != 0) {
                                bi = ryqm.getGuanglan_8D() / informationModel.getGuanglan_8D();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getGuanglan_12D() != 0 || ryqm.getGuanglan_12D() != 0) {
                            //计算权重值
                            double zhong = informationModel.getGuanglan_12D() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getGuanglan_12D() != 0) {
                                bi = ryqm.getGuanglan_12D() / informationModel.getGuanglan_12D();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getGuanglan_24D() != 0 || ryqm.getGuanglan_24D() != 0) {
                            //计算权重值
                            double zhong = informationModel.getGuanglan_24D() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getGuanglan_24D() != 0) {
                                bi = ryqm.getGuanglan_24D() / informationModel.getGuanglan_24D();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getGuanglan_48D() != 0 || ryqm.getGuanglan_48D() != 0) {
                            //计算权重值
                            double zhong = informationModel.getGuanglan_48D() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getGuanglan_48D() != 0) {
                                bi = ryqm.getGuanglan_48D() / informationModel.getGuanglan_48D();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getGuanglan_72D() != 0 || ryqm.getGuanglan_72D() != 0) {
                            //计算权重值
                            double zhong = informationModel.getGuanglan_72D() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getGuanglan_72D() != 0) {
                                bi = ryqm.getGuanglan_72D() / informationModel.getGuanglan_72D();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getGuanglan_96D() != 0 || ryqm.getGuanglan_96D() != 0) {
                            //计算权重值
                            double zhong = informationModel.getGuanglan_96D() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getGuanglan_96D() != 0) {
                                bi = ryqm.getGuanglan_96D() / informationModel.getGuanglan_96D();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getGuanglan_144D() != 0 || ryqm.getGuanglan_144D() != 0) {
                            //计算权重值
                            double zhong = informationModel.getGuanglan_144D() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getGuanglan_144D() != 0) {
                                bi = ryqm.getGuanglan_144D() / informationModel.getGuanglan_144D();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getGuanglan_288D() != 0 || ryqm.getGuanglan_288D() != 0) {
                            //计算权重值
                            double zhong = informationModel.getGuanglan_288D() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getGuanglan_288D() != 0) {
                                bi = ryqm.getGuanglan_288D() / informationModel.getGuanglan_288D();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getZhimai() != 0 || ryqm.getZhimai() != 0) {
                            //计算权重值
                            double zhong = informationModel.getZhimai() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getZhimai() != 0) {
                                bi = ryqm.getZhimai() / informationModel.getZhimai();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getKaiwa() != 0 || ryqm.getKaiwa() != 0) {
                            //计算权重值
                            double zhong = informationModel.getKaiwa() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getKaiwa() != 0) {
                                bi = ryqm.getKaiwa() / informationModel.getKaiwa();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        if (informationModel.getDingguan() != 0 || ryqm.getDingguan() != 0) {
                            //计算权重值
                            double zhong = informationModel.getDingguan() / all;
                            //计算完成比例
                            double bi = 1;
                            if (informationModel.getDingguan() != 0) {
                                bi = ryqm.getDingguan() / informationModel.getDingguan();
                            }
                            //配上权重得到完成率
                            lv += bi * zhong;
                        }
                        ryqm.setJindu(lv * 100);
                    }

                    BigDecimal bg = BigDecimal.valueOf(ryqm.getJindu());
                    ryqm.setJindu(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());

                    li.add(ryqm);
                }
            }

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (li.size() < min) {
                    return new ArrayList<>();
                } else if (li.size() < max) {
                    max = li.size();
                }

                //最终分页
                li = li.subList(min, max);
            }

            return li;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以返回工程量超额的信息，做告警处理
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public List<ReturnGaoModel> FindGao(String zhiname, String pa) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先查项目
            List<InformationModel> im = informationservice.FindTeam(null, null);

            //声明返回值
            List<ReturnGaoModel> res = new ArrayList<>();

            //计算数据
            for (InformationModel informationModel : im) {
                List<ReturnYiQuantityModel> ryqm = CalculateQuantity(informationModel.getCoding(), zhiname,
                        null, null, null, null);

                if (ryqm.get(0).getJindu() > 100) {
                    ReturnGaoModel rgm = new ReturnGaoModel(informationModel.getCoding(), informationModel.getName(),
                            ryqm.get(0).getJindu());
                    res.add(rgm);
                }
            }

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (res.size() < min) {
                    return new ArrayList<>();
                } else if (res.size() < max) {
                    max = res.size();
                }

                //最终分页
                res = res.subList(min, max);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表查询
     * <p>
     * 用以返回单项超出得项目，做警告处理
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public List<ReturnOneGaoModel> FindOneGao(String zhiname, String pa) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先查项目
            List<InformationModel> im = informationservice.FindTeam(null, null);

            //声明返回值
            List<ReturnOneGaoModel> res = new ArrayList<>();

            //计算数据
            for (InformationModel informationModel : im) {
                //先查该项目得工程量
                List<ReturnYiQuantityModel> ryqm = CalculateQuantity(informationModel.getCoding(), zhiname,
                        null, null, null, null);

                //对比工程量
                res.add(duibi(informationModel.getShitong(), ryqm.get(0).getShitong(), "管道试通",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getRuhu_mi(), ryqm.get(0).getRuhu_mi(), "入户线敷设米数",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getRuhu_hu(), ryqm.get(0).getRuhu_hu(), "入户线敷设户数",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getJiexu(), ryqm.get(0).getJiexu(), "光缆接续",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getXiangti(), ryqm.get(0).getXiangti(), "箱体安装",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getGuanglan_4D(), ryqm.get(0).getGuanglan_4D(), "4D光缆",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getGuanglan_8D(), ryqm.get(0).getGuanglan_8D(), "8D光缆",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getGuanglan_12D(), ryqm.get(0).getGuanglan_12D(), "12D光缆",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getGuanglan_24D(), ryqm.get(0).getGuanglan_24D(), "24D光缆",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getGuanglan_48D(), ryqm.get(0).getGuanglan_48D(), "48D光缆",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getGuanglan_72D(), ryqm.get(0).getGuanglan_72D(), "72D光缆",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getGuanglan_96D(), ryqm.get(0).getGuanglan_96D(), "96D光缆",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getGuanglan_144D(), ryqm.get(0).getGuanglan_144D(), "144D光缆",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getGuanglan_288D(), ryqm.get(0).getGuanglan_288D(), "288D光缆",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getZhimai(), ryqm.get(0).getZhimai(), "子管直埋",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getKaiwa(), ryqm.get(0).getKaiwa(), "开挖路面",
                        informationModel.getCoding(), informationModel.getName()));
                res.add(duibi(informationModel.getDingguan(), ryqm.get(0).getDingguan(), "顶管",
                        informationModel.getCoding(), informationModel.getName()));
            }
            res.removeIf(Objects::isNull);

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (res.size() < min) {
                    return new ArrayList<>();
                } else if (res.size() < max) {
                    max = res.size();
                }

                //最终分页
                res = res.subList(min, max);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 私有方法
     * <p>
     * 用以对比工程量并置入model实例
     */
    private ReturnOneGaoModel duibi(double yuan, double xian, String mu, String coding, String name) {
        if (yuan < xian) {
            return new ReturnOneGaoModel(coding, name, mu, yuan, xian);
        } else {
            return null;
        }
    }

    /**
     * 工程量表添加
     * <p>
     * 工程量表批量添加，用以管理员批量导入已完成工程量
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public void BatchAdd(MultipartFile file, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);

            try {
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    XSSFRow row = sheet.getRow(i);

                    //生成model实例
                    QuantityModel qm = setModel(row, i);

                    //次数time生成,寻找最大值
                    List<QuantityModel> li = quantitydao.Find(qm.getCoding(), null, null, null,
                            null, null, null, null, null, null,
                            null);
                    int max = 0;
                    for (QuantityModel quantityModel : li) {
                        if (strtoanythingservice.StrToInt(quantityModel.getTime()) > max) {
                            max = strtoanythingservice.StrToInt(quantityModel.getTime());
                        }
                    }
                    qm.setTime(String.valueOf(max + 1));

                    //添加
                    quantitydao.Add(qm);
                }
            } catch (Exception e) {
                throw new Exception("Excel表格中存在错误的数据类型，请检查并修改！" + e);
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 工程量表修改
     * <p>
     * 工程量表批量修改，用以管理员批量修改已完成工程量
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public void BatchUpdate(MultipartFile file, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);

            try {
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    XSSFRow row = sheet.getRow(i);

                    //生成model实例
                    QuantityModel qm = setModel(row, i);

                    qm.setDate(new Date());
                    qm.setLink("监理审批");
                    qm.setSup_status(null);
                    qm.setSup_bei(null);
                    qm.setCom_status(null);
                    qm.setCom_bei(null);

                    //添加
                    quantitydao.UpdateAll(qm);
                }
            } catch (Exception e) {
                throw new Exception("Excel表格中存在错误的数据类型，请检查并修改！" + e);
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 私有方法
     * <p>
     * 应以批量时生成model
     */
    private QuantityModel setModel(XSSFRow row, Integer i) throws Exception {
        int num = 0;
        try {
            QuantityModel qm = new QuantityModel();

            qm.setCoding(GetCellValue(row.getCell(num++)));
            qm.setName(GetCellValue(row.getCell(num++)));
            qm.setTime(GetCellValue(row.getCell(num++)));

            qm.setConstruction(GetCellValue(row.getCell(num++)));
            qm.setTeam(GetCellValue(row.getCell(num++)));

            //生成上报时间
            qm.setDate(new Date());

            qm.setRuhu_type(GetCellValue(row.getCell(num++)));
            qm.setShitong((double) 0);
            qm.setRuhu_mi(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setRuhu_hu((double) 0);

            qm.setJiexu(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setXiangti(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setGuanglan_4D(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setGuanglan_8D(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setGuanglan_12D(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setGuanglan_24D(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setGuanglan_48D(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setGuanglan_72D(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setGuanglan_96D(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setGuanglan_144D(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setGuanglan_288D(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setZhimai(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setKaiwa(Double.parseDouble(GetCellValue(row.getCell(num++))));
            qm.setDingguan(Double.parseDouble(GetCellValue(row.getCell(num++))));

            qm.setType(GetCellValue(row.getCell(num++)));

            //生成审批环节
            qm.setLink("监理审批");

            qm.setSup_man(GetCellValue(row.getCell(num++)));
            qm.setCom_man(GetCellValue(row.getCell(num++)));

            return qm;
        } catch (Exception e) {
            throw new Exception("第" + i + "行 " + "第" + num + "列数据发生错误！！");
        }
    }

    /**
     * 私有方法
     * <p>
     * 用以根据类型插入值
     */
    private String GetCellValue(Cell cell) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (BatchService.GetCellNull(cell)) {
            switch (BatchService.GetCellType(cell)) {
                case "String" -> {
                    return cell.getStringCellValue();
                }
                case "Number" -> {
                    if (BatchService.GetCellDate(cell)) {
                        return sdf.format(cell.getDateCellValue());
                    } else {
                        return NumberToTextConverter.toText(cell.getNumericCellValue());
                    }
                }
                default -> {
                    return "";
                }
            }
        } else {
            return "";
        }
    }

    /**
     * 私有方法
     * <p>
     * 用以生成防撞车编码，不做位数限制
     */
    private String getfz(int coding) {
        //防撞码自增
        coding++;

        //返回编码
        if (coding < 10) {
            return "0" + coding;
        } else {
            return String.valueOf(coding);
        }
    }
}
