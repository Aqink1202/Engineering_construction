package com.example.engineering_construction.Model.ReturnModel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReturnCodeModel {
    private String code;
    private String message;
    private List data = new ArrayList();

    //构造函数
    public ReturnCodeModel() {
    }

    public ReturnCodeModel(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ReturnCodeModel(String code,String message,List data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
