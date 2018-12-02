package iado;

import java.sql.SQLException;
import java.util.Collection;


/**
 * @author lixiaoqing
 *
 */
public interface CategoryIAdo {
	/**
	 * @param id �����
	 * @return ��ѯ���
	 */
	public Collection selectCategoryByID(int id) throws SQLException;
	
	/**
	 * @return ��������
	 */
	public Collection selectCategories() throws SQLException;
	
	/**
	 * @param categoryName ������
	 * @param description ������Ϣ
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
