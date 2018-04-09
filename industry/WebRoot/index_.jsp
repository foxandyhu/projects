<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>首页</title>
	<%@include file="res.jsp" %>
	<link href="<%=basePath%>resources/plugin/carousel/carousel.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class="mobile">
		<div class="top w">
	   		<div class="m_banner" id="owl">
	   			<c:forEach var="item" items="1,2,3,4,5">
					<a href="#" class="item"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/10250290397.png"></a>
	            </c:forEach>
	      	</div>
	      	 <div class="m_nav">
	      	 	<c:forEach var="item" items="${sellerTypes}">
	      			<a href="<%=basePath%>type/${item.id}.html"><img src="<%=basePath%>resources/images/ico_${item.id}.png"><span>${item.name}</span></a>
	      		</c:forEach>
			</div>
		</div>
  	</div>
	<div class="m_mall w">
		<div class="mall_title text-center"><span>— 推荐商家 —</span></div>
		<div class="mall_list">
			<c:forEach var="item" items="${recommends}">
		    	<a href="<%=basePath%>${item.id}.html" class="mall"><div class="mall_logo"><img src="${item.logo}" /></div><span>${item.name}</span></a>
		    </c:forEach>
		</div>
	</div>
	<div class="m_renqi w">
  		<div class="renqi_title"><span>人气商家</span><em><a href="javascript:void(0)" class="refresh"><img src="<%=basePath%>resources/images/shuaxin.png"></a></em></div>
	    <div class="renqi_list"></div>
    	<div class="bl_more"><a href="javascript:void(0)">加载更多</a></div>
	</div>
  	<%@include file="footer.jsp" %>
	<div class="backtop gotop none"></div>
	<script type="text/javascript" src="<%=basePath%>resources/plugin/carousel/carousel.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/index.js"></script>	
</body>
</html>