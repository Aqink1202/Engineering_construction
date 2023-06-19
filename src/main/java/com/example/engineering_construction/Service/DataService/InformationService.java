package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Dao.MySQLDao.InformationDao;
import com.example.engineering_construction.Dao.MySQLDao.LoadingDao;
import com.example.engineering_construction.Model.DataModel.InformationModel;
import com.example.engineering_construction.Model.DataModel.LoadingModel;
import com.example.engineering_construction.Model.ReturnModel.ReturnPieModel;
import com.example.engineering_construction.Model.ReturnModel.ReturnStatusGaoModel;
import com.example.engineering_construction.Service.ProcessService.BatchService;
import com.example.engineering_construction.Service.ProcessService.MiService;
import com.example.engineering_construction.Service.ProcessService.StrToAnythingService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class InformationService {

    @Autowired
    InformationDao informationdao;
    @Autowired
    StrToAnythingService strtoanythingservice;
    @Autowired
    MiService miservice;
    @Autowired
    LoadingDao loadingdao;
    @Autowired
    BatchService batchservice;

    /**
     * 基础信息添加
     * <p>
     * 用以管理员添加项目的基础信息，以便后续修改
     * <p>
     * 只有4、5级权限所有者才可操作
     */
    public void Add(String coding, String town, String type, String name, String li_da,
                    String pi_name, String pi_da, String chou_da, String he_year, String build_year,
                    String xiang_man, String construction, String team, String supervision, String sup_man,
                    String electronic_version, String shen_da, String wei_da, String wan_da, String bei,
                    String jg, String rg, String zl, String jl, String qt, String a,
                    String guimo, String hj, String ruhu_type, String st, String rh_mi, String rh_hu,
                    String jx, String xt, String gl_4D, String gl_8D, String gl_12D, String gl_24D,
                    String gl_48D, String gl_72D, String gl_96D, String gl_144D, String gl_288D, String zm,
                    String kw, String dg, String zhiname)
            throws Exception {
        //字符串转日期
        Date li_date = strtoanythingservice.StrToDate(li_da);
        Date pi_date = strtoanythingservice.StrToDate(pi_da);
        Date chou_date = strtoanythingservice.StrToDate(chou_da);
        Date shen_date = strtoanythingservice.StrToDate(shen_da);
        Date wei_date = strtoanythingservice.StrToDate(wei_da);
        Date wan_date = strtoanythingservice.StrToDate(wan_da);

        //字符串转浮点数
        Double jiagong = strtoanythingservice.StrToDou(jg);
        Double rengong = strtoanythingservice.StrToDou(rg);
        Double zhanlie = strtoanythingservice.StrToDou(zl);
        Double jianli = strtoanythingservice.StrToDou(jl);
        Double qita = strtoanythingservice.StrToDou(qt);
        Double allmoney = strtoanythingservice.StrToDou(a);
        Double hujun = strtoanythingservice.StrToDou(hj);
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

        //生成项目状态
        String status = "暂未更新，每日12点将自动更新！！";

        //生成Model实例
        InformationModel im = new InformationModel(coding, town, type, name, li_date, pi_name, pi_date, chou_date,
                he_year, build_year, xiang_man, construction, team, supervision, sup_man, electronic_version,
                shen_date, wei_date, wan_date, bei, status, jiagong, rengong, zhanlie, jianli, qita, allmoney, guimo,
                hujun, ruhu_type, (double) 0, ruhu_mi, (double) 0, jiexu, xiangti, guanglan_4D, guanglan_8D,
                guanglan_12D, guanglan_24D, guanglan_48D, guanglan_72D, guanglan_96D, guanglan_144D, guanglan_288D,
                zhimai, kaiwa, dingguan);

        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //添加
            informationdao.Add(im);
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 基础信息表删除
     * <p>
     * 需要密码，用以管理员删除部分无用信息
     * <p>
     * 只有4、5级权限所有者才可操作
     */
    public void Delete(String coding, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            informationdao.Delete(coding);
            informationdao.DeleteId();
            informationdao.AddId();
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 基础信息表修改
     * <p>
     * 用以用户修改输入的基础信息
     * <p>
     * 只有4、5级权限所有者才可操作
     */
    public void Update(String coding, String town, String type, String name, String li_da,
                       String pi_name, String pi_da, String chou_da, String he_year, String build_year,
                       String xiang_man, String construction, String team, String supervision, String sup_man,
                       String electronic_version, String shen_da, String wei_da, String wan_da, String bei,
                       String jg, String rg, String zl, String jl, String qt, String a,
                       String guimo, String hj, String ruhu_type, String st, String rh_mi, String rh_hu,
                       String jx, String xt, String gl_4D, String gl_8D, String gl_12D, String gl_24D,
                       String gl_48D, String gl_72D, String gl_96D, String gl_144D, String gl_288D, String zm,
                       String kw, String dg, String zhiname)
            throws Exception {
        //字符串转日期
        Date li_date = strtoanythingservice.StrToDate(li_da);
        Date pi_date = strtoanythingservice.StrToDate(pi_da);
        Date chou_date = strtoanythingservice.StrToDate(chou_da);
        Date shen_date = strtoanythingservice.StrToDate(shen_da);
        Date wei_date = strtoanythingservice.StrToDate(wei_da);
        Date wan_date = strtoanythingservice.StrToDate(wan_da);

        //字符串转浮点数
        Double jiagong = strtoanythingservice.StrToDou(jg);
        Double rengong = strtoanythingservice.StrToDou(rg);
        Double zhanlie = strtoanythingservice.StrToDou(zl);
        Double jianli = strtoanythingservice.StrToDou(jl);
        Double qita = strtoanythingservice.StrToDou(qt);
        Double allmoney = strtoanythingservice.StrToDou(a);
        Double hujun = strtoanythingservice.StrToDou(hj);
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

        //生成项目状态
        String status = "暂未更新，每日12点将自动更新！！";

        //生成Model实例
        InformationModel im = new InformationModel(coding, town, type, name, li_date, pi_name, pi_date, chou_date,
                he_year, build_year, xiang_man, construction, team, supervision, sup_man, electronic_version,
                shen_date, wei_date, wan_date, bei, status, jiagong, rengong, zhanlie, jianli, qita, allmoney, guimo,
                hujun, ruhu_type, (double) 0, ruhu_mi, (double) 0, jiexu, xiangti, guanglan_4D, guanglan_8D,
                guanglan_12D, guanglan_24D, guanglan_48D, guanglan_72D, guanglan_96D, guanglan_144D, guanglan_288D,
                zhimai, kaiwa, dingguan);

        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //修改
            informationdao.Update(im);
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 基础信息表修改
     * <p>
     * 用以管理员将项目状态置完工或验收
     * <p>
     * 只有4、5级权限所有者才可操作
     */
    public void UpdateStatus(String coding, String status, String zhiname) throws Exception {
        //生成Model实例
        InformationModel im = new InformationModel(coding, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, status, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null, null);

        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //修改
            informationdao.Update(im);
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 基础信息表查找数据
     * <p>
     * 用以用户查找自身需要的项目数据
     * <p>
     * 任何级别权限都可以操作,需根据权限等级做相应限制
     */
    public List<InformationModel> Find(String town, String coding, String name, String company, String construction,
                                       String supervision, String team, String type, String zhiname, String pa)
            throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先查操作者的用户信息
            List<LoadingModel> li = loadingdao.Find(zhiname, null);
            Integer deng = miservice.DengJi(li.get(0).getBao());

            //根据权限等级做限制
            switch (deng) {
                case 1 -> {
                    construction = li.get(0).getCompany();
                    team = li.get(0).getTeam();
                }
                case 2 -> supervision = li.get(0).getCompany();
                case 3 -> company = li.get(0).getCompany();
            }

            //分页
            Integer page;
            if (Objects.equals(pa, "") || pa == null) {
                page = null;
            } else {
                page = strtoanythingservice.StrToInt(pa);
                if (page >= 1) {
                    page = (page - 1) * 10;
                } else {
                    page = 0;
                }
            }

            return informationdao.Find(town, coding, name, company, construction, supervision, team, type, page);
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 基础信息表查找数据——施工队队伍查找对应项目
     * <p>
     * 用以输出施工队各队伍对应的项目数据
     * <p>
     * 不做限制，该部分专供施工队
     */
    public List<InformationModel> FindTeam(String construction, String team) {
        //先查数据
        List<InformationModel> li = informationdao.FindTeam(construction, team);
        List<InformationModel> res = new ArrayList<>();

        //数据处理-去除完工、已验收项目
        for (InformationModel informationModel : li) {
            if (!Objects.equals(informationModel.getStatus(), "已完工") &&
                    !Objects.equals(informationModel.getStatus(), "已验收")) {
                res.add(informationModel);
            }
        }

        return res;
    }

    /**
     * 基础信息表查询
     * <p>
     * 用以输出已逾期未完工的项目，做告警处理
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public List<ReturnStatusGaoModel> FindStatusGao(String zhiname, String coding, String town, String name,
                                                    String construction, String pa) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先查数据
            List<InformationModel> li_find = Find(town, coding, name, null, construction,
                    null, null, null, zhiname, null);

            //数据处理-去除完工、已验收项目
            List<InformationModel> li = new ArrayList<>();
            for (InformationModel informationModel : li_find) {
                if (!Objects.equals(informationModel.getStatus(), "已完工") ||
                        !Objects.equals(informationModel.getStatus(), "已验收")) {
                    li.add(informationModel);
                }
            }

            //声明返回值
            List<ReturnStatusGaoModel> res = new ArrayList<>();
            for (InformationModel informationModel : li) {
                if (Objects.equals(informationModel.getStatus(), "逾期未完工")) {
                    ReturnStatusGaoModel rsgm = new ReturnStatusGaoModel(informationModel.getCoding(),
                            informationModel.getName(), informationModel.getStatus());
                    res.add(rsgm);
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
     * 基础信息表批量
     * <p>
     * 用于管理员批量导入基础信息表，简化操作
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
                    InformationModel im = setModel(row, i);

                    //添加
                    informationdao.Add(im);
                }
            } catch (Exception e) {
                throw new Exception("Excel表格中存在错误的数据类型，请检查并修改！" + e);
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 基础信息表批量
     * <p>
     * 用于管理员批量修改基础信息表，简化操作
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
                    InformationModel im = setModel(row, i);

                    //添加
                    informationdao.Update(im);
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
    private InformationModel setModel(XSSFRow row, Integer i) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        int num = 0;
        try {
            InformationModel im = new InformationModel();
            im.setCoding(GetCellValue(row.getCell(num++)));
            im.setTown(GetCellValue(row.getCell(num++)));
            im.setType(GetCellValue(row.getCell(num++)));
            im.setName(GetCellValue(row.getCell(num++)));

            im.setLi_date(!Objects.equals(GetCellValue(row.getCell(num++)), "") ?
                    sdf.parse(GetCellValue(row.getCell(num - 1))) : null);

            im.setPi_name(GetCellValue(row.getCell(num++)));

            im.setPi_date(!Objects.equals(GetCellValue(row.getCell(num++)), "") ?
                    sdf.parse(GetCellValue(row.getCell(num - 1))) : null);
            im.setChou_date(!Objects.equals(GetCellValue(row.getCell(num++)), "") ?
                    sdf.parse(GetCellValue(row.getCell(num - 1))) : null);

            im.setHe_year(GetCellValue(row.getCell(num++)));
            im.setBuild_year(GetCellValue(row.getCell(num++)));
            im.setXiang_man(GetCellValue(row.getCell(num++)));
            im.setConstruction(GetCellValue(row.getCell(num++)));
            im.setTeam(GetCellValue(row.getCell(num++)));
            im.setSupervision(GetCellValue(row.getCell(num++)));
            im.setSup_man(GetCellValue(row.getCell(num++)));

            im.setElectronic_version(GetCellValue(row.getCell(num++)));

            im.setShen_date(!Objects.equals(GetCellValue(row.getCell(num++)), "") ?
                    sdf.parse(GetCellValue(row.getCell(num - 1))) : null);
            im.setWei_date(!Objects.equals(GetCellValue(row.getCell(num++)), "") ?
                    sdf.parse(GetCellValue(row.getCell(num - 1))) : null);
            im.setWan_date(!Objects.equals(GetCellValue(row.getCell(num++)), "") ?
                    sdf.parse(GetCellValue(row.getCell(num - 1))) : null);

            im.setBei(GetCellValue(row.getCell(num++)));
            im.setStatus(GetCellValue(row.getCell(num++)));

            im.setJiagong(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setRengong(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setZhanlie(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setJianli(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setQita(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setAllmoney(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuimo(GetCellValue(row.getCell(num++)));
            im.setHujun(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setRuhu_type(GetCellValue(row.getCell(num++)));
            im.setShitong((double) 0);
            im.setRuhu_mi(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setRuhu_hu((double) 0);
            im.setJiexu(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setXiangti(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuanglan_4D(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuanglan_8D(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuanglan_12D(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuanglan_24D(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuanglan_48D(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuanglan_72D(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuanglan_96D(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuanglan_144D(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setGuanglan_288D(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setZhimai(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setKaiwa(SetStringDouble(GetCellValue(row.getCell(num++))));
            im.setDingguan(SetStringDouble(GetCellValue(row.getCell(num++))));

            return im;
        } catch (Exception e) {
            throw new Exception("第" + i + "行 " + "第" + num + "列数据发生错误！！");
        }
    }

    /**
     * 私有方法
     * <p>
     * 当输入值不为空时，转化成数值，否则置null
     */
    private Double SetStringDouble(String in) {
        if (BatchService.GetStringNull(in)) {
            return Double.valueOf(in);
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

    /**
     * 基础信息表查询
     * <p>
     * 用以返回各项目项目状态的饼图
     * <p>
     * 该部分不做权限限制
     */
    public List<ReturnPieModel> FindStatusPie(String construction, String he_year, String build_year) {
        //先查数据
        List<InformationModel> li = informationdao.FindPie(construction, he_year, build_year);

        //声明返回值
        List<ReturnPieModel> res = new ArrayList<>();

        //数据处理
        int weikaigong = 0, weiwangong = 0, yiwangong = 0, kaigongzhong = 0, tinggongzhong = 0;
        for (InformationModel informationModel : li) {
            switch (informationModel.getStatus()) {
                case "未委托" -> weikaigong++;
                case "逾期未完工" -> weiwangong++;
                case "已完工" -> yiwangong++;
                case "开工中" -> kaigongzhong++;
                case "停工中" -> tinggongzhong++;
            }
        }

        //置入数据
        res.add(new ReturnPieModel(weikaigong, "未委托"));
        res.add(new ReturnPieModel(kaigongzhong, "开工中"));
        res.add(new ReturnPieModel(weiwangong, "逾期未完工"));
        res.add(new ReturnPieModel(tinggongzhong, "停工中"));
        res.add(new ReturnPieModel(yiwangong, "已完工"));

        return res;
    }

}
