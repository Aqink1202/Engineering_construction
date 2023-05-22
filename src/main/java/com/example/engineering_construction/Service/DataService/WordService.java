package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Model.ReturnModel.ReturnWordModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Transactional
@Service
public class WordService {

    /**
     * 数据处理
     */
    public ReturnWordModel create(String name, String dong, String hu, String gaoceng, String shangpu, String kehu,
                                  String hetong_time, String hetong_money, String yusuan, String cailiaofei,
                                  String jianlifei, String street, String type) {
        //处理name
        int findyear = name.indexOf("2022年");
        int findyouxian = name.indexOf("有线电视");
        String company = name.substring(0, findyear);
        String address = name.substring(findyear + 5, findyouxian);
        String year = name.substring(findyear, findyear + 4);
        String hetong_name = company + address + "有线电视配套工程";

        //处理时间
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy年MM月");
        Date da = new Date();
        String date = f1.format(da);
        String date_start = f2.format(da);
        long lo_zunbei1 = da.getTime() + (9 * 24 * 60 * 60 * 1000);
        long lo_zunbei2 = da.getTime() + (10 * 24 * 60 * 60 * 1000);
        long lo_shigong1 = lo_zunbei2 + (69L * 24 * 60 * 60 * 1000);
        long lo_shigong2 = lo_zunbei2 + (70L * 24 * 60 * 60 * 1000);
        long lo_yanshou = lo_shigong2 + (9 * 24 * 60 * 60 * 1000);
        String date_end = f2.format(new Date(lo_yanshou));
        String date_zunbei = f1.format(da) + "-" + f1.format(lo_zunbei1);
        String date_shigong = f1.format(lo_zunbei2) + "-" + f1.format(lo_shigong1);
        String date_yanshou = f1.format(lo_shigong2) + "-" + f1.format(lo_yanshou);

        //处理比例
        double bili_num;
        if (Objects.equals(type, "复合缆")) {
            bili_num = 0.62;
        } else {
            bili_num = 0.51;
        }
        String bili = bili_num * 100 + "%";
        String bili_money = String.valueOf(Double.parseDouble(hetong_money) * bili_num);

        //效益分析
        String hujun = String.valueOf(Double.parseDouble(hetong_money) * 10000 / Double.parseDouble(hu));
        String maoli = String.valueOf(Double.parseDouble(hetong_money) - Double.parseDouble(yusuan));
        String hu_xin = String.valueOf((int) (Double.parseDouble(hu) * 0.05));
        String hu_ru = String.valueOf(Integer.parseInt(hu_xin) * 100);
        String hu_tao = String.valueOf((int) (Integer.parseInt(hu_xin) * (0.8 * 69 + 0.2 * 79) * 12));
        String hu_yi = String.valueOf(Integer.parseInt(hu_xin) * 29);
        String hu_cheng = String.valueOf(Integer.parseInt(hu_xin) * 111);
        double xiaoyi_all = (Integer.parseInt(hu_ru) + Integer.parseInt(hu_tao)) * bili_num -
                Integer.parseInt(hu_yi) - Integer.parseInt(hu_cheng);
        String xiaoyi = "(" + hu_ru + "+" + hu_tao + ")*" + bili + "-" + hu_yi + "-" + hu_cheng + "=" +
                xiaoyi_all;

        //计算人工费
        String rengongfei = String.valueOf(Double.parseDouble(yusuan) - Double.parseDouble(cailiaofei) -
                Double.parseDouble(jianlifei) - 0.1);

        //形成model实例
        return new ReturnWordModel(name, dong, hu, gaoceng, shangpu, kehu, hetong_time,
                hetong_money, yusuan, cailiaofei, jianlifei, street, type, company, address, year, hetong_name,
                date, date_start, date_end, bili, bili_money, xiaoyi, hujun, maoli, hu_xin, hu_ru, hu_tao,
                hu_yi, hu_cheng, rengongfei, date_zunbei, date_shigong, date_yanshou);
    }
}
