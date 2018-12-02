package beans;

/**
 * @author lixiaoqing
 *
 */
public class PicBean {
	private int picID;
	private String picName;
	/**
	 * @param picID 图片id
	 * @param goodID 所属物品id
	 * @param picName 图片文件名
	 */
	public PicBean(int picID, String picName) {
		this.picID = picID;
		this.picName = picName;
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
	/**
	 * @return 返回 picName。
	 */
	public String getPicName() {
		return picName;
	}
	/**
	 * @param picName 要设置的 picName。
	 */
	public void setPicName(String picName) {
		this.picName = picName;
	}
	
	

}
