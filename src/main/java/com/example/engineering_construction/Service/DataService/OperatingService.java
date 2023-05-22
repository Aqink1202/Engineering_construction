package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Dao.MySQLDao.LoadingDao;
import com.example.engineering_construction.Dao.MySQLDao.OperatingDao;
import com.example.engineering_construction.Model.DataModel.OperatingModel;
import com.example.engineering_construction.Model.ReturnModel.ReturnOperatingModel;
import com.example.engineering_construction.Service.ProcessService.MiService;
import com.example.engineering_construction.Service.ProcessService.StrToAnythingService;
import com.example.engineering_construction.Service.ProcessService.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class OperatingService {

    @Autowired
    OperatingDao operatingdao;
    @Autowired
    LoadingDao loadingdao;
    @Autowired
    MiService miservice;
    @Autowired
    StrToAnythingService strtoanythingservice;
    @Autowired
    WeekService weekservice;


    /**
     * 开工率信息查询
     * <p>
     * 用以查询五个施工单位的开工率，每日，每周都可
     * <p>
     * 只有1，2，3，4，5级权限所有人可执行该操作
     */
    public List<ReturnOperatingModel> Find(String da1, String da2, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //得到日期
            Date date1, date2;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            if ((!Objects.equals(da1, "") && da1 != null) && (!Objects.equals(da2, "") && da2 != null)) {
                date1 = strtoanythingservice.StrToDate(da1);
                date2 = strtoanythingservice.StrToDate(da2);
            } else {
                //得到本周的日期
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, 0);
                date1 = sf.parse(sf1.format(weekservice.getFirstDayOfWeek(c.getTime())));
                date2 = sf.parse(sf2.format(weekservice.getLastDayOfWeek(c.getTime())));
            }

            //查询数据
            List<OperatingModel> li = operatingdao.Find(date1, date2);

            //声明返回值
            List<ReturnOperatingModel> res = new ArrayList<>();

            //计算平均值
            Double jin = 0.0, chang = 0.0, san = 0.0, tian = 0.0, you = 0.0, yi = 0.0, fu = 0.0, ding = 0.0;
            for (OperatingModel operatingModel : li) {
                jin += operatingModel.getJinhoutian();
                chang += operatingModel.getChangxun();
                san += operatingModel.getZhongtongsan();
                tian += operatingModel.getTiancheng();
                you += operatingModel.getZhongyoujian();
                yi += operatingModel.getJinyibai();
                fu += operatingModel.getZhongtongfu();
                ding += operatingModel.getDingxiguoxun();
            }
            int num = li.size();

            //防止被除数等于0
            if (li.size() == 0) {
                num = 1;
            }

            res.add(new ReturnOperatingModel("金厚田", jin / num));
            res.add(new ReturnOperatingModel("长讯", chang / num));
            res.add(new ReturnOperatingModel("中通三局", san / num));
            res.add(new ReturnOperatingModel("天诚伟业", tian / num));
            res.add(new ReturnOperatingModel("中邮建", you / num));
            res.add(new ReturnOperatingModel("金一百", yi / num));
            res.add(new ReturnOperatingModel("中通服", fu / num));
            res.add(new ReturnOperatingModel("鼎熙国讯", ding / num));

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }


}
