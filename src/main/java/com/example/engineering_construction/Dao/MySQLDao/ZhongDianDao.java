package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.ZhongDianModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface ZhongDianDao {

    //增
    @InsertProvider(type = ZhongDianDao.ZhongDianDaoProvider.class, method = "Add")
    void Add(ZhongDianModel zdm);

    //删
    @DeleteProvider(type = ZhongDianDao.ZhongDianDaoProvider.class, method = "Delete")
    void Delete();

    @DeleteProvider(type = ZhongDianDao.ZhongDianDaoProvider.class, method = "DeleteId")
    void DeleteId();

    @DeleteProvider(type = ZhongDianDao.ZhongDianDaoProvider.class, method = "AddId")
    void AddId();

    //查
    @SelectProvider(type = ZhongDianDao.ZhongDianDaoProvider.class, method = "Find")
    List<ZhongDianModel> Find();

    class ZhongDianDaoProvider {
        //增
        public String Add() {
            return new SQL() {{
                INSERT_INTO("zhongdian");
                VALUES("name", "#{name}");
                VALUES("danwei", "#{danwei}");
                VALUES("shijian", "#{shijian}");
                VALUES("neirong", "#{neirong}");
                VALUES("zengti", "#{zengti}");
                VALUES("shangzhou", "#{shangzhou}");
                VALUES("zhongdian", "#{zhongdian}");
            }}.toString();
        }

        //删
        public String Delete() {
            return new SQL() {{
                DELETE_FROM("zhongdian");
            }}.toString();
        }

        public String DeleteId() {
            return "ALTER TABLE zhongdian DROP id";
        }

        public String AddId() {
            return "ALTER TABLE zhongdian ADD id int NOT NULL PRIMARY KEY AUTO_INCREMENT FIRST";
        }

        //查
        public String Find() {
            return new SQL() {{
                SELECT("*");
                FROM("zhongdian");
            }}.toString();
        }
    }
}
