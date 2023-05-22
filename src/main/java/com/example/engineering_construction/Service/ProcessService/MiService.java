package com.example.engineering_construction.Service.ProcessService;

import com.example.engineering_construction.Dao.MySQLDao.LoadingDao;
import com.example.engineering_construction.Model.DataModel.LoadingModel;
import com.example.engineering_construction.Model.FindModel.FindUserBaoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class MiService {

    @Autowired
    LoadingDao loadingdao;

    /**
     * 分配信息处理
     */
    public FindUserBaoModel FindBao(String type) {
        switch (type) {
            case "施工单位" -> {
                return new FindUserBaoModel("Ca1pyue,10000hDyshen", 1);
            }
            case "监理单位" -> {
                return new FindUserBaoModel("McQlNotKonwYou,TxSrKnowYouAll", 2);
            }
            case "镇公司" -> {
                return new FindUserBaoModel("Zj1bJ10000li,YrWlGwji", 3);
            }
            case "规划建设部" -> {
                return new FindUserBaoModel("8yQgFnHao,JwWs3cMao", 4);
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * 记录部分信息
     */
    public Integer DengJi(String bao) {
        return switch (bao) {
            case "Ca1pyue,10000hDyshen" -> 1;
            case "McQlNotKonwYou,TxSrKnowYouAll" -> 2;
            case "Zj1bJ10000li,YrWlGwji" -> 3;
            case "8yQgFnHao,JwWs3cMao" -> 4;
            case "ZzRcB^*,HrDcSay88" -> 5;
            default -> null;
        };
    }

    /**
     * 根据登录表做匹配，得出boolean值
     */
    public boolean PiPei(String username, Integer[] quan) {
        try {
            //先确定username不得为空
            if (username == null || username.equals("")) {
                return false;
            }
            //先查操作者的用户信息
            List<LoadingModel> li = loadingdao.Find(username, null);
            //再获取用户权限
            Integer deng = DengJi(li.get(0).getBao());
            //在次确认等级与预设等级是否相同
            if (Objects.equals(deng, li.get(0).getDeng())) {
                //判断是否在权限数组内
                for (Integer integer : quan) {
                    if (Objects.equals(deng, integer)) {
                        return true;
                    }
                }
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
