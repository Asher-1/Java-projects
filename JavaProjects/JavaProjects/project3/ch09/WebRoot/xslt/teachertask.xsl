<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/records">
	<table border='1'>
	<tr>
	<th>作业名称</th>
	<th>所属章</th>
	<th>所属节</th>
	<th>布置时间</th>
	<th>完成期限</th>
	<th>作业类型</th>
	<th>公开答案</th>
	<th>操作</th>
	</tr>
		<xsl:apply-templates select="record" />
	</table>	
	</xsl:template>
	
	<xsl:template match="record">
	<xsl:variable name="taskid" select="taskid" />
	<tr>
	<td><xsl:value-of select="taskname" /></td>
	<td><xsl:value-of select="chaptername" /></td>
	<td><xsl:value-of select="nodename" /></td>
	<td><xsl:value-of select="substring(createtime,0,12)" /></td>
	<td><xsl:value-of select="substring(endtime,0,12)" /></td>
	<td><xsl:value-of select="type" /></td>
	<td>
	<xsl:if test="pubanswer='1'">是</xsl:if>
	<xsl:if test="pubanswer!='1'">否</xsl:if>
	</td>
	<td>
	<input type = 'button' value='编辑' onclick='edittask("{$taskid}")'/>
	<input type = 'button' value='删除' onclick='deltask("{$taskid}")'/>
	</td>
	</tr>
	</xsl:template>
</xsl:stylesheet>
