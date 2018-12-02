<%@ page language="java" import="java.util.*,beans.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%
request.setCharacterEncoding("gb2312");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
<%
	UserBean ubean = (UserBean)session.getAttribute("user");
%>
<form method="POST" name="userinfo" action="servlet/userAdmin">
<table  width="100%" height="100%">
<tr>
			<td colspan="3" align="center">
				<FONT color="white" size="5"><b>修改用户信息</b></Font>
				<br>
				<hr>
			</td>
		</tr>
<tr>
			<td align="right" width="45%">
				真实姓名：
			</td>
			<td width="10%"></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="realname" type="text" name="realname" value='<%=ubean.getUserRealName()%>'/>
				
			</td>
		</tr>
		<tr>
			<td align="right">
				联系电话：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="tel" type="text" name="tel" value='<%=ubean.getTel()%>'/>
				
			</td>
		</tr>
		<tr>
			<td align="right">
				手机：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="mobile" type="text" name="mobile" value='<%=ubean.getMobile()%>'/>
				
			</td>
		</tr>
		<tr>
			<td align="right">
				Email：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="email" type="text" name="email" value='<%=ubean.getEmail()%>' />
				
			</td>
		</tr>
		<tr>
			<td align="right">
				QQ：
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="qq" type="text" name="qq" value="<%=ubean.getQq()%>" />
				
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<hr>
				<br>
				<INPUT type="Submit" name="button1" value="修改"  onmouseover="confirm()"/>
				<INPUT type="Reset" name="button2" value="重置" />
				<input type="hidden" name="action" value="2">
			</td>
		</tr>  
</table>
</form>
 <%@ include file="root.jspf"%>
 <script language="javascript">
function confirm()
{
	if(document.userinfo.realname.value=="")
	{
		window.alert("请输入真实姓名");
		document.userinfo.realname.focus();
		return false;
	}
	if(document.userinfo.tel.value=="")
	{
		window.alert("请输入联系电话");
		document.userinfo.tel.focus();
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