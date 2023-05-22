package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.LoadingModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface LoadingDao {

    //查——根据用户名
    @SelectProvider(type = LoadingDao.LoadingDaoPeovider.class, method = "Find")
    List<LoadingModel> Find(String username, String type);

    //增
    @InsertProvider(type = LoadingDao.LoadingDaoPeovider.class, method = "Add")
    void Add(LoadingModel lm);

    //删
    @DeleteProvider(type = LoadingDao.LoadingDaoPeovider.class, method = "Delete")
    void Delete(String username);

    @DeleteProvider(type = LoadingDao.LoadingDaoPeovider.class, method = "DeleteId")
    void DeleteId();

    @DeleteProvider(type = LoadingDao.LoadingDaoPeovider.class, method = "AddId")
    void AddId();

    //改——用户改密码
    @UpdateProvider(type = LoadingDao.LoadingDaoPeovider.class, method = "Update")
    void Update(String username, String password);

    class LoadingDaoPeovider {
        //查——根据用户名
        public String Find(String username, String type) {
            return new SQL() {{
                SELECT("*");
                FROM("loading");
                if (username != null && !username.equals("")) {
                    WHERE("username = #{username}");
                }
                if (type != null && !type.equals("")) {
                    WHERE("type = #{type}");
                }
            }}.toString();
        }

        //增
        public String Add() {
            return new SQL() {{
                INSERT_INTO("loading");
                VALUES("username", "#{username}");
                VALUES("password", "#{password}");
                VALUES("type", "#{type}");
                VALUES("company", "#{company}");
                VALUES("team", "#{team}");
                VALUES("bao", "#{bao}");
                VALUES("deng", "#{deng}");
                VALUES("name", "#{name}");
            }}.toString();
        }

        //删
        public String Delete() {
            return new SQL() {{
                DELETE_FROM("loading");
                WHERE("username = #{username}");
            }}.toString();
        }

        public String DeleteId() {
            return "ALTER TABLE loading DROP id";
        }

        public String AddId() {
            return "ALTER TABLE loading ADD id int NOT NULL PRIMARY KEY AUTO_INCREMENT FIRST";
        }

        //改——用户修改密码
        public String Update() {
            return new SQL() {{
                UPDATE("loading");
                SET("password = #{password}");
                WHERE("username = #{username}");
            }}.toString();
        }
    }
}
