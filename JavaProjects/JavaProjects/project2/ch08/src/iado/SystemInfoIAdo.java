package iado;

import java.sql.SQLException;

/**
 * @author lixiaoqing
 *
 */
public interface SystemInfoIAdo {
	/**
	 * @return
	 */
	public String selectBulletin() throws SQLException;
	
	/**
	 * @return
	 */
	public int selectRowsPerPage() throws SQLException;
	
	/**
	 * @param bulletin
	 */
	public void updateBulletin(String bulletin) throws SQLException;
	
	/**
	 * @param rowsPerPage
	 */
	public void updateRowsPerPage(int rowsPerPage) throws SQLException;
	
	/**
	 * @throws SQLException
	 */
	public void close() throws SQLException;

}
