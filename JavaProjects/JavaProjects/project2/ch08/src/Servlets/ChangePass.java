package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.AdminBean;
import ado.*;

public class ChangePass extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ChangePass() {
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

		String pass1 = request.getParameter("newPass1");
		String pass2 = request.getParameter("newPass2");
		
		HttpSession session = request.getSession();
		AdminBean abean = (AdminBean)session.getAttribute("admin");
		int adminID = abean.getAdminID();
		if(!pass1.equals(pass2))
			request.getRequestDispatcher("/error.jsp?msg=两次输入的密码不相同")
			.forward(request,response);
		else{
			try{
				AdminAdo aado = new AdminAdo();
				aado.updatePassword(adminID,pass1);
				aado.close();
				
				request.getRequestDispatcher("/success.jsp?msg=密码修改成功")
				.forward(request,response);
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=密码修改失败")
				.forward(request,response);
			}
		}
	}

}
