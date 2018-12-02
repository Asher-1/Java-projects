<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%request.setCharacterEncoding("gb2312");
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			%>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
<form method="POST" name="register" action="servlet/userAdmin">
	<table width="100%" height="100%">
		<tr>
			<td colspan="3" align="center">
				<FONT color="white" size="5"><b>用户注册</b></Font>
				<br>
				<hr>
			</td>
		</tr>
		<tr>
			<td align="right">
				用户名：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT type="text" name="username" id="username" class="NormalTextBox">
				
			</td>
		</tr>
		<tr>
			<td width="45%" align="right">
				密码：
			</td>
			<td width="10%"></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="password1" type="password" name="password1">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				再次输入密码：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="password2" type="password" name="password2">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				真实姓名：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="realname" type="text" name="realname">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				联系电话：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="tel" type="text" name="tel">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				手机：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="mobile" type="text" name="mobile">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				Email：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="email" type="text" name="email">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				QQ：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="qq" type="text" name="qq">
				
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<hr>
				<br>
				<INPUT type="Submit" name="button1" value="提交" onclick="" onblur="" onmouseover="confirm()"/>
				<INPUT type="Reset" name="button2" value="重置" />
				<input type="hidden" name="action" value="4">
			</td>
		</tr>
	</table>
</form>
<%@ include file="root.jspf"%>
<script language="javascript">
function confirm()
{
	if(document.register.username.value=="")
	{
		window.alert("请输入用户名");
		document.register.username.focus();
		return false;
	}
	if(document.register.password1.value=="")
	{
		window.alert("请输入密码");
		document.register.password1.focus();
		return false;
	}
	if(document.register.password2.value=="")
	{
		window.alert("请再次输入密码");
		document.register.password2.focus();
		return false;
	}
	if(document.register.password1.value!=document.register.password2.value)
	{
		window.alert("两次输入密码必须相同");
		document.register.password2.focus();
		return false;
	}
	if(document.register.realname.value=="")
	{
		window.alert("请输入真实姓名");
		document.register.realname.focus();
		return false;
	}
	if(document.register.tel.value=="")
	{
		window.alert("请输入联系电话");
		document.register.tel.focus();
		return false;
	}
	if(isNaN(document.register.tel.value))
	{
		window.alert("电话必须是数字");
		document.register.tel.focus();
		return false;
	}
	if(isNaN(document.register.mobile.value))
	{
		window.alert("手机必须是数字");
		document.register.mobile.focus();
		return false;
	}
	if(document.register.email.value.length<6||document.register.email.value.indexOf('@',0)<0)
	{
		window.alert("邮件格式不正确");
		document.register.email.focus();
		return false;
	}
	if(isNaN(document.register.qq.value))
	{
		window.alert("qq必须是数字");
		document.register.qq.focus();
		return false;
	}
	return true;
}
</script>