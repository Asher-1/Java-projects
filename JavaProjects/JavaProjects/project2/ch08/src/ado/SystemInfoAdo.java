package ado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datasource.*;

import iado.SystemInfoIAdo;

public class SystemInfoAdo implements SystemInfoIAdo {
	
	private Connection conn;
	
	public SystemInfoAdo() throws SQLException{
		conn = DBConnection.getConnection();
	}

	public String selectBulletin() throws SQLException{
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from SystemInfo");
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public int selectRowsPerPage() throws SQLException{
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from SystemInfo");
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return 10;
	}

	public void updateBulletin(String bulletin) throws SQLException{
		try {
			PreparedStatement pst = conn
					.prepareStatement("update SystemInfo set bulletin=?");
			pst.setString(1,bulletin);
		    pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void updateRowsPerPage(int rowsPerPage) throws SQLException{
		try {
			PreparedStatement pst = conn
					.prepareStatement("update SystemInfo set RowsPerPage=?");
			pst.setInt(1,rowsPerPage);
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

}
