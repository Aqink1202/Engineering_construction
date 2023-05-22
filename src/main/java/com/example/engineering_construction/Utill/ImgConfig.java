package com.example.engineering_construction.Utill;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImgConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/Img/Report/**").addResourceLocations("file:C://工建系统/每日上报/");
        registry.addResourceHandler("/Img/Quantity/**").addResourceLocations("file:C://工建系统/工程量上报/");
        registry.addResourceHandler("/Img/**").addResourceLocations("file:\\E:\\ImgPackage\\");
        registry.addResourceHandler("/EDCQT/**").addResourceLocations("file:C:\\Users\\admin\\Desktop\\");
    }
}
