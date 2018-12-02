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
	 * @param userID 用户id
	 * @param userRealName 用户真实姓名
	 * @param userName 用户登录名
	 * @param password 用户密码
	 * @param tel 用户电话
	 * @param mobile 用户手机
	 * @param email 用户email
	 * @param qq 用户oicq
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
	 * @return 返回 email。
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email 要设置的 email。
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return 返回 mobile。
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile 要设置的 mobile。
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return 返回 password。
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password 要设置的 password。
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return 返回 qq。
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq 要设置的 qq。
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @return 返回 tel。
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel 要设置的 tel。
	 */
	public void setTel(String tel) {
		this.tel = tel;
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
	 * @return 返回 userName。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName 要设置的 userName。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return 返回 userRealName。
	 */
	public String getUserRealName() {
		return userRealName;
	}
	/**
	 * @param userRealName 要设置的 userRealName。
	 */
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

}
