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
				<FONT color="white" size="5"><b>�û�ע��</b></Font>
				<br>
				<hr>
			</td>
		</tr>
		<tr>
			<td align="right">
				�û�����
			</td>
			<td></td>
			<td align="left">
				
					<INPUT type="text" name="username" id="username" class="NormalTextBox">
				
			</td>
		</tr>
		<tr>
			<td width="45%" align="right">
				���룺
			</td>
			<td width="10%"></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="password1" type="password" name="password1">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				�ٴ��������룺
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="password2" type="password" name="password2">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				��ʵ������
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="realname" type="text" name="realname">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				��ϵ�绰��
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="tel" type="text" name="tel">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				�ֻ���
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="mobile" type="text" name="mobile">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				Email��
			</td>
			<td></td>
			<td align="left">
				
					<INPUT class="NormalTextBox" id="email" type="text" name="email">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				QQ��
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
				<INPUT type="Submit" name="button1" value="�ύ" onclick="" onblur="" onmouseover="confirm()"/>
				<INPUT type="Reset" name="button2" value="����" />
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
		window.alert("�������û���");
		document.register.username.focus();
		return false;
	}
	if(document.register.password1.value=="")
	{
		window.alert("����������");
		document.register.password1.focus();
		return false;
	}
	if(document.register.password2.value=="")
	{
		window.alert("���ٴ���������");
		document.register.password2.focus();
		return false;
	}
	if(document.register.password1.value!=document.register.password2.value)
	{
		window.alert("�����������������ͬ");
		document.register.password2.focus();
		return false;
	}
	if(document.register.realname.value=="")
	{
		window.alert("��������ʵ����");
		document.register.realname.focus();
		return false;
	}
	if(document.register.tel.value=="")
	{
		window.alert("��������ϵ�绰");
		document.register.tel.focus();
		return false;
	}
	if(isNaN(document.register.tel.value))
	{
		window.alert("�绰����������");
		document.register.tel.focus();
		return false;
	}
	if(isNaN(document.register.mobile.value))
	{
		window.alert("�ֻ�����������");
		document.register.mobile.focus();
		return false;
	}
	if(document.register.email.value.length<6||document.register.email.value.indexOf('@',0)<0)
	{
		window.alert("�ʼ���ʽ����ȷ");
		document.register.email.focus();
		return false;
	}
	if(isNaN(document.register.qq.value))
	{
		window.alert("qq����������");
		document.register.qq.focus();
		return false;
	}
	return true;
}
</script>