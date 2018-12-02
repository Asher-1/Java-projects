//Constants.java
package rmi;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Ϊ�˳���ά�����㣬��һЩ���������ڸ����У�
 * �����һ�������ļ��ж�ȡ�û����õĲ�����
 */
public class Constants {
	//++ȱʡ�ķ��������ƺͶ˿ڣ�������
	public static final String DEFAULT_SERVER;
	public static final String DEFAULT_PORT;
	public static final String SERVICE_NAME;
	//--ȱʡ�ķ��������ƺͶ˿ڣ�������
	//++����A��B�����������
	public static final int ROWS_A;
	public static final int COLUMNS_A;
	//B������Ҫ��A���������
	public static final int ROWS_B;
	public static final int COLUMNS_B;
	//--����A��B�����������
	//++�Ѿ����Ϊ�����ֽ��м��㣬������ʼ������Ҫ������������зָ�
	//��һ���ֵ���ʼ����
	public static final int PART1_START = 0;
	//��һ���ֵ�����
	public static final int PART1_LEN;
	//�ڶ����ֵ���ʼ����
	public static final int PART2_START;
	//�ڶ����ֵ�������
	public static final int PART2_LEN;
	//--�Ѿ����Ϊ�����ֽ��м��㣬������ʼ������Ҫ������������зָ�
	//++�������ļ���ȡ�����ò���
	static  {
		Properties pro = new Properties();

		try{
			pro.load(new FileInputStream("ch11.properties"));
	    } catch (Exception e)  {
	    	System.out.println("Fatal error: Can not open the ch11.properties file");
	    	System.exit(3);
	    }
	    DEFAULT_SERVER = pro.getProperty("default_server", "localhost");
	    DEFAULT_PORT = pro.getProperty("default_port", "1099");
	    SERVICE_NAME = pro.getProperty("service_name", "MatrixServer");
	    ROWS_A = Integer.parseInt(pro.getProperty("matrix_A_rows", "4"));
	    COLUMNS_A = Integer.parseInt(pro.getProperty("matrix_A_cols", "4"));
	    ROWS_B = COLUMNS_A;
	    COLUMNS_B = Integer.parseInt(pro.getProperty("matrix_B_cols", "4"));
	    PART1_LEN = Integer.parseInt(pro.getProperty("part1_length", "2"));
	    PART2_START = PART1_LEN;
	    PART2_LEN = ROWS_A - PART2_START; 
	}
	//--�������ļ���ȡ�����ò���
}
