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
 * 画线逻辑算法类
 *
 */
//绘画逻辑类及其内的逻辑方法
public  class Mydraw{
	/**
	 * 
	 */
	static WindowOperation win; // 定义窗体类变量，用于接收传进来的窗体对象的引用
	MyLabel myLabel;
	int[] RGB; //定义线条颜色的RGB数组
	Font m_font = null; // 定义字体

	public Mydraw(MyLabel myLabel){// 带MyaLabel参数类型的构造函数
		this.myLabel = myLabel;
		
	}
	
	//获取win窗体对象
	public static void setWin(WindowOperation wins) {
		win = wins;
	}
	
	public String color = win.getColor(); // 获取颜色类型
	public String string_number = win.getThick(); // 获取颜色类型
	Stroke stroke; // 定义笔画
	float thick; // 定义画笔粗细
	//调用画点方法
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

		m_font = myLabel.getm_font(); // 获取字体
		if(m_font != null){
			((Graphics2D)g).setFont(m_font);
			//thick = Integer.parseInt(string_number);
			stroke = new BasicStroke(m_font.getSize());//定义线宽
			((Graphics2D) g).setStroke(stroke); //设置线宽为4.0
		}
		else{
			g.setFont(new Font("新宋体", Font.PLAIN, 20));
			//thick = Integer.parseInt(string_number);
			stroke = new BasicStroke(3);//定义线宽
			((Graphics2D) g).setStroke(stroke); //设置线宽为3.0
		}
		
//		switch (color) {
//		case "red":
//			g.setColor(Color.red); // 设置画笔颜色为红色
//			break;
//		case "blue":
//			g.setColor(Color.blue); // 设置画笔颜色为蓝色
//			break;
//		case "green":
//			g.setColor(Color.green); // 设置画笔颜色为绿色
//			break;
//		case "black":
//			g.setColor(Color.black); // 设置画笔颜色为黑色
//			break;
//		case "white":
//			g.setColor(Color.white); // 设置画笔颜色为白色
//			break;
//		case "yellow":
//			g.setColor(Color.yellow); // 设置画笔颜色为黄色
//			break;
//		default:
//			g.setColor(Color.blue); // 设置画笔颜色为蓝色
//			break;
//		}
		
//		Graphics2D g2d=(Graphics2D)g; //建立2d画笔
//		thick = Integer.parseInt(string_number);
//		stroke = new BasicStroke(thick);//定义线宽
//		g2d.setStroke(stroke); //设置线宽为4.0
		
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
		//循环v1容器
		for (int i = 0; i < v1.size(); i++) {

			//根据顺序设置点
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
		// 容错代码
		if(v1_first == null | v1_second == null | v1_third == null){
			int sfd = JOptionPane.showConfirmDialog(null, "关节注解错误，确认是否重置？", "重置坐标确认对话框", JOptionPane.YES_NO_OPTION);
			 if(sfd == JOptionPane.YES_OPTION){
				 myLabel.reset();//设置重置参数，清空缓存
				 myLabel.repaint(); // 重绘界面
				return;
			}
			//如果取消重置操作，将什么都不做直接返回
			else if(sfd == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// 取消连线，等待用户调整标注参数
				return;
			}
			
		}

		//根据顺序画线
		g.drawLine(v1_first.point.x,v1_first.point.y,v1_second.point.x,v1_second.point.y); // 连接1号点与2号点
		g.drawLine(v1_second.point.x,v1_second.point.y,v1_third.point.x,v1_third.point.y); // 连接2号点与3号点
		
		//循环v2容器
		for (int i = 0; i < v2.size(); i++) {

			//根据顺序设置点
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
		
		// 容错代码
		if(v2_first == null | v2_second == null | v2_third == null){
		int sfd = JOptionPane.showConfirmDialog(null, "关节注解错误，确认是否重置？", "重置坐标确认对话框", JOptionPane.YES_NO_OPTION);
			if(sfd == JOptionPane.YES_OPTION){
				myLabel.reset();//设置重置参数，清空缓存
				myLabel.repaint(); // 重绘界面
				return;
			}
			//如果取消重置操作，将什么都不做直接返回
			else if(sfd == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// 取消连线，等待用户调整标注参数
				return;
			}
		}	
		//根据顺序画线
		g.drawLine(v2_first.point.x,v2_first.point.y,v1_second.point.x,v1_second.point.y); // 连接1号点与v1的2号点
		g.drawLine(v2_first.point.x,v2_first.point.y,v2_second.point.x,v2_second.point.y); // 连接1号点与2号点
		g.drawLine(v2_second.point.x,v2_second.point.y,v2_third.point.x,v2_third.point.y); // 连接2号点与3号点
		//循环v3容器
		for (int i = 0; i < v3.size(); i++) {

			//根据顺序设置点
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
		
		// 容错代码
		if(v3_first == null | v3_second == null | v3_third == null){
			int sf = JOptionPane.showConfirmDialog(null, "关节注解错误，确认是否重置？", "重置坐标确认对话框", JOptionPane.YES_NO_OPTION);
			 if(sf == JOptionPane.YES_OPTION){
				 myLabel.reset();//设置重置参数，清空缓存
				 myLabel.repaint(); // 重绘界面
				return;
			}
			//如果取消重置操作，将什么都不做直接返回
			else if(sf == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// 取消连线，等待用户调整标注参数
				return;
			}
		}
		//根据顺序画线
		g.drawLine(v3_first.point.x,v3_first.point.y,v1_second.point.x,v1_second.point.y); // 连接1号点与v1的2号点
		g.drawLine(v3_first.point.x,v3_first.point.y,v3_second.point.x,v3_second.point.y); // 连接1号点与2号点
		g.drawLine(v3_second.point.x,v3_second.point.y,v3_third.point.x,v3_third.point.y); // 连接2号点与3号点
		
		//循环v4容器
		for (int i = 0; i < v4.size(); i++) {

			//根据顺序设置点
			if(v4.elementAt(i).order == 1){
				v4_first = (MyPoint) v4.elementAt(i);
			}
			else if(v4.elementAt(i).order == 2){
				v4_second = (MyPoint) v4.elementAt(i);
			}
		}
		
		// 容错代码
		if(v4_first == null | v4_second == null | v1_third == null){
			int d = JOptionPane.showConfirmDialog(null, "关节注解错误，确认是否重置？", "重置坐标确认对话框", JOptionPane.YES_NO_OPTION);
			 if(d == JOptionPane.YES_OPTION){
				 myLabel.reset();//设置重置参数，清空缓存
				 myLabel.repaint(); // 重绘界面
				return;
			}
			//如果取消重置操作，将什么都不做直接返回
			else if(d == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// 取消连线，等待用户调整标注参数
				return;
			}
		}
		//根据顺序画线
		g.drawLine(v4_first.point.x,v4_first.point.y,v1_third.point.x,v1_third.point.y); // 连接1号点与v1的3号点
		g.drawLine(v4_first.point.x,v4_first.point.y,v4_second.point.x,v4_second.point.y); // 连接1号点与2号点
		//循环v5容器
		for (int i = 0; i < v5.size(); i++) {

			//根据顺序设置点
			if(v5.elementAt(i).order == 1){
				v5_first = (MyPoint) v5.elementAt(i);
			}
			else if(v5.elementAt(i).order == 2){
				v5_second = (MyPoint) v5.elementAt(i);
			}
		}
		
		// 容错代码
		if(v5_first == null | v5_second == null){
			int d5 = JOptionPane.showConfirmDialog(null, "关节注解错误，确认是否重置？", "重置坐标确认对话框", JOptionPane.YES_NO_OPTION);
			 if(d5 == JOptionPane.YES_OPTION){
				 myLabel.reset();//设置重置参数，清空缓存
				 myLabel.repaint(); // 重绘界面
				return;
			}
			//如果取消重置操作，将什么都不做直接返回
			else if(d5 == JOptionPane.NO_OPTION){
				myLabel.line_Flag = 0;// 取消连线，等待用户调整标注参数
				return;
			}
		}	 
		//根据顺序画线
		g.drawLine(v5_first.point.x,v5_first.point.y,v1_third.point.x,v1_third.point.y); // 连接1号点与v1的3号点
		g.drawLine(v5_first.point.x,v5_first.point.y,v5_second.point.x,v5_second.point.y); // 连接1号点与2号点
		//g.setColor(Color.RED); // 重置画笔颜色为默认的红色
	}

	// 中点画线法
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

	// 数值微分法
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

// Bresenham算法2
public void MydrawOval3(int x1, int y1, int x2, int y2, Graphics g) {
	g.setColor(Color.RED);
	int dx = x2 - x1;
	int dy = y2 - y1;
	int ux, uy; // = ((dx > 0) << 1) - 1;//x的增量方向，取或-1
	// int uy// = ((dy > 0) << 1) - 1;//y的增量方向，取或-1
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

	int x = x1, y = y1, eps;// eps为累加误差

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
