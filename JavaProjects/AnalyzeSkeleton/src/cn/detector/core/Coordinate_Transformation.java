package cn.detector.core;

import java.awt.Point;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

/**
 * 核心代码： 坐标转换及解析处理类 此类为整个系统的核心部分 用于数据分析和解析数据并将计算结果传出的类
 */
public class Coordinate_Transformation {
	Vector<MyPoint> v_Joint = new Vector<MyPoint>(); // 定义v_Joint用于缓存转换后的关节点对象
	Vector<MyPoint> pointsVector; // 定义pointsVector容器用于存储关节点
	// 创建五个用于缓存人体五个部分的关节点坐标，有左上（vector2），中部（vector1），右上（vector3），左下（vector4），右下（vector5）部位
	Vector<MyPoint> vector1 = new Vector<MyPoint>();
	Vector<MyPoint> vector2 = new Vector<MyPoint>();
	Vector<MyPoint> vector3 = new Vector<MyPoint>();
	Vector<MyPoint> vector4 = new Vector<MyPoint>();
	Vector<MyPoint> vector5 = new Vector<MyPoint>();
	MyPoint brow_Anchor = null;
	MyPoint jaw_Anchor = null;
	MyPoint belly_Anchor = null;

	// 获取容器函数
	public Vector<MyPoint> getVector1() {
		return vector1;
	}

	public Vector<MyPoint> getVector2() {
		return vector2;
	}

	public Vector<MyPoint> getVector3() {
		return vector3;
	}

	public Vector<MyPoint> getVector4() {
		return vector4;
	}

	public Vector<MyPoint> getVector5() {
		return vector5;
	}

	// 缓存边框的宽度和高度
	private double heigh_Distance;
	private double width_Distance;
	// 用于缓存原始坐标值
	private int x1 = 0, y1 = 0;
	private int x2, y2;
	@SuppressWarnings("unused")
	private int x3, y3;
	private int x4, y4;
	// 用于缓存转换后的坐标值
	private int Jx1, Jy1;
	@SuppressWarnings("unused")
	private int Jx2, Jy2;
	@SuppressWarnings("unused")
	private int Jx3, Jy3;
	@SuppressWarnings("unused")
	private int Jx4, Jy4;
	private final int delta1 = 1;

	public int parameter = 15;

	public int getParameter() {
		return parameter;
	}

	public void setParameter(int parameter) {
		this.parameter = parameter;
	}

	// 有参构造函数
	public Coordinate_Transformation(Vector<Line> lv, Vector<MyPoint> v) {
		super();
		if (v.size() != 13) {
			return;
		} else if (lv.size() != 4) {
			return;
		}
		// 点容器的引用的传入
		pointsVector = v;
		// 缓存边框四点的坐标值
		this.x1 = lv.elementAt(0).x0;
		this.y1 = lv.elementAt(0).y0;
		this.x2 = lv.elementAt(1).x0;
		this.y2 = lv.elementAt(1).y0;
		this.x3 = lv.elementAt(2).x0;
		this.y3 = lv.elementAt(2).y0;
		this.x4 = lv.elementAt(3).x0;
		this.y4 = lv.elementAt(3).y0;
		// 计算两点之间的距离
		heigh_Distance = PointToLine_Distance.lineSpace(x1, y1, x4, y4);
		width_Distance = PointToLine_Distance.lineSpace(x1, y1, x2, y2);

		// 转换坐标
		Jx1 = x1;
		Jy1 = y1;
		Jx2 = x1 + (int) width_Distance;
		Jy2 = y1;
		Jx3 = x1 + (int) width_Distance;
		Jy3 = y1 + (int) heigh_Distance;
		Jx4 = x1;
		Jy4 = y1 + (int) heigh_Distance;
	}

	// 转换边框四点的坐标时斜坐标转换成正坐标
	public boolean change_coordinate() {
		if (x1 == 0 && y1 == 0) {
			return false;
		}

		PointToLine_Distance p_Line_Distance = new PointToLine_Distance(); // 创建求点到直线距离的对象
		for (int i = 0; i < pointsVector.size(); i++) {// 循环容器，这里不需要对i局部变量进行++操作，容器会自动循环搜索数据
			// 获取容器中的元素
			MyPoint tmp = (MyPoint) pointsVector.elementAt(i);
			// 设置原始点对象的标记值
			tmp.link_Flag = i;
			// 创建新容器的MyPoint元素
			MyPoint m = new MyPoint();
			// 设置新建点对象的标记值，并与对应的原始点对象的标记值相同，以便一一对应
			m.link_Flag = i;
			// 创建点对象
			m.point = new Point();
			m.parameter = tmp.parameter;
			// 转换节点坐标
			m.point.x = Jx1 + (int) p_Line_Distance.getDistance(x1, y1, x4, y4, tmp.point.x, tmp.point.y);
			m.point.y = Jy1 + (int) p_Line_Distance.getDistance(x1, y1, x2, y2, tmp.point.x, tmp.point.y);
			// 将转换后的节点新坐标加入关节点容器中
			v_Joint.add(m);
		}
		return true;

	}

	// 对人体节点所在的位置进行分层，并将分层过的节点分别存入vector1，vector2，vector3，vector4，vector5容器中
	public boolean divide_Layer() {

		// 获取边框中点横坐标
		double middle = (Jx1 + Jx2) / 2;
		// 循环节点容器v_Joint
		for (int i = 0; i < v_Joint.size(); i++) {
			MyPoint tmp = (MyPoint) v_Joint.elementAt(i);
			if(tmp.parameter == 0){
				tmp.parameter = parameter;
			}
			// 1、获取属于中间层的点，放入vector1容器中
			if (tmp.point.x > middle - width_Distance / tmp.parameter
					&& tmp.point.x < middle + width_Distance / tmp.parameter) {
				addPoints(tmp, vector1);
			}
			// 2、获取左层的点，放入vector2容器中
			else if (tmp.point.x > Jx1 && tmp.point.x < middle - width_Distance / tmp.parameter) {
				addPoints(tmp, vector2);
			}
			// 3、获取右层的点，放入vector3容器中
			else if (tmp.point.x > middle + width_Distance / tmp.parameter && tmp.point.x < Jx2) {
				addPoints(tmp, vector3);
			}
		}
		/*
		 * 接下来，需要vector2和vector3容器进行分析出现的各种情况 有以下几种情况： 1、vector1的size为3
		 * 2、vector1的size不为3
		 * 
		 */
		// 人体四肢越过身体对称轴
		if (vector1.size() >= 3) {
			// 1、双手交叉放在腹前，但双掌接触，且双腿舒展放置，身体平躺
			// 2、身体平躺，双腿交叉，膝盖重叠，手臂舒展放置
			// 3、身体平躺，脚重叠，膝盖左右突出，手臂舒展放置
			// 4、身体平躺，四肢并未超出身体对称线（vector1.size() = 3）
			MyPoint[] anchor_Arr = anchor_analyze(); // 将三个锚点解析出来，vector1容器中只有除锚点以外的功能点对象
			// 通过锚点与各关节点的相对位置，依次分离出来，从而方便地为各个功能点设置绘制顺序的变量order
			if (!setOrder(anchor_Arr)) {
				return false;
			}

		}

		// 人体正常平躺姿势，且人体四肢未越过身体对称轴
		else if (vector1.size() < 3) {
			return false;
		}
		return true;
	}

	/*
	 * 对容器进行排序和分拣的代码块
	 * 
	 * 将点对象分类存放到不同的容器中
	 * 
	 */
	// [start]setOrder(MyPoint[] anchor_Arr)排序函数
	private boolean setOrder(MyPoint[] anchor_Arr) {
		boolean isUpperSingle = false;
		boolean isLowerSingle = false;
		brow_Anchor = anchor_Arr[0];
		jaw_Anchor = anchor_Arr[1];
		belly_Anchor = anchor_Arr[2];
		if(brow_Anchor == null | jaw_Anchor == null | belly_Anchor == null){
			return false;
		}

		// [start]多余0个节点
		if (vector1.size() == 0) {// 正常平躺的姿势
			int upper_Body_Counts = getNumOfUpper_Joint(); // 分析其余容器多余的关节点属于上体的个数
			// [start]对称判断逻辑
			if (upper_Body_Counts == -1) {// 上体不对称，下体对称
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// 上体对称，下体不对称
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// 上下体均不对称
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // 上下体均对称
				isUpperSingle = false;
				isLowerSingle = false;
			}
			// [end]

			separator(upper_Body_Counts, isUpperSingle, isLowerSingle); // 将vector2和vector3容器进行分离，分离的部分点对象放入vector4和vector5中
			// 将锚点重新加入vector1容器
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));

			// 左臂
			if (!analyse_LeftArm(jaw_Anchor)) {
				return false;
			}

			// 右臂
			if (!analyse_RightArm(jaw_Anchor)) {
				return false;
			}

			// 左腿
			if (!detail_Split(vector4, belly_Anchor)) {
				return false;
			} // 区分knee和ankle关节,并有序地加入容器参数中
			vector4.elementAt(0).order = 1; // 设置关节次序
			vector4.elementAt(1).order = 2; // 设置关节次序

			// 右腿
			if (!detail_Split(vector5, belly_Anchor)) {
				return false;
			} // 区分knee和ankle关节，并有序地加入容器参数中
			vector5.elementAt(0).order = 1; // 设置关节次序
			vector5.elementAt(1).order = 2; // 设置关节次序
		}
		// [end]
		// [start]多余1个节点
		// vector1容器中多余了1个关节点
		else if (vector1.size() == 1) {

			int upper_Body_Counts = getNumOfUpper_Joint(); // 分析多余的关节点s属于上体的个数

			// [start]对称判断逻辑
			if (upper_Body_Counts == -1) {// 上体不对称，下体对称
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// 上体对称，下体不对称
				upper_Body_Counts = 3;
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// 上下体均不对称
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // 上下体均对称
				isUpperSingle = false;
				isLowerSingle = false;
			}
			// [end]

			separator(upper_Body_Counts, isUpperSingle, isLowerSingle); // 将vector2和vector3容器进行分离，分离的部分点对象放入vector4和vector5中
			// 左臂
			if (vector2.size() == 3) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的

				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}

			} else if (vector2.size() == 2) {// 说明此时，有手掌放在身体上

				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // 加入左臂的shoulder节点

				vector2.elementAt(0).order = 2; // 设置关节次序
				if (!vector1.isEmpty()) {// 判断容器是否非空
					MyPoint p = vector1.remove(0); // 删除vector1多出的节点
					p.order = 3; // 将删除的节点设置关节次序为3
					vector2.add(p); // 将此节点加入左臂容器中
				}

			}

			// 右臂
			if (vector3.size() == 3) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的

				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}

			} else if (vector3.size() == 2) {// 说明此时，有双手掌放在身体上

				MyPoint right_shoulder = line_min(vector3, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				right_shoulder.order = 1; // 设置关节次序
				vector3.add(right_shoulder); // 加入右臂的shoulder节点
				if (vector3.size() == 2) {
					vector3.elementAt(0).order = 2; // 设置关节次序
					if (!vector1.isEmpty()) {// 判断容器是否非空
						MyPoint p = vector1.remove(0); // 删除vector1多出的节点
						p.order = 3; // 将删除的节点设置关节次序为3
						vector3.add(p); // 将此节点加入右臂容器中
					}
				}
			}
			// 左腿
			if (vector4.size() == 1) {// 说明是腿关节的位置处于人体中心位置

				MyPoint p = vector1.remove(0); // 删除vector1多出的节点
				vector4.add(p); // 将此节点加入右臂容器中
				// 左腿
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节,并有序地加入容器参数中
				vector4.elementAt(0).order = 1; // 设置关节次序
				vector4.elementAt(1).order = 2; // 设置关节次序

			} else if (vector4.size() == 2) {// 说明腿关节舒展躺着，并未处于人体中心位置

				// 左腿
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节,并有序地加入容器参数中
				vector4.elementAt(0).order = 1; // 设置关节次序
				vector4.elementAt(1).order = 2; // 设置关节次序

			}
			// 右腿
			if (vector5.size() == 1) {// 说明是腿关节的位置处于人体中心位置

				MyPoint p = vector1.remove(0); // 删除vector1多出的节点
				vector5.add(p); // 将此节点加入右臂容器中
				// 右腿
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节，并有序地加入容器参数中
				vector5.elementAt(0).order = 1; // 设置关节次序
				vector5.elementAt(1).order = 2; // 设置关节次序
			} else if (vector5.size() == 2) { // 说明腿关节舒展躺着，并未处于人体中心位置
				// 右腿
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节，并有序地加入容器参数中
				vector5.elementAt(0).order = 1; // 设置关节次序
				vector5.elementAt(1).order = 2; // 设置关节次序
			}

			// 将锚点重新加入vector1容器
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]
		// [start]多余2个节点
		// vector1容器中多余了2个关节点
		else if (vector1.size() == 2) {
			int upper_Body_Counts = getNumOfUpper_Joint(); // 分析多余的关节点s属于上体的个数

			if (upper_Body_Counts == -1) {// 上体不对称，下体对称
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// 上体对称，下体不对称
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// 上下体均不对称
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // 上下体均对称
				isUpperSingle = false;
				isLowerSingle = false;
			}
			// 将vector2和vector3容器进行分离，分离的部分点对象放入vector4和vector5中
			separator(upper_Body_Counts, isUpperSingle, isLowerSingle);

			// 左臂
			if (vector2.size() == 3 && !isUpperSingle) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的

				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			} else if (vector2.size() == 3 && isUpperSingle) {// 若上肢不对称直接对容器内点依次排序就好

				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			} else if (vector2.size() == 2) {// 说明此时，有双掌放在身体上
				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // 加入左臂的shoulder节点
				vector2.elementAt(0).order = 2; // 设置关节次序

				if (!vector1.isEmpty()) {// 判断容器是否非空
					MyPoint p = vector1.remove(0); // 删除vector1多出的节点
					p.order = 3; // 将删除的节点设置关节次序为3
					vector2.add(p); // 将此节点加入左臂容器中
				}
			}

			// 右臂
			if (vector3.size() == 3 && !isUpperSingle) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的

				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}
			}

			else if (vector3.size() == 3 && isUpperSingle) {// 若上肢不对称直接对容器内点依次排序就好

				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}
			}

			else if (vector3.size() == 2) {// 说明此时，有双手掌放在身体上

				MyPoint right_shoulder = line_min(vector3, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				right_shoulder.order = 1; // 设置关节次序
				vector3.add(right_shoulder); // 加入右臂的shoulder节点
				vector3.elementAt(0).order = 2; // 设置关节次序

				if (!vector1.isEmpty()) {// 判断容器是否非空
					MyPoint p = vector1.remove(0); // 删除vector1多出的节点
					p.order = 3; // 将删除的节点设置关节次序为3
					vector3.add(p); // 将此节点加入右臂容器中
				}
			}
			// 左腿
			if (vector4.size() == 1) {// 说明是腿关节的位置处于人体中心位置

				MyPoint p = vector1.remove(0); // 删除vector1多出的节点
				vector4.add(p); // 将此节点加入右臂容器中
				// 左腿
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节,并有序地加入容器参数中
				vector4.elementAt(0).order = 1; // 设置关节次序
				vector4.elementAt(1).order = 2; // 设置关节次序

			} else if (vector4.size() == 2) {// 说明腿关节舒展躺着，并未处于人体中心位置

				// 左腿
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节,并有序地加入容器参数中
				vector4.elementAt(0).order = 1; // 设置关节次序
				vector4.elementAt(1).order = 2; // 设置关节次序

			}
			// 右腿
			if (vector5.size() == 1) {// 说明是腿关节的位置处于人体中心位置
				if (!vector1.isEmpty()) {// 判断容器是否非空
					MyPoint p = vector1.remove(0); // 删除vector1多出的节点
					vector5.add(p); // 将此节点加入右臂容器中
				}
				// 右腿
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节，并有序地加入容器参数中
				vector5.elementAt(0).order = 1; // 设置关节次序
				vector5.elementAt(1).order = 2; // 设置关节次序
			} else if (vector5.size() == 2) { // 说明腿关节舒展躺着，并未处于人体中心位置
				// 右腿
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节，并有序地加入容器参数中
				vector5.elementAt(0).order = 1; // 设置关节次序
				vector5.elementAt(1).order = 2; // 设置关节次序
			}

			// 将锚点重新加入vector1容器
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]

		// [start]多余3个节点
		// vector1容器多余了3个节点
		else if (vector1.size() == 3) {
			int upper_Body_Counts = getNumOfUpper_Joint(); // 分析多余的关节点属于上体的个数

			if (upper_Body_Counts == -1) {// 上体不对称，下体对称
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// 上体对称，下体不对称
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// 上下体均不对称
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // 上下体均对称
				isUpperSingle = false;
				isLowerSingle = false;
			}

			if (!isUpperSingle && isLowerSingle) {// 上体对称，下体不对称
				MyPoint[] mp = sort(vector1);
				vector2.add(vector1.get(mp[0].vector_index));// 根据关节点所在原始容器中的索引号来索取当前容器vector1中的数据
				vector3.add(vector1.get(mp[1].vector_index));
				MyPoint p1 = vector1.get(2);
				vector1.clear();
				vector1.addElement(p1);
			} else if (isUpperSingle && !isLowerSingle) {// 上体不对称，下体对称
				MyPoint[] mp = sort(vector1);
				vector4.add(vector1.get(mp[1].vector_index));
				vector5.add(vector1.get(mp[2].vector_index));

			}

			// 将vector2和vector3容器进行分离，分离的部分点对象放入vector4和vector5中
			separator(upper_Body_Counts, isUpperSingle, isLowerSingle);

			// 左臂
			if (vector2.size() == 3 && !isUpperSingle) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的
				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			}

			else if (vector2.size() == 3 && isUpperSingle) {// 若上肢不对称直接对容器内点依次排序就好

				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			} else if (vector2.size() == 2) {// 说明此时，有双手掌放在身体上
				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // 加入左臂的shoulder节点
				vector2.elementAt(0).order = 2; // 设置关节次序

				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint third = new MyPoint(arr[0]);
				third.order = 3;
				vector2.add(third);
			} else if (vector2.size() == 1) {// 说明此时，有双臂合拢放在身体上
				vector2.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint second = new MyPoint(arr[0]);
				second.order = 2;
				vector2.add(second);
				MyPoint third = new MyPoint(arr[2]);
				third.order = 3;
				vector2.add(third);
			}
			// 右臂
			if (vector3.size() == 3 && !isUpperSingle) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的
				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}

			}

			else if (vector3.size() == 3 && isUpperSingle) {// 若上肢不对称直接对容器内点依次排序就好

				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}
			}

			else if (vector3.size() == 2) {// 说明此时，有双手掌放在身体上
				MyPoint left_shoulder = line_min(vector3, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				left_shoulder.order = 1;
				vector3.add(left_shoulder); // 加入左臂的shoulder节点
				vector3.elementAt(0).order = 2; // 设置关节次序

				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint third = new MyPoint(arr[1]);
				third.order = 3;
				vector3.add(third);
			} else if (vector3.size() == 1) {// 说明此时，有双臂合拢放在身体上
				vector3.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint second = new MyPoint(arr[1]);
				second.order = 2;
				vector3.add(second);
				MyPoint third = new MyPoint(arr[3]);
				third.order = 3;
				vector3.add(third);
			}

			// 左腿
			if (vector4.size() == 0) { // 说明两腿合拢，处于人体对称中心
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint first = new MyPoint(arr[0]);
				first.order = 1;
				vector4.add(first);
				MyPoint second = new MyPoint(arr[2]);
				second.order = 2;
				vector4.add(second);
			} else if (vector4.size() == 1) {// 说明是腿关节的位置处于人体中心位置
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint p = new MyPoint(arr[2]);
				if (vector4.get(0).point.y > p.point.y) {
					vector4.get(0).order = 2;
					p.order = 1;
				} else {
					vector4.get(0).order = 1;
					p.order = 2;
				}
				// 将p点加入容器中
				vector4.add(p);

			} else if (vector4.size() == 2) {// 说明腿关节舒展躺着，并未处于人体中心位置
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节,并有序地加入容器参数中
				vector4.elementAt(0).order = 1; // 设置关节次序
				vector4.elementAt(1).order = 2; // 设置关节次序
			}

			// 右腿
			if (vector5.size() == 0) { // 说明两腿合拢，处于人体对称中心
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint first = new MyPoint(arr[1]);
				first.order = 1;
				vector5.add(first);
				MyPoint second = new MyPoint(arr[3]);
				second.order = 2;
				vector5.add(second);
			} else if (vector5.size() == 1) {// 说明是腿关节的位置处于人体中心位置
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				if(arr.length <= 3){//容错判断
					return false;
				}
				MyPoint p = new MyPoint(arr[3]);
				if (vector5.get(0).point.y > p.point.y) {
					vector5.get(0).order = 2;
					p.order = 1;
				} else {
					vector5.get(0).order = 1;
					p.order = 2;
				}
				// 将p点加入容器中
				vector5.add(p);
			} else if (vector5.size() == 2) { // 说明腿关节舒展躺着，并未处于人体中心位置
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节，并有序地加入容器参数中
				vector5.elementAt(0).order = 1; // 设置关节次序
				vector5.elementAt(1).order = 2; // 设置关节次序
			}

			vector1.clear(); // 清空容器
			// 将锚点重新加入vector1容器
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]

		// [start]多余5个节点
		// vector1容器多余了5个节点
		else if (vector1.size() == 5) {
			int upper_Body_Counts = getNumOfUpper_Joint(); // 分析多余的关节点属于上体的个数

			if (upper_Body_Counts == -1) {// 上体不对称，下体对称
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// 上体对称，下体不对称
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// 上下体均不对称
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // 上下体均对称
				isUpperSingle = false;
				isLowerSingle = false;
			}

			if (!isUpperSingle && isLowerSingle) {// 上体对称，下体不对称
				MyPoint[] mp = sort(vector1);
				vector2.add(vector1.get(mp[0].vector_index));// 根据关节点所在原始容器中的索引号来索取当前容器vector1中的数据
				vector3.add(vector1.get(mp[1].vector_index));
				MyPoint p1 = vector1.get(2);
				vector1.clear();
				vector1.addElement(p1);
			} else if (isUpperSingle && !isLowerSingle) {// 上体不对称，下体对称
				MyPoint[] mp = sort(vector1);
				vector4.add(vector1.get(mp[1].vector_index));
				vector5.add(vector1.get(mp[2].vector_index));

			}

			// 将vector2和vector3容器进行分离，分离的部分点对象放入vector4和vector5中
			separator(upper_Body_Counts, isUpperSingle, isLowerSingle);

			// 左臂
			if (vector2.size() == 3) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的
				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			} else if (vector2.size() == 2) {// 说明此时，有双手掌放在身体上
				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // 加入左臂的shoulder节点
				vector2.elementAt(0).order = 2; // 设置关节次序

				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint third = new MyPoint(arr[0]);
				third.order = 3;
				vector2.add(third);
			} else if (vector2.size() == 1) {// 说明此时，有双臂合拢放在身体上
				vector2.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint second = new MyPoint(arr[0]);
				second.order = 2;
				vector2.add(second);
				MyPoint third = new MyPoint(arr[2]);
				third.order = 3;
				vector2.add(third);
			}
			// 右臂
			if (vector3.size() == 3) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的
				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}

			}

			else if (vector3.size() == 2) {// 说明此时，有双手掌放在身体上
				MyPoint left_shoulder = line_min(vector3, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				left_shoulder.order = 1;
				vector3.add(left_shoulder); // 加入左臂的shoulder节点
				vector3.elementAt(0).order = 2; // 设置关节次序

				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint third = new MyPoint(arr[0]);
				third.order = 3;
				vector3.add(third);
			} else if (vector3.size() == 1) {// 说明此时，有双臂合拢放在身体上
				vector3.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint second = new MyPoint(arr[1]);
				second.order = 2;
				vector3.add(second);
				MyPoint third = new MyPoint(arr[3]);
				third.order = 3;
				vector3.add(third);
			}

			// 左腿和右腿此时已经解析完毕无需再次调整

			vector1.clear(); // 清空容器
			// 将锚点重新加入vector1容器
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]

		// [start]多余4个或6个节点
		// vector1容器多余了四个节点或多余六个节点
		else if (vector1.size() == 4 || vector1.size() == 6) {
			int upper_Body_Counts = getNumOfUpper_Joint(); // 分析多余的关节点s属于上体的个数

			if (upper_Body_Counts == -1) {// 上体不对称，下体对称
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// 上体对称，下体不对称
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// 上下体均不对称
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // 上下体均对称
				isUpperSingle = false;
				isLowerSingle = false;
			}
			separator(upper_Body_Counts, isUpperSingle, isLowerSingle); // 将vector2和vector3容器进行分离，分离的部分点对象放入vector4和vector5中

			// 左臂
			if (vector2.size() == 3) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的
				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}

			} else if (vector2.size() == 2) {// 说明此时，有双手掌放在身体上
				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // 加入左臂的shoulder节点
				vector2.elementAt(0).order = 2; // 设置关节次序

				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint third = new MyPoint(arr[0]);
				third.order = 3;
				vector2.add(third);
			} else if (vector2.size() == 1) {// 说明此时，有双臂合拢放在身体上
				vector2.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint second = new MyPoint(arr[0]);
				second.order = 2;
				vector2.add(second);
				MyPoint third = new MyPoint(arr[2]);
				third.order = 3;
				vector2.add(third);
			}
			// 右臂
			if (vector3.size() == 3) {// 说明此时，手臂是放在身体两侧，并未放在身体上，并且推测应该是腿部关节导致的
				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}

			} else if (vector3.size() == 2) {// 说明此时，有双手掌放在身体上
				MyPoint left_shoulder = line_min(vector3, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
				left_shoulder.order = 1;
				vector3.add(left_shoulder); // 加入左臂的shoulder节点
				vector3.elementAt(0).order = 2; // 设置关节次序

				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint third = new MyPoint(arr[1]);
				third.order = 3;
				vector3.add(third);
			} else if (vector3.size() == 1) {// 说明此时，有双臂合拢放在身体上
				vector3.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint second = new MyPoint(arr[1]);
				second.order = 2;
				vector3.add(second);
				MyPoint third = new MyPoint(arr[3]);
				third.order = 3;
				vector3.add(third);
			}

			// 左腿
			if (vector4.size() == 0) { // 说明两腿合拢，处于人体对称中心
				// 说明手掌同时也处于身体对称处
				if (vector1.size() == 6) {
					MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
					MyPoint first = new MyPoint(arr[2]);
					first.order = 1;
					vector4.add(first);
					MyPoint second = new MyPoint(arr[4]);
					second.order = 2;
					vector4.add(second);
				}
				// 仅双腿合拢
				else if (vector1.size() == 4) {
					MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
					MyPoint first = new MyPoint(arr[0]);
					first.order = 1;
					vector4.add(first);
					MyPoint second = new MyPoint(arr[2]);
					second.order = 2;
					vector4.add(second);
				}
			} else if (vector4.size() == 1) {// 说明是腿关节的位置处于人体中心位置
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint p = new MyPoint(arr[2]);
				if (vector4.get(0).point.y > p.point.y) {
					vector4.get(0).order = 2;
					p.order = 1;
				} else {
					vector4.get(0).order = 1;
					p.order = 2;
				}
				// 将p点加入容器中
				vector4.add(p);

			} else if (vector4.size() == 2) {// 说明腿关节舒展躺着，并未处于人体中心位置
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节,并有序地加入容器参数中
				vector4.elementAt(0).order = 1; // 设置关节次序
				vector4.elementAt(1).order = 2; // 设置关节次序
			}

			// 右腿
			if (vector5.size() == 0) { // 说明两腿合拢，处于人体对称中心
				// 说明手掌同时也处于身体对称处
				if (vector1.size() == 6) {
					MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
					MyPoint first = new MyPoint(arr[3]);
					first.order = 1;
					vector5.add(first);
					MyPoint second = new MyPoint(arr[5]);
					second.order = 2;
					vector5.add(second);
				}
				// 仅双腿合拢
				else if (vector1.size() == 4) {

					MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
					MyPoint first = new MyPoint(arr[1]);
					first.order = 1;
					vector5.add(first);
					MyPoint second = new MyPoint(arr[3]);
					second.order = 2;
					vector5.add(second);
				}
			} else if (vector5.size() == 1) {// 说明是腿关节的位置处于人体中心位置
				MyPoint[] arr = sort(vector1); // 对点容器进行排序，按y坐标升序排列
				MyPoint p = new MyPoint(arr[3]);
				if (vector5.get(0).point.y > p.point.y) {
					vector5.get(0).order = 2;
					p.order = 1;
				} else {
					vector5.get(0).order = 1;
					p.order = 2;
				}
				// 将p点加入容器中
				vector5.add(p);
			} else if (vector5.size() == 2) { // 说明腿关节舒展躺着，并未处于人体中心位置
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // 区分knee和ankle关节，并有序地加入容器参数中
				vector5.elementAt(0).order = 1; // 设置关节次序
				vector5.elementAt(1).order = 2; // 设置关节次序
			}

			vector1.clear(); // 清空容器
			// 将锚点重新加入vector1容器
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}

		// [end]

		else if (vector1.size() == 8) {

			// 作者已筋疲力尽，不想写了，拜拜！

		}

		// [start]一字型躺姿
		// 一字型躺姿
		else if (vector1.size() == 10) {
			vector2.clear();
			vector3.clear();
			vector4.clear();
			vector5.clear();
			MyPoint[] mp = sort(vector1);
			vector2.add(vector1.get(mp[0].vector_index));
			vector2.add(vector1.get(mp[1].vector_index));
			vector2.add(vector1.get(mp[2].vector_index));
			vector3.add(vector1.get(mp[3].vector_index));
			vector3.add(vector1.get(mp[4].vector_index));
			vector3.add(vector1.get(mp[5].vector_index));
			vector4.add(vector1.get(mp[6].vector_index));
			vector4.add(vector1.get(mp[7].vector_index));
			vector5.add(vector1.get(mp[8].vector_index));
			vector5.add(vector1.get(mp[9].vector_index));
			vector2.elementAt(0).order = 1;
			vector2.elementAt(1).order = 2;
			vector2.elementAt(2).order = 3;
			vector3.elementAt(0).order = 1;
			vector3.elementAt(1).order = 2;
			vector3.elementAt(2).order = 3;
			vector4.elementAt(0).order = 1;
			vector4.elementAt(1).order = 2;
			vector5.elementAt(0).order = 1;
			vector5.elementAt(1).order = 2;
			vector1.clear(); // 清空容器
			// 将锚点重新加入vector1容器
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]
		
		
		
		// 重新解析腿关节位置
		if(!vector4.isEmpty()&&!vector5.isEmpty()){
			if(vector4.get(0).point.x > vector5.get(0).point.x){// 左腿膝盖横坐标大于右腿膝盖横坐标，说明解析出现异常，次处重新解析
				MyPoint p1;
				p1 = vector5.remove(0);
				vector5.add(0, vector4.remove(0));
				vector4.add(0, p1);
			}
			if(vector4.get(1).point.x > vector5.get(1).point.x){// 左腿踝关节横坐标大于右腿踝关节横坐标，说明解析出现异常，次处重新解析
				MyPoint p1;
				p1 = vector5.remove(1);
				vector5.add(vector4.remove(1));
				vector4.add(p1);
			}
		}	
		return true;
	}

	// [end]
	

	
	
	private boolean analyse_RightArm(MyPoint jaw_Anchor) {// 用于右臂关节点的解析函数
		MyPoint right_shoulder = line_min(vector3, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
		right_shoulder.order = 1; // 设置关节次序

		if (!detail_Split(vector3, right_shoulder)) {
			return false;
		} // 区分elbow和wrist关节,并有序地加入容器参数中
		vector3.elementAt(0).order = 2; // 设置关节次序
		vector3.elementAt(1).order = 3; // 设置关节次序
		vector3.add(right_shoulder); // 加入shoulder节点
		return true;
	}

	private Boolean analyse_LeftArm(MyPoint jaw_Anchor) {// 用于左臂关节点的解析函数
		MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // 获取与jaw_Anchor节点最近的点并删除容器中的该点
		if(left_shoulder == null){
			System.out.println("analyse_LeftArm（）函数出错");
			return false;
		}
		left_shoulder.order = 1;
		// 区分elbow和wrist关节,并有序地加入容器参数中
		if (!detail_Split(vector2, left_shoulder)) {
			return false;
		}
		vector2.elementAt(0).order = 2; // 设置关节次序
		vector2.elementAt(1).order = 3; // 设置关节次序
		vector2.add(left_shoulder); // 加入shoulder节点
		return true;
	}

	// 定义一个全局的MyPoint类类型的数组
	private MyPoint[] temp_Arr2;

	// [start] 获取上体关节点个数的函数
	private int getNumOfUpper_Joint() {
		int numberOfJoint = 0;
		int j = 0;
		int size = vector1.size() + vector2.size() + vector3.size(); // 记录元素总个数
		MyPoint[] temp_Arr = new MyPoint[size]; // 创建一个根据元素个数而定的功能点对象数组
		// 遍历容器，去除容器中的所用元素并放入数组中
		for (Iterator<MyPoint> iterator = vector1.iterator(); iterator.hasNext();) {
			MyPoint myPoint = (MyPoint) iterator.next();
			temp_Arr[j++] = new MyPoint(myPoint);
		}
		for (Iterator<MyPoint> iterator = vector2.iterator(); iterator.hasNext();) {
			MyPoint myPoint = (MyPoint) iterator.next();
			temp_Arr[j++] = new MyPoint(myPoint);
		}
		for (Iterator<MyPoint> iterator = vector3.iterator(); iterator.hasNext();) {
			MyPoint myPoint = (MyPoint) iterator.next();
			temp_Arr[j++] = new MyPoint(myPoint);
		}
		temp_Arr2 = temp_Arr;
		Arr_Sorted(temp_Arr); // 冒泡排序，按照y坐标值的大小
		// 如果vector1容器没有元素，则左右关节点基本正常放置
		if (vector1.size() == 0) {
			numberOfJoint = 3;
		}
		// 检查容器中重复元素的对数，该元素属于上体节点
		int same_Point = 0; // 定义重复点变量
		for (int k = 0; k < vector1.size(); k++) {
			if (vector1.get(k).point.y < temp_Arr[temp_Arr.length - 4].point.y - delta1) {// 判断容器中的元素值的y坐标值是否小于膝盖的坐标值
				same_Point++; // 累加重复点变量
			}
		}

		// 如果numberOfJoint和容器均为偶数，则直接获取上体节点数
		if (same_Point % 2 == 0 && vector1.size() % 2 == 0) {
			numberOfJoint = 3 - same_Point / 2;
		}

		// 上体不对称，下体对称
		else if (vector1.size() % 2 != 0 && same_Point % 2 != 0) {

			// numberOfJoint = (3 - same_Point) / 2 + 1;
			numberOfJoint = -1;
		}
		// 下体关节不对称，上体对称
		else if (vector1.size() % 2 != 0 && same_Point % 2 == 0) {
			numberOfJoint = -2;
		}
		// 上下体均不对称
		else if (vector1.size() % 2 == 0 && same_Point % 2 != 0) {
			numberOfJoint = -3;
		}
		return numberOfJoint; // 返回当前vector2中含有的上体节点元素的个数
	}
	// [end]

	// 将vector2和vector3容器进行分离，其中要用到基本的排序函数sort，分离的部分点对象放入vector4和vector5中
	private void separator(int upper_Body_Counts, boolean isUpperSingle, boolean isLowerSingle) {
		final int delta2 = 1;

		if (!isUpperSingle && !isLowerSingle) {// 上下体均对称

			// 处理左臂容器vector2
			MyPoint[] arr2 = sort(vector2); // 对点容器进行排序，按y坐标升序排列
			vector2.clear();
			int count1 = 0; // 定义标记个数变量，并初始化为0
			for (int i = 0; i < arr2.length; i++) {
				if (count1 < upper_Body_Counts) {
					vector2.add(arr2[i]); // 向左臂加入关节点对象，即功能点对象
					count1++;
					continue; // 退出本次循环
				}
				vector4.add(arr2[i]); // 向左腿加入功能点对象，即关节点对象
			}

			// 处理右臂容器vector3
			MyPoint[] arr3 = sort(vector3); // 对点容器进行排序，按y坐标升序排列
			vector3.clear(); // 清空容器
			int count2 = 0; // 定义标记个数变量
			for (int i = 0; i < arr3.length; i++) {
				if (count2 < upper_Body_Counts) {
					vector3.add(arr3[i]); // 向右臂加入关节点对象，即功能点对象
					count2++;
					continue; // 退出本次循环
				}
				vector5.add(arr3[i]); // 向右腿加入功能点对象，即关节点对象
			}
		}

		else if (isUpperSingle && !isLowerSingle) {// 上体不对称,下体对称

			vector4.clear();
			vector5.clear();
			// 处理左臂容器vector2
			MyPoint[] arr2 = sort(vector2); // 对点容器进行排序，按y坐标升序排列
			vector2.clear();
			int count1 = 0; // 定义标记个数变量，并初始化为0
			for (int i = 0; i < arr2.length; i++) {
				if (count1 < 2) {// 设置终止条件为2，因为无法确定是左臂在身体中央还是右臂在身体中央
					vector2.add(arr2[i]); // 向左臂加入关节点对象，即功能点对象
					count1++;
					continue; // 退出本次循环
				}
				vector4.add(arr2[i]); // 向左腿加入功能点对象，即关节点对象
			}

			// 处理右臂容器vector3
			MyPoint[] arr3 = sort(vector3); // 对点容器进行排序，按y坐标升序排列
			vector3.clear(); // 清空容器
			int count2 = 0; // 定义标记个数变量
			for (int i = 0; i < arr3.length; i++) {
				if (count2 < 2) {// 设置终止条件为2，因为无法确定是左臂在身体中央还是右臂在身体中央
					vector3.add(arr3[i]); // 向右臂加入关节点对象，即功能点对象
					count2++;
					continue; // 退出本次循环
				}
				vector5.add(arr3[i]); // 向右腿加入功能点对象，即关节点对象
			}

			// 双腿均处于人体中央，但此时这两个容器中有一个多了一个上体节点
			if (vector4.size() == 0 || vector5.size() == 0) {
				if (vector4.size() != 0) {
					for (int k = 0; k < vector4.size(); k++) {// 剔除多余节点放回上体容器
						if (vector4.get(k).point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// 判断容器中的元素值的y坐标值是否大于膝盖的坐标值
							vector2.add(vector4.remove(k));
						}
					}
				} else {
					for (int k = 0; k < vector5.size(); k++) {// 剔除多余节点放回上体容器
						if (vector5.get(k).point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// 判断容器中的元素值的y坐标值是否大于膝盖的坐标值
							vector3.add(vector5.remove(k));
						}
					}
				}

				if (vector4.size() == 0 && vector5.size() == 0) {// 对vector1容器中的两个膝关节和踝关节进行处理，放回下体关节容器
					MyPoint p = vector1.get(0); // 缓存上体节点的变量
					int index = 0;
					for (int k = 0; k < vector1.size(); k++) {// 寻找上体节点
						if (vector1.get(k).point.y < p.point.y) {// 判断容器中的元素值的y坐标值是否大于膝盖的坐标值
							p = new MyPoint(vector1.get(k));
							index = k;
						}
					}
					vector1.remove(index);
					vector4.add(vector1.remove(0));
					vector4.add(vector1.remove(0));
					vector5.add(vector1.remove(0));
					vector5.add(vector1.remove(0));
					vector4.get(0).order = 1;
					vector4.get(1).order = 2;
					vector5.get(0).order = 1;
					vector5.get(1).order = 2;
					vector1.add(p);
				}
				return;
			}

			// 将vector4或vector5中的多余一个关节点重新加入vector2或vector3中
			if (vector4.size() % 2 != 0) {
				for (int k = 0; k < vector4.size(); k++) {
					if (vector4.get(k).point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// 判断容器中的元素值的y坐标值是否大于膝盖的坐标值
						vector2.add(vector4.remove(k));
					}
				}
			} else if (vector5.size() % 2 != 0) {
				for (int k = 0; k < vector5.size(); k++) {
					if (vector5.get(k).point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// 判断容器中的元素值的y坐标值是否大于膝盖的坐标值
						vector3.add(vector5.remove(k));
					}
				}
			}
		}

		else if (!isUpperSingle && isLowerSingle) {// 上体对称，下体不对称
			// 处理左臂容器vector2
			MyPoint[] arr2 = sort(vector2); // 对点容器进行排序，按y坐标升序排列
			vector2.clear();

			int count1 = 0; // 定义标记个数变量，并初始化为0
			for (int i = 0; i < arr2.length; i++) {
				if (count1 < 3) {// 上肢正常舒展，应全部加入容器
					vector2.add(arr2[i]); // 向左臂加入关节点对象，即功能点对象
					count1++;
					continue; // 退出本次循环
				}
				vector4.add(arr2[i]); // 向左腿加入功能点对象，即关节点对象
			}

			// 处理右臂容器vector3
			MyPoint[] arr3 = sort(vector3); // 对点容器进行排序，按y坐标升序排列
			vector3.clear(); // 清空容器
			int count2 = 0; // 定义标记个数变量
			for (int i = 0; i < arr3.length; i++) {
				if (count2 < 3) {// 上肢正常舒展，应全部加入容器
					vector3.add(arr3[i]); // 向右臂加入关节点对象，即功能点对象
					count2++;
					continue; // 退出本次循环
				}
				vector5.add(arr3[i]); // 向右腿加入功能点对象，即关节点对象
			}

			// 将vector4或vector5中的多余一个关节点重新加入vector2或vector3中
			if (vector4.size() % 2 != 0) {
				vector4.add(vector1.remove(0));
			} else if (vector5.size() % 2 != 0) {
				vector5.add(vector1.remove(0));
			}

		}

		else if (isUpperSingle && isLowerSingle) {// 上体下体均不对称
			if (vector2.size() == vector3.size()) {// 身体的两侧都不对称
				int flag2 = 0;
				int flag3 = 0;
				// 处理左臂容器vector2
				MyPoint[] _arr2 = sort(vector2); // 对点容器进行排序，按y坐标升序排列
				for (int i = 0; i < _arr2.length; i++) {
					if (_arr2[i].point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// 判断容器中的元素值的y坐标值是否大于膝盖的坐标值
						flag2++;
					}
				}
				// 处理右臂容器vector3
				MyPoint[] _arr3 = sort(vector3); // 对点容器进行排序，按y坐标升序排列
				for (int i = 0; i < _arr3.length; i++) {
					if (_arr3[i].point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// 判断容器中的元素值的y坐标值是否大于膝盖的坐标值
						flag3++;
					}
				}

				// 判断是左侧上肢不对称还是右侧上肢不对称
				if (flag2 == 2) {// 左臂不对称，右腿不对称
					// 处理左臂容器vector2
					MyPoint[] arr2 = sort(vector2); // 对点容器进行排序，按y坐标升序排列
					vector2.clear();
					int count1 = 0; // 定义标记个数变量，并初始化为0
					for (int i = 0; i < arr2.length; i++) {
						if (count1 < 2) {// 设置终止条件为2，因为无法确定是左臂在身体中央还是右臂在身体中央
							vector2.add(arr2[i]); // 向左臂加入关节点对象，即功能点对象
							count1++;
							continue; // 退出本次循环
						}
						vector4.add(arr2[i]); // 向左腿加入功能点对象，即关节点对象
					}
					// 将vector1中多余的左手腕关节加入vecto2中，并删除vector1中的左手腕关节
					if (vector1.get(1).point.y > vector1.get(0).point.y) {
						vector2.add(vector1.remove(0));
					} else if (vector1.get(0).point.y > vector1.get(1).point.y) {
						vector2.add(vector1.remove(1));
					}

					// 处理右臂容器vector3
					MyPoint[] arr3 = sort(vector3); // 对点容器进行排序，按y坐标升序排列
					vector3.clear(); // 清空容器
					int count2 = 0; // 定义标记个数变量
					for (int i = 0; i < arr3.length; i++) {
						if (count2 < 3) {
							vector3.add(arr3[i]); // 向右臂加入关节点对象，即功能点对象
							count2++;
							continue; // 退出本次循环
						}
						vector5.add(arr3[i]); // 向右腿加入功能点对象，即关节点对象
					}

					// 将vector1多余的一个关节点重新加入vector5中
					if (vector5.size() % 2 != 0) {
						vector5.add(vector1.remove(0));
					}

				} else if (flag3 == 2) {// 右臂不对称，左腿不对称 与上面情况基本一致
					// 处理左臂容器vector2
					MyPoint[] arr2 = sort(vector2); // 对点容器进行排序，按y坐标升序排列
					vector2.clear();
					int count1 = 0; // 定义标记个数变量，并初始化为0
					for (int i = 0; i < arr2.length; i++) {
						if (count1 < 3) {// 无节点损失
							vector2.add(arr2[i]); // 向左臂加入关节点对象，即功能点对象
							count1++;
							continue; // 退出本次循环
						}
						vector4.add(arr2[i]); // 向左腿加入功能点对象，即关节点对象
					}

					// 处理右臂容器vector3
					MyPoint[] arr3 = sort(vector3); // 对点容器进行排序，按y坐标升序排列
					vector3.clear(); // 清空容器
					int count2 = 0; // 定义标记个数变量
					for (int i = 0; i < arr3.length; i++) {
						if (count2 < 2) {
							vector3.add(arr3[i]); // 向右臂加入关节点对象，即功能点对象
							count2++;
							continue; // 退出本次循环
						}
						vector5.add(arr3[i]); // 向右腿加入功能点对象，即关节点对象
					}
					// 将vector1中多余的右手腕关节加入vector3中，并删除vector1中的右手腕关节
					if (vector1.get(1).point.y > vector1.get(0).point.y) {
						vector3.add(vector1.remove(0));
					} else if (vector1.get(0).point.y > vector1.get(1).point.y) {
						vector3.add(vector1.remove(1));
					}

					// 将vector1多余的一个关节点重新加入vector5中
					if (vector4.size() % 2 != 0) {
						vector4.add(vector1.remove(0));
					}
				}

			} else {// 身体的一侧不对称
				if (vector2.size() < vector3.size()) {// 左臂和左腿均不对称
					// 处理左臂容器vector2
					MyPoint[] arr2 = sort(vector2); // 对点容器进行排序，按y坐标升序排列
					vector2.clear();
					int count1 = 0; // 定义标记个数变量，并初始化为0
					for (int i = 0; i < arr2.length; i++) {
						if (count1 < 2) {// 设置终止条件为2，因为无法确定是左臂在身体中央还是右臂在身体中央
							vector2.add(arr2[i]); // 向左臂加入关节点对象，即功能点对象
							count1++;
							continue; // 退出本次循环
						}
						vector4.add(arr2[i]); // 向左腿加入功能点对象，即关节点对象
					}
					// 将vector1中多余的左手腕关节加入vecto2中，并删除vector1中的左手腕关节
					if (vector1.get(1).point.y > vector1.get(0).point.y) {
						vector2.add(vector1.get(0));
						vector4.add(vector1.get(1));
						vector1.clear();
					} else if (vector1.get(0).point.y > vector1.get(1).point.y) {
						vector2.add(vector1.get(1));
						vector4.add(vector1.get(0));
						vector1.clear();
					}

					// 处理右臂容器vector3
					MyPoint[] arr3 = sort(vector3); // 对点容器进行排序，按y坐标升序排列
					vector3.clear(); // 清空容器
					int count2 = 0; // 定义标记个数变量
					for (int i = 0; i < arr3.length; i++) {
						if (count2 < 3) {
							vector3.add(arr3[i]); // 向右臂加入关节点对象，即功能点对象
							count2++;
							continue; // 退出本次循环
						}
						vector5.add(arr3[i]); // 向右腿加入功能点对象，即关节点对象
					}

				} else {// 右臂和右腿均不对称
						// 处理左臂容器vector2
					MyPoint[] arr2 = sort(vector2); // 对点容器进行排序，按y坐标升序排列
					vector2.clear();
					int count1 = 0; // 定义标记个数变量，并初始化为0
					for (int i = 0; i < arr2.length; i++) {
						if (count1 < 3) {// 设置终止条件为2，因为无法确定是左臂在身体中央还是右臂在身体中央
							vector2.add(arr2[i]); // 向左臂加入关节点对象，即功能点对象
							count1++;
							continue; // 退出本次循环
						}
						vector4.add(arr2[i]); // 向左腿加入功能点对象，即关节点对象
					}

					// 处理右臂容器vector3
					MyPoint[] arr3 = sort(vector3); // 对点容器进行排序，按y坐标升序排列
					vector3.clear(); // 清空容器
					int count2 = 0; // 定义标记个数变量
					for (int i = 0; i < arr3.length; i++) {
						if (count2 < 2) {
							vector3.add(arr3[i]); // 向右臂加入关节点对象，即功能点对象
							count2++;
							continue; // 退出本次循环
						}
						vector5.add(arr3[i]); // 向右腿加入功能点对象，即关节点对象
					}
					// 将vector1中多余的左手腕关节加入vecto2中，并删除vector1中的左手腕关节
					if (vector1.get(1).point.y > vector1.get(0).point.y) {
						vector3.add(vector1.get(0));
						vector5.add(vector1.get(1));
						vector1.clear();
					} else if (vector1.get(0).point.y > vector1.get(1).point.y) {
						vector3.add(vector1.get(1));
						vector5.add(vector1.get(0));
						vector1.clear();
					}
				}
			}
		}
	}

	private boolean detail_Split(Vector<MyPoint> v, MyPoint refer_Point) {
		// 设置关节
		MyPoint joint1;
		MyPoint joint2;
		if (v.size() < 2) {
			return false;
		}
		// 判断小臂是否抬起过肩
		if (v.elementAt(0).point.y <= refer_Point.point.y && v.elementAt(1).point.y <= refer_Point.point.y) {// 小臂抬起
			if (v.elementAt(0).point.y < v.elementAt(1).point.y) {
				joint1 = new MyPoint(v.elementAt(1));
				joint2 = new MyPoint(v.elementAt(0));
				v.clear(); // 清空容器
				// 关节点重新加入v容器
				v.add(joint1);
				v.add(joint2);
			} else {
				joint1 = new MyPoint(v.elementAt(0));
				joint2 = new MyPoint(v.elementAt(1));
				v.clear(); // 清空容器
				// 关节点重新加入v容器
				v.add(joint1);
				v.add(joint2);
			}

		} else { // 小臂未抬起过肩
			if (v.elementAt(0).point.y < refer_Point.point.y) {// 手过肩

				joint1 = new MyPoint(v.elementAt(1));
				joint2 = new MyPoint(v.elementAt(0));
				v.clear(); // 清空容器
				// 关节点重新加入v容器
				v.add(joint1); // 将处理过的关节点，即功能点加入容器，下同
				v.add(joint2);
			} else if (v.elementAt(1).point.y < refer_Point.point.y) {// 手过肩
				joint1 = new MyPoint(v.elementAt(0));
				joint2 = new MyPoint(v.elementAt(1));
				v.clear(); // 清空容器
				// 关节点重新加入v容器
				v.add(joint1); // 将处理过的关节点，即功能点加入容器，下同
				v.add(joint2);

			} else {// 小臂下垂或小臂抬起未过肩
				
				double distance = PointToLine_Distance.lineSpace(v.elementAt(1), v.elementAt(0));
				double distance0 = PointToLine_Distance.lineSpace(v.elementAt(0), refer_Point);
				double distance1 = PointToLine_Distance.lineSpace(v.elementAt(1), refer_Point);

				// 判断两直线的长度大小
				if (distance0 > distance1) {
					// 判断肩关节，肘关节，腕关节共同构成的三角形是锐角还是钝角
					if (isObtuse_Angle(distance, distance0, distance1)) {// 是钝角
						joint1 = new MyPoint(v.elementAt(1));// 加入肘关节
						joint2 = new MyPoint(v.elementAt(0));// 加入腕关节
					} 
					else {// 是锐角
						if(PointToLine_Distance.lineSpace(v.elementAt(0), belly_Anchor) >
						PointToLine_Distance.lineSpace(v.elementAt(1), belly_Anchor)){//如果“准腕关节"与肚脐锚点的距离大于
							joint1 = new MyPoint(v.elementAt(0));// 加入肘关节
							joint2 = new MyPoint(v.elementAt(1));// 加入腕关节														// “准肘关节与肚脐锚点之间的距离，则假设成立”
						}
						else{// 反之，准腕关节应为肘关节，其他类似
							joint1 = new MyPoint(v.elementAt(1));// 加入肘关节
							joint2 = new MyPoint(v.elementAt(0));// 加入腕关节
						}
						
					}

				} else {
					// 判断肩关节，肘关节，腕关节共同构成的三角形是锐角还是钝角
					if (isObtuse_Angle(distance, distance0, distance1)) {// 是钝角
						joint1 = new MyPoint(v.elementAt(0));// 加入肘关节
						joint2 = new MyPoint(v.elementAt(1));// 加入腕关节

					} else {// 是锐角
						
						if(PointToLine_Distance.lineSpace(v.elementAt(1), belly_Anchor) >
						PointToLine_Distance.lineSpace(v.elementAt(0), belly_Anchor)){//如果“准腕关节"与肚脐锚点的距离大于
							joint1 = new MyPoint(v.elementAt(1));// 加入肘关节
							joint2 = new MyPoint(v.elementAt(0));// 加入腕关节															// “准肘关节与肚脐锚点之间的距离，则假设成立”
						}
						else{// 反之，准腕关节应为肘关节，其他类似
							joint1 = new MyPoint(v.elementAt(0));// 加入肘关节
							joint2 = new MyPoint(v.elementAt(1));// 加入腕关节
						}
						
					}

				}
				v.clear(); // 清空容器
				// 关节点重新加入v容器
				v.add(joint1); // 将处理过的关节点，即功能点加入容器，下同
				v.add(joint2);
			}

		}

		return true;
	}

	private boolean isObtuse_Angle(double a, double b, double c) {// 判断三角形是否是钝角三角形
		double anglel;
		double angle2;
		double angle3;
		anglel = Math.toDegrees(Math.acos((a * a + b * b - c * c) / (2 * a * b)));
		angle2 = Math.toDegrees(Math.acos((a * a + c * c - b * b) / (2 * a * c)));
		angle3 = Math.toDegrees(Math.acos((c * c + b * b - a * a) / (2 * c * b)));
		if (anglel > 90 | angle2 > 90 | angle3 > 90) {
			return true;
		}
		return false;
	}

	private MyPoint line_min(Vector<MyPoint> v, MyPoint jaw_Anchor) {
		int flag = 0; // 设置执行标志，该标志决定是否执行 距离jaw_Anchor功能点最近的关节点（功能点）的初始化
		MyPoint min_Temp = null; // 定义目标关节点的变量
		if(v.isEmpty()){
			return null;
		}
		for (Iterator<MyPoint> iterator = v.iterator(); iterator.hasNext();) {
			MyPoint myPoint = (MyPoint) iterator.next(); // 通过迭代器遍历容器
			if (flag == 0) {
				min_Temp = new MyPoint(myPoint); // 初始化目标关节点
				flag = 1; // 关闭初始化功能
			}
			
			if(myPoint == null){
				return null;
			}
			// 计算容器中的各元素与jaw_Anchor关节点之间的距离
			double distance = PointToLine_Distance.lineSpace(myPoint.point.x, myPoint.point.y, jaw_Anchor.point.x,
					jaw_Anchor.point.y);
			double min_distance = PointToLine_Distance.lineSpace(min_Temp.point.x, min_Temp.point.y, jaw_Anchor.point.x,
					jaw_Anchor.point.y);

			if (distance < min_distance) {
				min_Temp = new MyPoint(myPoint); // 距离jaw_Anchor功能点最近的关节点(功能点)赋给目标关节点
			}
		}
		// 从容器中删除距离最近的节点
		for (ListIterator<MyPoint> listIterator = v.listIterator(); listIterator.hasNext();) {
			MyPoint myPoint = (MyPoint) listIterator.next();
			if (myPoint.point.x == min_Temp.point.x && myPoint.point.y == min_Temp.point.y) {
				listIterator.remove(); // 从容器中删除目标关节点
			}
		}
		return min_Temp; // 返回距离最近的节点
	}

	// 解析函数，主要用于将所有锚点（3个）解析出来，并将其加入数组返回回来
	private MyPoint[] anchor_analyze() {
		MyPoint[] arr = new MyPoint[3];
		MyPoint[] vector1_arr = sort(vector1); // 对容器进行排序
		vector1.clear(); // 清空容器
		int flag = 0; // 执行步骤的标志
		int j = 0; // 定义数组的循环标志
		for (int i = 0; i < vector1_arr.length; i++) {
			if (flag == 0) {
				if (vector1_arr[i].anchor) {
					brow_Anchor = vector1_arr[i]; // 头锚点，即额头锚点
					brow_Anchor.order = 1;
					arr[j++] = brow_Anchor;
					flag = 1;
					continue; // 退出本次循环，不加入容器
				}
			} else if (flag == 1) {
				if (vector1_arr[i].anchor) {
					jaw_Anchor = vector1_arr[i]; // 获取中间锚点,即下巴锚点
					jaw_Anchor.order = 2;
					arr[j++] = jaw_Anchor;
					flag = 2;
					continue; // 退出本次循环，不加入容器
				}
			} else if (flag == 2) {
				if (vector1_arr[i].anchor) {
					belly_Anchor = vector1_arr[i]; // 获取末尾锚点，即肚脐锚点
					belly_Anchor.order = 3;
					arr[j++] = belly_Anchor;
					flag = 3;
					continue; // 退出本次循环，不加入容器
				}
			}
			vector1.add(vector1_arr[i]); // 数组中的功能点对象加入容器1中
		}
		return arr; // 返回数组
	}

	// 用于将该点对应的原始点对象加入到传入的容器中去
	private void addPoints(MyPoint tmp, Vector<MyPoint> v) {
		// 循环原始点容器
		for (int k = 0; k < pointsVector.size(); k++) {
			MyPoint pt = (MyPoint) pointsVector.elementAt(k);
			// 判断转换坐标后的点对象是否是与原始点对象对应的点对象
			if (tmp.link_Flag == pt.link_Flag) {
				MyPoint m = new MyPoint(pt); // 创建新容器的MyPoint元素
				v.add(m); // 把点加入容器
				break; // 找到后便退出循环
			}
		}
	}

	// 排序函数，按照数组中元素的点坐标的y值的大小进行排序
	private void Arr_Sorted(MyPoint[] arr) {
		MyPoint myPoint = null; // 创建一个缓存对象，用于排序时的值替换
		// 冒泡排序法，按点对象的y坐标值从小到大一次排序
		for (int pass = 1; pass < arr.length; pass++) {
			for (int i = 0; i < arr.length - pass; i++) {
				// 根据功能点对象的y坐标值的大小，交换数组中相邻元素即功能点对象的位置
				if (arr[i].point.y > arr[i + 1].point.y) {
					myPoint = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = myPoint;
				}
			}
		}
	}

	// 基本排序函数
	private MyPoint[] sort(Vector<MyPoint> v) {

		// 创建一个缓存对象，用于排序时的值替换
		MyPoint myPoint;
		MyPoint[] arr = new MyPoint[v.size()];
		// 创建j变量，移动数组指针
		int j = 0;
		for (int i = 0; i < v.size(); i++) {
			MyPoint tmp = (MyPoint) v.elementAt(i);
			// 调用MyPoint的有参构造函数
			MyPoint mp = new MyPoint(tmp);
			// 缓存mp关节点所在容器中的索引号
			mp.vector_index = i;
			// 把点对象放入对象数组中
			arr[j++] = mp;
		}
		// 冒泡排序法，按点对象的y坐标值从小到大依次排序
		for (int pass = 1; pass < arr.length; pass++) {
			for (int i = 0; i < arr.length - pass; i++) {
				if (arr[i].point.y > arr[i + 1].point.y) {
					myPoint = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = myPoint;
				}
			}
		}
		return arr; // 返回关节点对象数组
	}

}
