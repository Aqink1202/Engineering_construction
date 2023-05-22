package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ReturnModel.ReturnCodeModel;
import com.example.engineering_construction.Service.DataService.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/Information")
public class InformationController {

    @Autowired
    InformationService informationservice;

    @ResponseBody
    @RequestMapping(value = "/Add")
    public ResponseEntity<ReturnCodeModel> Add(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String town = request.get("town");
        String type = request.get("type");
        String name = request.get("name");
        String li_date = request.get("li_date");
        String pi_name = request.get("pi_name");
        String pi_date = request.get("pi_date");
        String chou_date = request.get("chou_date");
        String he_year = request.get("he_year");
        String build_year = request.get("build_year");
        String xiang_man = request.get("xiang_man");
        String construction = request.get("construction");
        String team = request.get("team");
        String supervision = request.get("supervision");
        String sup_man = request.get("sup_man");
        String electronic_version = request.get("electronic_version");
        String shen_date = request.get("shen_date");
        String wei_date = request.get("wei_date");
        String wan_date = request.get("wan_date");
        String bei = request.get("bei");

        String jiagong = request.get("jiagong");
        String rengong = request.get("rengong");
        String zhanlie = request.get("zhanlie");
        String jianli = request.get("jianli");
        String qita = request.get("qita");
        String allmoney = request.get("allmoney");
        String guimo = request.get("guimo");
        String hujun = request.get("hujun");

        String ruhu_type = request.get("ruhu_type");
        String shitong = request.get("shitong");
        String ruhu_mi = request.get("ruhu_mi");
        String ruhu_hu = request.get("ruhu_hu");
        String jiexu = request.get("jiexu");
        String xiangti = request.get("xiangti");
        String guanglan_4D = request.get("guanglan_4D");
        String guanglan_8D = request.get("guanglan_8D");
        String guanglan_12D = request.get("guanglan_12D");
        String guanglan_24D = request.get("guanglan_24D");
        String guanglan_48D = request.get("guanglan_48D");
        String guanglan_72D = request.get("guanglan_72D");
        String guanglan_96D = request.get("guanglan_96D");
        String guanglan_144D = request.get("guanglan_144D");
        String guanglan_288D = request.get("guanglan_288D");
        String zhimai = request.get("zhimai");
        String kaiwa = request.get("kaiwa");
        String dingguan = request.get("dingguan");

        String zhiname = request.get("zhiname");

        try {
            informationservice.Add(coding, town, type, name, li_date, pi_name, pi_date, chou_date, he_year,
                    build_year, xiang_man, construction, team, supervision, sup_man, electronic_version,
                    shen_date, wei_date, wan_date, bei, jiagong, rengong, zhanlie, jianli, qita, allmoney,
                    guimo, hujun, ruhu_type, shitong, ruhu_mi, ruhu_hu,
                    jiexu, xiangti, guanglan_4D, guanglan_8D, guanglan_12D,
                    guanglan_24D, guanglan_48D, guanglan_72D, guanglan_96D, guanglan_144D, guanglan_288D,
                    zhimai, kaiwa, dingguan, zhiname);

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
        String zhiname = request.get("zhiname");

        try {
            informationservice.Delete(coding, zhiname);

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
        String town = request.get("town");
        String type = request.get("type");
        String name = request.get("name");
        String li_date = request.get("li_date");
        String pi_name = request.get("pi_name");
        String pi_date = request.get("pi_date");
        String chou_date = request.get("chou_date");
        String he_year = request.get("he_year");
        String build_year = request.get("build_year");
        String xiang_man = request.get("xiang_man");
        String construction = request.get("construction");
        String team = request.get("team");
        String supervision = request.get("supervision");
        String sup_man = request.get("sup_man");
        String electronic_version = request.get("electronic_version");
        String shen_date = request.get("shen_date");
        String wei_date = request.get("wei_date");
        String wan_date = request.get("wan_date");
        String bei = request.get("bei");

        String jiagong = request.get("jiagong");
        String rengong = request.get("rengong");
        String zhanlie = request.get("zhanlie");
        String jianli = request.get("jianli");
        String qita = request.get("qita");
        String allmoney = request.get("allmoney");
        String guimo = request.get("guimo");
        String hujun = request.get("hujun");

        String ruhu_type = request.get("ruhu_type");
        String shitong = request.get("shitong");
        String ruhu_mi = request.get("ruhu_mi");
        String ruhu_hu = request.get("ruhu_hu");
        String jiexu = request.get("jiexu");
        String xiangti = request.get("xiangti");
        String guanglan_4D = request.get("guanglan_4D");
        String guanglan_8D = request.get("guanglan_8D");
        String guanglan_12D = request.get("guanglan_12D");
        String guanglan_24D = request.get("guanglan_24D");
        String guanglan_48D = request.get("guanglan_48D");
        String guanglan_72D = request.get("guanglan_72D");
        String guanglan_96D = request.get("guanglan_96D");
        String guanglan_144D = request.get("guanglan_144D");
        String guanglan_288D = request.get("guanglan_288D");
        String zhimai = request.get("zhimai");
        String kaiwa = request.get("kaiwa");
        String dingguan = request.get("dingguan");

        String zhiname = request.get("zhiname");

        try {
            informationservice.Update(coding, town, type, name, li_date, pi_name, pi_date, chou_date, he_year,
                    build_year, xiang_man, construction, team, supervision, sup_man, electronic_version,
                    shen_date, wei_date, wan_date, bei, jiagong, rengong, zhanlie, jianli, qita, allmoney,
                    guimo, hujun, ruhu_type, shitong, ruhu_mi, ruhu_hu, jiexu, xiangti, guanglan_4D,
                    guanglan_8D, guanglan_12D, guanglan_24D, guanglan_48D, guanglan_72D, guanglan_96D,
                    guanglan_144D, guanglan_288D, zhimai, kaiwa, dingguan, zhiname);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/UpdateStatus")
    public ResponseEntity<ReturnCodeModel> UpdateStatus(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String status = request.get("status");
        String zhiname = request.get("zhiname");

        try {
            informationservice.UpdateStatus(coding, status, zhiname);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Find")
    public ResponseEntity<ReturnCodeModel> Find(@RequestBody Map<String, String> request) {
        String town = request.get("town");
        String coding = request.get("coding");
        String name = request.get("name");
        String company = request.get("company");
        String construction = request.get("construction");
        String supervision = request.get("supervision");
        String team = request.get("team");
        String type = request.get("type");
        String zhiname = request.get("zhiname");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    informationservice.Find(town, coding, name, company, construction, supervision,
                            team, type, zhiname, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindTeam")
    public ResponseEntity<ReturnCodeModel> FindTeam(@RequestBody Map<String, String> request) {
        String team = request.get("team");
        String construction = request.get("construction");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    informationservice.FindTeam(construction, team));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindStatusGao")
    public ResponseEntity<ReturnCodeModel> FindStatusGao(@RequestBody Map<String, String> request) {
        String zhiname = request.get("zhiname");
        String coding = request.get("coding");
        String town = request.get("town");
        String name = request.get("name");
        String construction = request.get("construction");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    informationservice.FindStatusGao(zhiname, coding, town, name, construction, page));
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
                informationservice.BatchAdd(file, zhiname);

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
                informationservice.BatchUpdate(file, zhiname);

                ReturnCodeModel res = new ReturnCodeModel("200", "success");
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindStatusPie")
    public ResponseEntity<ReturnCodeModel> FindStatusPie(@RequestBody Map<String, String> request) {
        String construction = request.get("construction");
        String he_year = request.get("he_year");
        String build_year = request.get("build_year");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    informationservice.FindStatusPie(construction, he_year, build_year));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }
}
