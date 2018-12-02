package util;

import java.util.LinkedList;
import java.util.ListIterator;

import ant.Pheromone;


/** 信息素表类
 * @author Leaf
 * @version 0.1
 */
public class PheList {
	/** 信息素表 */
	private LinkedList list = new LinkedList();;
	
	public class PheIterator {
		private ListIterator iter;
		
		public PheIterator() {
			iter = list.listIterator();
		}
		public PheIterator(int index) {
			iter = list.listIterator(index);
		}
		
		public Pheromone next() {
			return (Pheromone)iter.next();
		}
		
		public boolean hasNext() {
			return iter.hasNext();
		}
		
		public void remove() {
			iter.remove();
		}
	}
	
	/**
	 * 添加信息素对象
	 * @param item 信息素元素
	 */
	public void add(Pheromone item) {
		list.add(item);
		item.in();
	}
	
	/**
	 * 获取信息素对象
	 * @param index 索引
	 * @return 索引处信息素
	 */
	public Pheromone get(int index) {
		return (Pheromone)list.get(index);
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
	
	public boolean remove(Pheromone p) {
		p.out();
		return list.remove(p);
	}
	
	public PheIterator iter(int index) {
		return new PheIterator(index);
	}
	
	public PheIterator iterator() {
		return new PheIterator();
	}
	
	public void clear() {
		PheIterator i = iterator();
		while(i.hasNext()) {
			i.next().out();
		}
		list.clear();
	}
	
	public String toString() {
		String t = new String();
		PheIterator i = iterator();
		while(i.hasNext()) {
			t += i.next().toString();
		}
		return t;
	}
}
