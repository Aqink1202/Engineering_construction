package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Dao.MySQLDao.ZhongDianDao;
import com.example.engineering_construction.Model.DataModel.ZhongDianModel;
import com.example.engineering_construction.Service.ProcessService.MiService;
import com.example.engineering_construction.Service.ProcessService.StrToAnythingService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class ZhongDianService {

    @Autowired
    ZhongDianDao zhongdiandao;
    @Autowired
    MiService miservice;
    @Autowired
    StrToAnythingService strtoanythingservice;

    /**
     * 重点表添加
     * <p>
     * 批量添加，用以用户批量导入数据
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public void BatchAdd(MultipartFile file, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先清空表
            zhongdiandao.Delete();
            zhongdiandao.DeleteId();
            zhongdiandao.AddId();

            //再导入数据
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);

            try {
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    XSSFRow row = sheet.getRow(i);

                    //生成model实例
                    ZhongDianModel zdm = setModel(row, i);

                    //增
                    zhongdiandao.Add(zdm);
                }
            } catch (Exception e) {
                throw new Exception("Excel表格中存在错误的数据类型，请检查并修改！" + e);
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 重点表查找
     * <p>
     * 用以用户查看近期重点工程
     * <p>
     * 只有1、2、3、4、5级权限所有人可执行该操作
     */
    public List<ZhongDianModel> Find(String zhiname, String pa) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            List<ZhongDianModel> res = zhongdiandao.Find();

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (res.size() < min) {
                    return new ArrayList<>();
                } else if (res.size() < max) {
                    max = res.size();
                }

                //最终分页
                res = res.subList(min, max);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 私有方法
     * <p>
     * 生成model实例
     */
    public ZhongDianModel setModel(XSSFRow row, Integer i) throws Exception {
        int num = 0;
        try {
            ZhongDianModel zdm = new ZhongDianModel();

            zdm.setName(row.getCell(num++).getStringCellValue());
            zdm.setDanwei(row.getCell(num++).getStringCellValue());
            if (!Objects.equals(row.getCell(num).getStringCellValue(), "")) {
                zdm.setShijian(strtoanythingservice.Pi_StrToDate(row.getCell(num++).getStringCellValue()));
            } else {
                zdm.setShijian(null);
                num++;
            }
            zdm.setNeirong(row.getCell(num++).getStringCellValue());
            zdm.setZengti(row.getCell(num++).getStringCellValue());
            zdm.setShangzhou(row.getCell(num++).getStringCellValue());
            zdm.setZhongdian(row.getCell(num++).getStringCellValue());

            return zdm;
        } catch (Exception e) {
            throw new Exception("第" + i + "行 " + "第" + num + "列数据发生错误！！");
        }
    }
}
