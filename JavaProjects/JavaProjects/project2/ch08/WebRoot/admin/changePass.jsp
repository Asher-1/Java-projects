<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%@ include file="head.jspf"%>
<%@ page import="beans.*,ado.*" %>

<form action="servlet/changePass" method="POST" name="modifypass">
<table align="center">
<tr>
<td><b>新密码：</b></td>
<td><input type="password" id="newPass1" name="newPass1" class="NormalTextBox"/></td>
</tr>
<tr>
<td><b>确认新密码：</b></td>
<td><input type="password" id="newPass2" name="newPass2" class="NormalTextBox"/></td>
</tr>
<tr>
<td align="center" colspan="2">
<INPUT type="Submit" name="button2" value="提交" onmouseover="confirm()"/>
<INPUT type="Reset" name="button3" value="重置" />
</td>
</tr>
</table>
</form>

<script language="javascript">
function confirm()
{
	if(document.modifypass.newPass1.value=="")
	{
		window.alert("请输入密码");
		document.modifypass.newPass1.focus();
		return false;
	}
	if(document.modifypass.newPass2.value=="")
	{
		window.alert("请再次输入密码");
		document.modifypass.newPass2.focus();
		return false;
	}
	if(document.modifypass.newPass1.value!=document.modifypass.newPass2.value)
	{
		window.alert("两次输入密码必须相同");
		document.modifypass.newPass2.focus();
		return false;
	}
	return true;
}
</script>