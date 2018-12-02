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
	 * @param adminID ����Աid
	 * @param adminName ����Ա��¼��
	 * @param password ����
	 */
	public AdminBean(int adminID, String adminName, String password) {
		this.adminID = adminID;
		this.adminName = adminName;
		this.password = password;
	}
	/**
	 * @return ���� adminID��
	 */
	public int getAdminID() {
		return adminID;
	}
	/**
	 * @param adminID Ҫ���õ� adminID��
	 */
	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}
	/**
	 * @return ���� adminName��
	 */
	public String getAdminName() {
		return adminName;
	}
	/**
	 * @param adminName Ҫ���õ� adminName��
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
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
	
	

}
