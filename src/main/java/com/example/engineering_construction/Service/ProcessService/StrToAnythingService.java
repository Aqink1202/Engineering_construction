package com.example.engineering_construction.Service.ProcessService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Transactional
@Service
public class StrToAnythingService {

    //为转Date规定格式
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat pi_sdf = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * 字符串转日期
     */
    public Date StrToDate(String in) throws Exception {
        if (!Objects.equals(in, "") && in != null) {
            try {
                return sdf.parse(in);
            } catch (Exception e) {
                throw new Exception("字符转日期出错，请检查所有日期字段是否正常！！！" + e);
            }
        } else {
            return null;
        }
    }

    /**
     * 批量日期转换
     */
    public Date Pi_StrToDate(String in) throws Exception {
        if (!Objects.equals(in, "") && in != null) {
            try {
                return pi_sdf.parse(in);
            } catch (Exception e) {
                throw new Exception("字符转日期出错，请检查所有日期字段是否正常！！！" + e);
            }
        } else {
            return null;
        }
    }

    /**
     * 字符串转整数
     */
    public Integer StrToInt(String in) throws Exception {
        if (!Objects.equals(in, "") && in != null) {
            try {
                return Integer.parseInt(in);
            } catch (Exception e) {
                throw new Exception("字符转整数出错，请检查数字字段是否出错！！！" + e);
            }
        } else {
            return null;
        }
    }

    /**
     * 字符串转浮点数
     */
    public Double StrToDou(String in) throws Exception {
        if (!Objects.equals(in, "") && in != null) {
            try {
                return Double.parseDouble(in);
            } catch (Exception e) {
                throw new Exception("字符转浮点数出错，请检查小数字段是否出错！！！" + e);
            }
        } else {
            return null;
        }
    }
}
