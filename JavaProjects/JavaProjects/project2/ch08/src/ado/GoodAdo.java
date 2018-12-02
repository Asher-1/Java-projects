package ado;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import beans.GoodBean;
import pagination.Pagination;

import datasource.DBConnection;

import iado.GoodIAdo;

public class GoodAdo extends Pagination implements GoodIAdo {
	
	private Connection conn;
	
	public GoodAdo() throws SQLException{
		conn = DBConnection.getConnection();
	}

	public Collection selectGoodByID(int goodID)  throws SQLException{
		Collection goods = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Good where id=?");
			pst.setInt(1, goodID);
			ResultSet rs = pst.executeQuery();
			goods = packResultSet(rs);
			rs.close();
			return goods;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Collection selectGoods()  throws SQLException{
		Collection goods = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Good");
			ResultSet rs = pst.executeQuery();
			goods = packResultSet(rs);
			rs.close();
			return goods;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void addGood(int categoryID, String goodName, double goodPrice,
			int userID, Date datetime, String description,int picID)  throws SQLException{
		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into Good(CategoryID,GoodName,GoodPrice,UserID,DateTime,Description,PicID) values (?,?,?,?,?,?,?)");
			pst.setInt(1,categoryID);
			pst.setString(2,goodName);
			pst.setDouble(3,goodPrice);
			pst.setInt(4,userID);
			pst.setDate(5,datetime);
			pst.setString(6,description);
			pst.setInt(7,picID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void updateGoodBasicInfo(int goodID, String goodName,
			double goodPrice, String description)  throws SQLException{
		try {
			PreparedStatement pst = conn
					.prepareStatement("update Good set GoodName=?,GoodPrice=?,Description=? where id=?");
			pst.setString(1,goodName);
			pst.setDouble(2,goodPrice);
			pst.setString(3,description);
			pst.setInt(4,goodID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public void updateGoodCategory(int goodID, int categoryID)  throws SQLException{
		try {
			PreparedStatement pst = conn
					.prepareStatement("update Good set CategoryID=? where id=?");
			pst.setInt(1,categoryID);
			pst.setInt(2,goodID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteGoodByID(int goodID)  throws SQLException{
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
			pst.setInt(1,goodID);
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

	public void close() throws SQLException {
		if(conn!=null)
			conn.close();
	}


	

	@Override
	protected Collection packResultSet(ResultSet rs) throws SQLException {
		Collection goods = new ArrayList();

		while (rs.next()) {
			GoodBean good = new GoodBean(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDouble(4),rs.getInt(5),rs.getDate(6),rs.getString(7),rs.getInt(8));
			goods.add(good);
		}

		return goods;
	}
	
	public static void main(String[] args) throws SQLException{
		GoodAdo ga = new GoodAdo();
		//PicAdo pa = new PicAdo();
		//int picID = pa.addPic("pic1.jpg");
		//pa.close();
		//ga.addGood(1,"jsp应用开发",15.00,2,new Date(System.currentTimeMillis()),"看完了，留着没有用，还不错哦！",picID);
		
		//ga.updateGoodBasicInfo(1,"j2ee开发",20,"这可是一本好书啊");
		//ga.updateGoodCategory(1,3);
		//ga.deleteGoodByID(2);
		ga.setRowsPerPage(4);
		ga.setSQL("select * from Good");
		Collection c = ga.getPage(1);
		for(Object o : c){
			GoodBean good = (GoodBean)o;
			System.out.println(good.getGoodName()+" "+good.getDatetime()+" "+good.getDescription());
		}
		
	//	ga.deleteGoodByID(3);
		//ga.deleteGoodByID(11);
		ga.close();
	}
}
