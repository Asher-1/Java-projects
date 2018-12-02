<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<% String xmldoc = session.getAttribute("xmldoc").toString();%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>实例二：Ajax+XML+XSL实现页面数据格式化显示</title>
<link rel="stylesheet" type="text/css" href="/ajax/images/Style.css">
</head>
<script language='javascript'>
function showxmlcontent()
//显示xml内容
{
	var xmldoc = new ActiveXObject("Microsoft.XMLDOM");  //声明一个ActiveXObject对象，该对象可以装载一个xml对象
	xmldoc.async = false;  //设置为不必同步传输
	xmldoc.loadXML('<%=xmldoc%>');  //显示session中存储的xml内容
	document.getElementById('xmlcontent').value=xmldoc.xml;  //将层xmlcontent内容设为xml内容
}
function showxslcontent()
//显示xsl内容
{
	var xsl = new ActiveXObject("Microsoft.XMLDOM");
	xsl.async = false;
	xsl.load("/ajax/xslt/knowledgelist.xsl");  //ActiveXObject对象xsl装载样式文件knowledgelist.xsl
	document.getElementById('xslcontent').value=xsl.xml;   // 在层xslcontent中显示xsl样式表的内容
}
function showxml()
//显示格式化后的内容
{
	var xmldoc = new ActiveXObject("Microsoft.XMLDOM");
	xmldoc.async = false;
	xmldoc.loadXML('<%=xmldoc%>');		//xmldoc中装载xml文件
	var xsl = new ActiveXObject("Microsoft.XMLDOM");
	xsl.async = false;
	xsl.load("/ajax/xslt/knowledgelist.xsl"); //xsl中装载xsl文件
	var arti = xmldoc.transformNode(xsl);  //xml+xsl按样式表格式显示文件内容
	document.write(arti); //将转化后的结果输出
}
function edit(kid,teachername,description) //编辑知识点函数
{
	alert('编辑知识点：\n编号：'+kid+'\n作者：'+teachername+'\n描述：'+description);
}
function del(kid) //删除知识点函数
{
	alert('删除知识点：\n编号：'+kid);
}
</script>
<body><br><br>
<h1>实例二：Ajax+XML+XSL实现页面数据格式化显示</h1>
<h2>生成的xml内容（数据内容用xml表示）：</h2>
<textarea rows="20" cols="100" id='xmlcontent'></textarea>
<h2>xsl样式表的内容：</h2>
<textarea rows="38" cols="100" id='xslcontent'></textarea>
<h2>Ajax+XML+XSL实现页面数据格式化显示:</h2>
<script type="text/javascript">
showxmlcontent();  //显示xml文件内容
showxslcontent(); //显示xsl文件内容
showxml();  //按一定样式显示xml
</script>
<br><br><br><br>
</body>
</html>