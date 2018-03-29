<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>商户列表</title>
	<%@include file="res.jsp" %>
</head>

<body>
	<div class="m_renqi w">
		<div class="renqi_title"><span>人气商家</span><em><a href="#"><img src="<%=basePath%>resources/images/shuaxin.png"></a></em></div>
		<div class="renqi_list">
	    	<c:forEach var="item" items="[1,1,1,1,1]">
				<a href="renqi_view.html">
			        <div class="renqi_content">
			            <div class="bl_img"><img src="http://baoliao.178hui.com/upload/2015/0710/12332059693.jpg" /></div>
			            <div class="bl_right">
			                <div class="bl_title">韩国现代（HYUNDAI) BD-YS2003 多功能养生壶 煎药壶2.0L</div>
			                <div class="bl_note">手机端：99元包邮</div>
			                <div class="bl_tag">
			                    <div class="bl_price">￥99.00</div>
			                    <div class="bl_oprice">￥138.00</div>
			                    <div class="bl_time">07-10 12:33</div>
			                    <div class="bl_mall">京东商城</div>
			                </div>
			            </div>
			        </div> 
		        </a>
	    	</c:forEach>
	    </div>
    	<div class="bl_more"><a href="#">加载更多</a></div>
	</div>
  	<%@include file="footer.jsp" %>
</body>
</html>