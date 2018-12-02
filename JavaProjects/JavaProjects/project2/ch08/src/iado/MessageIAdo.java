package iado;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author lixiaoqing
 *
 */
public interface MessageIAdo {
	/**
	 * @param messageID
	 * @return
	 */
	public Collection selectMessageByID(int messageID) throws SQLException;
	
	public Collection selectMessageByGoodID(int goodID) throws SQLException;
	
	/**
	 * @return
	 */
	public Collection selectMessages() throws SQLException;
	
	/**
	 * @param title
	 * @param content
	 * @param datetime
	 * @param userID
	 */
	public void addMessage(String title,String content,Date datetime,int userID) throws SQLException;
	
	/**
	 * @param messageID
	 */
	public void deleteMessageByID(int messageID) throws SQLException;
	
	/**
	 * @throws SQLException
	 */
	public void close() throws SQLException;

}
