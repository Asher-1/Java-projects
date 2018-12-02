package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.*;
import beans.*;
import ado.*;

public class MessageAdmin extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MessageAdmin() {
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		
		if(action.equals("0")){
			getMessages(request,response);
			
			request.getRequestDispatcher("/admin/messageAdmin.jsp?msg=所有求购消息")
				.forward(request, response);	
		}
		else if(action.equals("1")){
			try {
				int delete = ParamUtils.getIntParameter(request, "delete", 0);
				MessageAdo mado = new MessageAdo();
				mado.deleteMessageByID(delete);
				mado.close();
				
				getMessages(request,response);
				
				request.getRequestDispatcher("/admin/messageAdmin.jsp?msg=删除成功！")
				.forward(request,response);
			}
			catch(Exception e){
				getMessages(request,response);
				request.getRequestDispatcher("/admin/messageAdmin.jsp?msg=删除失败！")
				.forward(request,response);
			}
		}
		else if(action.equals("2")){
			getMessages(request,response);
			
			request.getRequestDispatcher("/message.jsp")
				.forward(request, response);
		}
		else if(action.equals("3")){
			try{
				HttpSession session = request.getSession();
				if(session.getAttribute("user")==null){
					request.getRequestDispatcher("/error.jsp?msg=请先登录")
					.forward(request, response);
					return ;
				}
				
				TimeUtil tu = (TimeUtil)session.getAttribute("lastAdd");
				long now = System.currentTimeMillis();
				if(tu!=null){
					long lastAdd =  tu.getLastAdd();
					if((now-lastAdd) <= 60000){
						request.getRequestDispatcher("/error.jsp?msg=为防止灌水，60秒内不得重复提交")
						.forward(request, response);
						return ;
					}
					
				}
				tu = new TimeUtil(now);
				session.setAttribute("lastAdd",tu);
				
			String title = HtmlUtils.escapeHtml(request.getParameter("title"));
			String content = HtmlUtils.convertNewlines(
					HtmlUtils.escapeHtml(
							request.getParameter("content")
					)
				);
			int uid = ((UserBean)session.getAttribute("user")).getUserID();
			Date date = new Date(System.currentTimeMillis());
			
			MessageAdo mado = new MessageAdo();
			mado.addMessage(ChineseUtils.transToEn(title),ChineseUtils.transToEn(content),date,uid);
			mado.close();
			
			getMessages(request,response);
			
			request.getRequestDispatcher("/message.jsp")
				.forward(request, response);
			
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=添加失败！")
				.forward(request,response);
			}
			
		}
		else if(action.equals("4")){
			try{
				HttpSession session = request.getSession();
				if(session.getAttribute("user")==null){
					request.getRequestDispatcher("/error.jsp?msg=请先登录")
					.forward(request, response);
					return ;
				}
				
				TimeUtil tu = (TimeUtil)session.getAttribute("lastAdd");
				long now = System.currentTimeMillis();
				if(tu!=null){
					long lastAdd =  tu.getLastAdd();
					if((now-lastAdd) <= 60000){
						request.getRequestDispatcher("/error.jsp?msg=为防止灌水，60秒内不得重复提交")
						.forward(request, response);
						return ;
					}
					
				}
				tu = new TimeUtil(now);
				session.setAttribute("lastAdd",tu);
				
			String content = HtmlUtils.convertNewlines(
					HtmlUtils.escapeHtml(
							request.getParameter("content")
					)
				);
			int uid = ((UserBean)session.getAttribute("user")).getUserID();
			Date date = new Date(System.currentTimeMillis());
			int gid = ParamUtils.getIntParameter(request,"gid",0);
			MessageAdo mado = new MessageAdo();
			mado.addMessage(ChineseUtils.transToEn(content),date,uid,gid);
			mado.close();
			
			getMessages(request,response);
			
			request.getRequestDispatcher("/servlet/goodAdmin?action=9&gid="+gid)
				.forward(request, response);
			
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=添加失败！")
				.forward(request,response);
			}
		}
	}
	
	public void getMessages(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		try{
			int pageNo = 1;
			String strPage = request.getParameter("jumpPage");
			if (strPage != null) 
				pageNo = Integer.parseInt(strPage);
				
			MessageAdo mado = new MessageAdo();
			mado.setRowsPerPage(new SystemInfoAdo().selectRowsPerPage());
			mado.setSQL("select * from Message where GoodID is null");
			Collection messages = mado.getPage(pageNo);
			
			request.setAttribute("messages",messages);
			request.setAttribute("rowsperpage",mado.getRowsPerPage());
			request.setAttribute("rowscount",mado.getRowsCount());
			request.setAttribute("pageno",pageNo);
			request.setAttribute("pagescount",mado.getPagesCount());
			mado.close();
		}
		catch(Exception e){
			request.getRequestDispatcher("/admin/userAdmin.jsp?msg=无法读取用户！")
			.forward(request,response);
		}
	}
}
