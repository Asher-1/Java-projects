<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%
request.setCharacterEncoding("gb2312");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="beans.*,ado.*" %>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
<form method="POST"  action="servlet/myebay?action=2" name="myebay">
<table width="100%">
<tr>
 <td colspan="5" align="center"><FONT color="white" size="5"><b>我的ebay</b></FONT>
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
      <td bgcolor="#CCCCCC"><div align="center"><strong>物品名</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>所属种类</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>物品描述</strong></div></td>
      <td><div align="center"></div></td>
    </tr>
    <%
   
    Collection goods = (Collection)request.getAttribute("goods");
    int num = 0;
    if(goods!=null){
      for(Iterator i = goods.iterator();i.hasNext();) { 
        GoodBean gbean = (GoodBean)i.next();
        num++;
    %>
    <tr> 
      <td bgcolor="#99CCCC"><div align="center"><%=num%></div></td>
      <td bgcolor="#99CC99" ><div align="center"><a href='<%="servlet/goodAdmin?action=9&gid="+gbean.getGoodID()%>'><%=gbean.getGoodName()%></a></div></td>
      <td bgcolor="#CCFF66">
        <div align="center"><%=gbean.getCategoryName()%>
        </div></td>
      <td bgcolor="#FFCC99"><div align="left"><%=gbean.getDescription()%></div></td>
      <td bgcolor="#c5afd1">
        <div align="center">
            <%
		   	out.print("<a href='servlet/myebay?action=1&delete="+gbean.getGoodID()+"'><font color=red>删除</font></a>");
		%>
	      </div></td>
    </tr>
    <%
    	
      }
      
      }
    %>
    </table>
    <p align="center"><input type="submit" value="删除全部"  onclick="javascript:return confirm('确认要全部删除？')"/></p>
</form>
 <%@ include file="root.jspf"%>
