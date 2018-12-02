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
		String keywords = request.getParameter("keywords"); //��ȡҳ���ύ�����������ؼ���
		keywords = new String(keywords.getBytes("iso-8859-1"),"UTF-8");   //����UTF-8���룬��������
		ArrayList<TaskBean> taskList = new ArrayList<TaskBean>();
		try
		{
			ServletOutputStream out = response.getOutputStream();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Task where taskname like '%"+keywords+"%'");
			while(rs.next()){
				TaskBean tb = new TaskBean();  //ÿ����ҵ��¼���뵽һ��TaskBean��
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
				taskList.add(tb);  //��ѯ���е���ҵ���뵽taskList��
			}
			SearchResult<TaskBean> searchResult = new SearchResult<TaskBean>();
			searchResult.setRecord(taskList);
	        searchResult.setStatus(SearchStatus.ERROR.ordinal());
	        //SearchResult���밴��һ���Ĺ涨������ܱ�ת��ΪJSONObject����һ�����һ��ArrayList�������Ժ�һ��SearchStatus��������
	        //ArrayList�е�ÿ��JavaBean���ᱻת��Ϊxml�е�һ���ڵ�
			JSONObject jsonobj = JSONObject.fromBean(searchResult);
			//ʹ��JSONObject�����formbean�������԰�һ������ת��ΪJSONObject����
			String xmldoc = XML.toString(jsonobj, "records");
			//��JSONObject����ת��Ϊxml�ַ���	
			out.print(xmldoc);
			//��xmldoc�����������out�У���Ϊajax���󷵻ص����ݣ����Ա�ajaxҳ���ȡ�ⲿ������
			
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
