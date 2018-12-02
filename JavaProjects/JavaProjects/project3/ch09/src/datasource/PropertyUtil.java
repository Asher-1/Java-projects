package datasource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
	private static PropertyUtil propUtil = null;
	private Properties property;
	private FileInputStream input;
	
	private PropertyUtil(String path){
		try {
			input = new FileInputStream(path);
			property = new Properties();
			property.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				input.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public static PropertyUtil getInstance(String path){
		if(propUtil==null)
			propUtil = new PropertyUtil(path);
		return propUtil;
	}
	
	public String getProperty(String key){
		return property.getProperty(key).trim();
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/*PropertyUtil prp = PropertyUtil.getInstance("src/config.properties");
		System.out.print(prp.getProperty("password"));*/
		String path = "D:\\java\\home\\";
		String path2 = path.replace('\\','/');
		System.out.println(path2);
	}

}
