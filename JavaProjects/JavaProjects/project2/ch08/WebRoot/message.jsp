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
	<FONT color="white" size="5"><b>����Ϣ</b></FONT>
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
    <td  align="left" width="10%" ><b>���⣺</b></td>
    <td><%=mbean.getTitle()%></td>
    </tr>
    
    <tr> 
    <td  align="left" valign="top" width="10%"><b>���ݣ�</b></td>
    <td><%=mbean.getContent()%></td>
    </tr>
    
    <tr> 
    <td  align="left" width="10%">
    <b>�����ˣ�</b>
    </td>
    <td>
    <%=ubean.getUserRealName()%>
    </td>
    </tr>
    
     <tr> 
    <td  align="left" valign="top" width="10%">
    <b>��ϵ��ʽ��</b>
    </td>
    <td>
    
 	��ϵ�绰��<%=ubean.getTel()%>&nbsp;&nbsp;
    �ֻ���<%=ubean.getMobile()%>&nbsp;&nbsp;<br>
    Email��<%=ubean.getEmail()%>&nbsp;&nbsp;
    QQ��<%=ubean.getQq()%>
    </td>
    </tr>
    
    <tr> 
    <td  align="left"width="10%">
    <b>���ڣ�</b>
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
    ÿҳ<%=request.getAttribute("rowsperpage")%>��&nbsp;
    ��<%=request.getAttribute("rowscount")%>��&nbsp;
    ��<%=request.getAttribute("pageno")%>ҳ&nbsp;
    ��<%=request.getAttribute("pagescount")%>ҳ
    <br>
    <%
      if(pageNo==1) { 
    	out.print(" ��ҳ  ��һҳ "); 
      }else {  
    %>   
    	<a href="javascript:gotoPage(1)">��ҳ</a>&nbsp;
    	<a href="javascript:gotoPage(<%=pageNo-1%>)">��һҳ</a>&nbsp;
    <%
      }
    %>
    <%
      if(pageNo==pagesCount) { 
    	out.print("��һҳ  βҳ");   
      } else {  
    %>   
    	<a href="javascript:gotoPage(<%=pageNo+1%>)">��һҳ</a>&nbsp;
    	<a href="javascript:gotoPage(<%=pagesCount%>)">βҳ</a>
    <%
      }
    %>
    &nbsp;ת����
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
    </select>ҳ
  </form>
</td>
</tr>

<tr>
<td>
<form action="servlet/messageAdmin"  name="addmsg">
<hr>
<table width="100%">
<tr>
<td align="center" colspan="2"><b><font color="white">���������Ϣ</font></b></td>
<tr>

<tr>
<td width="30%" align="right">���⣺</td>
<td align="left">
<input type="text" id="title" name="title" class="NormalTextBox" size="50">
</td>
</tr>

<tr>
<td width="30%" align="right" valign="top">���ݣ�</td>
<td align="left">
<TEXTAREA name="content" rows="10" cols="50" id="content" class="NormalTextBox"></TEXTAREA>
</td>
</tr>

<tr>
<td colspan="2" align="center">
<INPUT type="Submit" name="submit" value="�ύ" onmouseover="confirm()"/>
<INPUT type="Reset" name="reset" value="����" />
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
		window.alert("����������");
		document.addmsg.username.focus();
		return false;
	}
	if(document.addmsg.content.value=="")
	{
		window.alert("����������");
		document.addmsg.content.focus();
		return false;
	}
	
	return true;
	}
</Script>
