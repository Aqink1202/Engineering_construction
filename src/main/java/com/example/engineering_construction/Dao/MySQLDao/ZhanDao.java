package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.CQTModel;
import com.example.engineering_construction.Model.DataModel.ZhanModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface ZhanDao {
    //查
    @SelectProvider(type = ZhanDao.ZhanDaoProvider.class, method = "Find")
    List<ZhanModel> Find();


    class ZhanDaoProvider {
        public String Find() {
            return new SQL() {{
                SELECT("*");
                FROM("zhan");
            }}.toString();
        }

    }
}
