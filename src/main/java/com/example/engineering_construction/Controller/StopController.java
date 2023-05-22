package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ReturnModel.ReturnCodeModel;
import com.example.engineering_construction.Service.DataService.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/Stop")
public class StopController {

    @Autowired
    StopService stopservice;

    @ResponseBody
    @RequestMapping(value = "/Add")
    public ResponseEntity<ReturnCodeModel> Add(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String name = request.get("name");
        String stat_date = request.get("stat_date");
        String end_date = request.get("end_date");
        String zhiname = request.get("zhiname");

        try {
            stopservice.Add(coding, name, stat_date, end_date, zhiname);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Delete")
    public ResponseEntity<ReturnCodeModel> Delete(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String time = request.get("time");
        String zhiname = request.get("zhiname");

        try {
            stopservice.Delete(coding, time, zhiname);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Update")
    public ResponseEntity<ReturnCodeModel> Update(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String name = request.get("name");
        String time = request.get("time");
        String stat_date = request.get("stat_date");
        String end_date = request.get("end_date");
        String zhiname = request.get("zhiname");

        try {
            stopservice.Update(coding, name, time, stat_date, end_date, zhiname);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Find")
    public ResponseEntity<ReturnCodeModel> Find(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String name = request.get("name");
        String zhiname = request.get("zhiname");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    stopservice.Find(coding, name, zhiname, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/BatchAdd")
    public ResponseEntity<ReturnCodeModel> BatchAdd(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("zhiname") String zhiname) {
        try {
            if (file.isEmpty()) {
                throw new Exception("文件为空");
            } else {
                stopservice.BatchAdd(file, zhiname);

                ReturnCodeModel res = new ReturnCodeModel("200", "success");
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/BatchDeleteAndAdd")
    public ResponseEntity<ReturnCodeModel> BatchDeleteAndAdd(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("zhiname") String zhiname) {
        try {
            if (file.isEmpty()) {
                throw new Exception("文件为空");
            } else {
                stopservice.BatchDeleteAndAdd(file, zhiname);

                ReturnCodeModel res = new ReturnCodeModel("200", "success");
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/BatchUpdate")
    public ResponseEntity<ReturnCodeModel> BatchUpdate(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("zhiname") String zhiname) {
        try {
            if (file.isEmpty()) {
                throw new Exception("文件为空");
            } else {
                stopservice.BatchUpdate(file, zhiname);

                ReturnCodeModel res = new ReturnCodeModel("200", "success");
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }
}
