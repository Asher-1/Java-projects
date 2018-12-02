package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.ParamUtils;
import ado.MyEbayAdo;
import ado.MyMessageAdo;
import beans.UserBean;

public class MyMsg extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MyMsg() {
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
		HttpSession session = request.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		
		if(action.equals("0")){
			try{
				getMsgs(request,response,ub.getUserID());
				request.getRequestDispatcher("/mymsg.jsp")
				.forward(request,response);
				
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=无法读取消息！")
				.forward(request,response);
			}
			
		}
		
		else if(action.equals("1")){
			try{
				int delete = ParamUtils.getIntParameter(request,"delete",0);
				MyMessageAdo mado = new MyMessageAdo();
				mado.deleteMsgByID(delete);
				mado.close();
				getMsgs(request,response,ub.getUserID());
				request.getRequestDispatcher("/mymsg.jsp?msg=删除成功！")
				.forward(request,response);
				
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=删除失败！")
				.forward(request,response);
			}
			
		}
		
		else if(action.equals("2")){
			try{
				MyMessageAdo mado = new MyMessageAdo();
				mado.deleteAllByUid(ub.getUserID());
				mado.close();
				request.getRequestDispatcher("/mymsg.jsp?msg=已全部删除！")
				.forward(request,response);
				
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=删除失败！")
				.forward(request,response);
			}
			
		}
	}
	
	public void getMsgs(HttpServletRequest request,HttpServletResponse response,int uid)
	throws ServletException, IOException {
		try{
			
			MyMessageAdo mado = new MyMessageAdo();
			
			Collection msgs = mado.selectMsgByUid(uid);
			
			request.setAttribute("msgs",msgs);
		
			mado.close();
			
			
		}
		catch(Exception e){
			request.getRequestDispatcher("/error.jsp?msg=无法读取消息！")
			.forward(request,response);
		}
	}


}
