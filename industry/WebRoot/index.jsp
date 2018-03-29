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
	      		<a href="industry_list.jsp"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/m-index_10.png"><span>商城返利</span></a>
	            <a href="industry_list.jsp"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/m-index_12.png"><span>优惠爆料</span></a>
	            <a href="industry_list.jsp"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/m-index_14.png"><span>淘宝返利</span></a>
	            <a href="industry_list.jsp"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/m-index_16.png"><span>购物资讯</span></a>
	            <a href="industry_list.jsp"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/m-index_22.png"><span>比价网</span></a>
	            <a href="industry_list.jsp"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/m-index_24.png"><span>有奖签到</span></a>
	            <a href="industry_list.jsp"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/m-index_26.png"><span>订单管理</span></a>
	            <a href="industry_list.jsp"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/m-index_27.png"><span>会员中心</span></a>
			</div>
		</div>
  	</div>
	<div class="m_mall w">
		<div class="mall_title text-center"><span>— 推荐商家 —</span></div>
		<div class="mall_list">
			<c:forEach var="item" items="1,1,1,1">
		    	<a href="#" class="mall"><div class="mall_logo"><img src="http://www.178hui.com/upload/2014/0427/17020977409.gif" /></div><span>最高返 <i>2.8%</i></span></a>
		    </c:forEach>
		</div>
	</div>
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
	<div class="backtop gotop none"></div>
	<script type="text/javascript" src="<%=basePath%>resources/plugin/carousel/carousel.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/index.js"></script>	
</body>
</html>