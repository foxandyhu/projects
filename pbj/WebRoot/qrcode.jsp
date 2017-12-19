<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
  <head>
   	<base href="<%=basePath%>">
	<title>记步记录-${serialNo}</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" href="<%=basePath%>resources/plugin/bootstrap3.3.2/css/bootstrap.min.css">
  </head>
  <body>
  	<header class="container"><img src="http://www.yishubook.cn:80/resources/images/logo.png" height="55em"></header>
	<div class="panel panel-danger" style="margin-top:20px">
	    <div class="panel-heading">
	        <h3 class="panel-title">生长情况</h3>
	    </div>
	    <div class="panel-body">
			<div class="">总步数:${totalStep}步</div>
			<div class="">生长天数:30天</div>
			<div class="">出生日期:2017-10-10</div>
			<div class="">入场日期:2017-10-10</div>
			<div class="">出栏日期:2017-11-10</div>
	    </div>
	</div>
	<div class="panel panel-info">
	    <div class="panel-heading">
	        <h3 class="panel-title">养殖信息</h3>
	    </div>
	    <div class="panel-body">
			<div class="">名称:西丽水库养鸡场</div>
			<div class="">位置:116.173728,40.006518</div>
			<div class="">品种:土鸡</div>
			<div class="">编号:${serialNo}</div>
	    </div>
	</div>
	<div class="panel panel-success">
	    <div class="panel-heading">
	        <h3 class="panel-title">记步明细</h3>
	    </div>
	    <div class="panel-body">
			<table class="table">
				<thead>
			  		<tr>
			  			<th>日期</th><th>步数</th><th>活动时间</th>
			    	</tr>
			  	</thead>
				<tbody>
					<c:forEach var="item" items="${list}">
				    	<tr>
				      		<td>${item.time}</td>
				      		<td>${item.step}</td>
				      		<td>${item.actTime}</td>
				    	</tr>
					</c:forEach>
			  </tbody>
			</table>
	    </div>
	</div>
  </body>
</html>
