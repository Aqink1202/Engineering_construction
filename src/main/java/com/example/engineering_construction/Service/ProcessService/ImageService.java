package com.example.engineering_construction.Service.ProcessService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Objects;

@Transactional
@Service
public class ImageService {

    /**
     * 图片删除
     * <p>
     * 用以对信息进行删除时、修改时的图片进行删除
     * <p>
     * 防止图片堆积照成存储资源浪费
     */
    public void DeleteImg(String picture) {
        if (picture != null && !picture.equals("")){

            //对字符串进行分割
            String[] arrpic = picture.split(";");

            //对字符串数组进行循环
            for (String str : arrpic) {
                //替换为本地地址
                if (str.contains("Quantity/")) {
                    String[] arr1 = str.split("Quantity/");
                    str = "C://工建系统/工程量上报/" + arr1[1];

                } else if (str.contains("Report/")) {
                    String[] arr1 = str.split("Report/");
                    str = "C://工建系统/每日上报/" + arr1[1];
                }

                //生成file用以删除
                File file = new File(str);
                if (file.isFile() && file.exists()) {
                    file.delete();
                }

                //判断文件夹是否为空
                str = str.substring(0, str.lastIndexOf("/"));
                file = new File(str);
                if (file.isDirectory()) {
                    if (!(Objects.requireNonNull(file.list()).length > 0)) {
                        file.delete();
                    }
                }

                str = str.substring(0, str.lastIndexOf("/"));
                file = new File(str);
                if (file.isDirectory()) {
                    if (!(Objects.requireNonNull(file.list()).length > 0)) {
                        file.delete();
                    }
                }
            }
        }
    }

}
