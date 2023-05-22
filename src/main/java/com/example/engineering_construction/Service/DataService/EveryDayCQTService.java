package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Model.DataModel.EveryDayCQTModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class EveryDayCQTService {

    public String GetEveryDayCQT(MultipartFile file) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet = wb.getSheetAt(0);
        List<EveryDayCQTModel> li = new ArrayList<>();

        for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            HSSFRow row = sheet.getRow(i);
            EveryDayCQTModel edc = setModel(row);

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
    private String SetExcel(List<EveryDayCQTModel> li) throws Exception {
        FileSystemView view = FileSystemView.getFileSystemView();
        File home = view.getHomeDirectory();
        String homelu = home.getPath();

        File file = new File(homelu + "\\result.xls");
        String res = "http://localhost:8787/EDCQT/out_result.xls";
        File Outfile = new File(homelu + "\\out_result.xls");

        if (Outfile.exists()) {
            res += "   " + Outfile.delete();
        }

        InputStream inputStream = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFCellStyle weneed = wb.createCellStyle();
        weneed.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        weneed.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int you = 0;
        for (int i = 2; i < li.size() + 2; i++) {
            HSSFRow row = sheet.createRow(i);
            EveryDayCQTModel edc = li.get(i - 2);

            if (edc.getYouxiao()) {
                row.setRowStyle(weneed);
                you++;
            }

            int num = 0;
            setCellStyle(weneed, row, num++, String.valueOf(i - 2), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getDate(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getName(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getPhone(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getSheng(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getShi(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getAddress(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getXuhao(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getDili(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getWangge(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getType(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getLatitude(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, edc.getLongitude(), edc.getYouxiao());

            setCellStyle(weneed, row, num++, edc.getGd5xinhao(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd5xinhao(), "有信号") ? edc.getGd5nr() : "",
                    edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd5xinhao(), "有信号") ? edc.getGd5pinlv() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd5xinhao(), "有信号") ? edc.getGd5pci() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd5xinhao(), "有信号") ? edc.getGd5ecl() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd5xinhao(), "有信号") ? edc.getGd5rsrp() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd5xinhao(), "有信号") ? edc.getGd5sinr() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd5xinhao(), "有信号") ? "VoNR" : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd5xinhao(), "有信号") ? "是" : ""
                    , edc.getYouxiao());


            num += 5;
            setCellStyle(weneed, row, num++, edc.getYd5xinhao(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd5xinhao(), "有信号") ? edc.getYd5nr() : "",
                    edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd5xinhao(), "有信号") ? edc.getYd5pinlv() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd5xinhao(), "有信号") ? edc.getYd5pci() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd5xinhao(), "有信号") ? edc.getYd5ecl() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd5xinhao(), "有信号") ? edc.getYd5rsrp() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd5xinhao(), "有信号") ? edc.getYd5sinr() : ""
                    , edc.getYouxiao());

            num += 5;
            setCellStyle(weneed, row, num++, edc.getGd4xinhao(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd4xinhao(), "有信号") ? edc.getGd4lte() : "",
                    edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd4xinhao(), "有信号") ? edc.getGd4pinlv() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd4xinhao(), "有信号") ? edc.getGd4pci() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd4xinhao(), "有信号") ? edc.getGd4ecl() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd4xinhao(), "有信号") ? edc.getGd4rsrp() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd4xinhao(), "有信号") ? edc.getGd4sinr() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd4xinhao(), "有信号") ? "VoLTE" : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getGd4xinhao(), "有信号") ? "是" : ""
                    , edc.getYouxiao());

            num += 5;
            setCellStyle(weneed, row, num++, edc.getYd4xinhao(), edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd4xinhao(), "有信号") ? edc.getYd4lte() : "",
                    edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd4xinhao(), "有信号") ? edc.getYd4pinlv() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd4xinhao(), "有信号") ? edc.getYd4pci() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd4xinhao(), "有信号") ? edc.getYd4ecl() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num++, Objects.equals(edc.getYd4xinhao(), "有信号") ? edc.getYd4rsrp() : ""
                    , edc.getYouxiao());
            setCellStyle(weneed, row, num, Objects.equals(edc.getYd4xinhao(), "有信号") ? edc.getYd4sinr() : ""
                    , edc.getYouxiao());
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
     * 用以生成有效数据背景
     */
    private void setCellStyle(HSSFCellStyle weneed, HSSFRow row, int num, String value, boolean youxiao) {
        Cell cell = row.createCell(num);
        cell.setCellValue(value);

        if (youxiao) {
            cell.setCellStyle(weneed);
        }
    }

    /**
     * 私有方法
     * <p>
     * 判断该信息是否可用
     */
    private boolean isNeed(EveryDayCQTModel edc) {
        return (GetStringNull(edc.getGd5xinhao()) && GetStringNull(edc.getGd5pinlv()) ||
                Objects.equals(edc.getGd5xinhao(), "无信号")) &&
                (GetStringNull(edc.getYd5xinhao()) && GetStringNull(edc.getYd5pinlv()) ||
                        Objects.equals(edc.getYd5xinhao(), "无信号")) &&
                (GetStringNull(edc.getGd4xinhao()) && GetStringNull(edc.getGd4pinlv()) ||
                        Objects.equals(edc.getGd4xinhao(), "无信号")) &&
                (GetStringNull(edc.getYd4xinhao()) && GetStringNull(edc.getYd4pinlv()) ||
                        Objects.equals(edc.getYd4xinhao(), "无信号")) &&
                (Integer.parseInt(edc.getXuhao()) == 0);
    }

    /**
     * 私有方法
     * <p>
     * 用以生成model实例
     */
    private EveryDayCQTModel setModel(HSSFRow row) throws Exception {
        EveryDayCQTModel ed = new EveryDayCQTModel();

        int num = 0;
        try {
            ed.setId(GetCellValue(row.getCell(num)));
            num++;
            ed.setDate(GetCellValue(row.getCell(num)));
            num++;
            ed.setName(GetCellValue(row.getCell(num)));
            num++;
            ed.setPhone(GetCellValue(row.getCell(num)));
            num++;
            ed.setSheng(GetCellValue(row.getCell(num)));
            num++;
            ed.setShi(GetCellValue(row.getCell(num)));
            num++;
            ed.setAddress(GetCellValue(row.getCell(num)));
            num++;
            ed.setXuhao(GetCellValue(row.getCell(num)));
            ed.setXuhao(String.valueOf((int) Math.ceil(Double.parseDouble(ed.getXuhao()))));
            num++;
            ed.setDili(GetCellValue(row.getCell(num)));
            num++;
            ed.setWangge(GetCellValue(row.getCell(num)));
            num++;
            ed.setType(GetCellValue(row.getCell(num)));
            num++;
            ed.setLatitude(GetCellValue(row.getCell(num)));
            num++;
            ed.setLongitude(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd5xinhao(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd5nr(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd5pinlv(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd5pci(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd5ecl(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd5rsrp(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd5sinr(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd5vonr(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd510099(GetCellValue(row.getCell(num)));


            num += 6;
            ed.setYd5xinhao(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd5nr(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd5pinlv(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd5pci(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd5ecl(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd5rsrp(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd5sinr(GetCellValue(row.getCell(num)));

            num += 6;
            ed.setGd4xinhao(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd4lte(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd4pinlv(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd4pci(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd4ecl(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd4rsrp(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd4sinr(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd4volte(GetCellValue(row.getCell(num)));
            num++;
            ed.setGd410099(GetCellValue(row.getCell(num)));

            num += 6;
            ed.setYd4xinhao(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd4lte(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd4pinlv(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd4pci(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd4ecl(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd4rsrp(GetCellValue(row.getCell(num)));
            num++;
            ed.setYd4sinr(GetCellValue(row.getCell(num)));

            return ed;
        } catch (Exception e) {
            throw new Exception("第" + num + "列信息出了问题");
        }
    }

    /**
     * 私有方法
     * <p>
     * 判断cell是否为空
     */
    private boolean GetCellNull(Cell cell) {
        return cell == null || cell.getCellType().equals(CellType.BLANK);
    }

    /**
     * 私有方法
     * <p>
     * 判断字符串是否为空
     */
    private boolean GetStringNull(String in) {
        return in != null && !in.equals("");
    }

    /**
     * 私有方法
     * <p>
     * 用以判断该格的类型
     */
    private String GetCellType(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return "Number";
        }
        return "String";
    }

    /**
     * 私有方法
     * <p>
     * 用以判断是否为日期
     */
    private boolean GetCellDate(Cell cell) {
        return DateUtil.isCellDateFormatted(cell);
    }

    /**
     * 私有方法
     * <p>
     * 用以根据类型插入值
     */
    private String GetCellValue(Cell cell) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (!GetCellNull(cell)) {
            switch (GetCellType(cell)) {
                case "String" -> {
                    return Deletetrim(cell.getStringCellValue());
                }
                case "Number" -> {
                    if (GetCellDate(cell)) {
                        return Deletetrim(sdf.format(cell.getDateCellValue()));
                    } else {
                        return Deletetrim(NumberToTextConverter.toText(cell.getNumericCellValue()));
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
     * 用以删除前后空格
     */
    private String Deletetrim(String in) {
        in = in.replaceAll("(\\r\\n|\\n|\\\\n|\\s)", "");
        return in.replaceAll(" ", "");
    }
}
