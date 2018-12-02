package iado;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lixiaoqing
 *
 */
public interface PicIAdo {
	/**
	 * @param picID
	 * @return
	 */
	public String selectPicNameByID(int picID) throws SQLException;
	
	
	/**
	 * @return
	 */
	public ResultSet selectPics() throws SQLException;
	
	/**
	 * @param goodID
	 * @param picName
	 */
	public int addPic(String picName) throws SQLException;
	
	/**
	 * @param picID
	 */
	public void deletePicByID(int picID) throws SQLException;
	/**
	 * @throws SQLException
	 */
	public void close() throws SQLException;

}
