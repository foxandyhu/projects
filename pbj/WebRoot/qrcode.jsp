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
	<style>
		.header{background-color: #2cb796;text-align: center;padding-top:30px;}
		.header p:nth-child(1){font-size:32px;color:#fff;}
		.header p:nth-child(2){font-size:22px;color:#006666;}
		.header p:nth-child(3){font-size:60px;color:#fff;}  
		.content h3{margin:0;padding-left:0.5em;line-height:50px;background-color: #f4f4f4;color:#999;}
		.content div{padding-left:0.5em;line-height:50px;background-color: #fff;border-bottom: 1px solid #eee}
		.content div span{margin-left:1em;}
		.foot{margin-bottom: 30px;text-align: center;margin-top: 50px;}
		.foot span{color:#ccc;margin-left: 0.5em}
	</style>
  </head>
  <body>
    <div class="container-fluid">
	    <div class="row header">
	    	<p>跑步鸡详情</p>
	    	<p style="margin-bottom: 0px;">-总步数-</p>
	    	<p>${totalStep}</p>
	    </div>
	    <div class="row content">
	        <h3>生长情况</h3>
	    </div>
	    <div class="row content">
			<div>生长天数<span>30天</span></div>
			<div>出生日期<span>2017-10-10</span></div>
			<div>入场日期<span>2017-10-10</span></div>
			<div>出栏日期<span>2017-11-10</span></div>
	    </div>
	    <div class="row content">
	        <h3>养殖信息</h3>
	    </div>
	    <div class="row content">
			<div>名称<span>西丽水库养鸡场</span></div>
			<div>位置<span>116.173728,40.006518</span></div>
			<div>品种<span>土鸡</span></div>
			<div>编号<span>${serialNo}</span></div>
	    </div>
	    <div class="row content">
	        <h3>记步明细</h3>
	    </div>
	    <div class="row content">
			<table class="table">
				<thead>
			  		<tr>
			  			<td>日期</td><td>步数</td><td>活动时间</td>
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
		<div class="row foot">
			<img src="resources/images/logo-1.png"><span>深圳市联文智能技术有限公司</span>
		</div>
	</div>
  </body>
</html>
