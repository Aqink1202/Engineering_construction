package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ReturnModel.ReturnWordModel;
import com.example.engineering_construction.Service.DataService.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/Word")
public class WordController {

    @Autowired
    WordService wordservice;

    @ResponseBody
    @RequestMapping(value = "/Create")
    public ReturnWordModel Create(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String dong = request.get("dong");
        String hu = request.get("hu");
        String gaoceng = request.get("gaoceng");
        String shangpu = request.get("shangpu");
        String kehu = request.get("kehu");
        String hetong_time = request.get("hetong_time");
        String hetong_money = request.get("hetong_money");
        String yusuan = request.get("yusuan");
        String cailiaofei = request.get("cailiaofei");
        String jianlifei = request.get("jianlifei");
        String street = request.get("street");
        String type = request.get("type");

        return wordservice.create(name, dong, hu, gaoceng, shangpu, kehu, hetong_time, hetong_money,
                yusuan, cailiaofei, jianlifei, street, type);
    }

    @RequestMapping(value = "/test")
    public void test(@RequestBody Map<String, String> request) throws ParseException {
        String time = "12:00:00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        Date da = new Date();
        String str = sdf1.format(da);
        System.out.println(str + " " + time);
        String reda = str + " " + time;
        Date date = sdf.parse(reda);
        System.out.println(date);
        System.out.println(sdf2.format(date));
    }
}
