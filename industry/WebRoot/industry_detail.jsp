<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>${sellerInfo.name}</title>
	<%@include file="res.jsp" %>
	<link href="<%=basePath%>resources/plugin/carousel/carousel.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>resources/plugin/typicons/typicons.min.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class="mobile">
		<div class="top w">
	   		<div class="m_banner" id="owl">
	   			<c:forEach var="item" items="${pics}">
					<a href="#" class="item"><img src="${item.url}"></a>
	            </c:forEach>
	      	</div>
		</div>
  	</div>
  	<div class="cls"></div>
  	<div class="list-group-item list-group-item-default">
  		<div class="text-center">${sellerInfo.name}</div>
  		<div class="huangguan" ><img src="<%=basePath%>resources/images/huangguan.png"/></div>
  	</div>
  	<div class="list-group-item list-group-item-default">
  		<div class="row">
  			<div class="col-1">
  				<span class="ico_color ico_location typcn typcn-location"></span>
  			</div>
  			<div class="col-8" style="padding-top:8px">
				<small>${sellerInfo.address}</small>
			</div>
			<div class="col-2">
				<span class="badge">
					<a href="tel:${sellerInfo.phone}"><h4><span class="ico_phone typcn typcn-phone"></span></h4></a>
				</span>
			</div>
		</div>
  	</div>
  	<div class="cls"></div><div class="cls"></div>
  	<div class="list-group-item list-group-item-default">
  		<c:forEach var="item" items="${sellerInfo.facilityNames}">  			  			
  			<small><span class="ico_color typcn typcn-wi-fi"></span> ${item}&nbsp;&nbsp;&nbsp;&nbsp;</small>
  		</c:forEach>
  		<div class="cls"></div>
  		<small>营业时间:${sellerInfo.shopHours}</small>
  	</div>
  	<div class="cls"></div><div class="cls"></div><div class="cls"></div><div class="cls"></div>
	<div class="container">
	        ${sellerInfo.remark }
	</div>
	<div class="cls"></div><div class="cls"></div><div class="cls"></div><div class="cls"></div>
	<div class="jumbotron jumbotron-fluid text-center" style="background-color:#f3f9f4">
			<button type="button" class="btn btn-outline-success"><span class="typcn typcn-heart-outline"></span>&nbsp;&nbsp;我是商家,免费入住</button>
	</div>	
  	<%@include file="footer.jsp" %>
	<script type="text/javascript" src="<%=basePath%>resources/plugin/carousel/carousel.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/index.js"></script>  	
</body>
</html>