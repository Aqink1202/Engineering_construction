package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.ManReportModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

@Mapper
public interface ManReportDao {

    //增
    @InsertProvider(type = ManReportDao.ManReportDaoProvider.class, method = "Add")
    void Add(ManReportModel mrm);

    //删
    @DeleteProvider(type = ManReportDao.ManReportDaoProvider.class, method = "Delete")
    void Delete(String name, Integer id);

    @DeleteProvider(type = ManReportDao.ManReportDaoProvider.class, method = "DeleteId")
    void DeleteId();

    @DeleteProvider(type = ManReportDao.ManReportDaoProvider.class, method = "AddId")
    void AddId();

    //查
    @SelectProvider(type = ManReportDao.ManReportDaoProvider.class, method = "Find")
    List<ManReportModel> Find(String name, Date date1, Date date2, String town, String construction, String team,
                              Integer page, String open, Integer id);

    //改
    @UpdateProvider(type = ManReportDao.ManReportDaoProvider.class, method = "Update")
    void Update(String name, Integer id, String picture);

    class ManReportDaoProvider {
        public String Add(ManReportModel mrm) {
            return new SQL() {{
                INSERT_INTO("manreport");
                VALUES("name", "#{name}");
                VALUES("date", "#{date}");
                VALUES("open", "#{open}");
                VALUES("address", "#{address}");
                VALUES("longitude", "#{longitude}");
                VALUES("latitude", "#{latitude}");
                VALUES("town", "#{town}");
                VALUES("construction", "#{construction}");
                VALUES("team", "#{team}");
                VALUES("man", "#{man}");
                VALUES("content", "#{content}");
                VALUES("picture", "#{picture}");
            }}.toString();
        }

        public String Delete(String name, Integer id) {
            return new SQL() {{
                DELETE_FROM("manreport");
                WHERE("name = #{name}");
                WHERE("id = #{id}");
            }}.toString();
        }

        public String DeleteId() {
            return "ALTER TABLE manreport DROP id";
        }

        public String AddId() {
            return "ALTER TABLE manreport ADD id int NOT NULL PRIMARY KEY AUTO_INCREMENT FIRST";
        }

        public String Find(String name, Date date1, Date date2, String town, String construction, String team,
                           Integer page, String open, Integer id) {
            String sql = new SQL() {{
                SELECT("*");
                FROM("manreport");
                if (name != null && !name.equals("")) {
                    WHERE("name LIKE CONCAT('%', #{name}, '%')");
                }
                if (date1 != null && date2 != null) {
                    WHERE("date between #{date1} and #{date2}");
                }
                if (town != null && !town.equals("")) {
                    WHERE("town = #{town}");
                }
                if (construction != null && !construction.equals("")) {
                    WHERE("construction = #{construction}");
                }
                if (team != null && !team.equals("")) {
                    WHERE("team = #{team}");
                }
                if (open != null && !open.equals("")) {
                    WHERE("open = #{open}");
                }
                if (id != null) {
                    WHERE("id = #{id}");
                }
            }}.toString();

            if (page != null) {
                sql += " limit #{page},10";
            }

            return sql;
        }

        public String Update(String name, Integer id, String picture) {
            return new SQL() {{
                UPDATE("manreport");
                SET("picture = #{picture}");
                WHERE("name = #{name}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}
