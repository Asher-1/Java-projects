package beans;

/**
 * @author lixiaoqing
 *
 */
public class PicBean {
	private int picID;
	private String picName;
	/**
	 * @param picID ͼƬid
	 * @param goodID ������Ʒid
	 * @param picName ͼƬ�ļ���
	 */
	public PicBean(int picID, String picName) {
		this.picID = picID;
		this.picName = picName;
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
	/**
	 * @return ���� picName��
	 */
	public String getPicName() {
		return picName;
	}
	/**
	 * @param picName Ҫ���õ� picName��
	 */
	public void setPicName(String picName) {
		this.picName = picName;
	}
	
	

}
