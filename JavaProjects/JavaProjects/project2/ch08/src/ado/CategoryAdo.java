package ado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import utils.ChineseUtils;
import beans.CategoryBean;

import datasource.DBConnection;

import iado.CategoryIAdo;

public class CategoryAdo implements CategoryIAdo {
	
	private Connection conn;
	
	public CategoryAdo() throws SQLException{
		conn = DBConnection.getConnection();
	}

	public Collection selectCategoryByID(int id) throws SQLException {
		Collection categories = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Category where id=?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			categories = packResultSet(rs);
			rs.close();
			return categories;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Collection selectCategories() throws SQLException {
		Collection categories = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Category");
			ResultSet rs = pst.executeQuery();
			categories = packResultSet(rs);
			rs.close();
			return categories;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String selectCategoryName(int id)  throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("select CategoryName from Category where id=?");
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

	public void addCategory(String categoryName, String description)
			throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into Category(CategoryName,Description) values(?,?)");
			pst.setString(1,categoryName);
			pst.setString(2,description);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public void deleteCategoryByID(int categoryID) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from Category where id=?");
			pst.setInt(1,categoryID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	public void close() throws SQLException{
		if(conn!=null)
			conn.close();
	}
	
	public Collection packResultSet(ResultSet rs) throws SQLException{
		
		ArrayList categories = new ArrayList();
		while(rs.next()){
			CategoryBean category = new CategoryBean(rs.getInt(1),rs.getString(2),rs.getString(3));
			categories.add(category);
		}
		
		return categories;
		
	}
	
	public static void main(String[] args) throws Exception{
		CategoryAdo ca = new CategoryAdo();
		ca.addCategory("文具","笔，本子等");
		//ca.deleteCategoryByID(2);
		ca.close();
	}

}
