package pagination;

import java.sql.*;
import java.util.*;
import datasource.*;

/**
 * @author lixiaoqing
 *
 */
public abstract class Pagination {
	private String sql;
	private int rowsPerPage; // 每页显示的行数
	private int rowsCount;   // 总行数
	private int pagesCount;  // 总页数

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	// 在设置SQL语句时计算总行数和总页数，
	// 这样总行数只要查询一次，可以提高效率！
	public void setSQL(String sql) throws SQLException {
		this.sql = sql;
		this.rowsCount = 0;
		this.pagesCount = 0;

		// 获取总行数并计算总页数
		this.rowsCount = countRows();
		this.pagesCount = countPages();
	}

	public String getSQL() {
		return sql;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public int getPagesCount() {
		return pagesCount;
	}

	public Collection getPage(int page) throws SQLException {
		Collection result = new ArrayList();

		Connection conn = DBConnection.getConnection();
		Statement stmt = conn.createStatement();

		// 根据页号计算起始行
		int rows = this.getRowsPerPage();

		// 将SQL语句转换为特定数据库的定位行集SQL语句
		String pageSql = SqlPageSQL.getPageSQL(this.sql, page, rows);
		ResultSet rs = stmt.executeQuery(pageSql);

		// 将结果集包装为对象集合
		result = packResultSet(rs);

		rs.close();
		stmt.close();
		conn.close();

		return result;
	}

	private int countRows() throws SQLException {
		String countSql = this.sql;
		countSql = countSql.toLowerCase();
		int fromPos = countSql.indexOf(" from ");
		countSql = countSql.substring(fromPos);
		countSql = "select count(*) " + countSql;

		Connection conn = DBConnection.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(countSql);

		rs.next();
		int count = rs.getInt(1);

		rs.close();
		stmt.close();
		conn.close();

		return count;
	}

	// 计算总页数
	private int countPages() {
		if ((rowsCount % rowsPerPage) == 0) {
			return rowsCount / rowsPerPage;
		} else {
			return (rowsCount / rowsPerPage + 1);
		}
	}

    // 在子类中将结果集包装为对象集合
	protected abstract Collection packResultSet(ResultSet rs)
		throws SQLException;

}
