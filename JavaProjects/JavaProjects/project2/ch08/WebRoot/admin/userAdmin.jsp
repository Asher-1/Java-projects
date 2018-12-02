<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%@ include file="head.jspf"%>
<%@ page import="beans.*,ado.*" %>

  <table width="100%">
  <tr>
	<td align="center">
	<%
		int pageNo = ((Integer)request.getAttribute("pageno")).intValue();
   		int pagesCount = ((Integer)request.getAttribute("pagescount")).intValue();
		String msg = request.getParameter("msg");
		if(msg!=null)
			out.print("<b><font size=4 color=red>"+msg+"</font></b>");
	%>
	</td>
	</tr>
	<tr>
	<td>
	<table width="100%">
    <tr bgcolor="#CCCCCC"> 
      <td bgcolor="#CCCCCC"><div align="center"><strong>编号</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>用户名</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>真实姓名</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>寝室电话</strong></div></td>
      <td><div align="center"><b>移动电话</b></div></td>
      <td><div align="center"><b>Email</b></div></td>
      <td><div align="center"><b>QQ</b></div></td>
      <td><div align="center"></div></td>
    </tr>
    <%
    Collection users = (Collection)request.getAttribute("users");
    int num = 0;
    if(users!=null){
      for(Iterator i = users.iterator();i.hasNext();) { 
        UserBean ubean = (UserBean)i.next();
        num++;
    %>
    <tr> 
      <td bgcolor="#99CCCC"><div align="center"><%=num%></div></td>
      <td bgcolor="#99CC99" ><div align="center"><%=ubean.getUserName()%></div></td>
      <td bgcolor="#CCFF66">
        <div align="center"><%=ubean.getUserRealName()%>
        </div></td>
      <td bgcolor="#FFCC99"><div align="center"><%=ubean.getTel()%></div></td>
      <td bgcolor="#FFCC99"><div align="center"><%=ubean.getMobile()%></div></td>
      <td bgcolor="#FFCC99"><div align="center"><%=ubean.getEmail()%></div></td>
      <td bgcolor="#FFCC99"><div align="center"><%=ubean.getQq()%></div></td>
      <td bgcolor="#c5afd1">
        <div align="center">
            <%
		   	out.print("<a href='servlet/userAdmin?action=1&delete="+ubean.getUserID()+"'><font color=red>删除</font></a>");
		%>
	      </div></td>
    </tr>
    <%}}%>
    </table>
    </td>
    </tr>
<tr>
<td colspan="5" align="center">
 <form action="servlet/userAdmin?action=0" method="POST" name="PageForm">
    每页<%=request.getAttribute("rowsperpage")%>行&nbsp;
    共<%=request.getAttribute("rowscount")%>行&nbsp;
    第<%=request.getAttribute("pageno")%>页&nbsp;
    共<%=request.getAttribute("pagescount")%>页
    <br>
    <%
      if(pageNo==1) { 
    	out.print(" 首页  上一页 "); 
      }else {  
    %>   
    	<a href="javascript:gotoPage(1)">首页</a>&nbsp;
    	<a href="javascript:gotoPage(<%=pageNo-1%>)">上一页</a>&nbsp;
    <%
      }
    %>
    <%
      if(pageNo==pagesCount) { 
    	out.print("下一页  尾页");   
      } else {  
    %>   
    	<a href="javascript:gotoPage(<%=pageNo+1%>)">下一页</a>&nbsp;
    	<a href="javascript:gotoPage(<%=pagesCount%>)">尾页</a>
    <%
      }
    %>
    &nbsp;转到第
    <select name="jumpPage" onchange="Jumping()">
    <%
      for(int i=1; i<=pagesCount; i++) {
     	if (i == pageNo) {
    %>
     	<option selected value=<%=i%>><%=i%></option>
    <%
    	} else {
    %>
     	<option value=<%=i%>><%=i%></option>
    <%
    	}
      }
    %>   
    </select>页
  </form>
</td>
</tr>
</table>

<%@ include file="root.jspf"%>
  <Script Language="JavaScript">
  function Jumping(){
    document.PageForm.submit();
  	return ;
  }

  function gotoPage(pagenum){
  	document.PageForm.jumpPage.value = pagenum;
  	document.PageForm.submit();
  	return ;
  }
</Script>

