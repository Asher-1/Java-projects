package ado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.MD5;

import datasource.*;

import iado.AdminIAdo;

public class AdminAdo implements AdminIAdo {
	
	private Connection conn;
	private MD5 md5;
	
	public AdminAdo() throws SQLException{
		conn = DBConnection.getConnection();
		md5 = new MD5();
	}

	public void updatePassword(int adminID, String password)
			throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("update Admin set Password=? where id=?");
			pst.setString(1,md5.getMD5ofStr(password));
			pst.setInt(2,adminID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public int doLogin(String adminName, String password) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("select id from Admin where AdminName=? and Password=?");
			pst.setString(1, adminName);
			pst.setString(2, md5.getMD5ofStr(password));
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				int uid = rs.getInt(1);
				rs.close();
				return uid;
			} else {
				rs.close();
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void close() throws SQLException {
		if(conn!=null)
			conn.close();
	}
	

	
	public static void main(String[] args) throws SQLException{
		AdminAdo ao = new AdminAdo();
		System.out.println(ao.doLogin("admin","admin"));
	}

}
