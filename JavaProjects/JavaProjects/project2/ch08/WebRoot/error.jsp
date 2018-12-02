
<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%
request.setCharacterEncoding("gb2312");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>error page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    
    <link rel="stylesheet" type="text/css" href="style.css">
   
  </head>
  
  <body>
  <center>
    <img src="images/error.gif"></img>
    <br>
    <%
    	String message = request.getParameter("msg");
      	out.println(message+"!");
     %>
     <br>
     <a href="javascript:history.back()"><font color="red">返回&lt;&lt;</font></a>
     <strong><label id="backTime"></label></strong>秒后自动返回上一页
<script language="javascript">
<!--
function clock()
{i=i-1
document.all.backTime.innerText=i;
if(i>0)
setTimeout("clock();",1000);
else window.history.back()}
var i=4
clock();
//-->
</script>
   </center>
  </body>
</html>
