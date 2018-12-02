package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import beans.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.*;

import ado.GoodAdo;
import ado.SystemInfoAdo;
import ado.UserAdo;

public class UserAdmin extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserAdmin() {
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
			getUsers(request,response);
			
			request.getRequestDispatcher("/admin/userAdmin.jsp?msg=所有用户")
				.forward(request, response);	
		}
		else if(action.equals("1")){
			try {
				int delete = ParamUtils.getIntParameter(request, "delete", 0);
				UserAdo uado = new UserAdo();
				uado.deleteUserByID(delete);
				uado.close();
				
				getUsers(request,response);
				
				request.getRequestDispatcher("/admin/userAdmin.jsp?msg=删除成功！")
				.forward(request,response);
			}
			catch(Exception e){
				getUsers(request,response);
				request.getRequestDispatcher("/admin/userAdmin.jsp?msg=删除失败！")
				.forward(request,response);
			}
		}
		else if(action.equals("2")){
			try{
				int uid = ((UserBean)request.getSession().getAttribute("user")).getUserID();
				String realname = HtmlUtils.escapeHtml(request.getParameter("realname"));
				String tel = HtmlUtils.escapeHtml(request.getParameter("tel"));
				String mobile = HtmlUtils.escapeHtml(request.getParameter("mobile"));
				String email = HtmlUtils.escapeHtml(request.getParameter("email"));
				String qq = HtmlUtils.escapeHtml(request.getParameter("qq"));
				
				UserAdo uado = new UserAdo();
				uado.updateUserBasicInfo(uid,ChineseUtils.transToEn(realname),tel,mobile,ChineseUtils.transToEn(email),qq);
				uado.close();
				
				request.getRequestDispatcher("/success.jsp?msg=修改成功")
				.forward(request,response);
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=修改失败")
				.forward(request,response);
			}
		}
		else if(action.equals("3")){
			try{
				int uid = ((UserBean)request.getSession().getAttribute("user")).getUserID();
				String password1 = request.getParameter("password1");
				String password2 = request.getParameter("password2");
				
				UserAdo uado = new UserAdo();
				uado.updateUserPassword(uid,password1);
				uado.close();
				
				request.getRequestDispatcher("/success.jsp?msg=修改成功")
				.forward(request,response);
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=修改失败")
				.forward(request,response);
			}
		}
		else if(action.equals("4")){
			try{
				HttpSession session = request.getSession();
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
				
				String username = HtmlUtils.escapeHtml(request.getParameter("username"));		
				String password1 = HtmlUtils.escapeHtml(request.getParameter("password1"));		
				//String password2 = request.getParameter("password2");		
				String realname = HtmlUtils.escapeHtml(request.getParameter("realname"));			
				String tel = HtmlUtils.escapeHtml(request.getParameter("tel"));
				String mobile = HtmlUtils.escapeHtml(request.getParameter("mobile"));
				String email = HtmlUtils.escapeHtml(request.getParameter("email"));
				String qq = HtmlUtils.escapeHtml(request.getParameter("qq"));
				
				
				
				UserAdo uado = new UserAdo();
				if(uado.selectUserByName(username)){
					request.getRequestDispatcher("/error.jsp?msg=该用户名已经存在")
					.forward(request,response);
					return ;
				}
				uado.addUser(ChineseUtils.transToEn(realname),ChineseUtils.transToEn(username),ChineseUtils.transToEn(password1),tel,mobile,ChineseUtils.transToEn(email),qq);
				uado.close();
				
				request.getRequestDispatcher("/success.jsp?msg=注册成功")
					.forward(request,response);
				
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=注册失败")
				.forward(request,response);
			}
		}
		else if(action.equals("5")){
			try{
				int uid = ParamUtils.getIntParameter(request,"uid",0);
				UserAdo uado = new UserAdo();
				Collection user = uado.selectUserByID(uid);
				uado.close();
				for(Object o : user){
					UserBean ubean = (UserBean)o;
					request.setAttribute("ubean",ubean);
					
				request.getRequestDispatcher("/user.jsp")
					.forward(request,response);
				}
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=读取用户信息失败")
				.forward(request,response);
			}
			
		}
		
	}
	
	public void getUsers(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		try{
			int pageNo = 1;
			String strPage = request.getParameter("jumpPage");
			if (strPage != null) 
				pageNo = Integer.parseInt(strPage);
				
			UserAdo uado = new UserAdo();
			uado.setRowsPerPage(new SystemInfoAdo().selectRowsPerPage());
			uado.setSQL("select * from Users");
			Collection users = uado.getPage(pageNo);
			
			request.setAttribute("users",users);
			request.setAttribute("rowsperpage",uado.getRowsPerPage());
			request.setAttribute("rowscount",uado.getRowsCount());
			request.setAttribute("pageno",pageNo);
			request.setAttribute("pagescount",uado.getPagesCount());
			uado.close();
		}
		catch(Exception e){
			request.getRequestDispatcher("/admin/userAdmin.jsp?msg=无法读取用户！")
			.forward(request,response);
		}
	}

}
