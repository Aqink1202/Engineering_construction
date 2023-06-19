package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Dao.MySQLDao.StopDao;
import com.example.engineering_construction.Model.DataModel.InformationModel;
import com.example.engineering_construction.Model.DataModel.StopModel;
import com.example.engineering_construction.Model.FindModel.FindDateNameModel;
import com.example.engineering_construction.Service.ProcessService.BatchService;
import com.example.engineering_construction.Service.ProcessService.MiService;
import com.example.engineering_construction.Service.ProcessService.StrToAnythingService;
import org.apache.commons.collections4.Get;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class StopService {

    @Autowired
    StopDao stopdao;
    @Autowired
    StrToAnythingService strtoanythingservice;
    @Autowired
    InformationService informationservice;
    @Autowired
    MiService miservice;

    /**
     * 停复工表增加
     * <p>
     * 用以管理员录入工程停复工信息
     * <p>
     * 只有4、5级权限所有人才可执行该操作
     */
    public void Add(String coding, String name, String stat, String end, String zhiname) throws Exception {
        //字符串转整数
        List<StopModel> li = stopdao.Find(coding, name);
        Integer time = li.size() + 1;

        //字符串转date
        Date stat_date = strtoanythingservice.StrToDate(stat);
        Date end_date = strtoanythingservice.StrToDate(end);

        //生成Model实例
        StopModel sm = new StopModel(coding, name, time, stat_date, end_date);

        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            stopdao.Add(sm);
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 停复工表删除
     * <p>
     * 用以管理员删除错误的停复工信息
     * <p>
     * 只有4、5级权限所有人才可执行该操作
     */
    public void Delete(String coding, String ti, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            Integer time = strtoanythingservice.StrToInt(ti);

            if (coding == null || coding.equals("")) {
                throw new Exception("项目编码或次序不可为空！！！");
            }

            stopdao.Delete(coding, time);
            stopdao.DeleteId();
            stopdao.AddId();
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 停复工表修改
     * <p>
     * 用以管理员修改停复工日期
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public void Update(String coding, String name, String ti, String stat, String end,
                       String zhiname) throws Exception {
        //字符串转整数
        Integer time = strtoanythingservice.StrToInt(ti);

        //字符串转date
        Date stat_date = strtoanythingservice.StrToDate(stat);
        Date end_date = strtoanythingservice.StrToDate(end);

        //形成model实例
        StopModel sm = new StopModel(coding, name, time, stat_date, end_date);

        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            stopdao.Update(sm);
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 停复工表查询
     * <p>
     * 用以管理员查看所有项目停复工信息
     * <p>
     * 只有4、5级权限所有人才可执行该操作
     */
    public List<StopModel> Find(String coding, String name, String zhiname, String pa) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //根据zhiname查项目基本信息
            List<InformationModel> li = informationservice.Find(null, coding, name, null,
                    null, null, null, null, zhiname, null);

            //声明返回值
            List<StopModel> res = new ArrayList<>();

            //数据处理
            if (li.size() != 0) {
                for (InformationModel informationModel : li) {
                    res.addAll(stopdao.Find(informationModel.getCoding(), informationModel.getName()));
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
     * 停复工表查询
     * <p>
     * 用以得出该项目当前状态
     * <p>
     * 自动更新、无需做权限处理
     */
    public String FindStatus(String coding) throws Exception {
        //先查基础信息表
        List<InformationModel> im = informationservice.Find(null, coding, null, null,
                null, null, null, null, "Aqink", null);

        if (im.get(0).getWei_date() == null) {
            return "未委托";
        }

        //再查停复工表
        List<StopModel> sm = stopdao.Find(coding, null);

        //得到今日日期
        Date date = new Date();

        //将日期分段
        List<FindDateNameModel> li_fdnm = new ArrayList<>();
        FindDateNameModel f_kai = new FindDateNameModel("开工", im.get(0).getWei_date());  //置入起始时间
        li_fdnm.add(f_kai);

        Date wan = im.get(0).getWan_date();
        for (StopModel stopModel : sm) {
            if (stopModel.getStat_date().before(wan)) {
                FindDateNameModel f_ting = new FindDateNameModel("停工", stopModel.getStat_date());
                FindDateNameModel f_fu = new FindDateNameModel("复工", stopModel.getEnd_date());
                li_fdnm.add(f_ting);
                li_fdnm.add(f_fu);

                //计算天数
                int time = daysBetween(stopModel.getStat_date(), wan);

                //得到新的完工日期
                wan = addDate(stopModel.getEnd_date(), time);
            }
        }

        FindDateNameModel f_wan = new FindDateNameModel("完工", wan);
        li_fdnm.add(f_wan);

        //分段后，用今日日期判断出项目状态
        String status = "";
        for (int i = 0; i < li_fdnm.size() - 1; i++) {
            //逾期
            if (date.after(li_fdnm.get(li_fdnm.size() - 1).getDate())) {
                status = "逾期未完工";
                break;
            }

            //未委托
            if (date.before(li_fdnm.get(0).getDate())) {
                status = "未委托";
                break;
            }

            //正常情况
            if (date.after(li_fdnm.get(i).getDate()) && date.before(li_fdnm.get(i + 1).getDate())) {
                switch (li_fdnm.get(i).getName()) {
                    case "开工", "复工" -> status = "开工中";
                    case "停工" -> status = "停工中";
                }
            }
        }

        return status;
    }


    /**
     * 私有方法
     * <p>
     * 用以得到两日期间计算天数
     */
    public int daysBetween(Date smdate, Date bdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 私有方法
     * <p>
     * 用以计算日期增加对应天数后的新日期
     */
    public Date addDate(Date d, long day) {
        long time = d.getTime();
        day = day * 24 * 60 * 60 * 1000;
        time += day;
        return new Date(time);
    }

    /**
     * 停复工表添加
     * <p>
     * 用以批量导入停复工数据
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

                    StopModel sm = setModel(row, i);

                    List<StopModel> li = stopdao.Find(sm.getCoding(), sm.getName());
                    sm.setTime(li.size() + 1);

                    stopdao.Add(sm);
                }
            } catch (Exception e) {
                throw new Exception("Excel表格中存在错误的数据类型，请检查并修改！" + e);
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 停复工表添加
     * <p>
     * 用以批量删除后导入停复工数据
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public void BatchDeleteAndAdd(MultipartFile file, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先删数据
            stopdao.Delete(null, null);
            stopdao.DeleteId();
            stopdao.AddId();

            //再添加数据
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);

            try {
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    XSSFRow row = sheet.getRow(i);

                    StopModel sm = setModel(row, i);

                    List<StopModel> li = stopdao.Find(sm.getCoding(), sm.getName());
                    sm.setTime(li.size() + 1);

                    stopdao.Add(sm);
                }

                //查询除已完工外所有项目
                List<InformationModel> li_in = informationservice.FindTeam(null, null);

                //寻找status并修改
                for (InformationModel informationModel : li_in) {
                    try {
                        String status = FindStatus(informationModel.getCoding());
                        informationservice.UpdateStatus(informationModel.getCoding(), status, "Aqink");
                    } catch (Exception ignored) {
                        throw new Exception("项目状态更新失败！！");
                    }
                }

            } catch (Exception e) {
                throw new Exception("Excel表格中存在错误的数据类型，请检查并修改！" + e);
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 停复工表修改
     * <p>
     * 用以批量修改停复工表
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

                    StopModel sm = setModel(row, i);

                    stopdao.Update(sm);
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
     * 生成model实例
     */
    public StopModel setModel(XSSFRow row, Integer i) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        int num = 0;
        try {
            StopModel sm = new StopModel();

            sm.setCoding(GetCellValue(row.getCell(num++)));
            sm.setName(GetCellValue(row.getCell(num++)));

            sm.setTime(SetStringInteger(GetCellValue(row.getCell(num++))));

            sm.setStat_date(!Objects.equals(GetCellValue(row.getCell(num++)), "") ?
                    sdf.parse(GetCellValue(row.getCell(num - 1))) : null);
            sm.setEnd_date(!Objects.equals(GetCellValue(row.getCell(num++)), "") ?
                    sdf.parse(GetCellValue(row.getCell(num - 1))) : null);

            return sm;
        } catch (Exception e) {
            throw new Exception("第" + i + "行 " + "第" + num + "列数据发生错误！！");
        }
    }

    /**
     * 私有方法
     * <p>
     * 当输入值不为空时，转化成数值，否则置null
     */
    private Integer SetStringInteger(String in) {
        if (BatchService.GetStringNull(in)) {
            return Integer.valueOf(in);
        } else {
            return null;
        }
    }

    /**
     * 私有方法
     * <p>
     * 用以根据类型插入值
     */
    private String GetCellValue(Cell cell) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

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
}
