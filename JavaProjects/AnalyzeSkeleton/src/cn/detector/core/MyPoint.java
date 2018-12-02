/**
 * 
 */
package cn.detector.core;

import java.awt.Color;
import java.awt.Point;

/**
 * 节点类
 *用于存储节点数据信息的类
 */

public class MyPoint{
	
	//定义点对象，用于接收点坐标
	public Point point =null; // 点对象
	public Color color;// Save the color
	public Boolean is_Stored = false; // 是否保存标志，默认为未保存
	public Boolean anchor = false; // 锚点，默认为非锚点
	public Boolean isUPPerBody = false; // 上体节点标志，默认为非下体节点
	public int link_Flag; // 关联标记
	public int order; // 绘图次序
	public int vector_index;// 容器标记层
	public int parameter = 0;// 标记参数
	public int body_Parts = 0;// 标记参数
	// 无参构造函数
	public MyPoint(){
		super();
	}
	// 有参构造函数，参数为MyPoint子对象
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
