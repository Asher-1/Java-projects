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
		Connection conn = dbmanager.getConnection();  //��ȡ���ݿ�����
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<KnowledgeBean> knowledgelist = new ArrayList<KnowledgeBean>();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Knowledge");
			while(rs.next()){
			KnowledgeBean kb = new KnowledgeBean();  //ÿ��֪ʶ���¼��һ��javabeanʵ���洢
			kb.setKid(rs.getString(1));
			kb.setKname(rs.getString(2));
			kb.setTeachername(rs.getString(3));
			kb.setCreatetime(rs.getString(4));
			kb.setDescription(rs.getString(5));
			knowledgelist.add(kb);  
			//�����ݿ��в�ѯ����֪ʶ���ŵ�knowledgelist��ȥ
			}
			SearchResult<KnowledgeBean> searchResult = new SearchResult<KnowledgeBean>();
			searchResult.setRecord(knowledgelist);
	        searchResult.setStatus(SearchStatus.ERROR.ordinal());
	        //SearchResult���밴��һ���Ĺ涨������ܱ�ת��ΪJSONObject����һ�����һ��ArrayList�������Ժ�һ��SearchStatus��������
	        //ArrayList�е�ÿ��JavaBean���ᱻת��Ϊxml�е�һ���ڵ�
			JSONObject jsonobj = JSONObject.fromBean(searchResult);
			//ʹ��JSONObject�����formbean�������԰�һ������ת��ΪJSONObject����
			String xmldoc = XML.toString(jsonobj, "records");
			//��JSONObject����ת��Ϊxml�ַ���
			session.setAttribute("xmldoc", xmldoc);
			//��ת����xml�ַ���������뵽session��
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
