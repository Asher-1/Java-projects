package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


/** 位置表类，
 * 存储蚂蚁路径
 * @author Leaf
 */
public class PosList implements Serializable{
	/** version ID */
	private static final long serialVersionUID = -6339031558992968567L;
	/** 位置表 */
	private ArrayList list = new ArrayList();
	
	public class PosListIterator {
		Iterator iter = list.iterator();
		
		public Position next() {
			return (Position)iter.next();
		}
		
		public boolean hasNext() {
			return iter.hasNext();
		}
		
		public void remove() {
			iter.remove();
		}
	}
	
	/**
	 * 添加位置信息
	 * @param item 位置元素
	 */
	public void add(Position item) {
		list.add(item);
	}
	
	/**
	 * 获取位置信息
	 * @param index 索引
	 * @return 索引处位置
	 */
	public Position get(int index) {
		return (Position)list.get(index);
	}
	
	/**
	 * 获取表长度
	 * @return 表长度
	 */
	public int size() {
		return list.size();
	}
	
	/**
	 * 判断表是否为空
	 * @return 如为空则返回true
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public PosListIterator iterator() {
		return new PosListIterator();
	}
	
	public void clear() {
		list.clear();
	}
	
	public boolean remove(Position p) {
		PosListIterator iter = iterator();
		Position t;
		while(iter.hasNext()) {
			t = iter.next();
			if(t.equals(p)) {
				iter.remove();
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Position p) {
		PosListIterator iter = iterator();
		Position t;
		while(iter.hasNext()) {
			t = iter.next();
			if(t.equals(p)) return true;
		}
		return false;
	}
	
	public PosList completeClone() {
		PosList p = new PosList();
		PosListIterator iter = iterator();
		while(iter.hasNext()) {
			p.add(iter.next());
		}
		return p;
	}
	
	public String toString() {
		String result = new String();
		PosListIterator iter = iterator();
		while(iter.hasNext()) {
			result += iter.next().toString();
		}
		return result;
	}
}
