package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author lixiaoqing
 *
 */
public class DBConnection{
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			//��һ��������������
			Class.forName(Constants.driver);
		} catch(ClassNotFoundException e) {
			System.err.print("ClassNotFoundException");
		}
		
		try {
            //�ڶ���������DriverManager.getConnection��̬�����õ����ݿ�����
			conn = DriverManager.getConnection(Constants.url, Constants.username, Constants.password);
			return conn;
			
		} catch(SQLException e) {
			System.err.println("Insert SQLException");
			return null;
		} 
	}
}
