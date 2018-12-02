/**
 * 
 */
package cn.detector.core;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 文件过滤类
 *用于过滤图片资源
 */
public class FileAcceptFilter implements FilenameFilter {
	private String extendName;
	private String name;
	public void setExtendName(String s){
		extendName = "." + s;
	}
	/* （非 Javadoc）
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String picName) {
		//将文件名转换成小写格式，排除大写的干扰
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
