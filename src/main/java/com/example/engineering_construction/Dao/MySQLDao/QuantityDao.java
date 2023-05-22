package com.example.engineering_construction.Dao.MySQLDao;

import com.example.engineering_construction.Model.DataModel.QuantityModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Mapper
public interface QuantityDao {

    //增
    @InsertProvider(type = QuantityDao.QuantityDaoProvider.class, method = "Add")
    void Add(QuantityModel qm);

    //删
    @DeleteProvider(type = QuantityDao.QuantityDaoProvider.class, method = "Delete")
    void Delete(String coding, String time);

    @DeleteProvider(type = QuantityDao.QuantityDaoProvider.class, method = "DeleteId")
    void DeleteId();

    @DeleteProvider(type = QuantityDao.QuantityDaoProvider.class, method = "AddId")
    void AddId();

    //改-只涉及监理跟分公司审核
    @UpdateProvider(type = QuantityDao.QuantityDaoProvider.class, method = "Update")
    void Update(String coding, String time, String sup_status, String com_status, String sup_bei,
                String com_bei, String link);

    //改-批量改
    @UpdateProvider(type = QuantityDao.QuantityDaoProvider.class, method = "UpdateAll")
    void UpdateAll(QuantityModel qm);

    //查-用以回显数据
    @SelectProvider(type = QuantityDao.QuantityDaoProvider.class, method = "Find")
    List<QuantityModel> Find(String coding, String name, String type, String town, String construction, String team,
                             Date date1, Date date2, String sup_status, String com_status, String time);

    //查—用以监理查询列表数据
    @SelectProvider(type = QuantityDao.QuantityDaoProvider.class, method = "FindSup")
    List<QuantityModel> FindSup(String sup_man, String name, String link, String construction);

    //查—用以查询单个数据
    @SelectProvider(type = QuantityDao.QuantityDaoProvider.class, method = "FindOne")
    List<QuantityModel> FindOne(String coding, String time);

    //查-用以分公司查询列表数据
    @SelectProvider(type = QuantityDao.QuantityDaoProvider.class, method = "FindCom")
    List<QuantityModel> FindCom(String com_man, String sup_status, String name, String link, String construction);

    class QuantityDaoProvider {
        //增
        public String Add(QuantityModel qm) {
            return new SQL() {{
                INSERT_INTO("quantity");
                VALUES("coding", "#{coding}");
                VALUES("name", "#{name}");
                VALUES("time", "#{time}");
                VALUES("construction", "#{construction}");
                VALUES("team", "#{team}");
                VALUES("date", "#{date}");

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

                VALUES("type", "#{type}");
                VALUES("link", "#{link}");
                VALUES("sup_man", "#{sup_man}");
                VALUES("sup_status", "#{sup_status}");
                VALUES("com_man", "#{com_man}");
                VALUES("com_status", "#{com_status}");
                VALUES("picture", "#{picture}");
            }}.toString();
        }

        //删
        public String Delete(String coding, String time) {
            return new SQL() {{
                DELETE_FROM("quantity");
                WHERE("coding = #{coding}");
                WHERE("time = #{time}");
            }}.toString();
        }

        public String DeleteId() {
            return "ALTER TABLE quantity DROP id";
        }

        public String AddId() {
            return "ALTER TABLE quantity ADD id int NOT NULL PRIMARY KEY AUTO_INCREMENT FIRST";
        }

        //改_只涉及监理跟分公司审核
        public String Update(String coding, String time, String sup_status, String com_status, String sup_bei,
                             String com_bei, String link) {
            return new SQL() {{
                UPDATE("quantity");
                if (sup_status != null && !sup_status.equals("")) {
                    SET("sup_status = #{sup_status}");
                }
                if (com_status != null && !com_status.equals("")) {
                    SET("com_status = #{com_status}");
                }
                if (sup_bei != null && !sup_bei.equals("")) {
                    SET("sup_bei = #{sup_bei}");
                }
                if (com_bei != null && !com_bei.equals("")) {
                    SET("com_bei = #{com_bei}");
                }
                if (link != null && !link.equals("")) {
                    SET("link = #{link}");
                }

                WHERE("coding = #{coding}");
                WHERE("time = #{time}");
            }}.toString();
        }

        //改——批量修改
        public String UpdateAll(QuantityModel qm) {
            return new SQL() {{
                UPDATE("quantity");
                if (qm.getName() != null && !Objects.equals(qm.getName(), "")) {
                    SET("name = #{name}");
                }
                if (qm.getConstruction() != null && !Objects.equals(qm.getConstruction(), "")) {
                    SET("construction = #{construction}");
                }
                if (qm.getTeam() != null && !Objects.equals(qm.getTeam(), "")) {
                    SET("team = #{team}");
                }
                if (qm.getDate() != null) {
                    SET("date = #{date}");
                }
                if (qm.getRuhu_type() != null && !Objects.equals(qm.getRuhu_type(), "")) {
                    SET("ruhu_type = #{ruhu_type}");
                }
                if (qm.getShitong() != null) {
                    SET("shitong = #{shitong}");
                }
                if (qm.getRuhu_mi() != null) {
                    SET("ruhu_mi = #{ruhu_mi}");
                }
                if (qm.getRuhu_hu() != null) {
                    SET("ruhu_hu = #{ruhu_hu}");
                }
                if (qm.getJiexu() != null) {
                    SET("jiexu = #{jiexu}");
                }
                if (qm.getXiangti() != null) {
                    SET("xiangti = #{xiangti}");
                }
                if (qm.getGuanglan_4D() != null) {
                    SET("guanglan_4D = #{guanglan_4D}");
                }
                if (qm.getGuanglan_8D() != null) {
                    SET("guanglan_8D = #{guanglan_8D}");
                }
                if (qm.getGuanglan_12D() != null) {
                    SET("guanglan_12D = #{guanglan_12D}");
                }
                if (qm.getGuanglan_24D() != null) {
                    SET("guanglan_24D = #{guanglan_24D}");
                }
                if (qm.getGuanglan_48D() != null) {
                    SET("guanglan_48D = #{guanglan_48D}");
                }
                if (qm.getGuanglan_72D() != null) {
                    SET("guanglan_72D = #{guanglan_72D}");
                }
                if (qm.getGuanglan_96D() != null) {
                    SET("guanglan_96D = #{guanglan_96D}");
                }
                if (qm.getGuanglan_144D() != null) {
                    SET("guanglan_144D = #{guanglan_144D}");
                }
                if (qm.getGuanglan_288D() != null) {
                    SET("guanglan_288D = #{guanglan_288D}");
                }
                if (qm.getZhimai() != null) {
                    SET("zhimai = #{zhimai}");
                }
                if (qm.getKaiwa() != null) {
                    SET("kaiwa = #{kaiwa}");
                }
                if (qm.getDingguan() != null) {
                    SET("dingguan = #{dingguan}");
                }
                if (qm.getLink() != null && !Objects.equals(qm.getLink(), "")) {
                    SET("link = #{link}");
                }
                if (qm.getType() != null && !Objects.equals(qm.getType(), "")) {
                    SET("type = #{type}");
                }
                if (qm.getSup_man() != null && !Objects.equals(qm.getSup_man(), "")) {
                    SET("sup_man = #{sup_man}");
                }
                SET("sup_status = #{sup_status}");
                SET("sup_bei = #{sup_bei}");
                if (qm.getCom_man() != null && !Objects.equals(qm.getCom_man(), "")) {
                    SET("com_man = #{com_man}");
                }
                SET("com_status = #{com_status}");
                SET("com_bei = #{com_bei}");
                if (qm.getPicture() != null && !Objects.equals(qm.getPicture(), "")) {
                    SET("picture = #{picture}");
                }

                WHERE("coding = #{coding}");
                WHERE("time = #{time}");
            }}.toString();
        }

        //查——用以回显数据
        public String Find(String coding, String name, String type, String town, String construction, String team,
                           Date date1, Date date2, String sup_status, String com_status, String time) {
            return new SQL() {{
                SELECT("*");
                FROM("quantity");
                if (coding != null && !coding.equals("")) {
                    WHERE("coding = #{coding}");
                }
                if (name != null && !name.equals("")) {
                    WHERE("name LIKE CONCAT('%', #{name}, '%')");
                }
                if (type != null && !type.equals("")) {
                    WHERE("type = #{type}");
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
                if (date1 != null && date2 != null) {
                    WHERE("date between #{date1} and #{date2}");
                }
                if (sup_status != null && !sup_status.equals("")) {
                    WHERE("sup_status = #{sup_status}");
                }
                if (com_status != null && !com_status.equals("")) {
                    WHERE("com_status = #{com_status}");
                }
                if (time != null && !time.equals("")) {
                    WHERE("time = #{time}");
                }
            }}.toString();
        }

        //查—用以监理查询列表数据
        public String FindSup(String sup_man, String name, String link, String construction) {
            return new SQL() {{
                SELECT("*");
                FROM("quantity");
                WHERE("sup_man = #{sup_man}");
                if (name != null && !name.equals("")) {
                    WHERE("name LIKE CONCAT('%', #{name}, '%')");
                }
                if (link != null && !link.equals("")) {
                    WHERE("link = #{link}");
                }
                if (construction != null && !construction.equals("")) {
                    WHERE("construction = #{construction}");
                }
            }}.toString();
        }

        //查—用以查询单个数据
        public String FindOne(String coding, String time) {
            return new SQL() {{
                SELECT("*");
                FROM("quantity");
                WHERE("coding = #{coding}");
                WHERE("time = #{time}");
            }}.toString();
        }

        //查-用以分公司查询列表数据
        public String FindCom(String com_man, String sup_status, String name, String link, String construction) {
            return new SQL() {{
                SELECT("*");
                FROM("quantity");
                WHERE("com_man = #{com_man}");
                WHERE("sup_status = #{sup_status}");
                if (name != null && !name.equals("")) {
                    WHERE("name LIKE CONCAT('%', #{name}, '%')");
                }
                if (link != null && !link.equals("")) {
                    WHERE("link = #{link}");
                }
                if (construction != null && !construction.equals("")) {
                    WHERE("construction = #{construction}");
                }
            }}.toString();
        }
    }
}
