package util;

import java.util.Iterator;
import java.util.LinkedList;

public class PosQueue {
	private LinkedList list = new LinkedList();
	public class PosQueueIterator {
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
	
	public void enQueue(Position p) {
		list.addLast(p);
	}
	
	public Position deQueue() {
		return (Position)list.remove();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void clear() {
		list.clear();
	}
	
	public PosQueueIterator iterator() {
		return new PosQueueIterator();
	}
}
