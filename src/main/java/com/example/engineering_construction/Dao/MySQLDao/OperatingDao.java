package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.OperatingModel;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

@Mapper
public interface OperatingDao {

    //增
    @InsertProvider(type = OperatingDao.OperatingDaoProvider.class, method = "Add")
    void Add(OperatingModel om);

    //查
    @SelectProvider(type = OperatingDao.OperatingDaoProvider.class, method = "Find")
    List<OperatingModel> Find(Date date1, Date date2);

    class OperatingDaoProvider {
        //增
        public String Add() {
            return new SQL() {{
                INSERT_INTO("operating");
                VALUES("date", "#{date}");
                VALUES("jinhoutian", "#{jinhoutian}");
                VALUES("changxun", "#{changxun}");
                VALUES("zhongtongsan", "#{zhongtongsan}");
                VALUES("tiancheng", "#{tiancheng}");
                VALUES("zhongyoujian", "#{zhongyoujian}");
                VALUES("jinyibai", "#{jinyibai}");
                VALUES("zhongtongfu", "#{zhongtongfu}");
                VALUES("dingxiguoxun", "#{dingxiguoxun}");
            }}.toString();
        }

        //查
        public String Find(Date date1, Date date2) {
            return new SQL() {{
                SELECT("*");
                FROM("operating");
                if (date1 != null && date2 != null) {
                    WHERE("date between #{date1} and #{date2}");
                }
            }}.toString();
        }
    }
}
