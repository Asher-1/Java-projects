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
    <td colspan="2" align="center"><FONT color="white" size="5"><b>�û���¼</b></FONT>
    <br><hr>
    </td>
  </tr>
  <tr>
    <td colspan="2"><br><br></td>
  </tr>
  <tr>
    <td width="47%" align="right">�û���:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    <td width="53%" ><input name="username" type="text" id="username" class="NormalTextBox"></td>
  </tr>
  <tr>
    <td align="right">�� &nbsp;��:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    <td><input name="password" type="password" id="password" class="NormalTextBox"></td>
  </tr>
  <tr>
    <td colspan="2" align="center"><input name="users" type="radio" value="user" checked>
    ��ͨ�û�
    <input type="radio" name="users" value="admin">
    ����Ա<br>
    <br>
    <br><hr><br>
    <input type="submit" name="Submit" value="�ύ">
    <input type="reset" name="Submit2" value="����">
    <br>
    </td>
  </tr>
</table>
</form>
 <%@ include file="root.jspf"%>