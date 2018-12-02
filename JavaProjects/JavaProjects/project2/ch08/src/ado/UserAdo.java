package ado;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import beans.UserBean;

import pagination.Pagination;
import utils.MD5;

import datasource.*;
import iado.UserIAdo;

/**
 * @author lixiaoqing
 * 
 */
public class UserAdo extends Pagination implements UserIAdo {
	private Connection conn;

	private MD5 md5;

	public UserAdo() throws SQLException{
		//conn = DBConnection.getConnection();	
		conn = DBConnection.getConnection();
		md5 = new MD5();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see iado.UserIAdo#selectUserByID(int)
	 */
	public Collection selectUserByID(int userID) throws SQLException {
		Collection users = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Users where id=?");
			pst.setInt(1, userID);
			ResultSet rs = pst.executeQuery();
			users = packResultSet(rs);
			rs.close();
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see iado.UserIAdo#selectUserByName(java.lang.String)
	 */
	public boolean selectUserByName(String username) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Users where UserName=?");
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see iado.UserIAdo#selectUsers()
	 */
	public Collection selectUsers() throws SQLException {
		Collection users = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Users");
			ResultSet rs = pst.executeQuery();
			users = packResultSet(rs);
			rs.close();
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	public String selectUserName(int id)  throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("select UserName from Users where id=?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			rs.next();
			String name = rs.getString(1);
			rs.close();
			return name;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see iado.UserIAdo#addUser(java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void addUser(String userRealName, String userName, String password,
			String tel, String mobile, String email, String qq)
			throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into Users(UserRealName,UserName,Password,Tel,Mobile,Email,QQ) values (?,?,?,?,?,?,?)");
			pst.setString(1, userRealName);
			pst.setString(2, userName);
			pst.setString(3, md5.getMD5ofStr(password));
			pst.setString(4, tel);
			pst.setString(5, mobile);
			pst.setString(6, email);
			pst.setString(7, qq);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see iado.UserIAdo#updateUserBasicInfo(int, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void updateUserBasicInfo(int userID, String userRealName,
			String tel, String mobile, String email, String qq)
			throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("update Users set UserRealName=?,Tel=?,Mobile=?,Email=?,QQ=? where id=?");
			pst.setString(1, userRealName);
			pst.setString(2, tel);
			pst.setString(3, mobile);
			pst.setString(4, email);
			pst.setString(5, qq);
			pst.setInt(6, userID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see iado.UserIAdo#updateUserPassword(int, java.lang.String)
	 */
	public void updateUserPassword(int userID, String password)
			throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("update Users set Password=? where id=?");
			pst.setString(1, md5.getMD5ofStr(password));
			pst.setInt(2, userID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see iado.UserIAdo#deleteUserByID(int)
	 */
	public void deleteUserByID(int userID) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from Users where id=?");
			pst.setInt(1, userID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see iado.UserIAdo#doLogin(java.lang.String, java.lang.String)
	 */
	public int doLogin(String username, String password) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("select id from Users where UserName=? and Password=?");
			pst.setString(1, username);
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

	/*
	 * （非 Javadoc）
	 * 
	 * @see pagination.Pagination#packResultSet(java.sql.ResultSet)
	 */
	@Override
	protected Collection packResultSet(ResultSet rs) throws SQLException {
		Collection users = new ArrayList();

		while (rs.next()) {
			UserBean user = new UserBean(rs.getInt(1), rs.getString(2), 
					rs.getString(3), rs.getString(4),rs.getString(5), rs
					.getString(6), rs.getString(7), rs.getString(8));
			users.add(user);
		}

		return users;
	}
	
	public void close() throws SQLException{
		if(conn!=null)
			conn.close();
	}
	
	public static void main(String[] args)  throws SQLException{
		UserAdo user = new UserAdo();
		System.out.print(user.doLogin("brighteyes","19831128"));
		//System.out.println(new MD5().getMD5ofStr("19831128"));
		user.close();
	}
}
