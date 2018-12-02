package cn.detector.core;
/**
 * 计算两点之间距离以及
 * 最短距离的判断的算法类
 */
public class PointToLine_Distance {
	// 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )
    public double getDistance(int x1, int y1, int x2, int y2, int x0,
           int y0) {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2);// 线段的长度
        b = lineSpace(x1, y1, x0, y0);// 待求点到线段端点(x1,y1)的距离
        c = lineSpace(x2, y2, x0, y0);// 待求点到线段端点(x2,y2)的距离
        // 海伦公式求解
        double p = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
        return space;//返回距离
    }
    // 计算两点之间的距离
    public static double lineSpace(int x1, int y1, int x2, int y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));// 计算两点之间的直线距离
        return lineLength;// 返回距离值
    }
    // 重载，静态，暴露给外部
    public static double lineSpace(MyPoint p1,MyPoint p2) {
    	int x1 = p1.point.x;
    	int y1 = p1.point.y;
    	int x2 = p2.point.x;
    	int y2 = p2.point.y;
    	double lineLength = 0;
    	lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));// 计算两点之间的直线距离
    	return lineLength;// 返回距离值
    }
}
