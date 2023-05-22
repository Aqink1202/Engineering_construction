package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.InformationModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Objects;

@Mapper
public interface InformationDao {

    //增
    @InsertProvider(type = InformationDao.InformationDaoProvider.class, method = "Add")
    void Add(InformationModel im);

    //删
    @DeleteProvider(type = InformationDao.InformationDaoProvider.class, method = "Delete")
    void Delete(String coding);

    @DeleteProvider(type = InformationDao.InformationDaoProvider.class, method = "DeleteId")
    void DeleteId();

    @DeleteProvider(type = InformationDao.InformationDaoProvider.class, method = "AddId")
    void AddId();

    //改
    @UpdateProvider(type = InformationDao.InformationDaoProvider.class, method = "Update")
    void Update(InformationModel im);

    //查
    @SelectProvider(type = InformationDao.InformationDaoProvider.class, method = "Find")
    List<InformationModel> Find(String town, String coding, String name, String company, String construction,
                                String supervision, String team, String type, Integer page);

    //查-返回每个工程队伍对应的项目
    @SelectProvider(type = InformationDao.InformationDaoProvider.class, method = "FindTeam")
    List<InformationModel> FindTeam(String construction, String team);

    //查-返回饼图
    @SelectProvider(type = InformationDao.InformationDaoProvider.class, method = "FindPie")
    List<InformationModel> FindPie(String construction, String he_year, String build_year);

    class InformationDaoProvider {
        //增
        public String Add(InformationModel im) {
            return new SQL() {{
                INSERT_INTO("information");
                VALUES("coding", "#{coding}");
                VALUES("town", "#{town}");
                VALUES("type", "#{type}");
                VALUES("name", "#{name}");
                VALUES("li_date", "#{li_date}");
                VALUES("pi_name", "#{pi_name}");
                VALUES("pi_date", "#{pi_date}");
                VALUES("chou_date", "#{chou_date}");
                VALUES("he_year", "#{he_year}");
                VALUES("build_year", "#{build_year}");
                VALUES("xiang_man", "#{xiang_man}");
                VALUES("construction", "#{construction}");
                VALUES("team", "#{team}");
                VALUES("supervision", "#{supervision}");
                VALUES("sup_man", "#{sup_man}");
                VALUES("electronic_version", "#{electronic_version}");
                VALUES("shen_date", "#{shen_date}");
                VALUES("wei_date", "#{wei_date}");
                VALUES("wan_date", "#{wan_date}");
                VALUES("bei", "#{bei}");
                VALUES("status", "#{status}");

                VALUES("jiagong", "#{jiagong}");
                VALUES("rengong", "#{rengong}");
                VALUES("zhanlie", "#{zhanlie}");
                VALUES("jianli", "#{jianli}");
                VALUES("qita", "#{qita}");
                VALUES("allmoney", "#{allmoney}");
                VALUES("guimo", "#{guimo}");
                VALUES("hujun", "#{hujun}");

                VALUES("ruhu_type", "#{ruhu_type}");
                VALUES("shitong", "#{shitong}");
                VALUES("ruhu_mi", "#{ruhu_mi}");
                VALUES("ruhu_hu", "#{ruhu_hu}");
                VALUES("jiexu", "#{jiexu}");
                VALUES("xiangti", "#{xiangti}");
                VALUES("guanglan_4D", "#{guanglan_4D}");
                VALUES("guanglan_8D", "#{guanglan_8D}");
                VALUES("guanglan_12D", "#{guanglan_12D}");
                VALUES("guanglan_24D", "#{guanglan_24D}");
                VALUES("guanglan_48D", "#{guanglan_48D}");
                VALUES("guanglan_72D", "#{guanglan_72D}");
                VALUES("guanglan_96D", "#{guanglan_96D}");
                VALUES("guanglan_144D", "#{guanglan_144D}");
                VALUES("guanglan_288D", "#{guanglan_288D}");
                VALUES("zhimai", "#{zhimai}");
                VALUES("kaiwa", "#{kaiwa}");
                VALUES("dingguan", "#{dingguan}");
            }}.toString();
        }

        //删
        public String Delete(String coding) {
            return new SQL() {{
                DELETE_FROM("information");
                WHERE("coding = #{coding}");
            }}.toString();
        }

        public String DeleteId() {
            return "ALTER TABLE information DROP id";
        }

        public String AddId() {
            return "ALTER TABLE information ADD id int NOT NULL PRIMARY KEY AUTO_INCREMENT FIRST";
        }

        //改
        public String Update(InformationModel im) {
            return new SQL() {{
                UPDATE("information");
                if (im.getTown() != null && !Objects.equals(im.getTown(), "")) {
                    SET("town = #{town}");
                }
                if (im.getType() != null && !Objects.equals(im.getType(), "")) {
                    SET("type = #{type}");
                }
                if (im.getName() != null && !Objects.equals(im.getName(), "")) {
                    SET("name = #{name}");
                }
                if (im.getLi_date() != null) {
                    SET("li_date = #{li_date}");
                }
                if (im.getPi_name() != null && !Objects.equals(im.getPi_name(), "")) {
                    SET("pi_name = #{pi_name}");
                }
                if (im.getPi_date() != null) {
                    SET("pi_date = #{pi_date}");
                }
                if (im.getChou_date() != null) {
                    SET("chou_date = #{chou_date}");
                }
                if (im.getHe_year() != null && !Objects.equals(im.getHe_year(), "")) {
                    SET("he_year = #{he_year}");
                }
                if (im.getBuild_year() != null && !Objects.equals(im.getBuild_year(), "")) {
                    SET("build_year = #{build_year}");
                }
                if (im.getXiang_man() != null && !Objects.equals(im.getXiang_man(), "")) {
                    SET("xiang_man = #{xiang_man}");
                }
                if (im.getConstruction() != null && !Objects.equals(im.getConstruction(), "")) {
                    SET("construction = #{construction}");
                }
                if (im.getTeam() != null && !Objects.equals(im.getTeam(), "")) {
                    SET("team = #{team}");
                }
                if (im.getSupervision() != null && !Objects.equals(im.getSupervision(), "")) {
                    SET("supervision = #{supervision}");
                }
                if (im.getSup_man() != null && !Objects.equals(im.getSup_man(), "")) {
                    SET("sup_man = #{sup_man}");
                }
                if (im.getElectronic_version() != null && !Objects.equals(im.getElectronic_version(), "")) {
                    SET("electronic_version = #{electronic_version}");
                }
                if (im.getShen_date() != null) {
                    SET("shen_date = #{shen_date}");
                }
                if (im.getWei_date() != null) {
                    SET("wei_date = #{wei_date}");
                }
                if (im.getWan_date() != null) {
                    SET("wan_date = #{wan_date}");
                }
                if (im.getBei() != null && !Objects.equals(im.getBei(), "")) {
                    SET("bei = #{bei}");
                }
                if (im.getStatus() != null && !Objects.equals(im.getStatus(), "")) {
                    SET("status = #{status}");
                }

                if (im.getJiagong() != null) {
                    SET("jiagong = #{jiagong}");
                }
                if (im.getRengong() != null) {
                    SET("rengong = #{rengong}");
                }
                if (im.getZhanlie() != null) {
                    SET("zhanlie = #{zhanlie}");
                }
                if (im.getJianli() != null) {
                    SET("jianli = #{jianli}");
                }
                if (im.getQita() != null) {
                    SET("qita = #{qita}");
                }
                if (im.getAllmoney() != null) {
                    SET("allmoney = #{allmoney}");
                }
                if (im.getGuimo() != null && !Objects.equals(im.getGuimo(), "")) {
                    SET("guimo = #{guimo}");
                }
                if (im.getHujun() != null) {
                    SET("hujun = #{hujun}");
                }

                if (im.getRuhu_type() != null && !Objects.equals(im.getRuhu_type(), "")) {
                    SET("ruhu_type = #{ruhu_type}");
                }
                if (im.getShitong() != null) {
                    SET("shitong = #{shitong}");
                }
                if (im.getRuhu_mi() != null) {
                    SET("ruhu_mi = #{ruhu_mi}");
                }
                if (im.getRuhu_hu() != null) {
                    SET("ruhu_hu = #{ruhu_hu}");
                }
                if (im.getJiexu() != null) {
                    SET("jiexu = #{jiexu}");
                }
                if (im.getXiangti() != null) {
                    SET("xiangti = #{xiangti}");
                }
                if (im.getGuanglan_4D() != null) {
                    SET("guanglan_4D = #{guanglan_4D}");
                }
                if (im.getGuanglan_8D() != null) {
                    SET("guanglan_8D = #{guanglan_8D}");
                }
                if (im.getGuanglan_12D() != null) {
                    SET("guanglan_12D = #{guanglan_12D}");
                }
                if (im.getGuanglan_24D() != null) {
                    SET("guanglan_24D = #{guanglan_24D}");
                }
                if (im.getGuanglan_48D() != null) {
                    SET("guanglan_48D = #{guanglan_48D}");
                }
                if (im.getGuanglan_72D() != null) {
                    SET("guanglan_72D = #{guanglan_72D}");
                }
                if (im.getGuanglan_96D() != null) {
                    SET("guanglan_96D = #{guanglan_96D}");
                }
                if (im.getGuanglan_144D() != null) {
                    SET("guanglan_144D = #{guanglan_144D}");
                }
                if (im.getGuanglan_288D() != null) {
                    SET("guanglan_288D = #{guanglan_288D}");
                }
                if (im.getZhimai() != null) {
                    SET("zhimai = #{zhimai}");
                }
                if (im.getKaiwa() != null) {
                    SET("kaiwa = #{kaiwa}");
                }
                if (im.getDingguan() != null) {
                    SET("dingguan = #{dingguan}");
                }
                WHERE("coding = #{coding}");
            }}.toString();
        }

        //查
        public String Find(String town, String coding, String name, String company, String construction,
                           String supervision, String team, String type, Integer page) {
            String sql = new SQL() {{
                SELECT("*");
                FROM("information");
                if (town != null && !town.equals("")) {
                    WHERE("town = #{town}");
                }
                if (coding != null && !coding.equals("")) {
                    WHERE("coding = #{coding}");
                }
                if (name != null && !name.equals("")) {
                    WHERE("name LIKE CONCAT('%', #{name}, '%')");
                }
                if (company != null && !company.equals("")) {
                    WHERE("town = #{company}");
                }
                if (construction != null && !construction.equals("")) {
                    WHERE("construction = #{construction}");
                }
                if (supervision != null && !supervision.equals("")) {
                    WHERE("supervision = #{supervision}");
                }
                if (team != null && !team.equals("")) {
                    WHERE("team = #{team}");
                }
                if (type != null && !type.equals("")) {
                    WHERE("type = #{type}");
                }
            }}.toString();

            if (page != null) {
                sql += " limit #{page},10";
            }

            return sql;
        }

        //查-返回每个工程队伍对应的项目
        public String FindTeam(String construction, String team) {
            return new SQL() {{
                SELECT("*");
                FROM("information");
                if (construction != null && !construction.equals("")) {
                    WHERE("construction = #{construction}");
                }
                if (team != null && !team.equals("")) {
                    WHERE("team = #{team}");
                }
            }}.toString();
        }

        //查-返回饼图
        public String FindPie(String construction, String he_year, String build_year) {
            return new SQL() {{
                SELECT("*");
                FROM("information");
                if (construction != null && !construction.equals("")) {
                    WHERE("construction = #{construction}");
                }
                if (he_year != null && !he_year.equals("")) {
                    WHERE("he_year = #{he_year}");
                }
                if (build_year != null && !build_year.equals("")) {
                    WHERE("build_year = #{build_year}");
                }
            }}.toString();
        }
    }
}
