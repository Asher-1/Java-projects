package ado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import datasource.DBConnection;

import beans.GoodBean;
import beans.MessageBean;

import iado.IMyMessage;

public class MyMessageAdo implements IMyMessage {
private Connection conn;
	
	public MyMessageAdo() throws SQLException{
		conn = DBConnection.getConnection();
	}

	public Collection selectMsgByUid(int uid) throws SQLException {
		Collection goods = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Message where UserID=?");
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

	public void deleteMsgByID(int id) throws SQLException {
		try {
			//conn.setAutoCommit(false);
		/*	ResultSet rs = this.selectGoodByID(goodID);
			int picID=0;
			if(rs.next())
				picID = rs.getInt(8);
			System.out.print("picID is "+picID);
		*/	
			PreparedStatement pst = conn
					.prepareStatement("delete from Message where id=?");
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
					.prepareStatement("delete from Message where UserID=?");
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

		ArrayList msgs = new ArrayList();
		while (rs.next()) {
			MessageBean msg = new MessageBean(rs.getInt(1),rs.getString(2),rs.getString(3),
					rs.getDate(4),rs.getInt(5));
			msgs.add(msg);
		}

		return msgs;

	}
	
	public void close() throws SQLException {
		if (conn != null)
			conn.close();
	}


}
