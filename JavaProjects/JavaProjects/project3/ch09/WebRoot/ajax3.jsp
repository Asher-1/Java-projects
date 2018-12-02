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
		<title>实例三：Ajax实现无刷新数据搜索</title>
		<link rel="stylesheet" type="text/css" href="/ajax/images/Style.css">
		<script language="javascript" src="/ajax/js/prototype.js"></script>
	</head>
	<script type="text/javascript">
	function edittask(taskid){
		alert('编辑作业：'+taskid);	
	}
	function deltask(taskid){
		alert('删除作业：'+taskid);
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
						document.getElementById('xmlcontent').value=xmldoc.xml; //在层xmlcontent中显示xml
						var xsl = new ActiveXObject("Microsoft.XMLDOM");
						xsl.async = false;
						xsl.load("/ajax/xslt/teachertask.xsl");
						document.getElementById('xslcontent').value=xsl.xml; //在层xslcontent中显示xsl
						var arti = xmldoc.transformNode(xsl); 
						document.getElementById("searchresult").innerHTML=arti;
					}else{
						alert("获取信息失败！");
						//当获取请求失败时，弹出窗口显示错误信息
				}
			}
		}
		xmlhttp.open("post", "servlet/Ajax3Servlet", true);
		//向servlet发送Ajax请求，获取当前章中的节信息
		xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
		var paras = "keywords="+document.getElementById("keywords").value;
		//发送ajax请求所带的参数信息
		xmlhttp.send(paras);
	 }
	</script>
	<body>
	<br><br>
	<h1>实例三：Ajax实现无刷新数据搜索</h1>
	<h3>搜索作业(作业名称):<input type="text" name="keywords" id="keywords"/>
	<input type='button' onclick='search()' value='search'/>
	</h3>
	<div id='searchresult'></div>
	<h2>搜索时只刷新搜索结果那块内容，下面图片在搜索后不刷新，减少了网络数据传输：</h2>
	<img src='/ajax/images/notrefresh.jpg' height='200'/>
	<h2>生成的xml内容（搜索结果用xml表示）：</h2>
	<textarea rows="21" cols="120" id='xmlcontent'></textarea>
	<h2>xsl样式表的内容（事先已经定义好，固定不变）：</h2>
	<textarea rows="40" cols="120" id='xslcontent'></textarea>
	<br><br>
	</body>
	<script type="text/javascript">
	search();
	</script>
</html>