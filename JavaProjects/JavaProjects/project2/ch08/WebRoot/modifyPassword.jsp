<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%
request.setCharacterEncoding("gb2312");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
<form method="POST" name="modifypass" action="servlet/userAdmin">
<table  width="100%" height="100%">
 <tr>
			<td colspan="3" align="center">
				<FONT color="white" size="5"><b>修改密码</b></Font>
				<br>
				<hr>
			</td>
		</tr> 
		<tr>
			<td width="45%" align="right">
			&nbsp;	
			</td>
			<td width="10%">&nbsp;</td>
			<td align="left">&nbsp;			
			</td>
		</tr>
		<tr>
			<td width="45%" align="right">
				输入新密码：
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
			<td width="45%" align="right">
			&nbsp;	
			</td>
			<td width="10%">&nbsp;</td>
			<td align="left">&nbsp;			
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<hr>
				<br>
				<INPUT type="Submit" name="button1" value="修改"  onmouseover="confirm()"/>
				<INPUT type="Reset" name="button2" value="重置" />
				<input type="hidden" name="action" value="3">
			</td>
		</tr>  
</table>
</form>
 <%@ include file="root.jspf"%>
 <script language="javascript">
function confirm()
{
	if(document.modifypass.password1.value=="")
	{
		window.alert("请输入密码");
		document.modifypass.password1.focus();
		return false;
	}
	if(document.modifypass.password2.value=="")
	{
		window.alert("请再次输入密码");
		document.modifypass.password2.focus();
		return false;
	}
	if(document.modifypass.password1.value!=document.modifypass.password2.value)
	{
		window.alert("两次输入密码必须相同");
		document.modifypass.password2.focus();
		return false;
	}
	return true;
}
</script>