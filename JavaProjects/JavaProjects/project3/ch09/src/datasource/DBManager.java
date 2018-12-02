package datasource;

import java.sql.*;

public class DBManager {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private boolean isLoad = false;
	private Connection conn;
	
	private static DBManager dbmanager = null;
	
	private DBManager(String path){
		if(!isLoad)
			load(path);
	}
	
	public void load(String path){
		PropertyUtil propUtil = PropertyUtil.getInstance(path);
		driver = propUtil.getProperty("driver");
		url = propUtil.getProperty("url");
		username = propUtil.getProperty("username");
		password = propUtil.getProperty("password");
		isLoad = true;
	}
	
	public static DBManager getInstance(String path){
		if(dbmanager==null)
			dbmanager = new DBManager(path);
		return dbmanager;
	}
	
	//这里只是方便测试，如果配置了数据源，可以使用数据源
	public Connection getConnection(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	public static void main(String args[])
	{
		DBManager dm = new DBManager("D:/workspace/ajax/src/config.properties");
		Connection con = dm.getConnection();
		System.out.println(con);
	}

}
