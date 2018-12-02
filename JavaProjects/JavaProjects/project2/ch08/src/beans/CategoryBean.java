package beans;

/**
 * @author lixiaoqing
 *
 */
public class CategoryBean {
	private int categoryID;
	private String categoryName;
	private String description;
	
	/**
	 * @param categoryID ����id
	 * @param categoryName ������
	 * @param description  ������Ϣ
	 */
	public CategoryBean(int categoryID, String categoryName, String description) {
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.description = description;
	}
	/**
	 * @return ���� categoryID��
	 */
	public int getCategoryID() {
		return categoryID;
	}
	/**
	 * @param categoryID Ҫ���õ� categoryID��
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	/**
	 * @return ���� categoryName��
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName Ҫ���õ� categoryName��
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return ���� description��
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description Ҫ���õ� description��
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
