/**
 * 
 */
package cn.detector.core;

import java.awt.Color;
import java.awt.Point;

/**
 * �ڵ���
 *���ڴ洢�ڵ�������Ϣ����
 */

public class MyPoint{
	
	//�����������ڽ��յ�����
	public Point point =null; // �����
	public Color color;// Save the color
	public Boolean is_Stored = false; // �Ƿ񱣴��־��Ĭ��Ϊδ����
	public Boolean anchor = false; // ê�㣬Ĭ��Ϊ��ê��
	public Boolean isUPPerBody = false; // ����ڵ��־��Ĭ��Ϊ������ڵ�
	public int link_Flag; // �������
	public int order; // ��ͼ����
	public int vector_index;// ������ǲ�
	public int parameter = 0;// ��ǲ���
	public int body_Parts = 0;// ��ǲ���
	// �޲ι��캯��
	public MyPoint(){
		super();
	}
	// �вι��캯��������ΪMyPoint�Ӷ���
	public MyPoint(MyPoint mp){
		point = new Point(mp.point);
		is_Stored = mp.is_Stored;
		link_Flag = mp.link_Flag;
		order = mp.order;
		anchor = mp.anchor;
		color = mp.color;
		isUPPerBody = mp.isUPPerBody;
		vector_index = mp.vector_index;
		parameter = mp.parameter;
		body_Parts = mp.parameter;
	}
}
