<%@ page language="java" pageEncoding="gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>ʵ������Ajaxʵ����ˢ����������</title>
		<link rel="stylesheet" type="text/css" href="/ajax/images/Style.css">
		<script language="javascript" src="/ajax/js/prototype.js"></script>
	</head>
	<script type="text/javascript">
	function edittask(taskid){
		alert('�༭��ҵ��'+taskid);	
	}
	function deltask(taskid){
		alert('ɾ����ҵ��'+taskid);
	}
	function search(){
	  	var xmlhttp;
		try{
			xmlhttp=new XMLHttpRequest();
		}catch(e){
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function(){
			if (4==xmlhttp.readyState){
				if (200==xmlhttp.status){
						var xmldoc = new ActiveXObject("Microsoft.XMLDOM");
						xmldoc.async = false;
						xmldoc.loadXML(xmlhttp.responseText);
						document.getElementById('xmlcontent').value=xmldoc.xml; //�ڲ�xmlcontent����ʾxml
						var xsl = new ActiveXObject("Microsoft.XMLDOM");
						xsl.async = false;
						xsl.load("/ajax/xslt/teachertask.xsl");
						document.getElementById('xslcontent').value=xsl.xml; //�ڲ�xslcontent����ʾxsl
						var arti = xmldoc.transformNode(xsl); 
						document.getElementById("searchresult").innerHTML=arti;
					}else{
						alert("��ȡ��Ϣʧ�ܣ�");
						//����ȡ����ʧ��ʱ������������ʾ������Ϣ
				}
			}
		}
		xmlhttp.open("post", "servlet/Ajax3Servlet", true);
		//��servlet����Ajax���󣬻�ȡ��ǰ���еĽ���Ϣ
		xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
		var paras = "keywords="+document.getElementById("keywords").value;
		//����ajax���������Ĳ�����Ϣ
		xmlhttp.send(paras);
	 }
	</script>
	<body>
	<br><br>
	<h1>ʵ������Ajaxʵ����ˢ����������</h1>
	<h3>������ҵ(��ҵ����):<input type="text" name="keywords" id="keywords"/>
	<input type='button' onclick='search()' value='search'/>
	</h3>
	<div id='searchresult'></div>
	<h2>����ʱֻˢ����������ǿ����ݣ�����ͼƬ��������ˢ�£��������������ݴ��䣺</h2>
	<img src='/ajax/images/notrefresh.jpg' height='200'/>
	<h2>���ɵ�xml���ݣ����������xml��ʾ����</h2>
	<textarea rows="21" cols="120" id='xmlcontent'></textarea>
	<h2>xsl��ʽ������ݣ������Ѿ�����ã��̶����䣩��</h2>
	<textarea rows="40" cols="120" id='xslcontent'></textarea>
	<br><br>
	</body>
	<script type="text/javascript">
	search();
	</script>
</html>