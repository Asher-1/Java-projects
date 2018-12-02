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
	 * @param categoryID 种类id
	 * @param categoryName 种类名
	 * @param description  描述信息
	 */
	public CategoryBean(int categoryID, String categoryName, String description) {
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.description = description;
	}
	/**
	 * @return 返回 categoryID。
	 */
	public int getCategoryID() {
		return categoryID;
	}
	/**
	 * @param categoryID 要设置的 categoryID。
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	/**
	 * @return 返回 categoryName。
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName 要设置的 categoryName。
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return 返回 description。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description 要设置的 description。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
