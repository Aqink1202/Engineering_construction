package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.ZhanAndCQTModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface ZhanAndCQTDao {

    @SelectProvider(type = ZhanAndCQTDao.ZhanAndCQTDaoProvider.class, method = "Find")
    List<ZhanAndCQTModel> Find();

    class ZhanAndCQTDaoProvider{
        public String Find() {
            return new SQL() {{
                SELECT("*");
                FROM("zhanandcqt");
            }}.toString();
        }
    }
}
