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
 

  
	<%
		int pageNo = ((Integer)request.getAttribute("pageno")).intValue();
   		int pagesCount = ((Integer)request.getAttribute("pagescount")).intValue();
		
	%>
	
	<table width="100%">
	<tr>
			<td colspan="6" align="center">
				<form  action="servlet/goodAdmin" name="search">
				<FONT color="white" size="5"><b>物品浏览</b></Font><br>
				
				物品搜索
				<input type="text" name="key" id="key" class="NormalTextBox"/>
				<select name="type">
				<option value="name">按名称</option>
				<option value="content">按内容</option>
				<select />
				<input type="submit" name="submit" value="搜索"/>
				<input type="hidden" name="action" value="8"/>
				
				
				<hr>
				 </form>
				</td>
	</tr>
    <tr bgcolor="#CCCCCC"> 
      <td bgcolor="#CCCCCC"><div align="center"><strong>时间</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>物品名</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>所属种类</strong></div></td>
      <td bgcolor="#CCCCCC"><div align="center"><strong>物品描述</strong></div></td>
      <td><div align="center"><strong>初订价格</strong></div></td>
      <td><div align="center"><strong>卖主</strong></div></td>
    </tr>
    <%
   
    Collection goods = (Collection)request.getAttribute("goods");
    
    if(goods!=null){
      for(Iterator i = goods.iterator();i.hasNext();) { 
        GoodBean gbean = (GoodBean)i.next();
       
    %>
    <tr> 
      <td bgcolor="#99CCCC"><div align="center"><%=DatetimeUtils.getDateByDate(gbean.getDatetime())%></div></td>
      <td bgcolor="#99CC99" ><div align="center"><a href='<%="servlet/goodAdmin?action=9&gid="+gbean.getGoodID()%>'><%=gbean.getGoodName()%></a></div>
      </td>
      <td bgcolor="#CCFF66">
        <div align="center">
        <a href='<%="servlet/goodAdmin?action=6&cid="+gbean.getCategoryID()%>'><%=gbean.getCategoryName()%></a>
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
    	
      }
      
      }
    %>
    <tr>
			<td colspan="6" align="center">
				<br>
				<hr>
			</td>
	</tr>
    </table>
   
 <table width="100%">   
<tr>
<td  align="center">
 <form action="servlet/goodAdmin?action=5" method="POST" name="PageForm">
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
 </td></tr></table>
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