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
	private int rowsPerPage; // ÿҳ��ʾ������
	private int rowsCount;   // ������
	private int pagesCount;  // ��ҳ��

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	// ������SQL���ʱ��������������ҳ����
	// ����������ֻҪ��ѯһ�Σ��������Ч�ʣ�
	public void setSQL(String sql) throws SQLException {
		this.sql = sql;
		this.rowsCount = 0;
		this.pagesCount = 0;

		// ��ȡ��������������ҳ��
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

		// ����ҳ�ż�����ʼ��
		int rows = this.getRowsPerPage();

		// ��SQL���ת��Ϊ�ض����ݿ�Ķ�λ�м�SQL���
		String pageSql = SqlPageSQL.getPageSQL(this.sql, page, rows);
		ResultSet rs = stmt.executeQuery(pageSql);

		// ���������װΪ���󼯺�
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

	// ������ҳ��
	private int countPages() {
		if ((rowsCount % rowsPerPage) == 0) {
			return rowsCount / rowsPerPage;
		} else {
			return (rowsCount / rowsPerPage + 1);
		}
	}

    // �������н��������װΪ���󼯺�
	protected abstract Collection packResultSet(ResultSet rs)
		throws SQLException;

}
