<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%request.setCharacterEncoding("gb2312");
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			%>
<%@ include file="head.jspf"%>
<%@ include file="left.jspf"%>
<%@ page import="beans.*,ado.*,utils.*" %>
<%
	UserBean ubean = (UserBean)request.getAttribute("ubean");
%>
<table width="100%">
<tr>
			<td colspan="3" align="center">
				<FONT color="white" size="5"><b>�û���Ϣ</b></Font>
				<br>
				<hr>
			</td>
		</tr>
<tr>
<td width="30%">&nbsp;</td>
<td align="left" >
<b>�û���:</b>
</td>
<td>
<%=ubean.getUserName()%>
</td>
</tr>
<tr>
<td width="30%">&nbsp;</td>
<td align="left" >
<b>��ʵ����:</b>
</td>
<td>
<%=ubean.getUserRealName()%>
</td>
</tr>
<tr>
<td width="30%">&nbsp;</td>
<td align="left" >
<b>��ϵ�绰</b>
</td>
<td>
<%=ubean.getTel()%>
</td>
</tr>
<tr>
<td width="30%">&nbsp;</td>
<td align="left" >
<b>�ֻ���</b>
</td>
<td>
<%=ubean.getMobile()%>
</td>
</tr>
<tr>
<td width="30%">&nbsp;</td>
<td align="left" >
<b>Email:</b>
</td>
<td>
<%=ubean.getEmail()%>
</td>
</tr>
<tr>
<td width="30%">&nbsp;</td>
<td align="left" >
<b>QQ:</b>
</td>
<td>
<%=ubean.getQq()%>

</td>
</tr>
<tr>
			<td colspan="3" align="center">
				<br>
				<hr>
			</td>
		</tr>
</table>
<%@ include file="root.jspf"%>
