<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%@ include file="head.jspf"%>
<%@ page import="beans.*,ado.*" %>

<form action="servlet/changePass" method="POST" name="modifypass">
<table align="center">
<tr>
<td><b>�����룺</b></td>
<td><input type="password" id="newPass1" name="newPass1" class="NormalTextBox"/></td>
</tr>
<tr>
<td><b>ȷ�������룺</b></td>
<td><input type="password" id="newPass2" name="newPass2" class="NormalTextBox"/></td>
</tr>
<tr>
<td align="center" colspan="2">
<INPUT type="Submit" name="button2" value="�ύ" onmouseover="confirm()"/>
<INPUT type="Reset" name="button3" value="����" />
</td>
</tr>
</table>
</form>

<script language="javascript">
function confirm()
{
	if(document.modifypass.newPass1.value=="")
	{
		window.alert("����������");
		document.modifypass.newPass1.focus();
		return false;
	}
	if(document.modifypass.newPass2.value=="")
	{
		window.alert("���ٴ���������");
		document.modifypass.newPass2.focus();
		return false;
	}
	if(document.modifypass.newPass1.value!=document.modifypass.newPass2.value)
	{
		window.alert("�����������������ͬ");
		document.modifypass.newPass2.focus();
		return false;
	}
	return true;
}
</script>