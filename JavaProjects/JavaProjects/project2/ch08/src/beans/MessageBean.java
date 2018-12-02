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
	 * @param messageID ����id
	 * @param title ���Ա���
	 * @param content ��������
	 * @param datetime ����ʱ��
	 * @param userID �������Ե��û�id
	 */
	public MessageBean(int messageID, String title, String content, Date datetime, int userID) {
		this.messageID = messageID;
		this.title = title;
		this.content = content;
		this.datetime = datetime;
		this.userID = userID;
	}
	/**
	 * @return ���� content��
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content Ҫ���õ� content��
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return ���� messageID��
	 */
	public int getMessageID() {
		return messageID;
	}
	/**
	 * @param messageID Ҫ���õ� messageID��
	 */
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	/**
	 * @return ���� title��
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title Ҫ���õ� title��
	 */
	public void setTitle(String title) {
		this.title = title;
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
	
	
	

}
