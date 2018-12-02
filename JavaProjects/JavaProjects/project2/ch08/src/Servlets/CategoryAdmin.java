package Servlets;

import java.io.IOException;
import java.util.Collection;

import utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ado.CategoryAdo;

public class CategoryAdmin extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CategoryAdmin() {
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
		
		
		
		if(action.equals("1")){
			try {
				int delete = ParamUtils.getIntParameter(request, "delete", 0);
				CategoryAdo cado = new CategoryAdo();
				cado.deleteCategoryByID(delete);
				cado.close();
				
				getCategories(request,response);
				
				request.getRequestDispatcher("/admin/categoryAdmin.jsp?msg=删除成功！")
				.forward(request,response);
			}
			catch(Exception e){
				getCategories(request,response);
				request.getRequestDispatcher("/admin/categoryAdmin.jsp?msg=删除失败！")
				.forward(request,response);
			}
		}
		else if(action.equals("2")){
			try{
				String categoryName = HtmlUtils.escapeHtml(ParamUtils.getParameter(request,"categoryName"));
				String description = HtmlUtils.escapeHtml(ParamUtils.getParameter(request,"description"));
				description = HtmlUtils.convertNewlines(description);
				CategoryAdo cado = new CategoryAdo();
				cado.addCategory(ChineseUtils.transToEn(categoryName),ChineseUtils.transToEn(description));
				cado.close();
				
				getCategories(request,response);
				
				request.getRequestDispatcher("/admin/categoryAdmin.jsp?msg=添加成功！")
				.forward(request,response);
			}
			catch(Exception e){
				getCategories(request,response);
				request.getRequestDispatcher("/admin/categoryAdmin.jsp?msg=添加失败！")
				.forward(request,response);
			}
		}
		else if (action.equals("0")) {
			getCategories(request,response);
			request.getRequestDispatcher("/admin/categoryAdmin.jsp?msg=所有种类")
					.forward(request, response);
		}
		else if(action.equals("3")){
			HttpSession session = request.getSession();
			if(session.getAttribute("user")==null){
				request.getRequestDispatcher("/error.jsp?msg=请先登录")
				.forward(request, response);
				return ;
			}
			
			getCategories(request,response);
			request.getRequestDispatcher("/sell.jsp")
				.forward(request, response);
		}
			
	}
	
	public void getCategories(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		try{
			CategoryAdo cado = new CategoryAdo();
			Collection categories = cado.selectCategories();
			request.setAttribute("categories",categories);
			cado.close();
		}
		catch(Exception e){
			request.getRequestDispatcher("/admin/categoryAdmin.jsp?msg=无法读取种类！")
			.forward(request,response);
		}
	}
}
