package Servlets;

import java.io.IOException;
import java.util.Collection;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.AdminBean;
import beans.UserBean;

import ado.AdminAdo;
import ado.UserAdo;

public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7152775440533785767L;

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
			throws ServletException, IOException{

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String userType = request.getParameter("users");
		HttpSession session = request.getSession();
		try {
			if (userType.equals("user")) {
				UserAdo user = new UserAdo();
				int userID = user.doLogin(username,password);
				
				if(userID!=0){
					Collection users = user.selectUserByID(userID);
					
					for(Object o : users){
						UserBean userbean = (UserBean)o;
						session.setAttribute("user",userbean);	
					}
					user.close();
					request.getRequestDispatcher("/index.jsp")
					.forward(request,response);
				}
				else{
					user.close();
					request.getRequestDispatcher("/error.jsp?msg=用户名或密码错误，请重新登录")
					.forward(request,response);
				}
						
			} 
			else {
				AdminAdo admin = new AdminAdo();
				int adminID = admin.doLogin(username, password);
				admin.close();
				if (adminID != 0) {
					AdminBean adminbean = new AdminBean(adminID, username,
							password);
					session.setAttribute("admin", adminbean);
					request.getRequestDispatcher("/admin/index.jsp")
					.forward(request,response);
				}
				else{
					request.getRequestDispatcher("/error.jsp?msg=用户名或密码错误，请重新登录")
					.forward(request,response);
				}
				
			}
		} catch (Exception e) {
			request.getRequestDispatcher("/error.jsp?msg=登录失败")
			.forward(request,response);
		}
	}

}
