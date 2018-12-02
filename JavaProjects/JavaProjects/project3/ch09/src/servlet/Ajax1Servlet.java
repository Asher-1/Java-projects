package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;

import bean.ChapterBean;
import bean.NodeBean;

import datasource.DBManager;

public class Ajax1Servlet extends HttpServlet {

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
		Connection conn = dbmanager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ArrayList chapterlist = new ArrayList();
		ArrayList nodelist = new ArrayList();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Chapter");
			while(rs.next())   //从数据库中读出本课程的所有章列表存入到chapterlist中
			{	
				ChapterBean cb = new ChapterBean();
				cb.setChapterid(rs.getString(1));
				cb.setChaptername(rs.getString(2));
				chapterlist.add(cb);
			}
			session.setAttribute("chapterlist", chapterlist); //将章列表chapterlist存入到session中
			if(chapterlist.size()>0)
			{
				ChapterBean firstchapter = (ChapterBean)chapterlist.get(0);
				String firstcid = firstchapter.getChapterid();
				rs = stmt.executeQuery("select nodeid from ChapterNode where chapterid="+firstcid);
				rs.next();
				String nodeid = rs.getString(1); //获取第一章所有的节
				rs1 = stmt.executeQuery("select * from node where nodeid="+nodeid);
				while(rs1.next())
				{
					NodeBean nb = new NodeBean();
					nb.setNodeid(rs1.getString(1));
					nb.setNodename(rs1.getString(2));
					nodelist.add(nb);
				}
			}
			session.setAttribute("nodelist", nodelist); //将节列表nodelist存入到session中
			response.sendRedirect("/ajax/ajax1.jsp");  //页面跳转到ajax1.jsp
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
			if(rs1!=null)
				try {
					rs1.close();
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
