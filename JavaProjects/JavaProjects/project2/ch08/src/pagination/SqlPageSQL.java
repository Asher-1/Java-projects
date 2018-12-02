package pagination;

/**
 * @author lixiaoqing
 *
 */
public class SqlPageSQL {
	/**
	 *@param sql ԭʼsql���
	 *@param curPage �ڼ�ҳ
	 *@param rowsPerPage ÿҳ������ 
	 */
	public static String getPageSQL(String sql,int curPage,int rowsPerPage){
		String afterFrom = sql.toLowerCase().substring(sql.indexOf("from"));
		String pageSql = null;
		if(afterFrom.indexOf("where")==-1)
			 pageSql = "select top "+ rowsPerPage + " * "+afterFrom
			+" where id not in(select top "+rowsPerPage*(curPage-1)+" id "
			+afterFrom+" order by id desc)"+"order by id desc";
		else
			pageSql = "select top "+ rowsPerPage + " * "+afterFrom
			+" and id not in(select top "+rowsPerPage*(curPage-1)+" id "
			+afterFrom+" order by id desc)"+"order by id desc";
		
		return pageSql;
	}
}
