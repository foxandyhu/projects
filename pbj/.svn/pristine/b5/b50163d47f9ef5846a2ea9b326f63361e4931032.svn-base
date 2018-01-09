<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
   	<base href="<%=basePath%>">
	<title>${sysConfig.appName}</title>
	<%@include file="tag.jsp" %>
  </head>
  <body>
    <div class="wrapper">
      	<%@include file="top.jsp" %>
      	<%@ include file="left.jsp" %>
      	<div class="content-wrapper">
      		<ng-view></ng-view>
      	</div>
    	<%@include file="foot.jsp" %>
    	<loading></loading>
    	<tipsuccess></tipsuccess>
    </div>
    <script type="text/javascript" data-main="resources/js/main" src="<%=basePath%>resources/plugin/requirejs/require.js"></script>
  </body>
</html>
