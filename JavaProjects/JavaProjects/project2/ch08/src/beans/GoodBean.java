package beans;

import java.sql.Date;

import ado.CategoryAdo;
import ado.UserAdo;



/**
 * @author lixiaoqing
 *
 */
public class GoodBean {
	private int goodID;
	private int categoryID;
	private String goodName;
	private double goodPrice;
	private int userID;
	private Date datetime;
	private String description;
	private int picID;
	private String categoryName;
	private String username;
	/**
	 * @param goodID 物品id
	 * @param categoryID 所属种类id
	 * @param goodName 物品名
	 * @param goodPrice 物品价格
	 * @param userID 所属用户id
	 * @param datetime 上传时间
	 * @param description 描述信息
	 * @param picID 图片号
	 */
	public GoodBean(int goodID, int categoryID, String goodName, double goodPrice, int userID, Date datetime, String description,int picID) {
		this.goodID = goodID;
		this.categoryID = categoryID;
		this.goodName = goodName;
		this.goodPrice = goodPrice;
		this.userID = userID;
		this.datetime = datetime;
		this.description = description;
		this.picID = picID;
		try{
			CategoryAdo cado = new CategoryAdo();
			this.categoryName = cado.selectCategoryName(categoryID);
			cado.close();
			
			UserAdo uado = new UserAdo();
			this.username = uado.selectUserName(userID);
			uado.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public GoodBean(int goodID, int categoryID, String goodName, double goodPrice, int userID, Date datetime, String description) {
		this.goodID = goodID;
		this.categoryID = categoryID;
		this.goodName = goodName;
		this.goodPrice = goodPrice;
		this.userID = userID;
		this.datetime = datetime;
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
	 * @return 返回 datetime。
	 */
	public Date getDatetime() {
		return datetime;
	}
	/**
	 * @param datetime 要设置的 datetime。
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
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
	/**
	 * @return 返回 goodID。
	 */
	public int getGoodID() {
		return goodID;
	}
	/**
	 * @param goodID 要设置的 goodID。
	 */
	public void setGoodID(int goodID) {
		this.goodID = goodID;
	}
	/**
	 * @return 返回 goodName。
	 */
	public String getGoodName() {
		return goodName;
	}
	/**
	 * @param goodName 要设置的 goodName。
	 */
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	/**
	 * @return 返回 goodPrice。
	 */
	public double getGoodPrice() {
		return goodPrice;
	}
	/**
	 * @param goodPrice 要设置的 goodPrice。
	 */
	public void setGoodPrice(double goodPrice) {
		this.goodPrice = goodPrice;
	}
	/**
	 * @return 返回 userID。
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID 要设置的 userID。
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/**
	 * @return 返回 picID。
	 */
	public int getPicID() {
		return picID;
	}
	/**
	 * @param picID 要设置的 picID。
	 */
	public void setPicID(int picID) {
		this.picID = picID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
