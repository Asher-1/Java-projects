//Task.java
package rmi;
import java.io.Serializable;
/**
 * �ýӿ�ʵ�����л����ܣ����ڼ�������Ĵ���
 */
public interface Task extends Serializable{
	public int[][] execute();
}

