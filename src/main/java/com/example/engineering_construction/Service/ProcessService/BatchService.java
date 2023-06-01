package com.example.engineering_construction.Service.ProcessService;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class BatchService {
    /**
     * 方法
     * <p>
     * 判断cell是否为空
     */
    public static boolean GetCellNull(Cell cell) {
        return cell != null && !cell.getCellType().equals(CellType.BLANK);
    }

    /**
     * 方法
     * <p>
     * 判断字符串是否为空
     */
    public static boolean GetStringNull(String in) {
        return in != null && !in.equals("") && !in.equals("N/A");
    }

    /**
     * 方法
     * <p>
     * 用以判断该格的类型
     */
    public static String GetCellType(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return "Number";
        }
        return "String";
    }

    /**
     * 方法
     * <p>
     * 用以判断是否为日期
     */
    public static boolean GetCellDate(Cell cell) {
        return DateUtil.isCellDateFormatted(cell);
    }


    /**
     * 方法
     * <p>
     * 用以删除前后空格
     */
    public static String Deletetrim(String in) {
        in = in.replaceAll("(\\r\\n|\\n|\\\\n|\\s)", "");
        return in.replaceAll(" ", "");
    }
}
