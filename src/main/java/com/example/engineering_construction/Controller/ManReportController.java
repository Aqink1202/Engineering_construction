package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ReturnModel.ReturnCodeModel;
import com.example.engineering_construction.Service.DataService.ManReportService;
import com.example.engineering_construction.Utill.Base64ToMulti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/ManReport")
public class ManReportController {

    @Autowired
    ManReportService manreportservice;

    @ResponseBody
    @RequestMapping(value = "/Find")
    public ResponseEntity<ReturnCodeModel> Find(@RequestBody Map<String, String> request) {
        String date1 = request.get("date1");
        String date2 = request.get("date2");
        String name = request.get("name");
        String town = request.get("town");
        String construction = request.get("construction");
        String team = request.get("team");
        String zhiname = request.get("zhiname");
        String page = request.get("page");
        String open = request.get("open");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    manreportservice.Find(name, date1, date2, town, construction, team, zhiname, page,
                            open));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/AddXiao")
    public ResponseEntity<ReturnCodeModel> AddXiao(@RequestBody Map<String, Object> request) {
        List<String> stringbase64 = (List<String>) request.get("picture");
        String name = (String) request.get("name");
        String open = (String) request.get("open");
        String address = (String) request.get("address");
        String longitude = String.valueOf(request.get("longitude"));
        String latitude = String.valueOf(request.get("latitude"));
        String town = (String) request.get("town");
        String construction = (String) request.get("construction");
        String team = (String) request.get("team");
        String man = (String) request.get("man");
        String content = (String) request.get("content");
        String zhiname = (String) request.get("zhiname");

        try {
            if (stringbase64.size() > 5) {
                throw new Exception("图片数量不能超过5张，请注意！！！");
            }

            MultipartFile[] files = new MultipartFile[stringbase64.size()];
            for (int i = 0; i < stringbase64.size(); i++) {
                String geshi = Base64ToMulti.checkImageBase64Format(stringbase64.get(i));
                String imgtype = "image/" + geshi;
                files[i] = Base64ToMulti.getMultipartFile(stringbase64.get(i), geshi, imgtype);
            }

            //接下来就交给service处理
            manreportservice.AddXiao(name, open, address, longitude, latitude, town,
                    construction, team, man, content, zhiname, files);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Add")
    public ResponseEntity<ReturnCodeModel> Add(@RequestParam("picture") MultipartFile[] files,
                                               @RequestParam("name") String name,
                                               @RequestParam("open") String open,
                                               @RequestParam("address") String address,
                                               @RequestParam("longitude") String longitude,
                                               @RequestParam("latitude") String latitude,
                                               @RequestParam("town") String town,
                                               @RequestParam("construction") String construction,
                                               @RequestParam("team") String team,
                                               @RequestParam("man") String man,
                                               @RequestParam("content") String content,
                                               @RequestParam("zhiname") String zhiname) {
        try {
            if (files.length > 5){
                throw new Exception("图片数量不能超过5张，请注意！！！");
            }

            manreportservice.Add(name, open, address, longitude, latitude, town, construction, team,
                    man, content, zhiname, files);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Delete")
    public ResponseEntity<ReturnCodeModel> Delete(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String id = request.get("id");
        String zhiname = request.get("zhiname");

        try {
            manreportservice.Delete(name, id, zhiname);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }
}
