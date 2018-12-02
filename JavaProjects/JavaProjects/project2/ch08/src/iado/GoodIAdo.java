package iado;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;


/**
 * @author lixiaoqing
 *
 */
public interface GoodIAdo {
	/**
	 * @param goodID
	 * @return
	 */
	public Collection selectGoodByID(int goodID) throws SQLException;
	
	/**
	 * @return
	 */
	public Collection selectGoods() throws SQLException;
	
	/**
	 * @param categoryID
	 * @param goodName
	 * @param goodPrice
	 * @param userID
	 * @param datetime
	 * @param description
	 * @param picID
	 * @return
	 * @throws SQLException
	 */
	public void addGood(int categoryID,String goodName,double goodPrice,int userID,Date datetime,String description,int picID) throws SQLException;

	/**
	 * @param goodID
	 * @param goodName
	 * @param goodPrice
	 * @param description
	 */
	public void updateGoodBasicInfo(int goodID,String goodName,double goodPrice,String description) throws SQLException;

	/**
	 * @param goodID
	 * @param category
	 */
	public void updateGoodCategory(int goodID,int categoryID) throws SQLException;
	
	/**
	 * @param goodID
	 */
	public void deleteGoodByID(int goodID) throws SQLException;
	
	/**
	 * @throws SQLException
	 */
	public void close() throws SQLException;
}
