<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/records">
<table align='center' border='1'>
	<tr align="center">
	<th width="10%">编号</th>
	<th width="10%">作者</th>
	<th width="10%">创建时间</th>
	<th width="10%">知识点名称</th>
	<th width="10%">说明</th>
	<th width="20%">操作</th>
	</tr>
		<xsl:apply-templates select="record" />
	</table> 
	</xsl:template>
	
	<xsl:template match="record">
	<xsl:variable name="kid" select="kid" />
	<xsl:variable name="teachername" select="teachername" />
	<xsl:variable name="description" select="description" />
	<tr align="center">
	<td><xsl:value-of select="kid" /></td>
	<td><xsl:value-of select="teachername" /></td>
	<td><xsl:value-of select="substring(createtime,0,12)" /></td>
	<td><xsl:value-of select="kname" /></td>
	<td><xsl:value-of select="description" /></td>
	<td>
	<input type = 'button' value='编辑' onclick='edit("{$kid}","{$teachername}","{$description}")'/>
	<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
	<input type = 'button' value='删除' onclick='del("{$kid}")'/>
	</td>
	</tr>
	</xsl:template>
</xsl:stylesheet>
