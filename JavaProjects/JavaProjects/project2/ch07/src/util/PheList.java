package util;

import java.util.LinkedList;
import java.util.ListIterator;

import ant.Pheromone;


/** ��Ϣ�ر���
 * @author Leaf
 * @version 0.1
 */
public class PheList {
	/** ��Ϣ�ر� */
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
	 * �����Ϣ�ض���
	 * @param item ��Ϣ��Ԫ��
	 */
	public void add(Pheromone item) {
		list.add(item);
		item.in();
	}
	
	/**
	 * ��ȡ��Ϣ�ض���
	 * @param index ����
	 * @return ��������Ϣ��
	 */
	public Pheromone get(int index) {
		return (Pheromone)list.get(index);
	}
	
	/**
	 * ��ȡ����
	 * @return ����
	 */
	public int size() {
		return list.size();
	}
	
	/**
	 * �жϱ��Ƿ�Ϊ��
	 * @return ��Ϊ���򷵻�true
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
