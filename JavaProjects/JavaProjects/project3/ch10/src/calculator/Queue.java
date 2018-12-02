/*
 * Created on 2004-12-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package calculator;

/**
 * @author tom1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Queue {

	/**
	 * 
	 * @uml.property name="head"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private Node head;

	/**
	 * 
	 * @uml.property name="tail"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private Node tail;

	public Queue() {
		head = tail = null;
	}

	public void enqueue(Object obj) {
		Node node = new Node(obj);
		if (head == null)
			head = node;
		else
			tail.next = node;
		tail = node;
	}

	public Object dequeue() throws QueueException {
		if (head == null)
			throw new QueueException("removing from empty queue");
		else {
			Object data = head.data;
			head = head.next;
			if (head == null)
				tail = null;
			return data;
		}
	}

	public Object peek() throws QueueException {
		if (head == null)
			throw new QueueException("peeking into empty queue");
		else
			return head.data;
	}

	public boolean isEmpty() {
		return (head == null);
	}
}