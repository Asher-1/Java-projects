<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%request.setCharacterEncoding("gb2312");
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			%>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
<form method="POST" name="sell" action="servlet/goodAdmin">
	<table width="100%" height="100%">
		<tr>
			<td colspan="3" align="center">
				<FONT color="white" size="5"><b>��Ʒ����</b></Font>
				<br>
				<hr>
			</td>
		</tr>
		<tr>
			<td align="right">
				��Ʒ����
			</td>
			<td></td>
			<td align="left">

				<INPUT type="text" name="goodname" id="goodname" class="NormalTextBox">

			</td>
		</tr>
		<tr>
			<td width="45%" align="right">
				�������ࣺ
			</td>
			<td width="10%"></td>
			<td align="left">
				<%
					Collection cat = (Collection)request.getAttribute("categories");
				%>
				<SELECT name="category" id="category" class="NormalTextBox">
				<%for (Iterator i = cat.iterator(); i.hasNext();) {
					CategoryBean cbean = (CategoryBean) i.next();
				%>
					<option value=<%=cbean.getCategoryID()%>>
						<%=cbean.getCategoryName()%>
					</option>
				<%}
				%>
				</SELECT>


			</td>
		</tr>
		<tr>
			<td align="right">
				Ԥ���ۼۣ�
			</td>
			<td></td>
			<td align="left">

				<INPUT class="NormalTextBox" id="price" type="text" name="price">

			</td>
		</tr>
		<tr>
			<td align="right" valign="top">
				��Ʒ������
			</td>
			<td></td>
			<td align="left">
				<TEXTAREA name="description" rows="10" cols="50" id="description" class="NormalTextBox"></TEXTAREA>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<hr>
				<br>
				<INPUT type="Submit" name="button1" value="�ύ"  onmouseover="confirm()" />
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
	if(document.sell.goodname.value=="")
	{
		window.alert("��������Ʒ��");
		document.sell.goodname.focus();
		return false;
	}
	 if(isNaN(document.sell.price.value))
	{
		window.alert("�۸����������");
		document.sell.price.focus();
		return false;
	}
	return true;
}
</script>