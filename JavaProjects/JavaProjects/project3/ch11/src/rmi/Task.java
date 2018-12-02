//Task.java
package rmi;
import java.io.Serializable;
/**
 * 该接口实现序列化功能，用于计算任务的传输
 */
public interface Task extends Serializable{
	public int[][] execute();
}

