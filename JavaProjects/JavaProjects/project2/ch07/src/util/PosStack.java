package util;

import java.util.LinkedList;

public class PosStack {
	private LinkedList list = new LinkedList();
	
	public void push(Position p) {
		list.addLast(p);
	}
	
	public Position pop() {
		return (Position)list.removeLast();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void clear() {
		list.clear();
	}
}
