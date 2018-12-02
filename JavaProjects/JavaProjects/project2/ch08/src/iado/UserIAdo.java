package iado;

import java.sql.SQLException;
import java.util.Collection;

/**
 * @author lixiaoqing
 *
 */
public interface UserIAdo {
	/**
	 * @param userID
	 * @return
	 */
	public Collection selectUserByID(int userID) throws SQLException;
	
	/**
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public boolean selectUserByName(String username) throws SQLException;
	
	/**
	 * @return
	 */
	public Collection selectUsers() throws SQLException;
	
	/**
	 * @param userRealName
	 * @param userName
	 * @param password
	 * @param tel
	 * @param mobile
	 * @param email
	 * @param qq
	 */
	public void addUser(String userRealName,String userName,String password,String tel,String mobile,String email,String qq) throws SQLException;

	/**
	 * @param userRealName
	 * @param tel
	 * @param mobile
	 * @param email
	 * @param qq
	 */
	public void updateUserBasicInfo(int userID,String userRealName,String tel,String mobile,String email,String qq) throws SQLException;

	/**
	 * @param userID
	 * @param password
	 */
	public void updateUserPassword(int userID,String password) throws SQLException;
	
	/**
	 * @param userID
	 */
	public void deleteUserByID(int userID) throws SQLException;
	
	/**
	 * @param username
	 * @param password
	 * @return UserID
	 */
	public int doLogin(String username,String password) throws SQLException;
	
	/**
	 * @throws SQLException
	 */
	public void close() throws SQLException;
}
