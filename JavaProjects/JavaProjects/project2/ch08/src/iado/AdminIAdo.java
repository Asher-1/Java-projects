package iado;

import java.sql.SQLException;

/**
 * @author lixiaoqing
 *
 */
public interface AdminIAdo {
	/**
	 * @param adminID
	 * @param password
	 */
	public void updatePassword(int adminID,String password) throws SQLException;
	
	/**
	 * @param adminName
	 * @param password
	 * @return AdminID
	 */
	public int doLogin(String adminName,String password) throws SQLException;
	
	/**
	 * @throws SQLException
	 */
	public void close() throws SQLException;

}
