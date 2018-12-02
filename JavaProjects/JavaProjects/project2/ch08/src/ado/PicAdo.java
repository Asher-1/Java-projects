package ado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datasource.*;
import iado.PicIAdo;

public class PicAdo implements PicIAdo {
	
	private Connection conn;
	
	public PicAdo() throws SQLException{
		conn = DBConnection.getConnection();
	}

	public String selectPicNameByID(int picID) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("select PicName from Pic where id=?");
			pst.setInt(1, picID);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public ResultSet selectPics() throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Pic");
			ResultSet rs = pst.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int addPic(String picName) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into Pic(PicName) values(?)");
			pst.setString(1,picName);
			pst.executeUpdate();
			
			pst = conn.prepareStatement("select id from Pic where PicName=?");
			pst.setString(1,picName);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return 0;
	}

	public void deletePicByID(int picID) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from Pic where id=?");
			pst.setInt(1, picID);
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
	

	public static void main(String[] args) throws SQLException{
		PicAdo pado = new PicAdo();
		//System.out.println(pado.addPic("pic2.jpg"));
		/*String name = pado.selectPicNameByID(1);
		if(name!=null)
			System.out.print(name);
		else
			System.out.print("no pic");*/
		pado.deletePicByID(8);
		pado.deletePicByID(9);
		pado.close();
	}
	

}
