package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import net.sf.json.JSONObject;
import net.sf.json.XML;


import bean.KnowledgeBean;
import bean.SearchResult;
import bean.SearchStatus;

import datasource.DBManager;

public class Ajax2Servlet extends HttpServlet {

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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String configpath= request.getSession().getServletContext().getRealPath("/WEB-INF/classes/config.properties");
		DBManager dbmanager = DBManager.getInstance(configpath);
		Connection conn = dbmanager.getConnection();  //获取数据库连接
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<KnowledgeBean> knowledgelist = new ArrayList<KnowledgeBean>();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Knowledge");
			while(rs.next()){
			KnowledgeBean kb = new KnowledgeBean();  //每条知识点记录用一个javabean实例存储
			kb.setKid(rs.getString(1));
			kb.setKname(rs.getString(2));
			kb.setTeachername(rs.getString(3));
			kb.setCreatetime(rs.getString(4));
			kb.setDescription(rs.getString(5));
			knowledgelist.add(kb);  
			//从数据库中查询所有知识点存放到knowledgelist中去
			}
			SearchResult<KnowledgeBean> searchResult = new SearchResult<KnowledgeBean>();
			searchResult.setRecord(knowledgelist);
	        searchResult.setStatus(SearchStatus.ERROR.ordinal());
	        //SearchResult必须按照一定的规定定义才能被转化为JSONObject对象，一般包括一个ArrayList类型属性和一个SearchStatus类型属性
	        //ArrayList中的每个JavaBean将会被转化为xml中的一个节点
			JSONObject jsonobj = JSONObject.fromBean(searchResult);
			//使用JSONObject对象的formbean方法可以把一个对象转化为JSONObject对象
			String xmldoc = XML.toString(jsonobj, "records");
			//将JSONObject对象转化为xml字符串
			session.setAttribute("xmldoc", xmldoc);
			//将转化的xml字符串对象存入到session中
			response.sendRedirect("/ajax/ajax2.jsp");
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
