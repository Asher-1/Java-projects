package utils;

/**
 * @author lixiaoqing
 *
 */
public class ChineseUtils {
	
	/**
	 * �����ݿ��������ʱ�ı���ת��
	 * @param chi �����ַ���
	 * @return ת������ַ���
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
