package cn.detector.core;
/**
 * ��������֮������Լ�
 * ��̾�����жϵ��㷨��
 */
public class PointToLine_Distance {
	// �㵽ֱ�ߵ���̾�����ж� �㣨x0,y0�� ����������ɵ��߶Σ�x1,y1�� ,( x2,y2 )
    public double getDistance(int x1, int y1, int x2, int y2, int x0,
           int y0) {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2);// �߶εĳ���
        b = lineSpace(x1, y1, x0, y0);// ����㵽�߶ζ˵�(x1,y1)�ľ���
        c = lineSpace(x2, y2, x0, y0);// ����㵽�߶ζ˵�(x2,y2)�ľ���
        // ���׹�ʽ���
        double p = (a + b + c) / 2;// ���ܳ�
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// ���׹�ʽ�����
        space = 2 * s / a;// ���ص㵽�ߵľ��루���������������ʽ��ߣ�
        return space;//���ؾ���
    }
    // ��������֮��ľ���
    public static double lineSpace(int x1, int y1, int x2, int y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));// ��������֮���ֱ�߾���
        return lineLength;// ���ؾ���ֵ
    }
    // ���أ���̬����¶���ⲿ
    public static double lineSpace(MyPoint p1,MyPoint p2) {
    	int x1 = p1.point.x;
    	int y1 = p1.point.y;
    	int x2 = p2.point.x;
    	int y2 = p2.point.y;
    	double lineLength = 0;
    	lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));// ��������֮���ֱ�߾���
    	return lineLength;// ���ؾ���ֵ
    }
}
