package com.example.engineering_construction.Service.DataService;

import com.example.engineering_construction.Dao.MySQLDao.LoadingDao;
import com.example.engineering_construction.Model.DataModel.LoadingModel;
import com.example.engineering_construction.Model.FindModel.FindUserBaoModel;
import com.example.engineering_construction.Service.ProcessService.MiService;
import com.example.engineering_construction.Service.ProcessService.StrToAnythingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class LoadingService {

    @Autowired
    LoadingDao loadingdao;
    @Autowired
    StrToAnythingService strtoanythingservice;
    @Autowired
    MiService miservice;

    /**
     * 登陆表查找
     * <p>
     * 用以用户登录时，确认登录信息并返回
     * <p>
     * 登录操作不需要鉴权
     */
    public List<LoadingModel> Loading(String username, String password) throws Exception {
        //先查数据
        List<LoadingModel> li = loadingdao.Find(username, null);

        //声明返回值
        List<LoadingModel> res = new ArrayList<>();

        //数据处理
        if (li.size() != 0 && li.get(0).getPassword().equals(password)) {
            LoadingModel lm = new LoadingModel(li.get(0).getUsername(), null, li.get(0).getType(),
                    li.get(0).getCompany(), li.get(0).getTeam(), null, null, li.get(0).getName());
            res.add(lm);
        } else {
            throw new Exception("请注意，账号密码错误！！！");
        }

        return res;
    }

    /**
     * 登陆表查找
     * <p>
     * 用以管理员查询用户信息
     * <p>
     * 只有4、5级权限所有人可执行该操作
     */
    public List<LoadingModel> FindUser(String type, String zhiname, String pa) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //先查数据
            List<LoadingModel> li = loadingdao.Find(null, type);

            //声明返回值
            List<LoadingModel> res = new ArrayList<>();
            for (LoadingModel loadingModel : li) {
                if (Objects.equals(loadingModel.getUsername(), "Aqink")) {
                    continue;
                }

                LoadingModel lm = new LoadingModel(loadingModel.getUsername(), null, loadingModel.getType(),
                        loadingModel.getCompany(), loadingModel.getTeam(), null, null, li.get(0).getName());
                res.add(lm);
            }

            //分页
            if (pa != null && !pa.equals("")) {
                int page = strtoanythingservice.StrToInt(pa);

                //先把page的值做个初始化
                if (page <= 0) {
                    page = 1;
                }

                //生成对应页码
                int max = page * 10;
                int min = (page - 1) * 10;

                //做页码的一些限制
                if (res.size() < min) {
                    return new ArrayList<>();
                } else if (res.size() < max) {
                    max = res.size();
                }

                //最终分页
                res = res.subList(min, max);
            }

            return res;
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 登陆表添加
     * <p>
     * 用以超级管理员添加用户信息
     * <p>
     * 只有4、5级权限所有者才可操作
     */
    public void Add(String username, String password, String type, String company, String team,
                    String zhiname, String name) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            //获取bao及deng
            FindUserBaoModel fubm = miservice.FindBao(type);

            //形成model实例
            LoadingModel lm = new LoadingModel(username, password, type, company, team,
                    fubm.getBao(), fubm.getDeng(), name);

            loadingdao.Add(lm);
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 登陆表删除
     * <p>
     * 用以管理员删除用户信息
     * <p>
     * 只有4、5级权限所有者才可操作
     */
    public void Delete(String username, String zhiname) throws Exception {
        //给定权限范围
        Integer[] quan = {4, 5};

        if (miservice.PiPei(zhiname, quan)) {
            loadingdao.Delete(username);
            loadingdao.DeleteId();
            loadingdao.AddId();
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }

    /**
     * 登陆表修改
     * <p>
     * 用以用户进行密码修改
     * <p>
     * 任何级别权限都可以操作
     */
    public void Update(String username, String new_password, String old_password) throws Exception {
        //给定权限范围
        Integer[] quan = {1, 2, 3, 4, 5};

        if (miservice.PiPei(username, quan)) {
            //先查数据
            List<LoadingModel> li = loadingdao.Find(username, null);

            if (li.size() != 0 && li.get(0).getPassword().equals(old_password)) {
                loadingdao.Update(username, new_password);
            } else {
                throw new Exception("旧账号密码错误，请确保账号密码输入正确！！！");
            }
        } else {
            throw new Exception("无对应权限，无法进行该操作！！！");
        }
    }
}
