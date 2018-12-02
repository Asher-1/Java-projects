<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%@ include file="head.jspf"%>
<%@ page import="beans.*,ado.*" %>
<%
	GoodBean gbean = (GoodBean)request.getAttribute("good");
	String catName = (String)request.getAttribute("catName");
	CategoryAdo cado = new CategoryAdo();
	Collection cat = cado.selectCategories();
%>
<form action="servlet/goodAdmin?action=3" method="post">
<table align="center">
<tr>
<td><div align="center"><b>物品名</b></div></td>
<td><div align="center"><b>现属种类</b></div></td>
<td><div align="center"><b>更改为</b></div></td>
<td><div align="center"></div></td>
</tr>
<tr>
<td bgcolor="#99CCFF"><div align="center"><%=gbean.getGoodName()%></div></td>
<td bgcolor="#99CCCC"><div align="center"><%=catName%></div></td>
<td bgcolor="#99CC66">
  <div align="center">
    <select name="catID">
    <%
	for(Iterator i = cat.iterator();i.hasNext();){
		CategoryBean cbean = (CategoryBean)i.next();
%>
	    <option value=<%=cbean.getCategoryID()%>><%=cbean.getCategoryName()%></option>
        <%
	}
	cado.close();	
%>
    </select>
  </div></td>
<td>
  <div align="center">
  <input type="hidden" value=<%=gbean.getGoodID()%> name="goodID"/>
  <input type="submit" value="更改"/>
  </div></td>
</tr>
</table>
</form>
<%@ include file="root.jspf"%>