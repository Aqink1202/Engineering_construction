package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Dao.MySQLDao.LoadingDao;
import com.example.engineering_construction.Dao.MySQLDao.ManReportDao;
import com.example.engineering_construction.Model.DataModel.LoadingModel;
import com.example.engineering_construction.Model.DataModel.ManReportModel;
import com.example.engineering_construction.Service.ProcessService.ImageService;
import com.example.engineering_construction.Service.ProcessService.MiService;
import com.example.engineering_construction.Service.ProcessService.StrToAnythingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ManReportService {

    @Autowired
    ManReportDao manreportdao;
    @Autowired
    StrToAnythingService strtoanythingservice;
    @Autowired
    MiService miservice;
    @Autowired
    LoadingDao loadingdao;
    @Autowired
    ImageService imageservice;

    /**
     * 上报表增加数据
     * <p>
     * 用以施工队日常上报是否开工情况
     * <p>
     * 只有1级权限所有人可执行该操作
     */
    public void Add(String name, String open, String address, String longitude, String latitude, String town,
                    String construction, String team, String ma, String content, String zhiname,
                    MultipartFile[] files) throws Exception {
        //生成时间
        Date date = new Date();

        //字符串转整数
        Integer man = strtoanythingservice.StrToInt(ma);

        //生成日期名称
        Date da = new Date();
        SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat time_sdf = new SimpleDateFormat("HH时mm分");

        //生成施工队名称
        String con_team = construction + team;

        //开始生成文件夹
        Path path = Paths.get("C://工建系统/每日上报/" + con_team + "/" +
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
            filePath[filePath_num] = "C://工建系统/每日上报/" + con_team + "/" +
                    name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                    "." + filename[1];
            File file = new File(filePath[filePath_num]);

            while (file.exists()) {
                fz++;
                fznum = getfz(fz);

                filePath[filePath_num] = "C://工建系统/每日上报/" + con_team + "/" +
                        name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                        "." + filename[1];
                file = new File(filePath[filePath_num]);
            }
            filePath_num++;

            //生成路径
            String callPath = "https://www.dgcatv.top:8787/Img/Report/" + con_team + "/" +
                    name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                    "." + filename[1];
            picture.append(callPath);
        }

        //生成Model实例
        ManReportModel mrm = new ManReportModel(name, date, open, address, longitude, latitude, town,
                construction, team, man, content, String.valueOf(picture));

        //给定权限范围
        Integer[] quan = {1, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //添加
            manreportdao.Add(mrm);
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
     * 上报表增加数据——小程序专用
     * <p>
     * 用以施工队日常上报是否开工情况
     * <p>
     * 只有1级权限所有人可执行该操作
     */
    public void AddXiao(String name, String open, String address, String longitude, String latitude, String town,
                        String construction, String team, String ma, String content, String zhiname,
                        MultipartFile[] files) throws Exception {
        //生成时间
        Date date = new Date();

        //字符串转整数
        Integer man = strtoanythingservice.StrToInt(ma);

        //生成日期名称
        Date da = new Date();
        SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat time_sdf = new SimpleDateFormat("HH时mm分");

        //生成施工队名称
        String con_team = construction + team;

        //开始生成文件夹
        Path path = Paths.get("C://工建系统/每日上报/" + con_team + "/" +
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
            filePath[filePath_num] = "C://工建系统/每日上报/" + con_team + "/" +
                    name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                    "." + filename[1];
            File file = new File(filePath[filePath_num]);

            while (file.exists()) {
                fz++;
                fznum = getfz(fz);

                filePath[filePath_num] = "C://工建系统/每日上报/" + con_team + "/" +
                        name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                        "." + filename[1];
                file = new File(filePath[filePath_num]);
            }
            filePath_num++;

            //生成路径
            String callPath = "https://www.dgcatv.top:8787/Img/Report/" + con_team + "/" +
                    name + "/" + date_sdf.format(da) + "/" + time_sdf.format(da) + "/" + num + fznum +
                    "." + filename[1];
            picture.append(callPath);
        }

        //生成Model实例
        ManReportModel mrm = new ManReportModel(name, date, open, address, longitude, latitude, town,
                construction, team, man, content, String.valueOf(picture));

        //给定权限范围
        Integer[] quan = {1, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //添加
            manreportdao.Add(mrm);
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
     * 上报表删除
     * <p>
     * 用以管理员删除日常上报中的无用信息
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public void Delete(String name, String id, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            List<ManReportModel> li = manreportdao.Find(name, null, null, null,
                    null, null, null, null, strtoanythingservice.StrToInt(id));
            imageservice.DeleteImg(li.get(0).getPicture());

            manreportdao.Delete(name, strtoanythingservice.StrToInt(id));
            manreportdao.DeleteId();
            manreportdao.AddId();
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 上报表查询
     * <p>
     * 用以管理员查询日常上报情况，筛选信息
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public List<ManReportModel> Find(String name, String da1, String da2, String town, String construction,
                                     String team, String zhiname, String pa, String open)
            throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            Date date1 = strtoanythingservice.StrToDate(da1);
            Date date2 = strtoanythingservice.StrToDate(da2);

            //先查操作者的用户信息
            List<LoadingModel> li = loadingdao.Find(zhiname, null);
            Integer deng = miservice.DengJi(li.get(0).getBao());

            //根据权限等级做限制
            switch (deng) {
                case 1 -> {
                    construction = li.get(0).getCompany();
                    team = li.get(0).getTeam();
                }
                case 3 -> town = li.get(0).getCompany();
            }

            List<ManReportModel> res = manreportdao.Find(name, date1, date2, town, construction, team,
                    null, open, null);
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
