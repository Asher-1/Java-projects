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
import beans.*;
import utils.*;
import ado.CategoryAdo;
import ado.GoodAdo;
import ado.MessageAdo;
import ado.SystemInfoAdo;

public class GoodAdmin extends HttpServlet {

	public static String sql = "select * from Good";
	/**
	 * Constructor of the object.
	 */
	public GoodAdmin() {
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
				GoodAdo gado = new GoodAdo();
				gado.deleteGoodByID(delete);
				gado.close();
				
				getGoods(request,response,sql);
				
				request.getRequestDispatcher("/admin/goodAdmin.jsp?msg=删除成功！")
				.forward(request,response);
			}
			catch(Exception e){
				getGoods(request,response,sql);
				request.getRequestDispatcher("/admin/goodAdmin.jsp?msg=删除失败！")
				.forward(request,response);
			}
		}
		else if (action.equals("2")) {

			try {
				int goodID = ParamUtils.getIntParameter(request, "goodID", 0);
				GoodAdo gado = new GoodAdo();
				CategoryAdo cado = new CategoryAdo();
				Collection good = gado.selectGoodByID(goodID);
				for (Object o : good) {
					GoodBean gbean = (GoodBean) o;
					int catID = gbean.getCategoryID();
					String catName = cado.selectCategoryName(catID);
					request.setAttribute("good", gbean);
					request.setAttribute("catName", catName);
				}
				cado.close();
				gado.close();
				request.getRequestDispatcher("/admin/changeCat.jsp").forward(
						request, response);
			} catch (Exception e) {
				request.getRequestDispatcher("/error?msg=无法读取物品").forward(
						request, response);
			}
		}
		else if(action.equals("3")){
			try{
				GoodAdo gado = new GoodAdo();
				int goodID = ParamUtils.getIntParameter(request,"goodID",0);
				int catID = ParamUtils.getIntParameter(request,"catID",0);
				gado.updateGoodCategory(goodID,catID);
				gado.close();
				
				getGoods(request,response,sql);
				request.getRequestDispatcher("/admin/goodAdmin.jsp?msg=种类更改成功").forward(
						request, response);
			}
			catch(Exception e){
				
			}
		}
		else if (action.equals("0")) {
			getGoods(request,response,sql);
			request.getRequestDispatcher("/admin/goodAdmin.jsp?msg=所有物品")
					.forward(request, response);
		}
		else if (action.equals("5")) {
			getGoods(request,response,sql);
			request.getRequestDispatcher("/buy.jsp")
					.forward(request, response);
		}
		else if (action.equals("7")) {
			sql = "select * from Good";
			getGoods(request,response,sql);
			request.getRequestDispatcher("/buy.jsp")
					.forward(request, response);
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
				
				GoodAdo gado = new GoodAdo();
				
				int categoryId = ParamUtils.getIntParameter(request,"category",0);
				String goodname = HtmlUtils.escapeHtml(ParamUtils.getParameter(request,"goodname"));
				
				double goodprice = ParamUtils.getDoubleParameter(request,HtmlUtils.escapeHtml("price"),0);
				int uid = ((UserBean)session.getAttribute("user")).getUserID();
				Date date = new Date(System.currentTimeMillis());
				String description = HtmlUtils.convertNewlines(HtmlUtils.escapeHtml(ParamUtils.getParameter(request,"description")));
				
				gado.addGood(categoryId,ChineseUtils.transToEn(goodname),goodprice,uid,date,ChineseUtils.transToEn(description),0);
				gado.close();
				
				request.getRequestDispatcher("/success.jsp?msg=成功添加出售物品")
				.forward(request, response);
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=添加失败")
				.forward(request, response);
			}
		}
		else if(action.equals("6")){
			try{
				int categoryID = ParamUtils.getIntParameter(request,"cid",0);
				 sql = "select * from Good where CategoryID="+categoryID;
				getGoods(request,response,sql);
				request.getRequestDispatcher("/buy.jsp")
				.forward(request, response);
			}
			catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=查询失败")
				.forward(request, response);
			}
		}
		else if(action.equals("8")){
			try{
				String type = request.getParameter("type");
				String key = ChineseUtils.transToEn(request.getParameter("key").trim());
				if(type.equals("name"))
				{	
					sql = "select * from Good where GoodName like '%"+key+"%'";
					getGoods(request,response,sql);
					request.getRequestDispatcher("/buy.jsp")
					.forward(request, response);
					return;
					
				}
				else if(type.equals("content")){
					sql = "select * from Good where Description like '%"+key+"%'";
					getGoods(request,response,sql);
					request.getRequestDispatcher("/buy.jsp")
					.forward(request, response);
					return;
					
				}
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=查询失败")
				.forward(request, response);
			}
		}
		else if(action.equals("9")){
			try{
				int goodID = ParamUtils.getIntParameter(request,"gid",0);
				GoodAdo gado = new GoodAdo();
				Collection good = gado.selectGoodByID(goodID);
				for(Object o : good){
					GoodBean gbean = (GoodBean)o;
					request.setAttribute("gbean",gbean);		
				}
				gado.close();
				
				MessageAdo mado = new MessageAdo();
				Collection messages = mado.selectMessageByGoodID(goodID);
				request.setAttribute("messages",messages);
				mado.close();
				request.getRequestDispatcher("/good.jsp")
				.forward(request, response);
			}catch(Exception e){
				request.getRequestDispatcher("/error.jsp?msg=查询失败")
				.forward(request, response);
			}
		}
	}
	
	public void getGoods(HttpServletRequest request,HttpServletResponse response,String sql)
	throws ServletException, IOException {
		try{
			int pageNo = 1;
			String strPage = request.getParameter("jumpPage");
			if (strPage != null) 
				pageNo = Integer.parseInt(strPage);
				
			GoodAdo gado = new GoodAdo();
			gado.setRowsPerPage(new SystemInfoAdo().selectRowsPerPage());
			gado.setSQL(sql);
			Collection goods = gado.getPage(pageNo);
			
			request.setAttribute("goods",goods);
			request.setAttribute("rowsperpage",gado.getRowsPerPage());
			request.setAttribute("rowscount",gado.getRowsCount());
			request.setAttribute("pageno",pageNo);
			request.setAttribute("pagescount",gado.getPagesCount());
			gado.close();
		}
		catch(Exception e){
			request.getRequestDispatcher("/admin/goodAdmin.jsp?msg=无法读取物品！")
			.forward(request,response);
		}
	}

}
