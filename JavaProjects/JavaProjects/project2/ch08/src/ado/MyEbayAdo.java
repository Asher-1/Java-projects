package ado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import beans.CategoryBean;
import beans.GoodBean;

import datasource.DBConnection;

import iado.IMyEbay;

public class MyEbayAdo implements IMyEbay {
	private Connection conn;
	
	public MyEbayAdo() throws SQLException{
		conn = DBConnection.getConnection();
	}

	public Collection selectGoodsByUid(int uid) throws SQLException {
		Collection goods = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Good where UserID=?");
			pst.setInt(1, uid);
			ResultSet rs = pst.executeQuery();
			goods = packResultSet(rs);
			rs.close();
			return goods;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteGoodByID(int id) throws SQLException {
		try {
			//conn.setAutoCommit(false);
		/*	ResultSet rs = this.selectGoodByID(goodID);
			int picID=0;
			if(rs.next())
				picID = rs.getInt(8);
			System.out.print("picID is "+picID);
		*/	
			PreparedStatement pst = conn
					.prepareStatement("delete from Good where id=?");
			pst.setInt(1,id);
			pst.executeUpdate();
			
			
		//	PicAdo pa = new PicAdo();
		//	pa.deletePicByID(picID);
			
			//conn.commit();
			//conn.setAutoCommit(true);
		
		} catch (SQLException e) {
			//conn.rollback();
			e.printStackTrace();
			throw e;
		}

	}

	public void deleteAllByUid(int uid) throws SQLException {
		try {
			//conn.setAutoCommit(false);
		/*	ResultSet rs = this.selectGoodByID(goodID);
			int picID=0;
			if(rs.next())
				picID = rs.getInt(8);
			System.out.print("picID is "+picID);
		*/	
			PreparedStatement pst = conn
					.prepareStatement("delete from Good where UserID=?");
			pst.setInt(1,uid);
			pst.executeUpdate();
			
			
		//	PicAdo pa = new PicAdo();
		//	pa.deletePicByID(picID);
			
			//conn.commit();
			//conn.setAutoCommit(true);
		
		} catch (SQLException e) {
			//conn.rollback();
			e.printStackTrace();
			throw e;
		}

	}
	

	public Collection packResultSet(ResultSet rs) throws SQLException {

		ArrayList goods = new ArrayList();
		while (rs.next()) {
			GoodBean good = new GoodBean(rs.getInt(1), rs.getInt(2), rs
					.getString(3), rs.getDouble(4), rs.getInt(5),
					rs.getDate(6), rs.getString(7),0);
			goods.add(good);
		}

		return goods;

	}

	public void close() throws SQLException {
		if (conn != null)
			conn.close();
	}

}


