/**
 * 
 */
package cn.detector.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Vector;

import javax.swing.JOptionPane;
import cn.detector.view.*;

/**
 * �����߼��㷨��
 *
 */
//�滭�߼��༰���ڵ��߼�����
public  class Mydraw{
	/**
	 * 
	 */
	static WindowOperation win; // ���崰������������ڽ��մ������Ĵ�����������
	MyLabel myLabel;
	int[] RGB; //����������ɫ��RGB����
	Font m_font = null; // ��������

	public Mydraw(MyLabel myLabel){// ��MyaLabel�������͵Ĺ��캯��
		this.myLabel = myLabel;
		
	}
	
	//��ȡwin�������
	public static void setWin(WindowOperation wins) {
		win = wins;
	}
	
	public String color = win.getColor(); // ��ȡ��ɫ����
	public String string_number = win.getThick(); // ��ȡ��ɫ����
	Stroke stroke; // ����ʻ�
	float thick; // ���廭�ʴ�ϸ
	//���û��㷽��
	public void myDrawPoints(MyPoint myP,Graphics g){
		g.setColor(Color.RED);

		g.fillRect((int)(myP.point.x - 3), (int)(myP.point.y - 3), 6, 6);
	
	}
	public void myDrawLine(Vector<MyPoint>v1,Vector<MyPoint>v2,Vector<MyPoint>v3,Vector<MyPoint>v4,
			Vector<MyPoint>v5,Graphics g){
		
		RGB = myLabel.getRGB();
		if(RGB[0] != 0){
			Color color = new Color(RGB[0], RGB[1],RGB[2]);
			g.setColor(color);
			
		}
		else{
			g.setColor(Color.GREEN);
		}

		m_font = myLabel.getm_font(); // ��ȡ����
		if(m_font != null){
			((Graphics2D)g).setFont(m_font);
			//thick = Integer.parseInt(string_number);
			stroke = new BasicStroke(m_font.getSize());//�����߿�
			((Graphics2D) g).setStroke(stroke); //�����߿�Ϊ4.0
		}
		else{
			g.setFont(new Font("������", Font.PLAIN, 20));
			//thick = Integer.parseInt(string_number);
			stroke = new BasicStroke(3);//�����߿�
			((Graphics2D) g).setStroke(stroke); //�����߿�Ϊ3.0
		}
		
//		switch (color) {
//		case "red":
//			g.setColor(Color.red); // ���û�����ɫΪ��ɫ
//			break;
//		case "blue":
//			g.setColor(Color.blue); // ���û�����ɫΪ��ɫ
//			break;
//		case "green":
//			g.setColor(Color.green); // ���û�����ɫΪ��ɫ
//			break;
//		case "black":
//			g.setColor(Color.black); // ���û�����ɫΪ��ɫ
//			break;
//		case "white":
//			g.setColor(Color.white); // ���û�����ɫΪ��ɫ
//			break;
//		case "yellow":
//			g.setColor(Color.yellow); // ���û�����ɫΪ��ɫ
//			break;
//		default:
//			g.setColor(Color.blue); // ���û�����ɫΪ��ɫ
//			break;
//		}
		
//		Graphics2D g2d=(Graphics2D)g; //����2d����
//		thick = Integer.parseInt(string_number);
//		stroke = new BasicStroke(thick);//�����߿�
//		g2d.setStroke(stroke); //�����߿�Ϊ4.0
		
		MyPoint v1_first = null;
		MyPoint v1_second = null;
		MyPoint v1_third = null;
		MyPoint v2_first = null;
		MyPoint v2_second = null;
		MyPoint v2_third = null;
		MyPoint v3_first = null;
		MyPoint v3_second = null;
		MyPoint v3_third = null;
		MyPoint v4_first = null;
		MyPoint v4_second = null;
		MyPoint v5_first = null;
		MyPoint v5_second = null;
		//ѭ��v1����
		for (int i = 0; i < v1.size(); i++) {

			//����˳�����õ�
			if(v1.elementAt(i).order == 1){
				v1_first = (MyPoint) v1.elementAt(i);
			}
			else if(v1.elementAt(i).order == 2){
				v1_second = (MyPoint) v1.elementAt(i);
			}
			else if(v1.elementAt(i).order == 3){
				v1_third = (MyPoint) v1.elementAt(i);
			}
		}
		// �ݴ�����
		if(v1_first == null | v1_second == null | v1_third == null){
			int sfd = JOptionPane.showConfirmDialog(null, "�ؽ�ע�����ȷ���Ƿ����ã�", "��������ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
			 if(sfd == JOptionPane.YES_OPTION){
				 myLabel.reset();//�������ò�������ջ���
				 myLabel.repaint(); // �ػ����
				return;
			}
			//���ȡ�����ò�������ʲô������ֱ�ӷ���
			else if(sfd == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// ȡ�����ߣ��ȴ��û�������ע����
				return;
			}
			
		}

		//����˳����
		g.drawLine(v1_first.point.x,v1_first.point.y,v1_second.point.x,v1_second.point.y); // ����1�ŵ���2�ŵ�
		g.drawLine(v1_second.point.x,v1_second.point.y,v1_third.point.x,v1_third.point.y); // ����2�ŵ���3�ŵ�
		
		//ѭ��v2����
		for (int i = 0; i < v2.size(); i++) {

			//����˳�����õ�
			if(v2.elementAt(i).order == 1){
				v2_first = (MyPoint) v2.elementAt(i);
			}
			else if(v2.elementAt(i).order == 2){
				v2_second = (MyPoint) v2.elementAt(i);
			}
			else if(v2.elementAt(i).order == 3){
				v2_third = (MyPoint) v2.elementAt(i);
			}	
		}
		
		// �ݴ�����
		if(v2_first == null | v2_second == null | v2_third == null){
		int sfd = JOptionPane.showConfirmDialog(null, "�ؽ�ע�����ȷ���Ƿ����ã�", "��������ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
			if(sfd == JOptionPane.YES_OPTION){
				myLabel.reset();//�������ò�������ջ���
				myLabel.repaint(); // �ػ����
				return;
			}
			//���ȡ�����ò�������ʲô������ֱ�ӷ���
			else if(sfd == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// ȡ�����ߣ��ȴ��û�������ע����
				return;
			}
		}	
		//����˳����
		g.drawLine(v2_first.point.x,v2_first.point.y,v1_second.point.x,v1_second.point.y); // ����1�ŵ���v1��2�ŵ�
		g.drawLine(v2_first.point.x,v2_first.point.y,v2_second.point.x,v2_second.point.y); // ����1�ŵ���2�ŵ�
		g.drawLine(v2_second.point.x,v2_second.point.y,v2_third.point.x,v2_third.point.y); // ����2�ŵ���3�ŵ�
		//ѭ��v3����
		for (int i = 0; i < v3.size(); i++) {

			//����˳�����õ�
			if(v3.elementAt(i).order == 1){
				v3_first = (MyPoint) v3.elementAt(i);
			}
			else if(v3.elementAt(i).order == 2){
				v3_second = (MyPoint) v3.elementAt(i);
			}
			else if(v3.elementAt(i).order == 3){
				v3_third = (MyPoint) v3.elementAt(i);
			}	
		}
		
		// �ݴ�����
		if(v3_first == null | v3_second == null | v3_third == null){
			int sf = JOptionPane.showConfirmDialog(null, "�ؽ�ע�����ȷ���Ƿ����ã�", "��������ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
			 if(sf == JOptionPane.YES_OPTION){
				 myLabel.reset();//�������ò�������ջ���
				 myLabel.repaint(); // �ػ����
				return;
			}
			//���ȡ�����ò�������ʲô������ֱ�ӷ���
			else if(sf == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// ȡ�����ߣ��ȴ��û�������ע����
				return;
			}
		}
		//����˳����
		g.drawLine(v3_first.point.x,v3_first.point.y,v1_second.point.x,v1_second.point.y); // ����1�ŵ���v1��2�ŵ�
		g.drawLine(v3_first.point.x,v3_first.point.y,v3_second.point.x,v3_second.point.y); // ����1�ŵ���2�ŵ�
		g.drawLine(v3_second.point.x,v3_second.point.y,v3_third.point.x,v3_third.point.y); // ����2�ŵ���3�ŵ�
		
		//ѭ��v4����
		for (int i = 0; i < v4.size(); i++) {

			//����˳�����õ�
			if(v4.elementAt(i).order == 1){
				v4_first = (MyPoint) v4.elementAt(i);
			}
			else if(v4.elementAt(i).order == 2){
				v4_second = (MyPoint) v4.elementAt(i);
			}
		}
		
		// �ݴ�����
		if(v4_first == null | v4_second == null | v1_third == null){
			int d = JOptionPane.showConfirmDialog(null, "�ؽ�ע�����ȷ���Ƿ����ã�", "��������ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
			 if(d == JOptionPane.YES_OPTION){
				 myLabel.reset();//�������ò�������ջ���
				 myLabel.repaint(); // �ػ����
				return;
			}
			//���ȡ�����ò�������ʲô������ֱ�ӷ���
			else if(d == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// ȡ�����ߣ��ȴ��û�������ע����
				return;
			}
		}
		//����˳����
		g.drawLine(v4_first.point.x,v4_first.point.y,v1_third.point.x,v1_third.point.y); // ����1�ŵ���v1��3�ŵ�
		g.drawLine(v4_first.point.x,v4_first.point.y,v4_second.point.x,v4_second.point.y); // ����1�ŵ���2�ŵ�
		//ѭ��v5����
		for (int i = 0; i < v5.size(); i++) {

			//����˳�����õ�
			if(v5.elementAt(i).order == 1){
				v5_first = (MyPoint) v5.elementAt(i);
			}
			else if(v5.elementAt(i).order == 2){
				v5_second = (MyPoint) v5.elementAt(i);
			}
		}
		
		// �ݴ�����
		if(v5_first == null | v5_second == null){
			int d5 = JOptionPane.showConfirmDialog(null, "�ؽ�ע�����ȷ���Ƿ����ã�", "��������ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
			 if(d5 == JOptionPane.YES_OPTION){
				 myLabel.reset();//�������ò�������ջ���
				 myLabel.repaint(); // �ػ����
				return;
			}
			//���ȡ�����ò�������ʲô������ֱ�ӷ���
			else if(d5 == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// ȡ�����ߣ��ȴ��û�������ע����
				return;
			}
		}	 
		//����˳����
		g.drawLine(v5_first.point.x,v5_first.point.y,v1_third.point.x,v1_third.point.y); // ����1�ŵ���v1��3�ŵ�
		g.drawLine(v5_first.point.x,v5_first.point.y,v5_second.point.x,v5_second.point.y); // ����1�ŵ���2�ŵ�
		//g.setColor(Color.RED); // ���û�����ɫΪĬ�ϵĺ�ɫ
	}

	// �е㻭�߷�
	public void MydrawOval1(int x0, int y0, int x1, int y1, Graphics g) {
		int a, b, delta1, delta2, d, x, y;
		float m;
		if (x1 < x0) {
			d = x0;
			x0 = x1;
			x1 = d;
			d = y0;
			y0 = y1;
			y1 = d;
		}
		a = y0 - y1;
		b = x1 - x0;
		if (b == 0)
			m = -1 * a * 100;
		else
			m = (float) a / (x0 - x1);
		x = x0;
		y = y0; // drawpoint(x,y,g);
		g.drawOval(x, y, 1, 1);
		if (m >= 0 && m <= 1) {
			d = 2 * a + b;
			delta1 = 2 * a;
			delta2 = 2 * (a + b);
			while (x < x1) {
				if (d <= 0) {
					x++;
					y++;
					d += delta2;
				} else {
					x++;
					d += delta1;
				} // drawpoint(x,y,g);
				g.drawOval(x, y, 1, 1);
			}
		} 
		else if (m <= 0 && m >= -1) {
			d = 2 * a - b;
			delta1 = 2 * a - 2 * b;
			delta2 = 2 * a;
			while (x < x1) {
				if (d > 0) {
					x++;
					y--;
					d += delta1;
				} else {
					x++;
					d += delta2;
				} // drawpoint(x,y,g);
				g.drawOval(x, y, 1, 1);
			}
		} 
		else if (m > 1) {
			d = a + 2 * b;
			delta1 = 2 * (a + b);
			delta2 = 2 * b;
			while (y < y1) {
				if (d > 0) {
					x++;
					y++;
					d += delta1;
				} 
				else {
					y++;
					d += delta2;
				} // drawpoint(x,y,g);
				g.drawOval(x, y, 1, 1);
			}
		} 
		else {
			d = a - 2 * b;
			delta1 = -2 * b;
			delta2 = 2 * (a - b);
			while (y > y1) {
				if (d <= 0) {
					x++;
					y--;
					d += delta2;
				} 
				else {
					y--;
					d += delta1;
				} // drawpoint(x,y,g); g.drawOval(x,y,1,1);

			}
		}
	}

	// ��ֵ΢�ַ�
	public void MydrawOval2(int x0, int y0, int x1, int y1, Graphics g) {
		g.setColor(Color.RED);
		int a, b, n, k;
		float xinc, yinc, x, y;
		b = x1 - x0;
		a = y1 - y0;
		if (Math.abs(b) > Math.abs(a))
			n = Math.abs(b);
		else
		n = Math.abs(a);
		xinc = (float) b / n;
		yinc = (float) a / n;
		x = (float) x0;
		y = (float) y0;
		for (k = 1; k <= n; k++) {
			// Drawpixel((int)(x+0.5),(int) (y+0.5),color);
			int i = (int) (x + 0.5);
			int j = (int) (y + 0.5);
			g.drawOval(i, j, 1, 1);
			x += xinc;
			y += yinc;
		}

	}

// Bresenham�㷨2
public void MydrawOval3(int x1, int y1, int x2, int y2, Graphics g) {
	g.setColor(Color.RED);
	int dx = x2 - x1;
	int dy = y2 - y1;
	int ux, uy; // = ((dx > 0) << 1) - 1;//x����������ȡ��-1
	// int uy// = ((dy > 0) << 1) - 1;//y����������ȡ��-1
	if (dx > 0) {
		ux = 1;
	} else {
		ux = -1;
	}
	if (dy > 0) {
		uy = 1;
	} else {
		uy = -1;
	}

	int x = x1, y = y1, eps;// epsΪ�ۼ����

	eps = 0;
	dx = Math.abs(dx);
	dy = Math.abs(dy);
	if (dx > dy) {
		for (x = x1; x != x2; x += ux) {
			g.drawOval(x, y, 1, 1);
			eps += dy;
			if ((eps << 1) >= dx) {
				y += uy;
				eps -= dx;
			}
		}
	} else {
		for (y = y1; y != y2; y += uy) {
			g.drawOval(x, y, 1, 1);
			eps += dx;
			if ((eps << 1) >= dy) {
				x += ux;
				eps -= dy;
				}
			}
		}
	}



}