package beans;

/**
 * @author lixiaoqing
 *
 */
public class UserBean {
	private int userID;
	private String userRealName;
	private String userName;
	private String password;
	private String tel;
	private String mobile;
	private String email;
	private String qq;
	/**
	 * 
	 */
	public UserBean() {
	}
	/**
	 * @param userID �û�id
	 * @param userRealName �û���ʵ����
	 * @param userName �û���¼��
	 * @param password �û�����
	 * @param tel �û��绰
	 * @param mobile �û��ֻ�
	 * @param email �û�email
	 * @param qq �û�oicq
	 */
	public UserBean(int userID, String userRealName, String userName, String password, String tel, String mobile, String email, String qq) {
		this.userID = userID;
		this.userRealName = userRealName;
		this.userName = userName;
		this.password = password;
		this.tel = tel;
		this.mobile = mobile;
		this.email = email;
		this.qq = qq;
	}
	/**
	 * @return ���� email��
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email Ҫ���õ� email��
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return ���� mobile��
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile Ҫ���õ� mobile��
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return ���� password��
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password Ҫ���õ� password��
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return ���� qq��
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq Ҫ���õ� qq��
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @return ���� tel��
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel Ҫ���õ� tel��
	 */
	public void setTel(String tel) {
		this.tel = tel;
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
	 * @return ���� userName��
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName Ҫ���õ� userName��
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return ���� userRealName��
	 */
	public String getUserRealName() {
		return userRealName;
	}
	/**
	 * @param userRealName Ҫ���õ� userRealName��
	 */
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

}
