package com.example.engineering_construction.Service.ProcessService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class LALService {
    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 组成点与点集合
     */
    public boolean isInPolygon(double pointLon, double pointLat, Double[] lon, Double[] lat) {
        //要判断坐标组成一个点
        Point2D.Double point = new Point2D.Double(pointLon, pointLat);

        //将区域各顶点坐标放到一个点集合里
        List<Point2D.Double> pointList = new ArrayList<>();
        double polygonPoint_x;
        double polygonPoint_y;
        for (int i = 0; i < lon.length; i++) {
            polygonPoint_x = lon[i];
            polygonPoint_y = lat[i];

            Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x, polygonPoint_y);
            pointList.add(polygonPoint);
        }

        return check(point, pointList);
    }

    /**
     * 判断点是否在多边形内部
     */
    private static boolean check(Point2D.Double point, List<Point2D.Double> polygon) {
        GeneralPath peneralPath = new GeneralPath();

        Point2D.Double first = polygon.get(0);

        //通过移动到指定坐标（以双精度指定），将一个点添加到路径中
        peneralPath.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            //通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中。
            peneralPath.lineTo(d.x, d.y);
        }

        //将集合多边形封闭
        peneralPath.lineTo(first.x, first.y);
        peneralPath.closePath();
        //测试指定的Point2D是否在Shape的边界内。
        return peneralPath.contains(point);
    }

    /**
     * 获取两个坐标点之间的距离
     */
    public double getDistance(double circlelon, double circlelat, double pointlon, double pointlat) {
        double sublat = rad(circlelat) - rad(pointlat);
        double sublon = rad(circlelon) - rad(pointlon);

        double res = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(sublat / 2), 2) +
                Math.cos(rad(circlelat)) * Math.cos(rad(pointlat)) * Math.pow(Math.sin(sublon / 2), 2)));

        res = res * EARTH_RADIUS;
        res = Math.round(res * 10000d) / 10000d;
        return res;
    }

    /**
     * 判断是否在圆内
     */
    public boolean isInCircle(double circlelon, double circlelat, double pointlon, double pointlat,
                              double radius) {
        double distance = getDistance(circlelon, circlelat, pointlon, pointlat);
        return !(distance > radius);
    }
}
