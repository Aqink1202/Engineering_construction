package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ReturnModel.ReturnCodeModel;
import com.example.engineering_construction.Service.DataService.LoadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/Loading")
public class LoadingController {

    @Autowired
    LoadingService loadingservice;

    @ResponseBody
    @RequestMapping(value = "/Add")
    public ResponseEntity<ReturnCodeModel> Add(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String type = request.get("type");
        String company = request.get("company");
        String team = request.get("team");
        String zhiname = request.get("zhiname");
        String name = request.get("name");

        try {
            loadingservice.Add(username, password, type, company, team, zhiname, name);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Loading")
    public ResponseEntity<ReturnCodeModel> Loading(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    loadingservice.Loading(username, password));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindUser")
    public ResponseEntity<ReturnCodeModel> FindUser(@RequestBody Map<String, String> request) {
        String type = request.get("type");
        String zhiname = request.get("zhiname");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    loadingservice.FindUser(type, zhiname, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Delete")
    public ResponseEntity<ReturnCodeModel> Delete(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String zhiname = request.get("zhiname");

        try {
            loadingservice.Delete(username, zhiname);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Update")
    public ResponseEntity<ReturnCodeModel> Update(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String old_password = request.get("old_password");
        String new_password = request.get("new_password");

        try {
            loadingservice.Update(username, new_password, old_password);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }
}
