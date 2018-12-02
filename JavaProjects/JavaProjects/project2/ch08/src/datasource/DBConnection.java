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
			//第一步：加载驱动器
			Class.forName(Constants.driver);
		} catch(ClassNotFoundException e) {
			System.err.print("ClassNotFoundException");
		}
		
		try {
            //第二步：调用DriverManager.getConnection静态方法得到数据库连接
			conn = DriverManager.getConnection(Constants.url, Constants.username, Constants.password);
			return conn;
			
		} catch(SQLException e) {
			System.err.println("Insert SQLException");
			return null;
		} 
	}
}
