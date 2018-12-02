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
class Node {
	protected Object data;

	/**
	 * 
	 * @uml.property name="next"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	protected Node next;

	protected Node() {
		data = null;
		next = null;
	}

	protected Node(Object _data) {
		data = _data;
		next = null;
	}
}