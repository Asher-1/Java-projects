package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.XML;
import bean.SearchResult;
import bean.SearchStatus;
import bean.TaskBean;

import datasource.DBManager;

public class Ajax3Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public static String getchapternamebychapterid(Connection conn,String cid)
	{
		Statement stmt = null;
		ResultSet rs = null;
		String chaptername = "unknow chapter";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select chaptername from Chapter where chapterid ="+cid);
			if(rs.next())
				chaptername = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return chaptername;
	}
	public static String getnodenamebynodeid(Connection conn,String nid)
	{
		Statement stmt = null;
		ResultSet rs = null;
		String nodename = "unknow node";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select nodename from Node where nodeid ="+nid);
			if(rs.next())
				nodename = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nodename;
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");  
		String configpath= request.getSession().getServletContext().getRealPath("/WEB-INF/classes/config.properties");
		DBManager dbmanager = DBManager.getInstance(configpath);
		Connection conn = dbmanager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String keywords = request.getParameter("keywords"); //获取页面提交过来的搜索关键字
		keywords = new String(keywords.getBytes("iso-8859-1"),"UTF-8");   //按照UTF-8编码，避免乱码
		ArrayList<TaskBean> taskList = new ArrayList<TaskBean>();
		try
		{
			ServletOutputStream out = response.getOutputStream();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Task where taskname like '%"+keywords+"%'");
			while(rs.next()){
				TaskBean tb = new TaskBean();  //每条作业记录存入到一个TaskBean中
				tb.setTaskid(rs.getString(1));
				tb.setTaskname(rs.getString(2));
				String cid = rs.getString(3);
				tb.setChapterid(cid);
				String nid = rs.getString(4);
				tb.setNodeid(nid);
				tb.setCreatetime(rs.getString(5));
				tb.setEndtime(rs.getString(6));
				tb.setType(rs.getString(7));
				tb.setPubanswer(rs.getString(8));
				tb.setChaptername(getchapternamebychapterid(conn, cid));
				tb.setNodename(getnodenamebynodeid(conn, nid));
				taskList.add(tb);  //查询所有的作业存入到taskList中
			}
			SearchResult<TaskBean> searchResult = new SearchResult<TaskBean>();
			searchResult.setRecord(taskList);
	        searchResult.setStatus(SearchStatus.ERROR.ordinal());
	        //SearchResult必须按照一定的规定定义才能被转化为JSONObject对象，一般包括一个ArrayList类型属性和一个SearchStatus类型属性
	        //ArrayList中的每个JavaBean将会被转化为xml中的一个节点
			JSONObject jsonobj = JSONObject.fromBean(searchResult);
			//使用JSONObject对象的formbean方法可以把一个对象转化为JSONObject对象
			String xmldoc = XML.toString(jsonobj, "records");
			//将JSONObject对象转化为xml字符串	
			out.print(xmldoc);
			//将xmldoc的内容输出到out中，作为ajax对象返回的内容，可以被ajax页面获取这部分内容
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request,response);
	}

}
