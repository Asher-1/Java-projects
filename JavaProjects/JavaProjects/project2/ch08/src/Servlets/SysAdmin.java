package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ado.SystemInfoAdo;
import utils.*;
public class SysAdmin extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SysAdmin() {
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
		
		if (action.equals("1")) {
			try {
				int rowsPerPage = ParamUtils.getIntParameter(request,
						"rowsPerPage", 0);
				SystemInfoAdo sado = new SystemInfoAdo();
				sado.updateRowsPerPage(rowsPerPage);
				sado.close();
				
				getSysInfo(request,response);
				request.getRequestDispatcher("/admin/sysAdmin.jsp?msg=修改成功")
						.forward(request, response);
			} catch (Exception e) {
				getSysInfo(request,response);
				request.getRequestDispatcher("/admin/sysAdmin.jsp?msg=修改失败")
						.forward(request, response);
			}
		}
		else if(action.equals("0")){
			getSysInfo(request,response);
			
			request.getRequestDispatcher("/admin/sysAdmin.jsp?msg=系统信息")
			.forward(request, response);
		}
	}
	
	public void getSysInfo(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		try{
			SystemInfoAdo sado = new SystemInfoAdo();
			int rowsPerPage = sado.selectRowsPerPage();
			request.setAttribute("rowsPerPage",rowsPerPage);
			
			
		}catch(Exception e){
			request.getRequestDispatcher("/admin/sysAdmin.jsp?msg=无法查看系统信息")
			.forward(request, response);
		}
	}

}
