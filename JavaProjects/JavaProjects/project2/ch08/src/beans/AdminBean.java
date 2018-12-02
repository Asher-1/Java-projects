package beans;

/**
 * @author lixiaoqing
 *
 */
public class AdminBean {
	private int adminID;
	private String adminName;
	private String password;
	/**
	 * @param adminID 管理员id
	 * @param adminName 管理员登录名
	 * @param password 密码
	 */
	public AdminBean(int adminID, String adminName, String password) {
		this.adminID = adminID;
		this.adminName = adminName;
		this.password = password;
	}
	/**
	 * @return 返回 adminID。
	 */
	public int getAdminID() {
		return adminID;
	}
	/**
	 * @param adminID 要设置的 adminID。
	 */
	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}
	/**
	 * @return 返回 adminName。
	 */
	public String getAdminName() {
		return adminName;
	}
	/**
	 * @param adminName 要设置的 adminName。
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
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
	
	

}
