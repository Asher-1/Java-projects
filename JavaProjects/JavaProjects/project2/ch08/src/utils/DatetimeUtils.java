package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lixiaoqing
 *
 */
public class DatetimeUtils {
	 public static String getDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date Now = new Date();
		String NDate = formatter.format(Now);
	 	return NDate;
	 }
	 
	 public static String getDate(){
		 return getDateTime().substring(0,10);
	 }
	 
	 public static String getDateTimeByDate(Date date){
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = date;
			String NDate = formatter.format(now);
		 	return NDate;
	 }
	 
	 public static String getDateByDate(Date date){
		 return getDateTimeByDate(date).substring(0,10);
	 }
	 
}
