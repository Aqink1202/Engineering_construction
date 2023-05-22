package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ReturnModel.ReturnCodeModel;
import com.example.engineering_construction.Service.DataService.ZhongDianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/ZhongDian")
public class ZhongDianController {

    @Autowired
    ZhongDianService zhongdianservice;

    @ResponseBody
    @RequestMapping(value = "/BatchAdd")
    public ResponseEntity<ReturnCodeModel> BatchAdd(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("zhiname") String zhiname) {
        try {
            if (file.isEmpty()) {
                throw new Exception("文件为空");
            } else {
                zhongdianservice.BatchAdd(file, zhiname);

                ReturnCodeModel res = new ReturnCodeModel("200", "success");
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Find")
    public ResponseEntity<ReturnCodeModel> Find(@RequestBody Map<String, String> request) {
        String zhiname = request.get("zhiname");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    zhongdianservice.Find(zhiname, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }
}
