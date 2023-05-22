package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ReturnModel.ReturnCodeModel;
import com.example.engineering_construction.Service.DataService.OperatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/Operating")
public class OperatingController {

    @Autowired
    OperatingService operatingservice;

    @ResponseBody
    @RequestMapping(value = "/Find")
    public ResponseEntity<ReturnCodeModel> Find(@RequestBody Map<String, String> request) {
        String date1 = request.get("date1");
        String date2 = request.get("date2");
        String zhiname = request.get("zhiname");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    operatingservice.Find(date1, date2, zhiname));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }
}
