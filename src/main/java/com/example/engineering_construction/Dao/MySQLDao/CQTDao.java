package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.CQTModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface CQTDao {

    //查
    @SelectProvider(type = CQTDao.CQTDaoProvider.class, method = "Find")
    List<CQTModel> Find();


    class CQTDaoProvider {
        public String Find() {
            return new SQL() {{
                SELECT("*");
                FROM("cqt");
            }}.toString();
        }

    }
}
