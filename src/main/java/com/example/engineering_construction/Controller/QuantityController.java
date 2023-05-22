package com.example.engineering_construction.Controller;

import com.example.engineering_construction.Model.ReturnModel.ReturnCodeModel;
import com.example.engineering_construction.Service.DataService.QuantityService;
import com.example.engineering_construction.Service.ProcessService.ImageService;
import com.example.engineering_construction.Utill.Base64ToMulti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/Quantity")
public class QuantityController {

    @Autowired
    QuantityService quantityservice;

    @ResponseBody
    @RequestMapping(value = "/Add")
    public ResponseEntity<ReturnCodeModel> Add(@RequestParam("picture") MultipartFile[] files,
                                               @RequestParam("coding") String coding,
                                               @RequestParam("name") String name,
                                               @RequestParam("construction") String construction,
                                               @RequestParam("team") String team,
                                               @RequestParam("ruhu_type") String ruhu_type,
                                               @RequestParam("shitong") String shitong,
                                               @RequestParam("ruhu_mi") String ruhu_mi,
                                               @RequestParam("ruhu_hu") String ruhu_hu,
                                               @RequestParam("jiexu") String jiexu,
                                               @RequestParam("xiangti") String xiangti,
                                               @RequestParam("guanglan_4D") String guanglan_4D,
                                               @RequestParam("guanglan_8D") String guanglan_8D,
                                               @RequestParam("guanglan_12D") String guanglan_12D,
                                               @RequestParam("guanglan_24D") String guanglan_24D,
                                               @RequestParam("guanglan_48D") String guanglan_48D,
                                               @RequestParam("guanglan_72D") String guanglan_72D,
                                               @RequestParam("guanglan_96D") String guanglan_96D,
                                               @RequestParam("guanglan_144D") String guanglan_144D,
                                               @RequestParam("guanglan_288D") String guanglan_288D,
                                               @RequestParam("zhimai") String zhimai,
                                               @RequestParam("kaiwa") String kaiwa,
                                               @RequestParam("dingguan") String dingguan,
                                               @RequestParam("type") String type,
                                               @RequestParam("sup_man") String sup_man,
                                               @RequestParam("com_man") String com_man,
                                               @RequestParam("zhiname") String zhiname) {
        try {
            if (files.length > 3) {
                throw new Exception("图片数量不能超过3张，请注意！！！");
            }

            quantityservice.Add(coding, name, construction, team, ruhu_type, shitong, ruhu_mi, ruhu_hu,
                    jiexu, xiangti, guanglan_4D, guanglan_8D, guanglan_12D, guanglan_24D, guanglan_48D, guanglan_72D,
                    guanglan_96D, guanglan_144D, guanglan_288D, zhimai, kaiwa, dingguan, type, sup_man, com_man,
                    zhiname, files);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/AddXiao")
    public ResponseEntity<ReturnCodeModel> AddXiao(@RequestBody Map<String, Object> request) {
        String coding = (String) request.get("coding");
        String name = (String) request.get("name");
        String construction = (String) request.get("construction");
        String team = (String) request.get("team");
        String ruhu_type = (String) request.get("ruhu_type");
        String shitong = String.valueOf(request.get("shitong"));
        String ruhu_mi = String.valueOf(request.get("ruhu_mi"));
        String ruhu_hu = String.valueOf(request.get("ruhu_hu"));
        String jiexu = String.valueOf(request.get("jiexu"));
        String xiangti = String.valueOf(request.get("xiangti"));
        String guanglan_4D = String.valueOf(request.get("guanglan_4D"));
        String guanglan_8D = String.valueOf(request.get("guanglan_8D"));
        String guanglan_12D = String.valueOf(request.get("guanglan_12D"));
        String guanglan_24D = String.valueOf(request.get("guanglan_24D"));
        String guanglan_48D = String.valueOf(request.get("guanglan_48D"));
        String guanglan_72D = String.valueOf(request.get("guanglan_72D"));
        String guanglan_96D = String.valueOf(request.get("guanglan_96D"));
        String guanglan_144D = String.valueOf(request.get("guanglan_144D"));
        String guanglan_288D = String.valueOf(request.get("guanglan_288D"));
        String zhimai = String.valueOf(request.get("zhimai"));
        String kaiwa = String.valueOf(request.get("kaiwa"));
        String dingguan = String.valueOf(request.get("dingguan"));
        String type = (String) request.get("type");
        String sup_man = (String) request.get("sup_man");
        String com_man = (String) request.get("com_man");
        String zhiname = (String) request.get("zhiname");
        List<String> stringbase64 = (List<String>) request.get("picture");

        try {
            if (stringbase64.size() > 3) {
                throw new Exception("图片数量不能超过3张，请注意！！！");
            }

            MultipartFile[] files = new MultipartFile[stringbase64.size()];
            for (int i = 0; i < stringbase64.size(); i++) {
                String geshi = Base64ToMulti.checkImageBase64Format(stringbase64.get(i));
                String imgtype = "image/" + geshi;
                files[i] = Base64ToMulti.getMultipartFile(stringbase64.get(i), geshi, imgtype);
            }

            quantityservice.AddXiao(coding, name, construction, team, ruhu_type, shitong, ruhu_mi, ruhu_hu,
                    jiexu, xiangti, guanglan_4D, guanglan_8D, guanglan_12D, guanglan_24D, guanglan_48D,
                    guanglan_72D, guanglan_96D, guanglan_144D, guanglan_288D, zhimai, kaiwa, dingguan, type,
                    sup_man, com_man, zhiname, files);

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
                    quantityservice.Find(coding, name, zhiname, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindBu")
    public ResponseEntity<ReturnCodeModel> FindBu(@RequestBody Map<String, String> request) {
        String zhiname = request.get("zhiname");
        String name = request.get("name");
        String construction = request.get("construction");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.FindBu(zhiname, name, construction, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindSup")
    public ResponseEntity<ReturnCodeModel> FindSup(@RequestBody Map<String, String> request) {
        String sup_man = request.get("sup_man");
        String zhiname = request.get("zhiname");
        String name = request.get("name");
        String link = request.get("link");
        String construction = request.get("construction");
        String isready = request.get("isready");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.FindSup(sup_man, zhiname, name, link, construction, isready, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindOne")
    public ResponseEntity<ReturnCodeModel> FindOne(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String time = request.get("time");
        String zhiname = request.get("zhiname");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.FindOne(coding, time, zhiname));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindShi")
    public ResponseEntity<ReturnCodeModel> FindShi(@RequestBody Map<String, String> request) {
        String zhiname = request.get("zhiname");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.FindShi(zhiname));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindNot")
    public ResponseEntity<ReturnCodeModel> FindNot(@RequestBody Map<String, String> request) {
        String zhiname = request.get("zhiname");
        String page = request.get("page");
        String name = request.get("name");
        String status = request.get("status");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.FindNot(zhiname, page, name, status));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindWeek")
    public ResponseEntity<ReturnCodeModel> FindWeek(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String name = request.get("name");
        String town = request.get("town");
        String construction = request.get("construction");
        String date1 = request.get("date1");
        String date2 = request.get("date2");
        String zhiname = request.get("zhiname");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.FindWeek(coding, name, town, construction, date1, date2, zhiname, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/UpdateSup")
    public ResponseEntity<ReturnCodeModel> UpdateSup(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String time = request.get("time");
        String sup_status = request.get("sup_status");
        String sup_man = request.get("sup_man");
        String bei = request.get("bei");
        String zhiname = request.get("zhiname");

        try {
            quantityservice.UpdateSup(coding, time, sup_status, sup_man, bei, zhiname);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindCom")
    public ResponseEntity<ReturnCodeModel> FindCom(@RequestBody Map<String, String> request) {
        String com_man = request.get("com_man");
        String zhiname = request.get("zhiname");
        String name = request.get("name");
        String link = request.get("link");
        String construction = request.get("construction");
        String isready = request.get("isready");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.FindCom(com_man, zhiname, name, link, construction, isready, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/UpdateCom")
    public ResponseEntity<ReturnCodeModel> UpdateCom(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String time = request.get("time");
        String com_status = request.get("com_status");
        String com_man = request.get("com_man");
        String bei = request.get("bei");
        String zhiname = request.get("zhiname");

        try {
            quantityservice.UpdateCom(coding, time, com_status, com_man, bei, zhiname);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/UpdateShi")
    public ResponseEntity<ReturnCodeModel> UpdateShi(@RequestBody Map<String, Object> request) {
        String coding = (String) request.get("coding");
        String time = (String) request.get("time");
        String zhiname = (String) request.get("zhiname");
        List<String> stringbase64 = (List<String>) request.get("picture");
        String shitong = String.valueOf(request.get("shitong"));
        String ruhu_mi = String.valueOf(request.get("ruhu_mi"));
        String ruhu_hu = String.valueOf(request.get("ruhu_hu"));
        String jiexu = String.valueOf(request.get("jiexu"));
        String xiangti = String.valueOf(request.get("xiangti"));
        String guanglan_4D = String.valueOf(request.get("guanglan_4D"));
        String guanglan_8D = String.valueOf(request.get("guanglan_8D"));
        String guanglan_12D = String.valueOf(request.get("guanglan_12D"));
        String guanglan_24D = String.valueOf(request.get("guanglan_24D"));
        String guanglan_48D = String.valueOf(request.get("guanglan_48D"));
        String guanglan_72D = String.valueOf(request.get("guanglan_72D"));
        String guanglan_96D = String.valueOf(request.get("guanglan_96D"));
        String guanglan_144D = String.valueOf(request.get("guanglan_144D"));
        String guanglan_288D = String.valueOf(request.get("guanglan_288D"));
        String zhimai = String.valueOf(request.get("zhimai"));
        String kaiwa = String.valueOf(request.get("kaiwa"));
        String dingguan = String.valueOf(request.get("dingguan"));

        try {
            if (stringbase64.size() > 3) {
                throw new Exception("图片数量不能超过3张，请注意！！！");
            }

            MultipartFile[] files = new MultipartFile[stringbase64.size()];
            for (int i = 0; i < stringbase64.size(); i++) {
                String geshi = Base64ToMulti.checkImageBase64Format(stringbase64.get(i));
                String imgtype = "image/" + geshi;
                files[i] = Base64ToMulti.getMultipartFile(stringbase64.get(i), geshi, imgtype);
            }

            quantityservice.UpdateShi(zhiname, coding, time, shitong, ruhu_mi, ruhu_hu, jiexu, xiangti,
                    guanglan_4D, guanglan_8D, guanglan_12D, guanglan_24D, guanglan_48D, guanglan_72D,
                    guanglan_96D, guanglan_144D, guanglan_288D, zhimai, kaiwa, dingguan, files);

            ReturnCodeModel res = new ReturnCodeModel("200", "success");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/Calculate")
    public ResponseEntity<ReturnCodeModel> CalculateQuantity(@RequestBody Map<String, String> request) {
        String coding = request.get("coding");
        String zhiname = request.get("zhiname");
        String name = request.get("name");
        String town = request.get("town");
        String construction = request.get("construction");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.CalculateQuantity(coding, zhiname, name, town, construction, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindGao")
    public ResponseEntity<ReturnCodeModel> FindGao(@RequestBody Map<String, String> request) {
        String zhiname = request.get("zhiname");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.FindGao(zhiname, page));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/FindOneGao")
    public ResponseEntity<ReturnCodeModel> FindOneGao(@RequestBody Map<String, String> request) {
        String zhiname = request.get("zhiname");
        String page = request.get("page");

        try {
            ReturnCodeModel res = new ReturnCodeModel("200", "success",
                    quantityservice.FindOneGao(zhiname, page));
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
                quantityservice.BatchAdd(file, zhiname);

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
                quantityservice.BatchUpdate(file, zhiname);

                ReturnCodeModel res = new ReturnCodeModel("200", "success");
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            ReturnCodeModel res = new ReturnCodeModel("201", e.toString());
            return ResponseEntity.ok(res);
        }
    }

}
