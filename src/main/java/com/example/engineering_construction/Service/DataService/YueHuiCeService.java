package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Model.DataModel.YueHuiCeModel;
import com.example.engineering_construction.Service.ProcessService.BatchService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class YueHuiCeService {

    public String GetYueHuiCeExcel(MultipartFile file) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = wb.getSheetAt(0);
        List<YueHuiCeModel> li = new ArrayList<>();

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            YueHuiCeModel edc = setModel(row);

            edc.setYouxiao(isNeed(edc));
            li.add(edc);
        }

        return SetExcel(li);
    }

    /**
     * 私有方法
     * <p>
     * 用以生成excel表格
     */
    private String SetExcel(List<YueHuiCeModel> li) throws Exception {
        FileSystemView view = FileSystemView.getFileSystemView();
        File home = view.getHomeDirectory();
        String homelu = home.getPath();

        File file = new File(homelu + "\\YueHuiCe.xlsx");
        String res = "http://localhost:8787/YueHuiCe/out_YueHuiCe.xlsx";
        File Outfile = new File(homelu + "\\out_YueHuiCe.xlsx");

        if (Outfile.exists()) {
            res += "   " + Outfile.delete();
        }

        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFCellStyle weneed = wb.createCellStyle();
        weneed.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        weneed.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int you = 0;
        for (int i = 1; i < li.size() + 1; i++) {
            XSSFRow row = sheet.createRow(i);
            YueHuiCeModel edc = li.get(i - 1);

            if (edc.getYouxiao()) {
                row.setRowStyle(weneed);
                you++;
            }

            int num = 0;
            setCellStyle(weneed, row, num++, String.valueOf(i - 1), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getUpdate(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getTime(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getIp(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getDate(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getName(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getPhone(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getImei(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getShi(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getCeaddress(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getWangge(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getAddress(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getType(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getLatitude(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getLongitude(), edc.getYouxiao());

            setCellStyle(weneed, row, num++, edc.getGd5xinhao(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd5nr(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd5pinlv(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd5pci(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd5ecl(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd5rsrp(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd5sinr(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd510099(), edc.getYouxiao());
            setCellStyle(weneed, row, num, edc.getGd5ping(), edc.getYouxiao());
            num += 3;
            setCellStyle(weneed, row, num++, edc.getGd5result(), edc.getYouxiao());

            setCellStyle(weneed, row, num++, edc.getYd5xinhao(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd5nr(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd5pinlv(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd5pci(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd5ecl(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd5rsrp(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd5sinr(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd5result(), edc.getYouxiao());

            setCellStyle(weneed, row, num++, edc.getGd4xinhao(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd4nr(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd4pinlv(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd4pci(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd4ecl(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd4rsrp(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd4sinr(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd410099(), edc.getYouxiao());
            setCellStyle(weneed, row, num, edc.getGd4ping(), edc.getYouxiao());
            num += 3;
            setCellStyle(weneed, row, num++, edc.getGd4result(), edc.getYouxiao());

            setCellStyle(weneed, row, num++, edc.getYd4xinhao(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd4nr(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd4pinlv(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd4pci(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd4ecl(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd4rsrp(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd4sinr(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd4result(), edc.getYouxiao());

            setCellStyle(weneed, row, num++, edc.getGd5p(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd5p(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getIf5p(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getGd4p(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getYd4p(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getIf4p(), edc.getYouxiao());
            setCellStyle(weneed, row, num, edc.getResult(), edc.getYouxiao());
        }

        inputStream.close();
        FileOutputStream fileOutputStream = new FileOutputStream(Outfile);
        wb.write(fileOutputStream);
        fileOutputStream.close();

        res += "   总数:" + li.size() + " 有效数:" + you + " 比例:" + String.format("%.2f",
                (double) you / li.size() * 100);
        return res;
    }


    /**
     * 私有方法
     * <p>
     * 用以生成model实例
     */
    private YueHuiCeModel setModel(XSSFRow row) throws Exception {
        YueHuiCeModel ed = new YueHuiCeModel();

        int num = 0;
        try {
            ed.setId(GetCellValue(row.getCell(num++)));
            ed.setUpdate(GetCellValue(row.getCell(num++)));
            ed.setTime(GetCellValue(row.getCell(num++)));
            ed.setIp(GetCellValue(row.getCell(num++)));
            ed.setDate(GetCellValue(row.getCell(num++)));
            ed.setName(GetCellValue(row.getCell(num++)));
            ed.setPhone(GetCellValue(row.getCell(num++)));
            ed.setImei(GetCellValue(row.getCell(num++)));
            ed.setShi(GetCellValue(row.getCell(num++)));
            ed.setCeaddress(GetCellValue(row.getCell(num++)));
            ed.setWangge(GetCellValue(row.getCell(num++)));
            ed.setAddress(GetCellValue(row.getCell(num++)));
            ed.setType(GetCellValue(row.getCell(num++)));
            ed.setLatitude(GetCellValue(row.getCell(num++)));
            ed.setLongitude(GetCellValue(row.getCell(num++)));

            ed.setGd5xinhao(GetCellValue(row.getCell(num++)));
            ed.setGd5nr(GetCellValue(row.getCell(num++)));
            ed.setGd5pinlv(GetCellValue(row.getCell(num++)));
            ed.setGd5pci(GetCellValue(row.getCell(num++)));
            ed.setGd5ecl(GetCellValue(row.getCell(num++)));
            ed.setGd5rsrp(GetCellValue(row.getCell(num++)));
            ed.setGd5sinr(GetCellValue(row.getCell(num++)));
            ed.setGd510099(GetCellValue(row.getCell(num++)));
            ed.setGd5ping(GetCellValue(row.getCell(num)));
            num += 3;
            ed.setGd5result(GetCellValue(row.getCell(num++)));


            ed.setYd5xinhao(GetCellValue(row.getCell(num++)));
            ed.setYd5nr(GetCellValue(row.getCell(num++)));
            ed.setYd5pinlv(GetCellValue(row.getCell(num++)));
            ed.setYd5pci(GetCellValue(row.getCell(num++)));
            ed.setYd5ecl(GetCellValue(row.getCell(num++)));
            ed.setYd5rsrp(GetCellValue(row.getCell(num++)));
            ed.setYd5sinr(GetCellValue(row.getCell(num++)));
            ed.setYd5result(GetCellValue(row.getCell(num++)));


            ed.setGd4xinhao(GetCellValue(row.getCell(num++)));
            ed.setGd4nr(GetCellValue(row.getCell(num++)));
            ed.setGd4pinlv(GetCellValue(row.getCell(num++)));
            ed.setGd4pci(GetCellValue(row.getCell(num++)));
            ed.setGd4ecl(GetCellValue(row.getCell(num++)));
            ed.setGd4rsrp(GetCellValue(row.getCell(num++)));
            ed.setGd4sinr(GetCellValue(row.getCell(num++)));
            ed.setGd410099(GetCellValue(row.getCell(num++)));
            ed.setGd4ping(GetCellValue(row.getCell(num)));
            num += 3;
            ed.setGd4result(GetCellValue(row.getCell(num++)));


            ed.setYd4xinhao(GetCellValue(row.getCell(num++)));
            ed.setYd4nr(GetCellValue(row.getCell(num++)));
            ed.setYd4pinlv(GetCellValue(row.getCell(num++)));
            ed.setYd4pci(GetCellValue(row.getCell(num++)));
            ed.setYd4ecl(GetCellValue(row.getCell(num++)));
            ed.setYd4rsrp(GetCellValue(row.getCell(num++)));
            ed.setYd4sinr(GetCellValue(row.getCell(num++)));
            ed.setYd4result(GetCellValue(row.getCell(num++)));

            ed.setGd5p(GetCellValue(row.getCell(num++)));
            ed.setYd5p(GetCellValue(row.getCell(num++)));
            ed.setIf5p(GetCellValue(row.getCell(num++)));
            ed.setGd4p(GetCellValue(row.getCell(num++)));
            ed.setYd4p(GetCellValue(row.getCell(num++)));
            ed.setIf4p(GetCellValue(row.getCell(num++)));
            ed.setResult(GetCellValue(row.getCell(num)));

            return ed;
        } catch (Exception e) {
            throw new Exception("第" + num + "列附近信息出了问题");
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
     * 判断该信息是否可用
     */
    private boolean isNeed(YueHuiCeModel edc) {
        return (allisNeed(edc.getGd5xinhao(), edc.getGd5nr(), edc.getGd5pinlv(), edc.getGd5pci(),
                edc.getGd5ecl(), edc.getGd5rsrp(), edc.getGd5sinr()) && isResult(edc.getGd5result())) &&

                (allisNeed(edc.getYd5xinhao(), edc.getYd5nr(), edc.getYd5pinlv(), edc.getYd5pci(),
                        edc.getYd5ecl(), edc.getYd5rsrp(), edc.getYd5sinr()) && isResult(edc.getYd5result())) &&

                (allisNeed(edc.getGd4xinhao(), edc.getGd4nr(), edc.getGd4pinlv(), edc.getGd4pci(),
                        edc.getGd4ecl(), edc.getGd4rsrp(), edc.getGd4sinr()) && isResult(edc.getGd4result())) &&

                (allisNeed(edc.getYd4xinhao(), edc.getYd4nr(), edc.getYd4pinlv(), edc.getYd4pci(),
                        edc.getYd4ecl(), edc.getYd4rsrp(), edc.getYd4sinr()) && isResult(edc.getYd4result())) &&
                isResult(edc.getResult()) &&
                isWu(edc.getGd5xinhao(), edc.getYd5xinhao(), edc.getGd4xinhao(), edc.getYd4xinhao());
    }

    /**
     * 私有方法
     * <p>
     * 判断四个是否为全无信号
     */
    private boolean isWu(String gd5x, String yd5x, String gd4x, String yd4x) {
        return !Objects.equals(gd5x, "无信号") || !Objects.equals(gd4x, "无信号") ||
                !Objects.equals(yd5x, "无信号") || !Objects.equals(yd4x, "无信号");
    }

    /**
     * 私有方法
     * <p>
     * 判断条件是否有效
     */
    private boolean isResult(String in) {
        return !in.contains("无效数据建议剔除");
    }

    /**
     * 私有方法
     * <p>
     * 判断多个条件是否有效
     */
    private boolean allisNeed(String xinhao, String nr, String pinlv, String pci, String ecl,
                              String rsrp, String sinr) {
        if (Objects.equals(xinhao, "无信号")) {
            return true;
        }
        return (BatchService.GetStringNull(xinhao) && BatchService.GetStringNull(nr) &&
                BatchService.GetStringNull(pinlv) && BatchService.GetStringNull(pci) &&
                BatchService.GetStringNull(ecl) && BatchService.GetStringNull(rsrp) &&
                BatchService.GetStringNull(sinr));
    }

    /**
     * 私有方法
     * <p>
     * 用以生成有效数据背景
     */
    private void setCellStyle(XSSFCellStyle weneed, XSSFRow row, int num, String value, boolean youxiao) {
        Cell cell = row.createCell(num);
        cell.setCellValue(value);

        if (youxiao) {
            cell.setCellStyle(weneed);
        }
    }
}
