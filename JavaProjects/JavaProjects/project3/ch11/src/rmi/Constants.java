//Constants.java
package rmi;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 为了程序维护方便，把一些常量定义在该类中，
 * 该类从一个属性文件中读取用户设置的参数。
 */
public class Constants {
	//++缺省的服务器名称和端口，服务名
	public static final String DEFAULT_SERVER;
	public static final String DEFAULT_PORT;
	public static final String SERVICE_NAME;
	//--缺省的服务器名称和端口，服务名
	//++定义A、B矩阵的行列数
	public static final int ROWS_A;
	public static final int COLUMNS_A;
	//B的行数要与A的列数相等
	public static final int ROWS_B;
	public static final int COLUMNS_B;
	//--定义A、B矩阵的行列数
	//++把矩阵分为两部分进行计算，进行起始行数和要计算的行数进行分割
	//第一部分的起始行数
	public static final int PART1_START = 0;
	//第一部分的行数
	public static final int PART1_LEN;
	//第二部分的起始行数
	public static final int PART2_START;
	//第二部分的总行数
	public static final int PART2_LEN;
	//--把矩阵分为两部分进行计算，进行起始行数和要计算的行数进行分割
	//++从属性文件中取得配置参数
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
	//--从属性文件中取得配置参数
}
