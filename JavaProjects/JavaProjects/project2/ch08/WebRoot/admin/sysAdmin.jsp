<%@ page language="java" import="java.util.*" pageEncoding="GB2312" contentType="text/html;charset=gb2312"%>
<%@ include file="head.jspf"%>
<%@ page import="beans.*,ado.*" %>
<form action="servlet/sysAdmin?action=1" method="post">
<table width="100%">
<tr>
<td colspan="2" align="center">

	<%
		String msg = request.getParameter("msg");
		if(msg!=null)
			out.print("<b><font size=4 color=red>"+msg+"</font></b>");
	%>

</td>
</tr>
<tr>
<td width="50%" align="center"><b>系统分页项数</b></td>
<td><input id="rowsPerPage" type="text" class="NormalTextBox" name="rowsPerPage" value='<%=request.getAttribute("rowsPerPage")%>'/></td>
</tr>
<tr>
<td colspan="2" align="center">
				<INPUT type="Submit" name="sub" value="更新" />
			</td>
</tr>
</table>
</form>


