package util;

import java.io.Serializable;

/** λ���࣬
 * ����/��Ϣ�ص�λ�ñ�ʾ
 * @author Leaf
 * @version 0.1
 */
public class Position implements Serializable {
	/** version ID */
	private static final long serialVersionUID = 9216002894233951112L;
	/** x���� */
	public int x;
	/** y���� */
	public int y;

	/** ȱʡ������ */
	public Position() {}
	/**
	 * ������������
	 * @param x x����
	 * @param y y����
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/** ���������� */
	public Position(Position p) {
		x = p.x;
		y = p.y;
	}
	
	/**
	 * ��������
	 * @param x x����
	 * @param y y����
	 */
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void set(Position p) {
		x = p.x;
		y = p.y;
	}

	/**
	 * �޸�x����
	 * @param dx x������+/-��
	 */
	public void dx(int dx) {
		x += dx;
	}

	/**
	 * �޸�y����
	 * @param dy y������+/-��
	 */
	public void dy(int dy) {
		y += dy;
	}
	
	/**
	 * ����toString����
	 */
	public String toString() {
//		return "Position(" + x + ", " + y + ")\n";
		return x + "," + y + "\n";
	}
	
	public boolean equals(Position p) {
		return (p.x == x && p.y == y);
	}
	
	public int hashCode() {
		return (x * 10000 + y);
	}
}
