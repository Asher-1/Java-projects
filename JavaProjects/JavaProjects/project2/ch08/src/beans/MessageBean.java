package beans;

import java.sql.Date;


/**
 * @author lixiaoqing
 *
 */
public class MessageBean {
	private int messageID;
	private String title;
	private String content;
	private Date datetime;
	private int userID;
	/**
	 * @param messageID 留言id
	 * @param title 留言标题
	 * @param content 留言内容
	 * @param datetime 发布时间
	 * @param userID 发布留言的用户id
	 */
	public MessageBean(int messageID, String title, String content, Date datetime, int userID) {
		this.messageID = messageID;
		this.title = title;
		this.content = content;
		this.datetime = datetime;
		this.userID = userID;
	}
	/**
	 * @return 返回 content。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content 要设置的 content。
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return 返回 messageID。
	 */
	public int getMessageID() {
		return messageID;
	}
	/**
	 * @param messageID 要设置的 messageID。
	 */
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	/**
	 * @return 返回 title。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title 要设置的 title。
	 */
	public void setTitle(String title) {
		this.title = title;
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
	
	
	

}
