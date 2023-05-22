package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ExCQTModel.ExcelModel;
import com.example.engineering_construction.Model.ReturnModel.ReturnCodeModel;
import com.example.engineering_construction.Service.DataService.CQTService;
import com.example.engineering_construction.Service.ExCQTService.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/CQT")
public class CQTController {

    @Autowired
    CQTService cqtservice;
    @Autowired
    ExcelService excelservice;

    @ResponseBody
    @RequestMapping(value = "/Find")
    public ResponseEntity<ReturnCodeModel> Find() {
        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    cqtservice.FindFu());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindZhan")
    public ResponseEntity<ReturnCodeModel> FindZhan() {
        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    cqtservice.FindZhan());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindGuangDian")
    public ResponseEntity<ReturnCodeModel> FindGuangDian() {
        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    cqtservice.FindGuangDian());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Find700")
    public ResponseEntity<ReturnCodeModel> Find700() {
        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    cqtservice.Find700());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindZhanAndCQT")
    public ResponseEntity<ReturnCodeModel> FindZhanAndCQT() {
        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    cqtservice.FindZhanAndCQT());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Find30")
    public ResponseEntity<ReturnCodeModel> Find30() {
        try {
            cqtservice.Find30();
            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindRad")
    public ResponseEntity<ReturnCodeModel> FindRad() {
        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    cqtservice.FindRad());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }


    @RequestMapping(value = "/Excel")
    public ResponseEntity<ReturnCodeModel> Excel(@RequestParam("file") MultipartFile file) {
        try {
            List<ExcelModel> aa = excelservice.excel(file);
            excelservice.find(aa);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }
}
