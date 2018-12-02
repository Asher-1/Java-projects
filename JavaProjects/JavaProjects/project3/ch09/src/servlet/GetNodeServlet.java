package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datasource.DBManager;



public class GetNodeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public GetNodeServlet() {
		super();
	}

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

		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setCharacterEncoding("utf-8");
			String chapterid = request.getParameter("chapterid");  //获取章编号
			String configpath= request.getSession().getServletContext().getRealPath("/WEB-INF/classes/config.properties");
			DBManager dbmanager = DBManager.getInstance(configpath);
			Connection conn = dbmanager.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			ServletOutputStream out = response.getOutputStream();
			try
			{
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select nodeid from ChapterNode where chapterid="+chapterid);
				ArrayList nodeidlist = new ArrayList();
				while(rs.next())
				{
					String nodeid = rs.getString(1);
					nodeidlist.add(nodeid);
				}
				stmt.close();
				rs.close();
				out.print("请选择节： <select name='node'>");
				Iterator niter = nodeidlist.iterator();
				while(niter.hasNext())
				{
					String nid = niter.next().toString();
					String nodename = getnodenamebynodeid(conn,nid);
					out.print("<option value='"+nid+"'>"+nodename+"</option>");
				}
				out.print("</select>");
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
}
	
	