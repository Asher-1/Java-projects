/**
 * 
 */
package cn.detector.core;

import java.io.File;
import java.io.FilenameFilter;

/**
 * �ļ�������
 *���ڹ���ͼƬ��Դ
 */
public class FileAcceptFilter implements FilenameFilter {
	private String extendName;
	private String name;
	public void setExtendName(String s){
		extendName = "." + s;
	}
	/* ���� Javadoc��
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String picName) {
		//���ļ���ת����Сд��ʽ���ų���д�ĸ���
		name = picName.toLowerCase();
		if(name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(extendName) 
				|| name.endsWith(".bmp") || name.endsWith(".gif") || name.endsWith(".jpeg")){
			
			return true;
		}
		else{
			return false;
		}
	}
}
