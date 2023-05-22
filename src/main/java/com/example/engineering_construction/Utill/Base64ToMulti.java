package com.example.engineering_construction.Utill;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

public class Base64ToMulti implements MultipartFile {
    //后缀
    private String suffix;
    //字节数组
    private byte[] content;
    //类型
    private String contentType;


    /**
     * 带dataURI的base64数据
     *
     * @param base64
     */
    public Base64ToMulti(String base64) {
        String[] data = base64.split(",");
        this.content = Base64.getDecoder().decode(data[1].getBytes());
        this.suffix = data[0].split(";")[0].split("/")[1];
        this.contentType = data[0].split(";")[0].split(":")[1];
    }

    /**
     * 不带dataURI的base64数据
     *
     * @param base64      base64数据
     * @param suffix      文件后缀名
     * @param contentType 内容类型
     */
    public Base64ToMulti(String base64, String suffix, String contentType) {
        this.content = Base64.getDecoder().decode(base64.getBytes());
        this.suffix = suffix;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return "temp_" + System.currentTimeMillis();
    }

    @Override
    public String getOriginalFilename() {
        return "temp_" + System.currentTimeMillis() + "." + suffix;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content == null || content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        }
    }


    public static MultipartFile getMultipartFile(String base64) {
        return new Base64ToMulti(base64);
    }

    public static MultipartFile getMultipartFile(String base64, String suffix, String contentType) {
        return new Base64ToMulti(base64, suffix, contentType);
    }

    /**
     * 判断图片base64字符串的文件格式
     */
    public static String checkImageBase64Format(String base64ImgData) {
        byte[] b = Base64.getDecoder().decode(base64ImgData);
        String type = "";
        if (0x424D == ((b[0] & 0xff) << 8 | (b[1] & 0xff))) {
            type = "bmp";
        } else if (0x8950 == ((b[0] & 0xff) << 8 | (b[1] & 0xff))) {
            type = "png";
        } else if (0xFFD8 == ((b[0] & 0xff) << 8 | (b[1] & 0xff))) {
            type = "jpg";
        } else {
            type = "jpg";
        }
        return type;
    }

}

