<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<% request.setCharacterEncoding("gb2312");
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			%>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
<%@ page import="beans.*,ado.*,utils.*" %>
<%@ page import="utils.DatetimeUtils" %>
	<table width="100%">
	
    <tr bgcolor="#CCCCCC"> 
      <td bgcolor="#CCCCCC"><div align="center"><strong>时间</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>物品名</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>所属种类</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>物品描述</strong></div></td>
      <td><div align="center"><strong>初订价格</strong></div></td>
      <td><div align="center"><strong>卖主</strong></div></td>
    </tr>
    <%
   
    
        GoodBean gbean = (GoodBean)request.getAttribute("gbean");
       
    %>
    <tr> 
      <td bgcolor="#99CCCC"><div align="center"><%=DatetimeUtils.getDateByDate(gbean.getDatetime())%></div></td>
      <td bgcolor="#99CC99" ><div align="center"><%=gbean.getGoodName()%></div>
      </td>
      <td bgcolor="#CCFF66">
        <div align="center">
       <%=gbean.getCategoryName()%>
        </div></td>
      <td bgcolor="#b1e7bb"><div align="left"><%=gbean.getDescription()%></div></td>
      <td bgcolor="#c5afd1">
        <div align="center">
            <%=gbean.getGoodPrice()%>
	      </div></td>
	  <td bgcolor="#95c0ec">
        <div align="center">
           <a href='<%="servlet/userAdmin?action=5&uid="+gbean.getUserID()%>'> <%=gbean.getUsername()%> </a>
	      </div>
	      
	      </td>
    </tr>
    <%
     Collection messages = (Collection)request.getAttribute("messages");
    
     int n=0;
    if(messages!=null){
      for(Iterator i = messages.iterator();i.hasNext();) { 
        MessageBean mbean = (MessageBean)i.next();
        
         UserAdo uado = new UserAdo();
    	String user = uado.selectUserName(mbean.getUserID());
   
   if(n++%2==0)
   		out.print("<tr bgcolor=\"#CCFF66\">");
   	else
    	out.print("<tr bgcolor=\"#b1e7bb\">");
    	
    	
    	%>
   
    <td>
     <a href='<%="servlet/userAdmin?action=5&uid="+mbean.getUserID()%>'> <%=user%> </a>:
    </td>
    <td colspan="5">
    <%=mbean.getContent()%>
    </td>
    </tr>
    <%}}%>
    <tr>
			<td colspan="6" align="center">
				<br>
				<hr>
			</td>
	</tr>
    </table>
    <form action="servlet/messageAdmin"  name="addmsg" method="post">
    <table align="center">
    <tr><td  align="left"><b>竞价，建议</b></td></tr>
    <tr><td>
    <TEXTAREA name="content" rows="10" cols="50" id="content" class="NormalTextBox"></TEXTAREA>
    </td></tr>
    <tr><td align="center">
    <INPUT type="Submit" name="submit" value="提交" onmouseover="confirm()"/>
	<INPUT type="Reset" name="reset" value="重置" />
	<input type="hidden" name="action" value="4"/>
	<input type="hidden" name="gid" value="<%=gbean.getGoodID()%>"/>
    </td></tr>
    </table>
    </form>
    <%@ include file="root.jspf"%>
  <Script Language="JavaScript">
  
  
  function confirm()
	{
	
	if(document.addmsg.content.value=="")
	{
		window.alert("请输入内容");
		document.addmsg.content.focus();
		return false;
	}
	
	return true;
	}
</Script>
    
   