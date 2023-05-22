package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ReturnModel.ReturnCodeModel;
import com.example.engineering_construction.Service.DataService.EveryDayCQTService;
import com.example.engineering_construction.Service.DataService.YueHuiCeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/EDCQT")
public class EveryDayCQTController {

    @Autowired
    EveryDayCQTService everydaycqtservice;
    @Autowired
    YueHuiCeService yuehuiceservice;

    @ResponseBody
    @RequestMapping(value = "/GetExcel")
    public ResponseEntity<ReturnCodeModel> GetExcel(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new Exception("文件为空");
            } else {
                ReturnCodeModel res = new ReturnCodeModel("200", "success",
                        Collections.singletonList(everydaycqtservice.GetEveryDayCQT(file)));
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.getMessage());
            return ResponseEntity.ok(res);
        }
    }


    @RequestMapping(value = "/GetYHC")
    public ResponseEntity<ReturnCodeModel> GetYHC(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new Exception("文件为空");
            } else {
                ReturnCodeModel res = new ReturnCodeModel("200", "success",
                        Collections.singletonList(yuehuiceservice.GetYueHuiCeExcel(file)));
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.getMessage());
            return ResponseEntity.ok(res);
        }
    }
}
