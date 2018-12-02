<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>实例一：Ajax实现级联菜单</title>
		<link rel="stylesheet" type="text/css" href="/ajax/images/Style.css">
	</head>
	<script type="text/javascript">
	  function getnodelist(){
	  	var xmlhttp;
		try{
			xmlhttp=new XMLHttpRequest();
		}catch(e){
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function(){
			if (4==xmlhttp.readyState){
				if (200==xmlhttp.status){
						document.getElementById("result").innerHTML = xmlhttp.responseText ;
						//当获取请求成功时，将得到的节列表至于result这个div中
					}else{
						alert("获取章节信息失败！");
						//当获取请求失败时，弹出窗口显示错误信息
				}
			}
		}
		xmlhttp.open("post", "servlet/getnode", true);
		//向servlet发送Ajax请求，获取当前章中的节信息
		xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
		var paras = "chapterid="+document.getElementById("curchapter").value;
		//发送ajax请求所带的参数信息
		xmlhttp.send(paras);
	  }
  	</script>
	<body>
		<br>
		<br>
		<h1>
			实例一：Ajax实现级联菜单
		</h1>
		<h2>
			填写作业基本信息
		</h2>
		<form action="" method="post">
			<table border="0" align="center">
				<tr>
					<td width='30%'></td>
					<td>
						<table width="100%" border="0" align="center">
							<tr>
								<td>
									作业名称：
									<input type="text" name="name" id="name" />
									<font color='red'> *</font>
									<br>
									<br>
								</td>
							</tr>
							<tr>
								<td>
									请选择章：
									<select name="chapter" id="curchapter" onchange="getnodelist()">
										<!-- 显示章列表 -->
										<c:forEach items='${chapterlist}' var='chapter'>
											<option value='${chapter.chapterid }'>
												${chapter.chaptername }
											</option>
										</c:forEach>
									</select>
									<br>
									<br>
								</td>
							</tr>
							<tr>
								<td>
									<div id='result'>
										请选择节：
										<select name='node'>
											<c:forEach items='${nodelist}' var='node'>
												<!-- 显示节列表 -->
												<option value='${node.nodeid }'>
													${node.nodename }
												</option>
											</c:forEach>
										</select>
									</div>
									<br>
								</td>
							</tr>
							<tr>
								<td>
									完成期限：
									<input type='text' name="endTime" />
									<font color='red'> *</font>
									<br>
									<br>
								</td>
							</tr>
							<tr>
								<td>
									<input type='submit' value='生成作业' />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
