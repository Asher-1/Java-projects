<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%
request.setCharacterEncoding("gb2312");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="beans.*,ado.*" %>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
<form method="POST"  action="servlet/mymsg?action=2" name="mymsg">
<table width="100%">
<tr>
<td colspan="5" align="center"><FONT color="white" size="5"><b>我的留言</b></FONT>
    <br><hr>
    <%
		String msg = request.getParameter("msg");
		if(msg!=null)
			out.print("<b><font size=4 color=red>"+msg+"</font></b>");
	%>
    </td>
</tr>
    <tr bgcolor="#CCCCCC"> 
      <td bgcolor="#CCCCCC"><div align="center"><strong>编号</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>标题</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>内容</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>日期</strong></div></td>
      <td><div align="center"></div></td>
    </tr>
    <%
    Collection messages = (Collection)request.getAttribute("msgs");
    int num = 0;
    if(messages!=null){
      for(Iterator i = messages.iterator();i.hasNext();) { 
        MessageBean mbean = (MessageBean)i.next();
        num++;
    %>
    <tr> 
      <td bgcolor="#99CCCC"><div align="center"><%=num%></div></td>
      <td bgcolor="#99CC99" ><div align="center"><%=mbean.getTitle()%></div></td>
      <td bgcolor="#CCFF66">
        <div align="left"><%=mbean.getContent()%>
        </div></td>
      <td bgcolor="#FFCC99"><div align="center"><%=mbean.getDatetime()%></div></td>
      <td bgcolor="#c5afd1">
        <div align="center">
            <%
		   	out.print("<a href='servlet/mymsg?action=1&delete="+mbean.getMessageID()+"'><font color=red>删除</font></a>");
		%>
	      </div></td>
    </tr>
    <%}}%>
    </table>
    <p align="center"><input type="submit" value="删除全部"  onclick="javascript:return confirm('确认要全部删除？')"/></p>
</form>
 <%@ include file="root.jspf"%>
