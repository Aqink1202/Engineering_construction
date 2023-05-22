package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.StopModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface StopDao {

    //增
    @InsertProvider(type = StopDao.StopDaoProvider.class, method = "Add")
    void Add(StopModel sm);

    //删
    @DeleteProvider(type = StopDao.StopDaoProvider.class, method = "Delete")
    void Delete(String coding, Integer time);

    @DeleteProvider(type = StopDao.StopDaoProvider.class, method = "DeleteId")
    void DeleteId();

    @DeleteProvider(type = StopDao.StopDaoProvider.class, method = "AddId")
    void AddId();

    //改
    @UpdateProvider(type = StopDao.StopDaoProvider.class, method = "Update")
    void Update(StopModel sm);

    //查
    @SelectProvider(type = StopDao.StopDaoProvider.class, method = "Find")
    List<StopModel> Find(String coding, String name);

    class StopDaoProvider {
        //增
        public String Add() {
            return new SQL() {{
                INSERT_INTO("stop");
                VALUES("coding", "#{coding}");
                VALUES("name", "#{name}");
                VALUES("time", "#{time}");
                VALUES("stat_date", "#{stat_date}");
                VALUES("end_date", "#{end_date}");
            }}.toString();
        }

        //删
        public String Delete(String coding, Integer time) {
            return new SQL() {{
                DELETE_FROM("stop");
                if (coding != null && !coding.equals("")){
                    WHERE("coding = #{coding}");
                }
                if (time != null) {
                    WHERE("time = #{time}");
                }
            }}.toString();
        }

        public String DeleteId() {
            return "ALTER TABLE stop DROP id";
        }

        public String AddId() {
            return "ALTER TABLE stop ADD id int NOT NULL PRIMARY KEY AUTO_INCREMENT FIRST";
        }

        //改
        public String Update(StopModel sm) {
            return new SQL() {{
                UPDATE("stop");
                if (sm.getStat_date() != null) {
                    SET("stat_date = #{stat_date}");
                }
                if (sm.getEnd_date() != null) {
                    SET("end_date = #{end_date}");
                }
                WHERE("coding = #{coding}");
                WHERE("name = #{name}");
                WHERE("time = #{time}");
            }}.toString();
        }

        //查
        public String Find(String coding, String name) {
            return new SQL() {{
                SELECT("*");
                FROM("stop");
                if (coding != null && !coding.equals("")) {
                    WHERE("coding = #{coding}");
                }
                if (name != null && !name.equals("")) {
                    WHERE("name = #{name}");
                }
            }}.toString();
        }
    }
}
