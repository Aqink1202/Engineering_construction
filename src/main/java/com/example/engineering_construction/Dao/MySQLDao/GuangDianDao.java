package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.GuangDianModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface GuangDianDao {
    @SelectProvider(type = GuangDianDaoProvider.class, method = "Find")
    List<GuangDianModel> Find();

    class GuangDianDaoProvider {
        public String Find() {
            return new SQL() {{
                SELECT("*");
                FROM("guangdian");
            }}.toString();
        }
    }
}
