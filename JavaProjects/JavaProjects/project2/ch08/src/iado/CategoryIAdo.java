package iado;

import java.sql.SQLException;
import java.util.Collection;


/**
 * @author lixiaoqing
 *
 */
public interface CategoryIAdo {
	/**
	 * @param id 种类号
	 * @return 查询结果
	 */
	public Collection selectCategoryByID(int id) throws SQLException;
	
	/**
	 * @return 所有种类
	 */
	public Collection selectCategories() throws SQLException;
	
	/**
	 * @param categoryName 种类名
	 * @param description 描述信息
	 */
	public void addCategory(String categoryName,String description) throws SQLException;
	
	/**
	 * @param categoryID
	 */
	public void deleteCategoryByID(int categoryID) throws SQLException;
	
	/**
	 * @throws SQLException
	 */
	public void close() throws SQLException;

}
