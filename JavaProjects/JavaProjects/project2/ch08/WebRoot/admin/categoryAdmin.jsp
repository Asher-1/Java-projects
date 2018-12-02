<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%@ include file="head.jspf"%>
<%@ page import="beans.*,java.util.*" %>
<form action="servlet/categoryAdmin" method="POST">
<table width="100%" >
<tr>
<td align="center">
	<%
		String msg = request.getParameter("msg");
		if(msg!=null)
			out.print("<b><font size=4 color=red>"+msg+"</font></b>");
	%>
</td>
</tr>
<tr>
   <td >
		 <table width="100%"  >
		   <tr bordercolor="#00CC00" bgcolor="#CCCCCC">
			 <td align="center" valign="middle" bordercolor="#00CC00" bgcolor="#CCCCCC"><b>编号</b></td>
			 <td align="center" valign="middle"><b>种类名</b></td>
			 <td align="center" valign="middle"><b>描述</b></td>
			 <td>&nbsp;</td>
		   </tr>
		   <%
		   		Collection categories = (Collection)request.getAttribute("categories");
		   		int num = 0;
		   		if(categories!=null){
		   		for(Iterator i = categories.iterator();i.hasNext();){
		     		CategoryBean cbean = (CategoryBean)i.next();
		     		num++;
		    %>
		   <tr bordercolor="#00CC00">
		   <td align="center" bordercolor="#00CC00" bgcolor="#99CCCC"><%=String.valueOf(num)%></td>
		   <td align="center" bgcolor="#99CC99"><%=cbean.getCategoryName()%></td>
		   <td align="center" bgcolor="#CCFF66"><%=cbean.getDescription()%></td>
		   <td align="center" bgcolor="#FFCC99">
		   <%
		   	out.print("<a href='servlet/categoryAdmin?action=1&delete="+cbean.getCategoryID()+"'><font color=red>删除</font></a>");
		   %>		   </td>
		   </tr>
		   <%}}%>
		   <tr bordercolor="#00CC00">
		   <td colspan="4" align="center" >
		   			<table>
		   			<TR bgcolor="#CCCCCC"><td colspan="2"><b>添加新种类：</b></td>
		   			</TR>
						 
					<tr bgcolor="#CCCCCC">	 
					<td>种类名:</td>
					<td>
					<INPUT type="text" name="categoryName" id="categoryName" class="NormalTextBox"/>					</td>
					<tr bgcolor="#CCCCCC">
					<td>描述:</td>
					<td>
					  <TEXTAREA name="description" rows="3" cols="30" id="description" class="NormalTextBox"></TEXTAREA>					</td>
					</tr>	
					<tr bgcolor="#CCCCCC">
					<td colspan="2" align="center">
					<input type="hidden" name="action" value="2"/>
					<INPUT type="Submit" name="submit" value="添加" />					</td>
					</tr>
			 </table>			</td>
		   </tr>
	    </table>
   </td>
</tr>
</table>
</form>
<%@ include file="root.jspf"%>
 
