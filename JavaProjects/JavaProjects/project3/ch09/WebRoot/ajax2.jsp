<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<% String xmldoc = session.getAttribute("xmldoc").toString();%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>ʵ������Ajax+XML+XSLʵ��ҳ�����ݸ�ʽ����ʾ</title>
<link rel="stylesheet" type="text/css" href="/ajax/images/Style.css">
</head>
<script language='javascript'>
function showxmlcontent()
//��ʾxml����
{
	var xmldoc = new ActiveXObject("Microsoft.XMLDOM");  //����һ��ActiveXObject���󣬸ö������װ��һ��xml����
	xmldoc.async = false;  //����Ϊ����ͬ������
	xmldoc.loadXML('<%=xmldoc%>');  //��ʾsession�д洢��xml����
	document.getElementById('xmlcontent').value=xmldoc.xml;  //����xmlcontent������Ϊxml����
}
function showxslcontent()
//��ʾxsl����
{
	var xsl = new ActiveXObject("Microsoft.XMLDOM");
	xsl.async = false;
	xsl.load("/ajax/xslt/knowledgelist.xsl");  //ActiveXObject����xslװ����ʽ�ļ�knowledgelist.xsl
	document.getElementById('xslcontent').value=xsl.xml;   // �ڲ�xslcontent����ʾxsl��ʽ�������
}
function showxml()
//��ʾ��ʽ���������
{
	var xmldoc = new ActiveXObject("Microsoft.XMLDOM");
	xmldoc.async = false;
	xmldoc.loadXML('<%=xmldoc%>');		//xmldoc��װ��xml�ļ�
	var xsl = new ActiveXObject("Microsoft.XMLDOM");
	xsl.async = false;
	xsl.load("/ajax/xslt/knowledgelist.xsl"); //xsl��װ��xsl�ļ�
	var arti = xmldoc.transformNode(xsl);  //xml+xsl����ʽ���ʽ��ʾ�ļ�����
	document.write(arti); //��ת����Ľ�����
}
function edit(kid,teachername,description) //�༭֪ʶ�㺯��
{
	alert('�༭֪ʶ�㣺\n��ţ�'+kid+'\n���ߣ�'+teachername+'\n������'+description);
}
function del(kid) //ɾ��֪ʶ�㺯��
{
	alert('ɾ��֪ʶ�㣺\n��ţ�'+kid);
}
</script>
<body><br><br>
<h1>ʵ������Ajax+XML+XSLʵ��ҳ�����ݸ�ʽ����ʾ</h1>
<h2>���ɵ�xml���ݣ�����������xml��ʾ����</h2>
<textarea rows="20" cols="100" id='xmlcontent'></textarea>
<h2>xsl��ʽ������ݣ�</h2>
<textarea rows="38" cols="100" id='xslcontent'></textarea>
<h2>Ajax+XML+XSLʵ��ҳ�����ݸ�ʽ����ʾ:</h2>
<script type="text/javascript">
showxmlcontent();  //��ʾxml�ļ�����
showxslcontent(); //��ʾxsl�ļ�����
showxml();  //��һ����ʽ��ʾxml
</script>
<br><br><br><br>
</body>
</html>