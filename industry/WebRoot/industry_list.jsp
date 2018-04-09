<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>${typeName}</title>
	<%@include file="res.jsp" %>
</head>

<body>
	<div class="m_renqi w">
		<div class="renqi_title"><span>${typeName}</span><em><a href="javascript:void(0)" class="refresh"><img src="<%=basePath%>resources/images/shuaxin.png"></a></em></div>
		<div class="renqi_list"></div>
    	<div class="bl_more"><a href="javascript:void(0)">加载更多</a></div>
	</div>
  	<%@include file="footer.jsp" %>
  	<div class="backtop gotop none"></div>
  	<script type="text/javascript">window.typeId=${typeId}</script>
  	<script type="text/javascript" src="<%=basePath%>resources/js/industry_list.js"></script>
</body>
</html>