<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>商户详情</title>
	<%@include file="res.jsp" %>
	<link href="<%=basePath%>resources/plugin/carousel/carousel.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>resources/plugin/typicons/typicons.min.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class="mobile">
		<div class="top w">
	   		<div class="m_banner" id="owl">
	   			<c:forEach var="item" items="1,2,3,4,5">
					<a href="#" class="item"><img src="http://www.17sucai.com/preview/1983/2015-09-23/m.178hui_.com_/images/10250290397.png"></a>
	            </c:forEach>
	      	</div>
		</div>
  	</div>
  	<div class="cls"></div>
  	<div class="list-group-item list-group-item-default">
  		<div class="text-center">深圳食品有限公司</div>
  		<div class="huangguan" ><img src="<%=basePath%>resources/images/huangguan.png"/></div>
  	</div>
  	<div class="list-group-item list-group-item-default">
  		<div class="row">
  			<div class="col-1">
  				<span class="ico_color ico_location typcn typcn-location"></span>
  			</div>
  			<div class="col-8">
				<small>深圳市宝安区西乡流塘村花安居五项十三号606室</small>
			</div>
			<div class="col-2">
				<span class="badge">
					<a href="tel:11111111111"><h4><span class="ico_phone typcn typcn-phone"></span></h4></a>
				</span>
			</div>
		</div>
  	</div>
  	<div class="cls"></div><div class="cls"></div>
  	<div class="list-group-item list-group-item-default">
  		<small><span class="ico_color typcn typcn-wi-fi"></span> wifi&nbsp;&nbsp;&nbsp;&nbsp;</small>
  		<small><span class="ico_color typcn typcn-wi-fi"></span> 停车位&nbsp;&nbsp;&nbsp;&nbsp;</small>
  		<small><span class="ico_color typcn typcn-wi-fi"></span> 支付宝支付&nbsp;&nbsp;&nbsp;&nbsp;</small>
  		<small><span class="ico_color typcn typcn-wi-fi"></span> 微信支付&nbsp;&nbsp;&nbsp;&nbsp;</small>
  		<div class="cls"></div>
  		<small>营业时间:09:00~18:00</small>
  	</div>
  	<div class="cls"></div><div class="cls"></div><div class="cls"></div><div class="cls"></div>
	<div class="container">
	        尔斯道夫公司Beiersdorf A G（简称BDF）1882年创立于德国汉堡，致力于开发，生产及销售高品质的护肤品，创口贴及胶带等品牌产品。事业体分成三大部份：化妆品事业部（占62%），工业胶带TESA事业部（占17%），医疗用品事业部∶（占21%）。员工近2万人。行销100余国。
		一百多年以来,公司所推崇的“研究,创新,追求高品质”的经营哲学使公司的研发拥有坚实的基础。1911年诞生的油包水专利配方的妮维雅润肤霜是公司在化妆品事业部研发的开始。今天,在全球公认的最具权威性的肌肤及医学研究中心—BDF公司的PGU研究中心,有一百多位博士正在研发自然,高效的产品,其成果已使“妮维雅”成为全球最大的护肤用品品牌。其品牌形象 —“妮维雅能给肌肤最温和的呵护”已深入人心.在欧洲,“妮维雅”更已成为皮肤保养的代名词。妮维雅产品在身体保养，脸部保养，防晒，唇部保养，个人清洁，男士护肤等品类已稳居欧洲市场排名第一。2003年8月美国商业周刊杂志公布的最新全球100个最有价值品牌排行榜上，妮维雅品牌名列第92位，品牌价值增长8%。2007年德国拜尔斯道夫出资3.17亿欧元（折合约人民币35亿元），购入丝宝国际集团旗下丝宝日化85%的股份。
		1994年6月8日，Nivea(Shanghai)Co., Ltd.妮维雅(上海)有限公司成立了，当时她是德国拜尔斯道夫公司与上海飞妮丝工贸公司(上海凤凰日用化学有限公司的子公司)的合资企业。 2001年2月，妮维雅(上海)有限公司成为德国拜尔斯道夫公司的独资子公司
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