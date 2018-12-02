<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%
request.setCharacterEncoding("gb2312");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
 <%@ page import="beans.*,ado.*" %>

  <table width="100%">
  <tr>
	<td align="center">
	<%
		int pageNo = ((Integer)request.getAttribute("pageno")).intValue();
   		int pagesCount = ((Integer)request.getAttribute("pagescount")).intValue();
		
	%>
	<FONT color="white" size="5"><b>求购信息</b></FONT>
    <br><hr>
	</td>
	</tr>
	
	<tr>
	<td>   
    <%
    Collection messages = (Collection)request.getAttribute("messages");
    int n = 0;
    if(messages!=null){
      for(Iterator i = messages.iterator();i.hasNext();) { 
      	n++;
        MessageBean mbean = (MessageBean)i.next();
        UserAdo uado = new UserAdo();
        Collection user = uado.selectUserByID(mbean.getUserID());
        uado.close();
        UserBean ubean = (UserBean)user.iterator().next();
    %>
    <%
     if(n%2==0) 
     	out.print("<table width='100%' bgcolor='white'>");
	 else
		out.print("<table width='100%' bgcolor='silver'>");
    %>
    <table width="100%">
    <tr> 
    <td  align="left" width="10%" ><b>主题：</b></td>
    <td><%=mbean.getTitle()%></td>
    </tr>
    
    <tr> 
    <td  align="left" valign="top" width="10%"><b>内容：</b></td>
    <td><%=mbean.getContent()%></td>
    </tr>
    
    <tr> 
    <td  align="left" width="10%">
    <b>留言人：</b>
    </td>
    <td>
    <%=ubean.getUserRealName()%>
    </td>
    </tr>
    
     <tr> 
    <td  align="left" valign="top" width="10%">
    <b>联系方式：</b>
    </td>
    <td>
    
 	联系电话：<%=ubean.getTel()%>&nbsp;&nbsp;
    手机：<%=ubean.getMobile()%>&nbsp;&nbsp;<br>
    Email：<%=ubean.getEmail()%>&nbsp;&nbsp;
    QQ：<%=ubean.getQq()%>
    </td>
    </tr>
    
    <tr> 
    <td  align="left"width="10%">
    <b>日期：</b>
    </td>
    
    <td>
    <%=DatetimeUtils.getDateByDate(mbean.getDatetime())%>
    </td>
    </tr>
    
    </table>
    <table width="100%" bgcolor="blue"><tr><td>&nbsp;</td></tr></table>
    <%}}%>
    
    </td>
    </tr>
<tr>
<td  align="center">
 <form action="servlet/messageAdmin?action=2" method="POST" name="PageForm">
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

<tr>
<td>
<form action="servlet/messageAdmin"  name="addmsg">
<hr>
<table width="100%">
<tr>
<td align="center" colspan="2"><b><font color="white">添加新求购信息</font></b></td>
<tr>

<tr>
<td width="30%" align="right">主题：</td>
<td align="left">
<input type="text" id="title" name="title" class="NormalTextBox" size="50">
</td>
</tr>

<tr>
<td width="30%" align="right" valign="top">内容：</td>
<td align="left">
<TEXTAREA name="content" rows="10" cols="50" id="content" class="NormalTextBox"></TEXTAREA>
</td>
</tr>

<tr>
<td colspan="2" align="center">
<INPUT type="Submit" name="submit" value="提交" onmouseover="confirm()"/>
<INPUT type="Reset" name="reset" value="重置" />
<input type="hidden" name="action" value="3">
</td>
</tr>

</table>
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
  
  function confirm()
	{
	if(document.addmsg.title.value=="")
	{
		window.alert("请输入主题");
		document.addmsg.username.focus();
		return false;
	}
	if(document.addmsg.content.value=="")
	{
		window.alert("请输入内容");
		document.addmsg.content.focus();
		return false;
	}
	
	return true;
	}
</Script>
