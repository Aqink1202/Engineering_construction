package com.example.engineering_construction.Service.ExCQTService;

import com.example.engineering_construction.Dao.MySQLDao.ZhanAndCQTDao;
import com.example.engineering_construction.Model.DataModel.CQTModel;
import com.example.engineering_construction.Model.DataModel.InformationModel;
import com.example.engineering_construction.Model.DataModel.ZhanAndCQTModel;
import com.example.engineering_construction.Model.ExCQTModel.ExcelModel;
import com.example.engineering_construction.Service.ProcessService.LALService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


@Transactional
@Service
public class ExcelService {

    @Autowired
    ZhanAndCQTDao zhanandcqtdao;
    @Autowired
    LALService lalservice;

    public List<ExcelModel> excel(MultipartFile file) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = wb.getSheetAt(0);

        //声明返回值
        List<ExcelModel> res = new ArrayList<>();

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);

            //生成Model实例
            ExcelModel em = setModel(row, i);

            res.add(em);
        }

        return res;
    }

    private ExcelModel setModel(XSSFRow row, Integer i) throws Exception {
        try {
            ExcelModel im = new ExcelModel();

            im.setName(row.getCell(2).getStringCellValue());
            im.setPhone(row.getCell(3).getStringCellValue());
            im.setShen(row.getCell(4).getStringCellValue());
            im.setCompany(row.getCell(5).getStringCellValue());
            im.setGe(row.getCell(7).getStringCellValue());
            im.setAlladd(row.getCell(8).getStringCellValue());
            im.setStyle(row.getCell(9).getStringCellValue());
            im.setLng(row.getCell(10).getStringCellValue());
            im.setLat(row.getCell(11).getStringCellValue());

            im.setGdif_5g(row.getCell(12).getStringCellValue());
            if (Objects.equals(im.getGdif_5g(), "有信号")) {
                im.setGdnr(row.getCell(13).getStringCellValue());
                im.setGdfreq_5g(row.getCell(14).getStringCellValue());
                im.setGdpci_5g(row.getCell(15).getStringCellValue());
                im.setGdeci_5g(row.getCell(16).getStringCellValue());
                im.setGdrsrp_5g(row.getCell(17).getStringCellValue());
                im.setGdsinr_5g(row.getCell(18).getStringCellValue());
                im.setGd10099_5g(row.getCell(19).getStringCellValue());
            }

            im.setYdif_5g(row.getCell(20).getStringCellValue());
            if (Objects.equals(im.getYdif_5g(), "有信号")) {
                im.setYdnr(row.getCell(21).getStringCellValue());
                im.setYdfreq_5g(row.getCell(22).getStringCellValue());
                im.setYdpci_5g(row.getCell(23).getStringCellValue());
                im.setYdeci_5g(row.getCell(24).getStringCellValue());
                im.setYdrsrp_5g(row.getCell(25).getStringCellValue());
                im.setYdsinr_5g(row.getCell(26).getStringCellValue());
            }

            im.setGdif_4g(row.getCell(27).getStringCellValue());
            if (Objects.equals(im.getGdif_4g(), "有信号")) {
                im.setGdlte(row.getCell(28).getStringCellValue());
                im.setGdfreq_4g(row.getCell(29).getStringCellValue());
                im.setGdpci_4g(row.getCell(30).getStringCellValue());
                im.setGdeci_4g(row.getCell(31).getStringCellValue());
                im.setGdrsrp_4g(row.getCell(32).getStringCellValue());
                im.setGdsinr_4g(row.getCell(33).getStringCellValue());
                im.setGd10099_4g(row.getCell(34).getStringCellValue());
            }

            im.setYdif_4g(row.getCell(35).getStringCellValue());
            if (Objects.equals(im.getYdif_4g(), "有信号")) {
                im.setYdlte(row.getCell(36).getStringCellValue());
                im.setYdfreq_4g(row.getCell(37).getStringCellValue());
                im.setYdpci_4g(row.getCell(38).getStringCellValue());
                im.setYdeci_4g(row.getCell(39).getStringCellValue());
                im.setYdrsrp_4g(row.getCell(40).getStringCellValue());
                im.setYdsinr_4g(row.getCell(41).getStringCellValue());
            }

            return im;

        } catch (Exception e) {
            throw new Exception("第" + i + "行数据发生错误！！");
        }
    }


    public void find(List<ExcelModel> em) {
        List<ExcelModel> res = new ArrayList<>();
        List<ZhanAndCQTModel> li_zhan = zhanandcqtdao.Find();
        int number = 0;

        //生成随机数
        Random ran = new Random();
        for (int i = 0; i < 15000; ) {
            int mu = ran.nextInt(em.size());

            if (!res.contains(em.get(mu))) {
                res.add(em.get(mu));
                i = res.size();
            }
        }

        XSSFWorkbook wb;
        OutputStream out = null;
        try {
            wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("CQT测试表"); //重命名sheet1的工作表名
            Row r = sheet.createRow(0);            //0 表示 第一行

            //给表头定义字段名
            r.createCell(0).setCellValue("新增序号");
            r.createCell(1).setCellValue("填报日期");
            r.createCell(2).setCellValue("姓名");
            r.createCell(3).setCellValue("手机号");
            r.createCell(4).setCellValue("请选择省份城市地区");
            r.createCell(5).setCellValue("所属分公司");
            r.createCell(6).setCellValue("测试点地址（需细化到楼宇层数）");
            r.createCell(7).setCellValue("所属网格（XX市XX区XX网格）");
            r.createCell(8).setCellValue("您的地理位置");
            r.createCell(9).setCellValue("场所属性");
            r.createCell(10).setCellValue("经度");
            r.createCell(11).setCellValue("纬度");
            r.createCell(12).setCellValue("广电5G信号监测");
            r.createCell(13).setCellValue("广电5G网络小区类型");
            r.createCell(14).setCellValue("广电5G网络小区频点");
            r.createCell(15).setCellValue("广电5G网络基站信息");
            r.createCell(16).setCellValue("ECI或NR-CI");
            r.createCell(17).setCellValue("SS-RSRP");
            r.createCell(18).setCellValue("SS-SINR");
            r.createCell(19).setCellValue("广电5G是否能打通10099");
            r.createCell(20).setCellValue("移动5G信号对比");
            r.createCell(21).setCellValue("移动5G网络小区类型");
            r.createCell(22).setCellValue("移动5G网络小区频点");
            r.createCell(23).setCellValue("移动5G网络基站信息");
            r.createCell(24).setCellValue("ECI或NR-CI");
            r.createCell(25).setCellValue("SS-RSRP");
            r.createCell(26).setCellValue("SS-SINR");
            r.createCell(27).setCellValue("广电4G信号监测");
            r.createCell(28).setCellValue("广电4G网络小区类型");
            r.createCell(29).setCellValue("广电4G网络小区频点");
            r.createCell(30).setCellValue("广电4G网络基站信息");
            r.createCell(31).setCellValue("ECI或NR-CI");
            r.createCell(32).setCellValue("RSRP");
            r.createCell(33).setCellValue("SINR");
            r.createCell(34).setCellValue("广电4G是否能打通10099");
            r.createCell(35).setCellValue("移动4G信号对比");
            r.createCell(36).setCellValue("移动4G网络小区类型");
            r.createCell(37).setCellValue("移动4G网络小区频点");
            r.createCell(38).setCellValue("移动4G网络基站信息");
            r.createCell(39).setCellValue("ECI或NR-CI");
            r.createCell(40).setCellValue("RSRP");
            r.createCell(41).setCellValue("SINR");

            //声明33个镇公司数组
            List<String> chashan = new ArrayList<>();
            List<String> changping = new ArrayList<>();
            List<String> chengqu = new ArrayList<>();
            List<String> dalang = new ArrayList<>();
            List<String> dalinshan = new ArrayList<>();
            List<String> daojiao = new ArrayList<>();
            List<String> dongcheng = new ArrayList<>();
            List<String> dongkeng = new ArrayList<>();
            List<String> fenggang = new ArrayList<>();
            List<String> gaobu = new ArrayList<>();
            List<String> hengli = new ArrayList<>();
            List<String> hongmei = new ArrayList<>();
            List<String> houjie = new ArrayList<>();
            List<String> humen = new ArrayList<>();
            List<String> huangjiang = new ArrayList<>();
            List<String> liaobu = new ArrayList<>();
            List<String> machong = new ArrayList<>();
            List<String> nancheng = new ArrayList<>();
            List<String> qishi = new ArrayList<>();
            List<String> qiaotou = new ArrayList<>();
            List<String> qinxi = new ArrayList<>();
            List<String> shatian = new ArrayList<>();
            List<String> shijie = new ArrayList<>();
            List<String> shilong = new ArrayList<>();
            List<String> shipai = new ArrayList<>();
            List<String> songshanhu = new ArrayList<>();
            List<String> tangxia = new ArrayList<>();
            List<String> wanjiang = new ArrayList<>();
            List<String> wanniudun = new ArrayList<>();
            List<String> xiegang = new ArrayList<>();
            List<String> zhanmutou = new ArrayList<>();
            List<String> changan = new ArrayList<>();
            List<String> zhongtang = new ArrayList<>();

            int i = 1;
            for (ExcelModel ex : res) {
                System.out.println(ex);

                r = sheet.createRow(i); //按照对象的index对应第几行
                r.createCell(0).setCellValue(i++);

                Random rndDay = new Random();
                int Day = rndDay.nextInt(31) + 1;
                String da = "2022/12/" + Day;
                r.createCell(1).setCellValue(da);

                switch (ex.getCompany()) {
                    case "茶山" -> {
                        if (!chashan.contains(ex.getName() + "/" + ex.getPhone())) {
                            chashan.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(chashan.size());
                        String[] arr1 = chashan.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "常平" -> {
                        if (!changping.contains(ex.getName() + "/" + ex.getPhone())) {
                            changping.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(changping.size());
                        String[] arr1 = changping.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "城区" -> {
                        if (!chengqu.contains(ex.getName() + "/" + ex.getPhone())) {
                            chengqu.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(chengqu.size());
                        String[] arr1 = chengqu.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "大朗" -> {
                        if (!dalang.contains(ex.getName() + "/" + ex.getPhone())) {
                            dalang.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(dalang.size());
                        String[] arr1 = dalang.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "大岭山" -> {
                        if (!dalinshan.contains(ex.getName() + "/" + ex.getPhone())) {
                            dalinshan.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(dalinshan.size());
                        String[] arr1 = dalinshan.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "道滘" -> {
                        if (!daojiao.contains(ex.getName() + "/" + ex.getPhone())) {
                            daojiao.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(daojiao.size());
                        String[] arr1 = daojiao.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "东城" -> {
                        if (!dongcheng.contains(ex.getName() + "/" + ex.getPhone())) {
                            dongcheng.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(dongcheng.size());
                        String[] arr1 = dongcheng.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "东坑" -> {
                        if (!dongkeng.contains(ex.getName() + "/" + ex.getPhone())) {
                            dongkeng.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(dongkeng.size());
                        String[] arr1 = dongkeng.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "凤岗" -> {
                        if (!fenggang.contains(ex.getName() + "/" + ex.getPhone())) {
                            fenggang.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(fenggang.size());
                        String[] arr1 = fenggang.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "高埗" -> {
                        if (!gaobu.contains(ex.getName() + "/" + ex.getPhone())) {
                            gaobu.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(gaobu.size());
                        String[] arr1 = gaobu.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "横沥" -> {
                        if (!hengli.contains(ex.getName() + "/" + ex.getPhone())) {
                            hengli.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(hengli.size());
                        String[] arr1 = hengli.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "洪梅" -> {
                        if (!hongmei.contains(ex.getName() + "/" + ex.getPhone())) {
                            hongmei.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(hongmei.size());
                        String[] arr1 = hongmei.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "厚街" -> {
                        if (!houjie.contains(ex.getName() + "/" + ex.getPhone())) {
                            houjie.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(houjie.size());
                        String[] arr1 = houjie.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "虎门" -> {
                        if (!humen.contains(ex.getName() + "/" + ex.getPhone())) {
                            humen.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(humen.size());
                        String[] arr1 = humen.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "黄江" -> {
                        if (!huangjiang.contains(ex.getName() + "/" + ex.getPhone())) {
                            huangjiang.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(huangjiang.size());
                        String[] arr1 = huangjiang.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "寮步" -> {
                        if (!liaobu.contains(ex.getName() + "/" + ex.getPhone())) {
                            liaobu.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(liaobu.size());
                        String[] arr1 = liaobu.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "麻涌" -> {
                        if (!machong.contains(ex.getName() + "/" + ex.getPhone())) {
                            machong.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(machong.size());
                        String[] arr1 = machong.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "南城" -> {
                        if (!nancheng.contains(ex.getName() + "/" + ex.getPhone())) {
                            nancheng.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(nancheng.size());
                        String[] arr1 = nancheng.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "企石" -> {
                        if (!qishi.contains(ex.getName() + "/" + ex.getPhone())) {
                            qishi.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(qishi.size());
                        String[] arr1 = qishi.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "桥头" -> {
                        if (!qiaotou.contains(ex.getName() + "/" + ex.getPhone())) {
                            qiaotou.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(qiaotou.size());
                        String[] arr1 = qiaotou.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "清溪" -> {
                        if (!qinxi.contains(ex.getName() + "/" + ex.getPhone())) {
                            qinxi.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(qinxi.size());
                        String[] arr1 = qinxi.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "沙田" -> {
                        if (!shatian.contains(ex.getName() + "/" + ex.getPhone())) {
                            shatian.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(shatian.size());
                        String[] arr1 = shatian.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "石碣" -> {
                        if (!shijie.contains(ex.getName() + "/" + ex.getPhone())) {
                            shijie.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(shijie.size());
                        String[] arr1 = shijie.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "石龙" -> {
                        if (!shilong.contains(ex.getName() + "/" + ex.getPhone())) {
                            shilong.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(shilong.size());
                        String[] arr1 = shilong.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "石排" -> {
                        if (!shipai.contains(ex.getName() + "/" + ex.getPhone())) {
                            shipai.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(shipai.size());
                        String[] arr1 = shipai.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "松山湖" -> {
                        if (!songshanhu.contains(ex.getName() + "/" + ex.getPhone())) {
                            songshanhu.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(songshanhu.size());
                        String[] arr1 = songshanhu.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "塘厦" -> {
                        if (!tangxia.contains(ex.getName() + "/" + ex.getPhone())) {
                            tangxia.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(tangxia.size());
                        String[] arr1 = tangxia.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "万江" -> {
                        if (!wanjiang.contains(ex.getName() + "/" + ex.getPhone())) {
                            wanjiang.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(wanjiang.size());
                        String[] arr1 = wanjiang.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "望牛墩" -> {
                        if (!wanniudun.contains(ex.getName() + "/" + ex.getPhone())) {
                            wanniudun.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(wanniudun.size());
                        String[] arr1 = wanniudun.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "谢岗" -> {
                        if (!xiegang.contains(ex.getName() + "/" + ex.getPhone())) {
                            xiegang.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(xiegang.size());
                        String[] arr1 = xiegang.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "樟木头" -> {
                        if (!zhanmutou.contains(ex.getName() + "/" + ex.getPhone())) {
                            zhanmutou.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(zhanmutou.size());
                        String[] arr1 = zhanmutou.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "长安" -> {
                        if (!changan.contains(ex.getName() + "/" + ex.getPhone())) {
                            changan.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(changan.size());
                        String[] arr1 = changan.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                    case "中堂" -> {
                        if (!zhongtang.contains(ex.getName() + "/" + ex.getPhone())) {
                            zhongtang.add(ex.getName() + "/" + ex.getPhone());
                        }
                        int nn = ran.nextInt(zhongtang.size());
                        String[] arr1 = zhongtang.get(nn).split("/");
                        r.createCell(2).setCellValue(arr1[0]);
                        r.createCell(3).setCellValue(arr1[1]);
                    }
                }

                r.createCell(4).setCellValue(ex.getShen());
                r.createCell(5).setCellValue(ex.getCompany());

                if (ex.getAlladd().contains("[")) {
                    ex.setAddress(ex.getAlladd().substring(0, ex.getAlladd().indexOf("[")));
                    ex.setAlladd(ex.getAlladd().substring(0, ex.getAlladd().indexOf("[")));
                } else {
                    ex.setAddress(ex.getAlladd());
                }
                if (ex.getAddress().contains("靠近")) {
                    ex.setAddress(ex.getAddress().substring(0, ex.getAlladd().indexOf("靠近")));
                }
                if (ex.getAddress().contains("广东省东莞市")) {
                    ex.setAddress(ex.getAddress().replace("广东省东莞市", ""));
                }
                if (!ex.getAddress().contains(ex.getCompany())) {
                    ex.setAddress(ex.getCompany() + ex.getAddress());
                }
                r.createCell(6).setCellValue(ex.getAddress());


                r.createCell(7).setCellValue(ex.getGe());
                r.createCell(8).setCellValue(ex.getAlladd());
                r.createCell(9).setCellValue(ex.getStyle());

                String lng = li_zhan.get(number).getLongitude() + rndDay.nextInt(10);
                r.createCell(10).setCellValue(lng);
                String lat = li_zhan.get(number).getLatitude() + rndDay.nextInt(10);
                r.createCell(11).setCellValue(lat);
                number++;

                r.createCell(12).setCellValue(ex.getGdif_5g());
                r.createCell(13).setCellValue(ex.getGdnr());
                r.createCell(14).setCellValue(ex.getGdfreq_5g());
                r.createCell(15).setCellValue(ex.getGdpci_5g());
                r.createCell(16).setCellValue(ex.getGdeci_5g());
                r.createCell(17).setCellValue(ex.getGdrsrp_5g());
                r.createCell(18).setCellValue(ex.getGdsinr_5g());
                r.createCell(19).setCellValue(ex.getGd10099_5g());
                r.createCell(20).setCellValue(ex.getYdif_5g());
                r.createCell(21).setCellValue(ex.getYdnr());
                r.createCell(22).setCellValue(ex.getYdfreq_5g());
                r.createCell(23).setCellValue(ex.getYdpci_5g());
                r.createCell(24).setCellValue(ex.getYdeci_5g());
                r.createCell(25).setCellValue(ex.getYdrsrp_5g());
                r.createCell(26).setCellValue(ex.getYdsinr_5g());
                r.createCell(27).setCellValue(ex.getGdif_4g());
                r.createCell(28).setCellValue(ex.getGdlte());
                r.createCell(29).setCellValue(ex.getGdfreq_4g());
                r.createCell(30).setCellValue(ex.getGdpci_4g());
                r.createCell(31).setCellValue(ex.getGdeci_4g());
                r.createCell(32).setCellValue(ex.getGdrsrp_4g());
                r.createCell(33).setCellValue(ex.getGdsinr_4g());
                r.createCell(34).setCellValue(ex.getGd10099_4g());
                r.createCell(35).setCellValue(ex.getYdif_4g());
                r.createCell(36).setCellValue(ex.getYdlte());
                r.createCell(37).setCellValue(ex.getYdfreq_4g());
                r.createCell(38).setCellValue(ex.getYdpci_4g());
                r.createCell(39).setCellValue(ex.getYdeci_4g());
                r.createCell(40).setCellValue(ex.getYdrsrp_4g());
                r.createCell(41).setCellValue(ex.getYdsinr_4g());
            }
            out = new FileOutputStream("D:\\15000条数据.xlsx"); // 字节输出流
            wb.write(out); //导出excel
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
