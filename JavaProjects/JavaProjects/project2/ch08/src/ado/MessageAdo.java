package ado;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import beans.MessageBean;
import pagination.Pagination;
import datasource.DBConnection;
import iado.MessageIAdo;

public class MessageAdo extends Pagination implements MessageIAdo {
	
	private Connection conn;
	
	public MessageAdo() throws SQLException{
		conn = DBConnection.getConnection();
	}

	public Collection selectMessageByID(int messageID) throws SQLException {
		Collection messages = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Message where id=?");
			pst.setInt(1, messageID);
			ResultSet rs = pst.executeQuery();
			messages = packResultSet(rs);
			rs.close();
			return messages;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Collection selectMessages() throws SQLException {
		Collection messages = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Message");
			ResultSet rs = pst.executeQuery();
			messages = packResultSet(rs);
			rs.close();
			return messages;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void addMessage(String title, String content, Date datetime,
			int userID) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into Message(Title,Content,DateTime,UserID) values(?,?,?,?)");
			pst.setString(1,title);
			pst.setString(2,content);
			pst.setDate(3,datetime);
			pst.setInt(4,userID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	public void addMessage( String content, Date datetime,
			int userID,int goodID) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into Message(Content,DateTime,UserID,GoodID) values(?,?,?,?)");
			pst.setString(1,content);
			pst.setDate(2,datetime);
			pst.setInt(3,userID);
			pst.setInt(4,goodID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public void deleteMessageByID(int messageID) throws SQLException {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from Message where id=?");
			pst.setInt(1, messageID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	protected Collection packResultSet(ResultSet rs) throws SQLException {
		Collection messages = new ArrayList();

		while (rs.next()) {
			MessageBean message = new MessageBean(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getInt(5));
			messages.add(message);
		}

		return messages;
	}
	
	public void close() throws SQLException{
		if(conn!=null)
			conn.close();
	}
	
	public static void main(String[] args) throws Exception{
		MessageAdo mado = new MessageAdo();
		//mado.addMessage("«Ûπ∫2","À≠”–jsp ÈºÆ∞°",new Date(System.currentTimeMillis()),2);
		mado.setRowsPerPage(2);
		mado.setSQL("select * from Message");
		
		Collection c = mado.getPage(1);
		for(Object o : c){
			System.out.println(((MessageBean)o).getContent());
		}
			
	}

	public Collection selectMessageByGoodID(int goodID) throws SQLException {
		Collection messages = new ArrayList();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select * from Message where GoodID=?");
			pst.setInt(1, goodID);
			ResultSet rs = pst.executeQuery();
			messages = packResultSet(rs);
			rs.close();
			return messages;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
