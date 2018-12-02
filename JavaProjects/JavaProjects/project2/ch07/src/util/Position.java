package util;

import java.io.Serializable;

/** 位置类，
 * 蚂蚁/信息素的位置表示
 * @author Leaf
 * @version 0.1
 */
public class Position implements Serializable {
	/** version ID */
	private static final long serialVersionUID = 9216002894233951112L;
	/** x坐标 */
	public int x;
	/** y坐标 */
	public int y;

	/** 缺省构造器 */
	public Position() {}
	/**
	 * 含参数构造器
	 * @param x x坐标
	 * @param y y坐标
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/** 拷贝构造器 */
	public Position(Position p) {
		x = p.x;
		y = p.y;
	}
	
	/**
	 * 设置坐标
	 * @param x x坐标
	 * @param y y坐标
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
	 * 修改x坐标
	 * @param dx x增量（+/-）
	 */
	public void dx(int dx) {
		x += dx;
	}

	/**
	 * 修改y坐标
	 * @param dy y增量（+/-）
	 */
	public void dy(int dy) {
		y += dy;
	}
	
	/**
	 * 覆盖toString方法
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
