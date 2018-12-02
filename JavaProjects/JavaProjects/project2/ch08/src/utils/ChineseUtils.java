package utils;

/**
 * @author lixiaoqing
 *
 */
public class ChineseUtils {
	
	/**
	 * 从数据库读出数据时的编码转换
	 * @param chi 输入字符串
	 * @return 转换后的字符串
	 */
	public static String transToEn(String chi)
	{
		String result = null;
		byte temp[];
		try
		{
			temp = chi.getBytes("iso-8859-1");
			result = new String(temp);
		}
		catch(Exception e)
		{
			
		}
		return result;
	}
	
	public static String transToCh(String chi)
	{
		String result = null;
		byte temp[];
		try
		{
			temp = chi.getBytes("gb2312");
			result = new String(temp);
		}
		catch(Exception e)
		{
			
		}
		return result;
	}
}
