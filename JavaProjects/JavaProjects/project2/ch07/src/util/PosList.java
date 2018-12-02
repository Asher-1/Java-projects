package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


/** λ�ñ��࣬
 * �洢����·��
 * @author Leaf
 */
public class PosList implements Serializable{
	/** version ID */
	private static final long serialVersionUID = -6339031558992968567L;
	/** λ�ñ� */
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
	 * ���λ����Ϣ
	 * @param item λ��Ԫ��
	 */
	public void add(Position item) {
		list.add(item);
	}
	
	/**
	 * ��ȡλ����Ϣ
	 * @param index ����
	 * @return ������λ��
	 */
	public Position get(int index) {
		return (Position)list.get(index);
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
