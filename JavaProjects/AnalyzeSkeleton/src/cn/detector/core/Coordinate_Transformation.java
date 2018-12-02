package cn.detector.core;

import java.awt.Point;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

/**
 * ���Ĵ��룺 ����ת�������������� ����Ϊ����ϵͳ�ĺ��Ĳ��� �������ݷ����ͽ������ݲ�����������������
 */
public class Coordinate_Transformation {
	Vector<MyPoint> v_Joint = new Vector<MyPoint>(); // ����v_Joint���ڻ���ת����Ĺؽڵ����
	Vector<MyPoint> pointsVector; // ����pointsVector�������ڴ洢�ؽڵ�
	// ����������ڻ�������������ֵĹؽڵ����꣬�����ϣ�vector2�����в���vector1�������ϣ�vector3�������£�vector4�������£�vector5����λ
	Vector<MyPoint> vector1 = new Vector<MyPoint>();
	Vector<MyPoint> vector2 = new Vector<MyPoint>();
	Vector<MyPoint> vector3 = new Vector<MyPoint>();
	Vector<MyPoint> vector4 = new Vector<MyPoint>();
	Vector<MyPoint> vector5 = new Vector<MyPoint>();
	MyPoint brow_Anchor = null;
	MyPoint jaw_Anchor = null;
	MyPoint belly_Anchor = null;

	// ��ȡ��������
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

	// ����߿�Ŀ�Ⱥ͸߶�
	private double heigh_Distance;
	private double width_Distance;
	// ���ڻ���ԭʼ����ֵ
	private int x1 = 0, y1 = 0;
	private int x2, y2;
	@SuppressWarnings("unused")
	private int x3, y3;
	private int x4, y4;
	// ���ڻ���ת���������ֵ
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

	// �вι��캯��
	public Coordinate_Transformation(Vector<Line> lv, Vector<MyPoint> v) {
		super();
		if (v.size() != 13) {
			return;
		} else if (lv.size() != 4) {
			return;
		}
		// �����������õĴ���
		pointsVector = v;
		// ����߿��ĵ������ֵ
		this.x1 = lv.elementAt(0).x0;
		this.y1 = lv.elementAt(0).y0;
		this.x2 = lv.elementAt(1).x0;
		this.y2 = lv.elementAt(1).y0;
		this.x3 = lv.elementAt(2).x0;
		this.y3 = lv.elementAt(2).y0;
		this.x4 = lv.elementAt(3).x0;
		this.y4 = lv.elementAt(3).y0;
		// ��������֮��ľ���
		heigh_Distance = PointToLine_Distance.lineSpace(x1, y1, x4, y4);
		width_Distance = PointToLine_Distance.lineSpace(x1, y1, x2, y2);

		// ת������
		Jx1 = x1;
		Jy1 = y1;
		Jx2 = x1 + (int) width_Distance;
		Jy2 = y1;
		Jx3 = x1 + (int) width_Distance;
		Jy3 = y1 + (int) heigh_Distance;
		Jx4 = x1;
		Jy4 = y1 + (int) heigh_Distance;
	}

	// ת���߿��ĵ������ʱб����ת����������
	public boolean change_coordinate() {
		if (x1 == 0 && y1 == 0) {
			return false;
		}

		PointToLine_Distance p_Line_Distance = new PointToLine_Distance(); // ������㵽ֱ�߾���Ķ���
		for (int i = 0; i < pointsVector.size(); i++) {// ѭ�����������ﲻ��Ҫ��i�ֲ���������++�������������Զ�ѭ����������
			// ��ȡ�����е�Ԫ��
			MyPoint tmp = (MyPoint) pointsVector.elementAt(i);
			// ����ԭʼ�����ı��ֵ
			tmp.link_Flag = i;
			// ������������MyPointԪ��
			MyPoint m = new MyPoint();
			// �����½������ı��ֵ�������Ӧ��ԭʼ�����ı��ֵ��ͬ���Ա�һһ��Ӧ
			m.link_Flag = i;
			// ���������
			m.point = new Point();
			m.parameter = tmp.parameter;
			// ת���ڵ�����
			m.point.x = Jx1 + (int) p_Line_Distance.getDistance(x1, y1, x4, y4, tmp.point.x, tmp.point.y);
			m.point.y = Jy1 + (int) p_Line_Distance.getDistance(x1, y1, x2, y2, tmp.point.x, tmp.point.y);
			// ��ת����Ľڵ����������ؽڵ�������
			v_Joint.add(m);
		}
		return true;

	}

	// ������ڵ����ڵ�λ�ý��зֲ㣬�����ֲ���Ľڵ�ֱ����vector1��vector2��vector3��vector4��vector5������
	public boolean divide_Layer() {

		// ��ȡ�߿��е������
		double middle = (Jx1 + Jx2) / 2;
		// ѭ���ڵ�����v_Joint
		for (int i = 0; i < v_Joint.size(); i++) {
			MyPoint tmp = (MyPoint) v_Joint.elementAt(i);
			if(tmp.parameter == 0){
				tmp.parameter = parameter;
			}
			// 1����ȡ�����м��ĵ㣬����vector1������
			if (tmp.point.x > middle - width_Distance / tmp.parameter
					&& tmp.point.x < middle + width_Distance / tmp.parameter) {
				addPoints(tmp, vector1);
			}
			// 2����ȡ���ĵ㣬����vector2������
			else if (tmp.point.x > Jx1 && tmp.point.x < middle - width_Distance / tmp.parameter) {
				addPoints(tmp, vector2);
			}
			// 3����ȡ�Ҳ�ĵ㣬����vector3������
			else if (tmp.point.x > middle + width_Distance / tmp.parameter && tmp.point.x < Jx2) {
				addPoints(tmp, vector3);
			}
		}
		/*
		 * ����������Ҫvector2��vector3�������з������ֵĸ������ �����¼�������� 1��vector1��sizeΪ3
		 * 2��vector1��size��Ϊ3
		 * 
		 */
		// ������֫Խ������Գ���
		if (vector1.size() >= 3) {
			// 1��˫�ֽ�����ڸ�ǰ����˫�ƽӴ�����˫����չ���ã�����ƽ��
			// 2������ƽ�ɣ�˫�Ƚ��棬ϥ���ص����ֱ���չ����
			// 3������ƽ�ɣ����ص���ϥ������ͻ�����ֱ���չ����
			// 4������ƽ�ɣ���֫��δ��������Գ��ߣ�vector1.size() = 3��
			MyPoint[] anchor_Arr = anchor_analyze(); // ������ê�����������vector1������ֻ�г�ê������Ĺ��ܵ����
			// ͨ��ê������ؽڵ�����λ�ã����η���������Ӷ������Ϊ�������ܵ����û���˳��ı���order
			if (!setOrder(anchor_Arr)) {
				return false;
			}

		}

		// ��������ƽ�����ƣ���������֫δԽ������Գ���
		else if (vector1.size() < 3) {
			return false;
		}
		return true;
	}

	/*
	 * ��������������ͷּ�Ĵ����
	 * 
	 * �����������ŵ���ͬ��������
	 * 
	 */
	// [start]setOrder(MyPoint[] anchor_Arr)������
	private boolean setOrder(MyPoint[] anchor_Arr) {
		boolean isUpperSingle = false;
		boolean isLowerSingle = false;
		brow_Anchor = anchor_Arr[0];
		jaw_Anchor = anchor_Arr[1];
		belly_Anchor = anchor_Arr[2];
		if(brow_Anchor == null | jaw_Anchor == null | belly_Anchor == null){
			return false;
		}

		// [start]����0���ڵ�
		if (vector1.size() == 0) {// ����ƽ�ɵ�����
			int upper_Body_Counts = getNumOfUpper_Joint(); // ����������������Ĺؽڵ���������ĸ���
			// [start]�Գ��ж��߼�
			if (upper_Body_Counts == -1) {// ���岻�Գƣ�����Գ�
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// ����Գƣ����岻�Գ�
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// ����������Գ�
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // ��������Գ�
				isUpperSingle = false;
				isLowerSingle = false;
			}
			// [end]

			separator(upper_Body_Counts, isUpperSingle, isLowerSingle); // ��vector2��vector3�������з��룬����Ĳ��ֵ�������vector4��vector5��
			// ��ê�����¼���vector1����
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));

			// ���
			if (!analyse_LeftArm(jaw_Anchor)) {
				return false;
			}

			// �ұ�
			if (!analyse_RightArm(jaw_Anchor)) {
				return false;
			}

			// ����
			if (!detail_Split(vector4, belly_Anchor)) {
				return false;
			} // ����knee��ankle�ؽ�,������ؼ�������������
			vector4.elementAt(0).order = 1; // ���ùؽڴ���
			vector4.elementAt(1).order = 2; // ���ùؽڴ���

			// ����
			if (!detail_Split(vector5, belly_Anchor)) {
				return false;
			} // ����knee��ankle�ؽڣ�������ؼ�������������
			vector5.elementAt(0).order = 1; // ���ùؽڴ���
			vector5.elementAt(1).order = 2; // ���ùؽڴ���
		}
		// [end]
		// [start]����1���ڵ�
		// vector1�����ж�����1���ؽڵ�
		else if (vector1.size() == 1) {

			int upper_Body_Counts = getNumOfUpper_Joint(); // ��������Ĺؽڵ�s��������ĸ���

			// [start]�Գ��ж��߼�
			if (upper_Body_Counts == -1) {// ���岻�Գƣ�����Գ�
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// ����Գƣ����岻�Գ�
				upper_Body_Counts = 3;
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// ����������Գ�
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // ��������Գ�
				isUpperSingle = false;
				isLowerSingle = false;
			}
			// [end]

			separator(upper_Body_Counts, isUpperSingle, isLowerSingle); // ��vector2��vector3�������з��룬����Ĳ��ֵ�������vector4��vector5��
			// ���
			if (vector2.size() == 3) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�

				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}

			} else if (vector2.size() == 2) {// ˵����ʱ�������Ʒ���������

				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // ������۵�shoulder�ڵ�

				vector2.elementAt(0).order = 2; // ���ùؽڴ���
				if (!vector1.isEmpty()) {// �ж������Ƿ�ǿ�
					MyPoint p = vector1.remove(0); // ɾ��vector1����Ľڵ�
					p.order = 3; // ��ɾ���Ľڵ����ùؽڴ���Ϊ3
					vector2.add(p); // ���˽ڵ�������������
				}

			}

			// �ұ�
			if (vector3.size() == 3) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�

				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}

			} else if (vector3.size() == 2) {// ˵����ʱ����˫���Ʒ���������

				MyPoint right_shoulder = line_min(vector3, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				right_shoulder.order = 1; // ���ùؽڴ���
				vector3.add(right_shoulder); // �����ұ۵�shoulder�ڵ�
				if (vector3.size() == 2) {
					vector3.elementAt(0).order = 2; // ���ùؽڴ���
					if (!vector1.isEmpty()) {// �ж������Ƿ�ǿ�
						MyPoint p = vector1.remove(0); // ɾ��vector1����Ľڵ�
						p.order = 3; // ��ɾ���Ľڵ����ùؽڴ���Ϊ3
						vector3.add(p); // ���˽ڵ�����ұ�������
					}
				}
			}
			// ����
			if (vector4.size() == 1) {// ˵�����ȹؽڵ�λ�ô�����������λ��

				MyPoint p = vector1.remove(0); // ɾ��vector1����Ľڵ�
				vector4.add(p); // ���˽ڵ�����ұ�������
				// ����
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽ�,������ؼ�������������
				vector4.elementAt(0).order = 1; // ���ùؽڴ���
				vector4.elementAt(1).order = 2; // ���ùؽڴ���

			} else if (vector4.size() == 2) {// ˵���ȹؽ���չ���ţ���δ������������λ��

				// ����
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽ�,������ؼ�������������
				vector4.elementAt(0).order = 1; // ���ùؽڴ���
				vector4.elementAt(1).order = 2; // ���ùؽڴ���

			}
			// ����
			if (vector5.size() == 1) {// ˵�����ȹؽڵ�λ�ô�����������λ��

				MyPoint p = vector1.remove(0); // ɾ��vector1����Ľڵ�
				vector5.add(p); // ���˽ڵ�����ұ�������
				// ����
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽڣ�������ؼ�������������
				vector5.elementAt(0).order = 1; // ���ùؽڴ���
				vector5.elementAt(1).order = 2; // ���ùؽڴ���
			} else if (vector5.size() == 2) { // ˵���ȹؽ���չ���ţ���δ������������λ��
				// ����
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽڣ�������ؼ�������������
				vector5.elementAt(0).order = 1; // ���ùؽڴ���
				vector5.elementAt(1).order = 2; // ���ùؽڴ���
			}

			// ��ê�����¼���vector1����
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]
		// [start]����2���ڵ�
		// vector1�����ж�����2���ؽڵ�
		else if (vector1.size() == 2) {
			int upper_Body_Counts = getNumOfUpper_Joint(); // ��������Ĺؽڵ�s��������ĸ���

			if (upper_Body_Counts == -1) {// ���岻�Գƣ�����Գ�
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// ����Գƣ����岻�Գ�
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// ����������Գ�
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // ��������Գ�
				isUpperSingle = false;
				isLowerSingle = false;
			}
			// ��vector2��vector3�������з��룬����Ĳ��ֵ�������vector4��vector5��
			separator(upper_Body_Counts, isUpperSingle, isLowerSingle);

			// ���
			if (vector2.size() == 3 && !isUpperSingle) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�

				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			} else if (vector2.size() == 3 && isUpperSingle) {// ����֫���Գ�ֱ�Ӷ������ڵ���������ͺ�

				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			} else if (vector2.size() == 2) {// ˵����ʱ����˫�Ʒ���������
				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // ������۵�shoulder�ڵ�
				vector2.elementAt(0).order = 2; // ���ùؽڴ���

				if (!vector1.isEmpty()) {// �ж������Ƿ�ǿ�
					MyPoint p = vector1.remove(0); // ɾ��vector1����Ľڵ�
					p.order = 3; // ��ɾ���Ľڵ����ùؽڴ���Ϊ3
					vector2.add(p); // ���˽ڵ�������������
				}
			}

			// �ұ�
			if (vector3.size() == 3 && !isUpperSingle) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�

				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}
			}

			else if (vector3.size() == 3 && isUpperSingle) {// ����֫���Գ�ֱ�Ӷ������ڵ���������ͺ�

				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}
			}

			else if (vector3.size() == 2) {// ˵����ʱ����˫���Ʒ���������

				MyPoint right_shoulder = line_min(vector3, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				right_shoulder.order = 1; // ���ùؽڴ���
				vector3.add(right_shoulder); // �����ұ۵�shoulder�ڵ�
				vector3.elementAt(0).order = 2; // ���ùؽڴ���

				if (!vector1.isEmpty()) {// �ж������Ƿ�ǿ�
					MyPoint p = vector1.remove(0); // ɾ��vector1����Ľڵ�
					p.order = 3; // ��ɾ���Ľڵ����ùؽڴ���Ϊ3
					vector3.add(p); // ���˽ڵ�����ұ�������
				}
			}
			// ����
			if (vector4.size() == 1) {// ˵�����ȹؽڵ�λ�ô�����������λ��

				MyPoint p = vector1.remove(0); // ɾ��vector1����Ľڵ�
				vector4.add(p); // ���˽ڵ�����ұ�������
				// ����
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽ�,������ؼ�������������
				vector4.elementAt(0).order = 1; // ���ùؽڴ���
				vector4.elementAt(1).order = 2; // ���ùؽڴ���

			} else if (vector4.size() == 2) {// ˵���ȹؽ���չ���ţ���δ������������λ��

				// ����
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽ�,������ؼ�������������
				vector4.elementAt(0).order = 1; // ���ùؽڴ���
				vector4.elementAt(1).order = 2; // ���ùؽڴ���

			}
			// ����
			if (vector5.size() == 1) {// ˵�����ȹؽڵ�λ�ô�����������λ��
				if (!vector1.isEmpty()) {// �ж������Ƿ�ǿ�
					MyPoint p = vector1.remove(0); // ɾ��vector1����Ľڵ�
					vector5.add(p); // ���˽ڵ�����ұ�������
				}
				// ����
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽڣ�������ؼ�������������
				vector5.elementAt(0).order = 1; // ���ùؽڴ���
				vector5.elementAt(1).order = 2; // ���ùؽڴ���
			} else if (vector5.size() == 2) { // ˵���ȹؽ���չ���ţ���δ������������λ��
				// ����
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽڣ�������ؼ�������������
				vector5.elementAt(0).order = 1; // ���ùؽڴ���
				vector5.elementAt(1).order = 2; // ���ùؽڴ���
			}

			// ��ê�����¼���vector1����
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]

		// [start]����3���ڵ�
		// vector1����������3���ڵ�
		else if (vector1.size() == 3) {
			int upper_Body_Counts = getNumOfUpper_Joint(); // ��������Ĺؽڵ���������ĸ���

			if (upper_Body_Counts == -1) {// ���岻�Գƣ�����Գ�
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// ����Գƣ����岻�Գ�
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// ����������Գ�
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // ��������Գ�
				isUpperSingle = false;
				isLowerSingle = false;
			}

			if (!isUpperSingle && isLowerSingle) {// ����Գƣ����岻�Գ�
				MyPoint[] mp = sort(vector1);
				vector2.add(vector1.get(mp[0].vector_index));// ���ݹؽڵ�����ԭʼ�����е�����������ȡ��ǰ����vector1�е�����
				vector3.add(vector1.get(mp[1].vector_index));
				MyPoint p1 = vector1.get(2);
				vector1.clear();
				vector1.addElement(p1);
			} else if (isUpperSingle && !isLowerSingle) {// ���岻�Գƣ�����Գ�
				MyPoint[] mp = sort(vector1);
				vector4.add(vector1.get(mp[1].vector_index));
				vector5.add(vector1.get(mp[2].vector_index));

			}

			// ��vector2��vector3�������з��룬����Ĳ��ֵ�������vector4��vector5��
			separator(upper_Body_Counts, isUpperSingle, isLowerSingle);

			// ���
			if (vector2.size() == 3 && !isUpperSingle) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�
				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			}

			else if (vector2.size() == 3 && isUpperSingle) {// ����֫���Գ�ֱ�Ӷ������ڵ���������ͺ�

				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			} else if (vector2.size() == 2) {// ˵����ʱ����˫���Ʒ���������
				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // ������۵�shoulder�ڵ�
				vector2.elementAt(0).order = 2; // ���ùؽڴ���

				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint third = new MyPoint(arr[0]);
				third.order = 3;
				vector2.add(third);
			} else if (vector2.size() == 1) {// ˵����ʱ����˫�ۺ�£����������
				vector2.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint second = new MyPoint(arr[0]);
				second.order = 2;
				vector2.add(second);
				MyPoint third = new MyPoint(arr[2]);
				third.order = 3;
				vector2.add(third);
			}
			// �ұ�
			if (vector3.size() == 3 && !isUpperSingle) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�
				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}

			}

			else if (vector3.size() == 3 && isUpperSingle) {// ����֫���Գ�ֱ�Ӷ������ڵ���������ͺ�

				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}
			}

			else if (vector3.size() == 2) {// ˵����ʱ����˫���Ʒ���������
				MyPoint left_shoulder = line_min(vector3, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				left_shoulder.order = 1;
				vector3.add(left_shoulder); // ������۵�shoulder�ڵ�
				vector3.elementAt(0).order = 2; // ���ùؽڴ���

				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint third = new MyPoint(arr[1]);
				third.order = 3;
				vector3.add(third);
			} else if (vector3.size() == 1) {// ˵����ʱ����˫�ۺ�£����������
				vector3.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint second = new MyPoint(arr[1]);
				second.order = 2;
				vector3.add(second);
				MyPoint third = new MyPoint(arr[3]);
				third.order = 3;
				vector3.add(third);
			}

			// ����
			if (vector4.size() == 0) { // ˵�����Ⱥ�£����������Գ�����
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint first = new MyPoint(arr[0]);
				first.order = 1;
				vector4.add(first);
				MyPoint second = new MyPoint(arr[2]);
				second.order = 2;
				vector4.add(second);
			} else if (vector4.size() == 1) {// ˵�����ȹؽڵ�λ�ô�����������λ��
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint p = new MyPoint(arr[2]);
				if (vector4.get(0).point.y > p.point.y) {
					vector4.get(0).order = 2;
					p.order = 1;
				} else {
					vector4.get(0).order = 1;
					p.order = 2;
				}
				// ��p�����������
				vector4.add(p);

			} else if (vector4.size() == 2) {// ˵���ȹؽ���չ���ţ���δ������������λ��
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽ�,������ؼ�������������
				vector4.elementAt(0).order = 1; // ���ùؽڴ���
				vector4.elementAt(1).order = 2; // ���ùؽڴ���
			}

			// ����
			if (vector5.size() == 0) { // ˵�����Ⱥ�£����������Գ�����
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint first = new MyPoint(arr[1]);
				first.order = 1;
				vector5.add(first);
				MyPoint second = new MyPoint(arr[3]);
				second.order = 2;
				vector5.add(second);
			} else if (vector5.size() == 1) {// ˵�����ȹؽڵ�λ�ô�����������λ��
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				if(arr.length <= 3){//�ݴ��ж�
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
				// ��p�����������
				vector5.add(p);
			} else if (vector5.size() == 2) { // ˵���ȹؽ���չ���ţ���δ������������λ��
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽڣ�������ؼ�������������
				vector5.elementAt(0).order = 1; // ���ùؽڴ���
				vector5.elementAt(1).order = 2; // ���ùؽڴ���
			}

			vector1.clear(); // �������
			// ��ê�����¼���vector1����
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]

		// [start]����5���ڵ�
		// vector1����������5���ڵ�
		else if (vector1.size() == 5) {
			int upper_Body_Counts = getNumOfUpper_Joint(); // ��������Ĺؽڵ���������ĸ���

			if (upper_Body_Counts == -1) {// ���岻�Գƣ�����Գ�
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// ����Գƣ����岻�Գ�
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// ����������Գ�
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // ��������Գ�
				isUpperSingle = false;
				isLowerSingle = false;
			}

			if (!isUpperSingle && isLowerSingle) {// ����Գƣ����岻�Գ�
				MyPoint[] mp = sort(vector1);
				vector2.add(vector1.get(mp[0].vector_index));// ���ݹؽڵ�����ԭʼ�����е�����������ȡ��ǰ����vector1�е�����
				vector3.add(vector1.get(mp[1].vector_index));
				MyPoint p1 = vector1.get(2);
				vector1.clear();
				vector1.addElement(p1);
			} else if (isUpperSingle && !isLowerSingle) {// ���岻�Գƣ�����Գ�
				MyPoint[] mp = sort(vector1);
				vector4.add(vector1.get(mp[1].vector_index));
				vector5.add(vector1.get(mp[2].vector_index));

			}

			// ��vector2��vector3�������з��룬����Ĳ��ֵ�������vector4��vector5��
			separator(upper_Body_Counts, isUpperSingle, isLowerSingle);

			// ���
			if (vector2.size() == 3) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�
				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}
			} else if (vector2.size() == 2) {// ˵����ʱ����˫���Ʒ���������
				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // ������۵�shoulder�ڵ�
				vector2.elementAt(0).order = 2; // ���ùؽڴ���

				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint third = new MyPoint(arr[0]);
				third.order = 3;
				vector2.add(third);
			} else if (vector2.size() == 1) {// ˵����ʱ����˫�ۺ�£����������
				vector2.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint second = new MyPoint(arr[0]);
				second.order = 2;
				vector2.add(second);
				MyPoint third = new MyPoint(arr[2]);
				third.order = 3;
				vector2.add(third);
			}
			// �ұ�
			if (vector3.size() == 3) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�
				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}

			}

			else if (vector3.size() == 2) {// ˵����ʱ����˫���Ʒ���������
				MyPoint left_shoulder = line_min(vector3, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				left_shoulder.order = 1;
				vector3.add(left_shoulder); // ������۵�shoulder�ڵ�
				vector3.elementAt(0).order = 2; // ���ùؽڴ���

				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint third = new MyPoint(arr[0]);
				third.order = 3;
				vector3.add(third);
			} else if (vector3.size() == 1) {// ˵����ʱ����˫�ۺ�£����������
				vector3.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint second = new MyPoint(arr[1]);
				second.order = 2;
				vector3.add(second);
				MyPoint third = new MyPoint(arr[3]);
				third.order = 3;
				vector3.add(third);
			}

			// ���Ⱥ����ȴ�ʱ�Ѿ�������������ٴε���

			vector1.clear(); // �������
			// ��ê�����¼���vector1����
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]

		// [start]����4����6���ڵ�
		// vector1�����������ĸ��ڵ����������ڵ�
		else if (vector1.size() == 4 || vector1.size() == 6) {
			int upper_Body_Counts = getNumOfUpper_Joint(); // ��������Ĺؽڵ�s��������ĸ���

			if (upper_Body_Counts == -1) {// ���岻�Գƣ�����Գ�
				isUpperSingle = true;
				isLowerSingle = false;
			} else if (upper_Body_Counts == -2) {// ����Գƣ����岻�Գ�
				isUpperSingle = false;
				isLowerSingle = true;
			}

			else if (upper_Body_Counts == -3) {// ����������Գ�
				isUpperSingle = true;
				isLowerSingle = true;
			} else { // ��������Գ�
				isUpperSingle = false;
				isLowerSingle = false;
			}
			separator(upper_Body_Counts, isUpperSingle, isLowerSingle); // ��vector2��vector3�������з��룬����Ĳ��ֵ�������vector4��vector5��

			// ���
			if (vector2.size() == 3) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�
				if (!analyse_LeftArm(jaw_Anchor)) {
					return false;
				}

			} else if (vector2.size() == 2) {// ˵����ʱ����˫���Ʒ���������
				MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				left_shoulder.order = 1;
				vector2.add(left_shoulder); // ������۵�shoulder�ڵ�
				vector2.elementAt(0).order = 2; // ���ùؽڴ���

				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint third = new MyPoint(arr[0]);
				third.order = 3;
				vector2.add(third);
			} else if (vector2.size() == 1) {// ˵����ʱ����˫�ۺ�£����������
				vector2.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint second = new MyPoint(arr[0]);
				second.order = 2;
				vector2.add(second);
				MyPoint third = new MyPoint(arr[2]);
				third.order = 3;
				vector2.add(third);
			}
			// �ұ�
			if (vector3.size() == 3) {// ˵����ʱ���ֱ��Ƿ����������࣬��δ���������ϣ������Ʋ�Ӧ�����Ȳ��ؽڵ��µ�
				if (!analyse_RightArm(jaw_Anchor)) {
					return false;
				}

			} else if (vector3.size() == 2) {// ˵����ʱ����˫���Ʒ���������
				MyPoint left_shoulder = line_min(vector3, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
				left_shoulder.order = 1;
				vector3.add(left_shoulder); // ������۵�shoulder�ڵ�
				vector3.elementAt(0).order = 2; // ���ùؽڴ���

				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint third = new MyPoint(arr[1]);
				third.order = 3;
				vector3.add(third);
			} else if (vector3.size() == 1) {// ˵����ʱ����˫�ۺ�£����������
				vector3.get(0).order = 1;
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint second = new MyPoint(arr[1]);
				second.order = 2;
				vector3.add(second);
				MyPoint third = new MyPoint(arr[3]);
				third.order = 3;
				vector3.add(third);
			}

			// ����
			if (vector4.size() == 0) { // ˵�����Ⱥ�£����������Գ�����
				// ˵������ͬʱҲ��������Գƴ�
				if (vector1.size() == 6) {
					MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
					MyPoint first = new MyPoint(arr[2]);
					first.order = 1;
					vector4.add(first);
					MyPoint second = new MyPoint(arr[4]);
					second.order = 2;
					vector4.add(second);
				}
				// ��˫�Ⱥ�£
				else if (vector1.size() == 4) {
					MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
					MyPoint first = new MyPoint(arr[0]);
					first.order = 1;
					vector4.add(first);
					MyPoint second = new MyPoint(arr[2]);
					second.order = 2;
					vector4.add(second);
				}
			} else if (vector4.size() == 1) {// ˵�����ȹؽڵ�λ�ô�����������λ��
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint p = new MyPoint(arr[2]);
				if (vector4.get(0).point.y > p.point.y) {
					vector4.get(0).order = 2;
					p.order = 1;
				} else {
					vector4.get(0).order = 1;
					p.order = 2;
				}
				// ��p�����������
				vector4.add(p);

			} else if (vector4.size() == 2) {// ˵���ȹؽ���չ���ţ���δ������������λ��
				if (!detail_Split(vector4, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽ�,������ؼ�������������
				vector4.elementAt(0).order = 1; // ���ùؽڴ���
				vector4.elementAt(1).order = 2; // ���ùؽڴ���
			}

			// ����
			if (vector5.size() == 0) { // ˵�����Ⱥ�£����������Գ�����
				// ˵������ͬʱҲ��������Գƴ�
				if (vector1.size() == 6) {
					MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
					MyPoint first = new MyPoint(arr[3]);
					first.order = 1;
					vector5.add(first);
					MyPoint second = new MyPoint(arr[5]);
					second.order = 2;
					vector5.add(second);
				}
				// ��˫�Ⱥ�£
				else if (vector1.size() == 4) {

					MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
					MyPoint first = new MyPoint(arr[1]);
					first.order = 1;
					vector5.add(first);
					MyPoint second = new MyPoint(arr[3]);
					second.order = 2;
					vector5.add(second);
				}
			} else if (vector5.size() == 1) {// ˵�����ȹؽڵ�λ�ô�����������λ��
				MyPoint[] arr = sort(vector1); // �Ե������������򣬰�y������������
				MyPoint p = new MyPoint(arr[3]);
				if (vector5.get(0).point.y > p.point.y) {
					vector5.get(0).order = 2;
					p.order = 1;
				} else {
					vector5.get(0).order = 1;
					p.order = 2;
				}
				// ��p�����������
				vector5.add(p);
			} else if (vector5.size() == 2) { // ˵���ȹؽ���չ���ţ���δ������������λ��
				if (!detail_Split(vector5, belly_Anchor)) {
					return false;
				} // ����knee��ankle�ؽڣ�������ؼ�������������
				vector5.elementAt(0).order = 1; // ���ùؽڴ���
				vector5.elementAt(1).order = 2; // ���ùؽڴ���
			}

			vector1.clear(); // �������
			// ��ê�����¼���vector1����
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}

		// [end]

		else if (vector1.size() == 8) {

			// �����ѽ�ƣ����������д�ˣ��ݰݣ�

		}

		// [start]һ��������
		// һ��������
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
			vector1.clear(); // �������
			// ��ê�����¼���vector1����
			vector1.add(new MyPoint(brow_Anchor));
			vector1.add(new MyPoint(jaw_Anchor));
			vector1.add(new MyPoint(belly_Anchor));
		}
		// [end]
		
		
		
		// ���½����ȹؽ�λ��
		if(!vector4.isEmpty()&&!vector5.isEmpty()){
			if(vector4.get(0).point.x > vector5.get(0).point.x){// ����ϥ�Ǻ������������ϥ�Ǻ����꣬˵�����������쳣���δ����½���
				MyPoint p1;
				p1 = vector5.remove(0);
				vector5.add(0, vector4.remove(0));
				vector4.add(0, p1);
			}
			if(vector4.get(1).point.x > vector5.get(1).point.x){// �����׹ؽں�������������׹ؽں����꣬˵�����������쳣���δ����½���
				MyPoint p1;
				p1 = vector5.remove(1);
				vector5.add(vector4.remove(1));
				vector4.add(p1);
			}
		}	
		return true;
	}

	// [end]
	

	
	
	private boolean analyse_RightArm(MyPoint jaw_Anchor) {// �����ұ۹ؽڵ�Ľ�������
		MyPoint right_shoulder = line_min(vector3, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
		right_shoulder.order = 1; // ���ùؽڴ���

		if (!detail_Split(vector3, right_shoulder)) {
			return false;
		} // ����elbow��wrist�ؽ�,������ؼ�������������
		vector3.elementAt(0).order = 2; // ���ùؽڴ���
		vector3.elementAt(1).order = 3; // ���ùؽڴ���
		vector3.add(right_shoulder); // ����shoulder�ڵ�
		return true;
	}

	private Boolean analyse_LeftArm(MyPoint jaw_Anchor) {// ������۹ؽڵ�Ľ�������
		MyPoint left_shoulder = line_min(vector2, jaw_Anchor); // ��ȡ��jaw_Anchor�ڵ�����ĵ㲢ɾ�������еĸõ�
		if(left_shoulder == null){
			System.out.println("analyse_LeftArm������������");
			return false;
		}
		left_shoulder.order = 1;
		// ����elbow��wrist�ؽ�,������ؼ�������������
		if (!detail_Split(vector2, left_shoulder)) {
			return false;
		}
		vector2.elementAt(0).order = 2; // ���ùؽڴ���
		vector2.elementAt(1).order = 3; // ���ùؽڴ���
		vector2.add(left_shoulder); // ����shoulder�ڵ�
		return true;
	}

	// ����һ��ȫ�ֵ�MyPoint�����͵�����
	private MyPoint[] temp_Arr2;

	// [start] ��ȡ����ؽڵ�����ĺ���
	private int getNumOfUpper_Joint() {
		int numberOfJoint = 0;
		int j = 0;
		int size = vector1.size() + vector2.size() + vector3.size(); // ��¼Ԫ���ܸ���
		MyPoint[] temp_Arr = new MyPoint[size]; // ����һ������Ԫ�ظ��������Ĺ��ܵ��������
		// ����������ȥ�������е�����Ԫ�ز�����������
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
		Arr_Sorted(temp_Arr); // ð�����򣬰���y����ֵ�Ĵ�С
		// ���vector1����û��Ԫ�أ������ҹؽڵ������������
		if (vector1.size() == 0) {
			numberOfJoint = 3;
		}
		// ����������ظ�Ԫ�صĶ�������Ԫ����������ڵ�
		int same_Point = 0; // �����ظ������
		for (int k = 0; k < vector1.size(); k++) {
			if (vector1.get(k).point.y < temp_Arr[temp_Arr.length - 4].point.y - delta1) {// �ж������е�Ԫ��ֵ��y����ֵ�Ƿ�С��ϥ�ǵ�����ֵ
				same_Point++; // �ۼ��ظ������
			}
		}

		// ���numberOfJoint��������Ϊż������ֱ�ӻ�ȡ����ڵ���
		if (same_Point % 2 == 0 && vector1.size() % 2 == 0) {
			numberOfJoint = 3 - same_Point / 2;
		}

		// ���岻�Գƣ�����Գ�
		else if (vector1.size() % 2 != 0 && same_Point % 2 != 0) {

			// numberOfJoint = (3 - same_Point) / 2 + 1;
			numberOfJoint = -1;
		}
		// ����ؽڲ��Գƣ�����Գ�
		else if (vector1.size() % 2 != 0 && same_Point % 2 == 0) {
			numberOfJoint = -2;
		}
		// ����������Գ�
		else if (vector1.size() % 2 == 0 && same_Point % 2 != 0) {
			numberOfJoint = -3;
		}
		return numberOfJoint; // ���ص�ǰvector2�к��е�����ڵ�Ԫ�صĸ���
	}
	// [end]

	// ��vector2��vector3�������з��룬����Ҫ�õ�������������sort������Ĳ��ֵ�������vector4��vector5��
	private void separator(int upper_Body_Counts, boolean isUpperSingle, boolean isLowerSingle) {
		final int delta2 = 1;

		if (!isUpperSingle && !isLowerSingle) {// ��������Գ�

			// �����������vector2
			MyPoint[] arr2 = sort(vector2); // �Ե������������򣬰�y������������
			vector2.clear();
			int count1 = 0; // �����Ǹ�������������ʼ��Ϊ0
			for (int i = 0; i < arr2.length; i++) {
				if (count1 < upper_Body_Counts) {
					vector2.add(arr2[i]); // ����ۼ���ؽڵ���󣬼����ܵ����
					count1++;
					continue; // �˳�����ѭ��
				}
				vector4.add(arr2[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
			}

			// �����ұ�����vector3
			MyPoint[] arr3 = sort(vector3); // �Ե������������򣬰�y������������
			vector3.clear(); // �������
			int count2 = 0; // �����Ǹ�������
			for (int i = 0; i < arr3.length; i++) {
				if (count2 < upper_Body_Counts) {
					vector3.add(arr3[i]); // ���ұۼ���ؽڵ���󣬼����ܵ����
					count2++;
					continue; // �˳�����ѭ��
				}
				vector5.add(arr3[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
			}
		}

		else if (isUpperSingle && !isLowerSingle) {// ���岻�Գ�,����Գ�

			vector4.clear();
			vector5.clear();
			// �����������vector2
			MyPoint[] arr2 = sort(vector2); // �Ե������������򣬰�y������������
			vector2.clear();
			int count1 = 0; // �����Ǹ�������������ʼ��Ϊ0
			for (int i = 0; i < arr2.length; i++) {
				if (count1 < 2) {// ������ֹ����Ϊ2����Ϊ�޷�ȷ����������������뻹���ұ�����������
					vector2.add(arr2[i]); // ����ۼ���ؽڵ���󣬼����ܵ����
					count1++;
					continue; // �˳�����ѭ��
				}
				vector4.add(arr2[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
			}

			// �����ұ�����vector3
			MyPoint[] arr3 = sort(vector3); // �Ե������������򣬰�y������������
			vector3.clear(); // �������
			int count2 = 0; // �����Ǹ�������
			for (int i = 0; i < arr3.length; i++) {
				if (count2 < 2) {// ������ֹ����Ϊ2����Ϊ�޷�ȷ����������������뻹���ұ�����������
					vector3.add(arr3[i]); // ���ұۼ���ؽڵ���󣬼����ܵ����
					count2++;
					continue; // �˳�����ѭ��
				}
				vector5.add(arr3[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
			}

			// ˫�Ⱦ������������룬����ʱ��������������һ������һ������ڵ�
			if (vector4.size() == 0 || vector5.size() == 0) {
				if (vector4.size() != 0) {
					for (int k = 0; k < vector4.size(); k++) {// �޳�����ڵ�Ż���������
						if (vector4.get(k).point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// �ж������е�Ԫ��ֵ��y����ֵ�Ƿ����ϥ�ǵ�����ֵ
							vector2.add(vector4.remove(k));
						}
					}
				} else {
					for (int k = 0; k < vector5.size(); k++) {// �޳�����ڵ�Ż���������
						if (vector5.get(k).point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// �ж������е�Ԫ��ֵ��y����ֵ�Ƿ����ϥ�ǵ�����ֵ
							vector3.add(vector5.remove(k));
						}
					}
				}

				if (vector4.size() == 0 && vector5.size() == 0) {// ��vector1�����е�����ϥ�ؽں��׹ؽڽ��д����Ż�����ؽ�����
					MyPoint p = vector1.get(0); // ��������ڵ�ı���
					int index = 0;
					for (int k = 0; k < vector1.size(); k++) {// Ѱ������ڵ�
						if (vector1.get(k).point.y < p.point.y) {// �ж������е�Ԫ��ֵ��y����ֵ�Ƿ����ϥ�ǵ�����ֵ
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

			// ��vector4��vector5�еĶ���һ���ؽڵ����¼���vector2��vector3��
			if (vector4.size() % 2 != 0) {
				for (int k = 0; k < vector4.size(); k++) {
					if (vector4.get(k).point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// �ж������е�Ԫ��ֵ��y����ֵ�Ƿ����ϥ�ǵ�����ֵ
						vector2.add(vector4.remove(k));
					}
				}
			} else if (vector5.size() % 2 != 0) {
				for (int k = 0; k < vector5.size(); k++) {
					if (vector5.get(k).point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// �ж������е�Ԫ��ֵ��y����ֵ�Ƿ����ϥ�ǵ�����ֵ
						vector3.add(vector5.remove(k));
					}
				}
			}
		}

		else if (!isUpperSingle && isLowerSingle) {// ����Գƣ����岻�Գ�
			// �����������vector2
			MyPoint[] arr2 = sort(vector2); // �Ե������������򣬰�y������������
			vector2.clear();

			int count1 = 0; // �����Ǹ�������������ʼ��Ϊ0
			for (int i = 0; i < arr2.length; i++) {
				if (count1 < 3) {// ��֫������չ��Ӧȫ����������
					vector2.add(arr2[i]); // ����ۼ���ؽڵ���󣬼����ܵ����
					count1++;
					continue; // �˳�����ѭ��
				}
				vector4.add(arr2[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
			}

			// �����ұ�����vector3
			MyPoint[] arr3 = sort(vector3); // �Ե������������򣬰�y������������
			vector3.clear(); // �������
			int count2 = 0; // �����Ǹ�������
			for (int i = 0; i < arr3.length; i++) {
				if (count2 < 3) {// ��֫������չ��Ӧȫ����������
					vector3.add(arr3[i]); // ���ұۼ���ؽڵ���󣬼����ܵ����
					count2++;
					continue; // �˳�����ѭ��
				}
				vector5.add(arr3[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
			}

			// ��vector4��vector5�еĶ���һ���ؽڵ����¼���vector2��vector3��
			if (vector4.size() % 2 != 0) {
				vector4.add(vector1.remove(0));
			} else if (vector5.size() % 2 != 0) {
				vector5.add(vector1.remove(0));
			}

		}

		else if (isUpperSingle && isLowerSingle) {// ������������Գ�
			if (vector2.size() == vector3.size()) {// ��������඼���Գ�
				int flag2 = 0;
				int flag3 = 0;
				// �����������vector2
				MyPoint[] _arr2 = sort(vector2); // �Ե������������򣬰�y������������
				for (int i = 0; i < _arr2.length; i++) {
					if (_arr2[i].point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// �ж������е�Ԫ��ֵ��y����ֵ�Ƿ����ϥ�ǵ�����ֵ
						flag2++;
					}
				}
				// �����ұ�����vector3
				MyPoint[] _arr3 = sort(vector3); // �Ե������������򣬰�y������������
				for (int i = 0; i < _arr3.length; i++) {
					if (_arr3[i].point.y < temp_Arr2[temp_Arr2.length - 4].point.y - delta2) {// �ж������е�Ԫ��ֵ��y����ֵ�Ƿ����ϥ�ǵ�����ֵ
						flag3++;
					}
				}

				// �ж��������֫���Գƻ����Ҳ���֫���Գ�
				if (flag2 == 2) {// ��۲��Գƣ����Ȳ��Գ�
					// �����������vector2
					MyPoint[] arr2 = sort(vector2); // �Ե������������򣬰�y������������
					vector2.clear();
					int count1 = 0; // �����Ǹ�������������ʼ��Ϊ0
					for (int i = 0; i < arr2.length; i++) {
						if (count1 < 2) {// ������ֹ����Ϊ2����Ϊ�޷�ȷ����������������뻹���ұ�����������
							vector2.add(arr2[i]); // ����ۼ���ؽڵ���󣬼����ܵ����
							count1++;
							continue; // �˳�����ѭ��
						}
						vector4.add(arr2[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
					}
					// ��vector1�ж����������ؽڼ���vecto2�У���ɾ��vector1�е�������ؽ�
					if (vector1.get(1).point.y > vector1.get(0).point.y) {
						vector2.add(vector1.remove(0));
					} else if (vector1.get(0).point.y > vector1.get(1).point.y) {
						vector2.add(vector1.remove(1));
					}

					// �����ұ�����vector3
					MyPoint[] arr3 = sort(vector3); // �Ե������������򣬰�y������������
					vector3.clear(); // �������
					int count2 = 0; // �����Ǹ�������
					for (int i = 0; i < arr3.length; i++) {
						if (count2 < 3) {
							vector3.add(arr3[i]); // ���ұۼ���ؽڵ���󣬼����ܵ����
							count2++;
							continue; // �˳�����ѭ��
						}
						vector5.add(arr3[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
					}

					// ��vector1�����һ���ؽڵ����¼���vector5��
					if (vector5.size() % 2 != 0) {
						vector5.add(vector1.remove(0));
					}

				} else if (flag3 == 2) {// �ұ۲��Գƣ����Ȳ��Գ� �������������һ��
					// �����������vector2
					MyPoint[] arr2 = sort(vector2); // �Ե������������򣬰�y������������
					vector2.clear();
					int count1 = 0; // �����Ǹ�������������ʼ��Ϊ0
					for (int i = 0; i < arr2.length; i++) {
						if (count1 < 3) {// �޽ڵ���ʧ
							vector2.add(arr2[i]); // ����ۼ���ؽڵ���󣬼����ܵ����
							count1++;
							continue; // �˳�����ѭ��
						}
						vector4.add(arr2[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
					}

					// �����ұ�����vector3
					MyPoint[] arr3 = sort(vector3); // �Ե������������򣬰�y������������
					vector3.clear(); // �������
					int count2 = 0; // �����Ǹ�������
					for (int i = 0; i < arr3.length; i++) {
						if (count2 < 2) {
							vector3.add(arr3[i]); // ���ұۼ���ؽڵ���󣬼����ܵ����
							count2++;
							continue; // �˳�����ѭ��
						}
						vector5.add(arr3[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
					}
					// ��vector1�ж����������ؽڼ���vector3�У���ɾ��vector1�е�������ؽ�
					if (vector1.get(1).point.y > vector1.get(0).point.y) {
						vector3.add(vector1.remove(0));
					} else if (vector1.get(0).point.y > vector1.get(1).point.y) {
						vector3.add(vector1.remove(1));
					}

					// ��vector1�����һ���ؽڵ����¼���vector5��
					if (vector4.size() % 2 != 0) {
						vector4.add(vector1.remove(0));
					}
				}

			} else {// �����һ�಻�Գ�
				if (vector2.size() < vector3.size()) {// ��ۺ����Ⱦ����Գ�
					// �����������vector2
					MyPoint[] arr2 = sort(vector2); // �Ե������������򣬰�y������������
					vector2.clear();
					int count1 = 0; // �����Ǹ�������������ʼ��Ϊ0
					for (int i = 0; i < arr2.length; i++) {
						if (count1 < 2) {// ������ֹ����Ϊ2����Ϊ�޷�ȷ����������������뻹���ұ�����������
							vector2.add(arr2[i]); // ����ۼ���ؽڵ���󣬼����ܵ����
							count1++;
							continue; // �˳�����ѭ��
						}
						vector4.add(arr2[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
					}
					// ��vector1�ж����������ؽڼ���vecto2�У���ɾ��vector1�е�������ؽ�
					if (vector1.get(1).point.y > vector1.get(0).point.y) {
						vector2.add(vector1.get(0));
						vector4.add(vector1.get(1));
						vector1.clear();
					} else if (vector1.get(0).point.y > vector1.get(1).point.y) {
						vector2.add(vector1.get(1));
						vector4.add(vector1.get(0));
						vector1.clear();
					}

					// �����ұ�����vector3
					MyPoint[] arr3 = sort(vector3); // �Ե������������򣬰�y������������
					vector3.clear(); // �������
					int count2 = 0; // �����Ǹ�������
					for (int i = 0; i < arr3.length; i++) {
						if (count2 < 3) {
							vector3.add(arr3[i]); // ���ұۼ���ؽڵ���󣬼����ܵ����
							count2++;
							continue; // �˳�����ѭ��
						}
						vector5.add(arr3[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
					}

				} else {// �ұۺ����Ⱦ����Գ�
						// �����������vector2
					MyPoint[] arr2 = sort(vector2); // �Ե������������򣬰�y������������
					vector2.clear();
					int count1 = 0; // �����Ǹ�������������ʼ��Ϊ0
					for (int i = 0; i < arr2.length; i++) {
						if (count1 < 3) {// ������ֹ����Ϊ2����Ϊ�޷�ȷ����������������뻹���ұ�����������
							vector2.add(arr2[i]); // ����ۼ���ؽڵ���󣬼����ܵ����
							count1++;
							continue; // �˳�����ѭ��
						}
						vector4.add(arr2[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
					}

					// �����ұ�����vector3
					MyPoint[] arr3 = sort(vector3); // �Ե������������򣬰�y������������
					vector3.clear(); // �������
					int count2 = 0; // �����Ǹ�������
					for (int i = 0; i < arr3.length; i++) {
						if (count2 < 2) {
							vector3.add(arr3[i]); // ���ұۼ���ؽڵ���󣬼����ܵ����
							count2++;
							continue; // �˳�����ѭ��
						}
						vector5.add(arr3[i]); // �����ȼ��빦�ܵ���󣬼��ؽڵ����
					}
					// ��vector1�ж����������ؽڼ���vecto2�У���ɾ��vector1�е�������ؽ�
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
		// ���ùؽ�
		MyPoint joint1;
		MyPoint joint2;
		if (v.size() < 2) {
			return false;
		}
		// �ж�С���Ƿ�̧�����
		if (v.elementAt(0).point.y <= refer_Point.point.y && v.elementAt(1).point.y <= refer_Point.point.y) {// С��̧��
			if (v.elementAt(0).point.y < v.elementAt(1).point.y) {
				joint1 = new MyPoint(v.elementAt(1));
				joint2 = new MyPoint(v.elementAt(0));
				v.clear(); // �������
				// �ؽڵ����¼���v����
				v.add(joint1);
				v.add(joint2);
			} else {
				joint1 = new MyPoint(v.elementAt(0));
				joint2 = new MyPoint(v.elementAt(1));
				v.clear(); // �������
				// �ؽڵ����¼���v����
				v.add(joint1);
				v.add(joint2);
			}

		} else { // С��δ̧�����
			if (v.elementAt(0).point.y < refer_Point.point.y) {// �ֹ���

				joint1 = new MyPoint(v.elementAt(1));
				joint2 = new MyPoint(v.elementAt(0));
				v.clear(); // �������
				// �ؽڵ����¼���v����
				v.add(joint1); // ��������Ĺؽڵ㣬�����ܵ������������ͬ
				v.add(joint2);
			} else if (v.elementAt(1).point.y < refer_Point.point.y) {// �ֹ���
				joint1 = new MyPoint(v.elementAt(0));
				joint2 = new MyPoint(v.elementAt(1));
				v.clear(); // �������
				// �ؽڵ����¼���v����
				v.add(joint1); // ��������Ĺؽڵ㣬�����ܵ������������ͬ
				v.add(joint2);

			} else {// С���´���С��̧��δ����
				
				double distance = PointToLine_Distance.lineSpace(v.elementAt(1), v.elementAt(0));
				double distance0 = PointToLine_Distance.lineSpace(v.elementAt(0), refer_Point);
				double distance1 = PointToLine_Distance.lineSpace(v.elementAt(1), refer_Point);

				// �ж���ֱ�ߵĳ��ȴ�С
				if (distance0 > distance1) {
					// �жϼ�ؽڣ���ؽڣ���ؽڹ�ͬ���ɵ�����������ǻ��Ƕ۽�
					if (isObtuse_Angle(distance, distance0, distance1)) {// �Ƕ۽�
						joint1 = new MyPoint(v.elementAt(1));// ������ؽ�
						joint2 = new MyPoint(v.elementAt(0));// ������ؽ�
					} 
					else {// �����
						if(PointToLine_Distance.lineSpace(v.elementAt(0), belly_Anchor) >
						PointToLine_Distance.lineSpace(v.elementAt(1), belly_Anchor)){//�����׼��ؽ�"�����ê��ľ������
							joint1 = new MyPoint(v.elementAt(0));// ������ؽ�
							joint2 = new MyPoint(v.elementAt(1));// ������ؽ�														// ��׼��ؽ������ê��֮��ľ��룬����������
						}
						else{// ��֮��׼��ؽ�ӦΪ��ؽڣ���������
							joint1 = new MyPoint(v.elementAt(1));// ������ؽ�
							joint2 = new MyPoint(v.elementAt(0));// ������ؽ�
						}
						
					}

				} else {
					// �жϼ�ؽڣ���ؽڣ���ؽڹ�ͬ���ɵ�����������ǻ��Ƕ۽�
					if (isObtuse_Angle(distance, distance0, distance1)) {// �Ƕ۽�
						joint1 = new MyPoint(v.elementAt(0));// ������ؽ�
						joint2 = new MyPoint(v.elementAt(1));// ������ؽ�

					} else {// �����
						
						if(PointToLine_Distance.lineSpace(v.elementAt(1), belly_Anchor) >
						PointToLine_Distance.lineSpace(v.elementAt(0), belly_Anchor)){//�����׼��ؽ�"�����ê��ľ������
							joint1 = new MyPoint(v.elementAt(1));// ������ؽ�
							joint2 = new MyPoint(v.elementAt(0));// ������ؽ�															// ��׼��ؽ������ê��֮��ľ��룬����������
						}
						else{// ��֮��׼��ؽ�ӦΪ��ؽڣ���������
							joint1 = new MyPoint(v.elementAt(0));// ������ؽ�
							joint2 = new MyPoint(v.elementAt(1));// ������ؽ�
						}
						
					}

				}
				v.clear(); // �������
				// �ؽڵ����¼���v����
				v.add(joint1); // ��������Ĺؽڵ㣬�����ܵ������������ͬ
				v.add(joint2);
			}

		}

		return true;
	}

	private boolean isObtuse_Angle(double a, double b, double c) {// �ж��������Ƿ��Ƕ۽�������
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
		int flag = 0; // ����ִ�б�־���ñ�־�����Ƿ�ִ�� ����jaw_Anchor���ܵ�����Ĺؽڵ㣨���ܵ㣩�ĳ�ʼ��
		MyPoint min_Temp = null; // ����Ŀ��ؽڵ�ı���
		if(v.isEmpty()){
			return null;
		}
		for (Iterator<MyPoint> iterator = v.iterator(); iterator.hasNext();) {
			MyPoint myPoint = (MyPoint) iterator.next(); // ͨ����������������
			if (flag == 0) {
				min_Temp = new MyPoint(myPoint); // ��ʼ��Ŀ��ؽڵ�
				flag = 1; // �رճ�ʼ������
			}
			
			if(myPoint == null){
				return null;
			}
			// ���������еĸ�Ԫ����jaw_Anchor�ؽڵ�֮��ľ���
			double distance = PointToLine_Distance.lineSpace(myPoint.point.x, myPoint.point.y, jaw_Anchor.point.x,
					jaw_Anchor.point.y);
			double min_distance = PointToLine_Distance.lineSpace(min_Temp.point.x, min_Temp.point.y, jaw_Anchor.point.x,
					jaw_Anchor.point.y);

			if (distance < min_distance) {
				min_Temp = new MyPoint(myPoint); // ����jaw_Anchor���ܵ�����Ĺؽڵ�(���ܵ�)����Ŀ��ؽڵ�
			}
		}
		// ��������ɾ����������Ľڵ�
		for (ListIterator<MyPoint> listIterator = v.listIterator(); listIterator.hasNext();) {
			MyPoint myPoint = (MyPoint) listIterator.next();
			if (myPoint.point.x == min_Temp.point.x && myPoint.point.y == min_Temp.point.y) {
				listIterator.remove(); // ��������ɾ��Ŀ��ؽڵ�
			}
		}
		return min_Temp; // ���ؾ�������Ľڵ�
	}

	// ������������Ҫ���ڽ�����ê�㣨3��������������������������鷵�ػ���
	private MyPoint[] anchor_analyze() {
		MyPoint[] arr = new MyPoint[3];
		MyPoint[] vector1_arr = sort(vector1); // ��������������
		vector1.clear(); // �������
		int flag = 0; // ִ�в���ı�־
		int j = 0; // ���������ѭ����־
		for (int i = 0; i < vector1_arr.length; i++) {
			if (flag == 0) {
				if (vector1_arr[i].anchor) {
					brow_Anchor = vector1_arr[i]; // ͷê�㣬����ͷê��
					brow_Anchor.order = 1;
					arr[j++] = brow_Anchor;
					flag = 1;
					continue; // �˳�����ѭ��������������
				}
			} else if (flag == 1) {
				if (vector1_arr[i].anchor) {
					jaw_Anchor = vector1_arr[i]; // ��ȡ�м�ê��,���°�ê��
					jaw_Anchor.order = 2;
					arr[j++] = jaw_Anchor;
					flag = 2;
					continue; // �˳�����ѭ��������������
				}
			} else if (flag == 2) {
				if (vector1_arr[i].anchor) {
					belly_Anchor = vector1_arr[i]; // ��ȡĩβê�㣬������ê��
					belly_Anchor.order = 3;
					arr[j++] = belly_Anchor;
					flag = 3;
					continue; // �˳�����ѭ��������������
				}
			}
			vector1.add(vector1_arr[i]); // �����еĹ��ܵ�����������1��
		}
		return arr; // ��������
	}

	// ���ڽ��õ��Ӧ��ԭʼ�������뵽�����������ȥ
	private void addPoints(MyPoint tmp, Vector<MyPoint> v) {
		// ѭ��ԭʼ������
		for (int k = 0; k < pointsVector.size(); k++) {
			MyPoint pt = (MyPoint) pointsVector.elementAt(k);
			// �ж�ת�������ĵ�����Ƿ�����ԭʼ������Ӧ�ĵ����
			if (tmp.link_Flag == pt.link_Flag) {
				MyPoint m = new MyPoint(pt); // ������������MyPointԪ��
				v.add(m); // �ѵ��������
				break; // �ҵ�����˳�ѭ��
			}
		}
	}

	// ������������������Ԫ�صĵ������yֵ�Ĵ�С��������
	private void Arr_Sorted(MyPoint[] arr) {
		MyPoint myPoint = null; // ����һ�����������������ʱ��ֵ�滻
		// ð�����򷨣���������y����ֵ��С����һ������
		for (int pass = 1; pass < arr.length; pass++) {
			for (int i = 0; i < arr.length - pass; i++) {
				// ���ݹ��ܵ�����y����ֵ�Ĵ�С����������������Ԫ�ؼ����ܵ�����λ��
				if (arr[i].point.y > arr[i + 1].point.y) {
					myPoint = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = myPoint;
				}
			}
		}
	}

	// ����������
	private MyPoint[] sort(Vector<MyPoint> v) {

		// ����һ�����������������ʱ��ֵ�滻
		MyPoint myPoint;
		MyPoint[] arr = new MyPoint[v.size()];
		// ����j�������ƶ�����ָ��
		int j = 0;
		for (int i = 0; i < v.size(); i++) {
			MyPoint tmp = (MyPoint) v.elementAt(i);
			// ����MyPoint���вι��캯��
			MyPoint mp = new MyPoint(tmp);
			// ����mp�ؽڵ����������е�������
			mp.vector_index = i;
			// �ѵ����������������
			arr[j++] = mp;
		}
		// ð�����򷨣���������y����ֵ��С������������
		for (int pass = 1; pass < arr.length; pass++) {
			for (int i = 0; i < arr.length - pass; i++) {
				if (arr[i].point.y > arr[i + 1].point.y) {
					myPoint = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = myPoint;
				}
			}
		}
		return arr; // ���عؽڵ��������
	}

}
