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
	<table class="table">
		<caption>记步记录</caption>
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
  </body>
</html>
