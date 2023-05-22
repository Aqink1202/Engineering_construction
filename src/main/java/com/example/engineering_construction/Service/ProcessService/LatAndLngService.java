package com.example.engineering_construction.Service.ProcessService;

import com.example.engineering_construction.Model.DataModel.GPSModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LatAndLngService {
    //π的定义
    public static double pi = 3.1415926535897932384626;
    //椭球长半径，依据克拉索索夫斯基椭球系数计算
    public static double a = 6378245.0;
    //第一偏心率的平方
    public static double ee = 0.00669342162296594323;

    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
                * pi)) * 2.0 / 3.0;
        return ret;
    }

    /***
     * 判断是否在中国范围之内
     */
    public static boolean outOfChina(double lat, double lon) {
        return (lon < 72.004 || lon > 137.8347) && (lat < 0.8293 || lat > 55.8271);
    }

    /***
     * 把公式部分抽取出来
     */
    public static GPSModel transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new GPSModel(lat, lon);
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new GPSModel(mgLat, mgLon);
    }

    /***
     * wgs84到gcj02转换
     */
    public static GPSModel wgs84_To_Gcj02(double lat, double lon) {
        return transform(lat, lon);
    }

    /***
     * 简单的gcj02到wgs84坐标类型转换，只做了一次迭代
     */
    public static GPSModel gcj02_To_Wgs84(double lat, double lon) {
        GPSModel GPSModel = transform(lat, lon);
        double lontitude = lon * 2 - GPSModel.getLon();
        double latitude = lat * 2 - GPSModel.getLat();
        return new GPSModel(latitude, lontitude);
    }

    /***
     * 稍微精确一点的gcj02到wgs84转换
     */
    public static GPSModel gcj02_To_Wgs84_exact(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return gcj02_To_Wgs84(lat, lon);
        }
        double initDelta = 0.01;
        double threshold = 0.000001;
        double dLat = initDelta;
        double dLon = initDelta;
        double mLat = lat - dLat;
        double mLon = lon - dLon;
        double pLat = lat + dLat;
        double pLon = lon + dLon;
        double wgsLat;
        double wgsLon;
        int i = 0;
        while (true) {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            GPSModel tmp = wgs84_To_Gcj02(wgsLat, wgsLon);
            dLat = tmp.getLat() - lat;
            dLon = tmp.getLon() - lon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold)) {
                break;
            }
            if (dLat > 0) {
                pLat = wgsLat;
            } else {
                mLat = wgsLat;
            }
            if (dLon > 0) {
                pLon = wgsLon;
            } else {
                mLon = wgsLon;
            }

            if (++i > 1000) break;
        }

        return new GPSModel(wgsLat, wgsLon);
    }

    /***
     * 百度坐标是在火星坐标基础上做的二次加密
     */
    public static GPSModel gcj02_To_Bd09(double gg_lat, double gg_lon) {
        double z = Math.sqrt(gg_lon * gg_lon + gg_lat * gg_lat) + 0.00002 * Math.sin(gg_lat * pi);
        double theta = Math.atan2(gg_lat, gg_lon) + 0.000003 * Math.cos(gg_lon * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new GPSModel(bd_lat, bd_lon);
    }

    /***
     * 百度坐标与火星坐标逆向转换
     */
    public static GPSModel bd09_To_Gcj02(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new GPSModel(gg_lat, gg_lon);
    }

}
