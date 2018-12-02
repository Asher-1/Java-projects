<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%
request.setCharacterEncoding("gb2312");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
<form method="POST" name="login" action="servlet/login">
<table  width="100%" height="100%">
  <tr>
    <td colspan="2" align="center"><FONT color="white" size="5"><b>用户登录</b></FONT>
    <br><hr>
    </td>
  </tr>
  <tr>
    <td colspan="2"><br><br></td>
  </tr>
  <tr>
    <td width="47%" align="right">用户名:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    <td width="53%" ><input name="username" type="text" id="username" class="NormalTextBox"></td>
  </tr>
  <tr>
    <td align="right">密 &nbsp;码:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    <td><input name="password" type="password" id="password" class="NormalTextBox"></td>
  </tr>
  <tr>
    <td colspan="2" align="center"><input name="users" type="radio" value="user" checked>
    普通用户
    <input type="radio" name="users" value="admin">
    管理员<br>
    <br>
    <br><hr><br>
    <input type="submit" name="Submit" value="提交">
    <input type="reset" name="Submit2" value="重置">
    <br>
    </td>
  </tr>
</table>
</form>
 <%@ include file="root.jspf"%>