/*
 * Created on 2004-12-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package calculator;

public class Stack
{  private Node top;
   public Stack()
   {  top = null; }
   public void push(Object obj)
   {  Node node = new Node(obj);
      node.next = top;
      top = node;
   }
   public Object pop() throws StackException
   {  if (top == null)
         throw new StackException("popping from an empty stack");
      else
      {  Object obj = top.data;
         top = top.next;
         return obj;
      }
   }
   public Object peek() throws StackException
   {  if (top == null)
         throw new StackException("peeking into empty stack");
      else
         return top.data;
   }
   public boolean isEmpty()
   {  return (top == null); }
}


