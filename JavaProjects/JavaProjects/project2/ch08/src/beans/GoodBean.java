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
	 * @param goodID ��Ʒid
	 * @param categoryID ��������id
	 * @param goodName ��Ʒ��
	 * @param goodPrice ��Ʒ�۸�
	 * @param userID �����û�id
	 * @param datetime �ϴ�ʱ��
	 * @param description ������Ϣ
	 * @param picID ͼƬ��
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
	 * @return ���� datetime��
	 */
	public Date getDatetime() {
		return datetime;
	}
	/**
	 * @param datetime Ҫ���õ� datetime��
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
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
	/**
	 * @return ���� goodID��
	 */
	public int getGoodID() {
		return goodID;
	}
	/**
	 * @param goodID Ҫ���õ� goodID��
	 */
	public void setGoodID(int goodID) {
		this.goodID = goodID;
	}
	/**
	 * @return ���� goodName��
	 */
	public String getGoodName() {
		return goodName;
	}
	/**
	 * @param goodName Ҫ���õ� goodName��
	 */
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	/**
	 * @return ���� goodPrice��
	 */
	public double getGoodPrice() {
		return goodPrice;
	}
	/**
	 * @param goodPrice Ҫ���õ� goodPrice��
	 */
	public void setGoodPrice(double goodPrice) {
		this.goodPrice = goodPrice;
	}
	/**
	 * @return ���� userID��
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID Ҫ���õ� userID��
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/**
	 * @return ���� picID��
	 */
	public int getPicID() {
		return picID;
	}
	/**
	 * @param picID Ҫ���õ� picID��
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
