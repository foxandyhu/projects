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
		.header{background-color: #2cb796;text-align: center;padding-top: 1em;}
		.header p:nth-child(1){font-size:1.5em;color:#fff;}
		.header p:nth-child(2){font-size:1em;color:#006666;}
		.header p:nth-child(3){font-size:3em;color:#fff;}  
		.content h3{font-size:1.5em;margin:0;padding-left:0.5em;line-height:2em;background-color: #f4f4f4;color:#999;}
		.content div{padding-left:0.5em;line-height:4em;background-color: #fff;border-bottom: 1px solid #eee}
		.content div span{margin-left:1em;}
		.foot{margin: 1em 0;text-align: center;width:100%;} 
		.foot span{color:#ccc;margin-left: 0.5em}
	</style>
  </head>
  <body>
    <div class="container-fluid">
	    <div class="row header">
	    	<p style="margin-bottom: 0px;">总步数<span id="totalStep">${totalStep}</span></p>
			<div class="row" style="margin-top:10px"> 
				<p class="col-xs-4" style="font-size: 12px;color:#ffffff"><span style="float:left; padding-left:5px;">鸡只编号${serialNo}</span></p>
		    	<p class="col-xs-4" style="font-size: 12px;color:#ffffff">生长天数<span>${totalDays}天</span></p>
				<p class="col-xs-4" style="font-size: 12px;color:#ffffff"><span style="float:right; padding-right:5px;">今日步数
				${curStep}步</span>
				</p>
			</div>
	    </div>
	    <ul class="row nav nav-tabs">
		  <li class="active" style="width:50%;text-align: center;"><a href="#home" data-toggle="tab">基本信息</a></li>
		  <li style="width:50%;text-align: center;"><a href="#panel1" data-toggle="tab">步数统计</a></li>
		</ul>
		<div class="row tab-content">
		  <div class="tab-pane active" id="home">
			    <div class="content">
					<div>出生日期<span>2017-10-10</span></div>
					<div>入场日期<span>2017-10-10</span></div>
					<div>出栏日期<span>2017-11-10</span></div>
			    </div>
			    <div class="content">
			        <h3>养殖信息</h3>
			    </div>
			    <div class="content">
					<div>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称<span>西丽水库养鸡场</span></div>
					<div>位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置
						<span>
							<a href="http://api.map.baidu.com/geocoder?location=${lat},${lon}&output=html&src=lwintel|iot">${location }</a>
						</span></div>
					<div>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;种<span>土鸡</span></div>
					<div>编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号<span>${serialNo}</span></div> 
			    </div>
		  </div>
		  <div class="tab-pane" id="panel1"  style="padding-top:3rem;">
		    <div  id="chart" style="min-width: 100%;height:400px;margin: 0 auto"></div>
		  </div>
		</div>
		<div class="row foot"> 
			<p><img src="resources/images/logo-1.png"><span>深圳市联文智能技术有限公司</span></p>  
		</div> 
	</div>
  </body>
  <script src="resources/plugin/jquery/jquery.min.js"></script>
  <script src="resources/plugin/jquery/jquery.animateNumber.min.js"></script>
  <script src="resources/plugin/highchart/js/highcharts.js"></script>
  <script type="text/javascript">
  	var list = ${json};
	var days = list.length;
	$("#chart").highcharts({
		credits: {
            enabled:false
		},
		chart : {
			type : "spline"
		},
		legend: {                                                                    
            enabled: false                                                           
        },
		title : {
			text : null
		},
		subtitle : { text : "最近" + days + "天" },
		xAxis:{ 
		    labels:{ 
		        step:2
		    }
		},
		yAxis : {
			title : {
				text : "单位（步）"
			},
			labels: {
	            formatter: function() {
	                return this.value + "";
	            }
	        },
			min : 0,
			startOnTick : false
		},
		tooltip: {
		    shared: true,
		    useHTML: true,
		    headerFormat: '<span>日期 {point.key}</span><br>',
		    pointFormat: '<span>步数 {point.y}</span>'
		},
		series : [ {
			color: '#2cb796'
		} ]
	});
	var chart = $("#chart").highcharts();
	var arrayKey = new Array();
	var arrayValue = new Array();
	for ( var key in list) {
		arrayKey.push(list[key].time);
		arrayValue.push(list[key].step);
	}
	chart.xAxis[0].setCategories(arrayKey);
	chart.series[0].setData(arrayValue);

	var totalStep = ${totalStep};
	setInterval(function() {
		$.ajax({
			url : "qrcode/${serialNo}/dynamic.html",
			dataType : "json",
			success : function(msg) {
				var tempTotalStep = parseInt(msg);
				if (tempTotalStep > totalStep) {
					$('#totalStep').prop('number',totalStep).animateNumber(
						  {
						    number: tempTotalStep,
						    color: 'green', // require jquery.color
						    easing: 'linear' // require jquery.easing
						  },
						  500  
					); 
					totalStep = tempTotalStep;
				}
			}
		});
	}, 2000);
</script>
<script src="resources/plugin/bootstrap3.3.2/js/bootstrap.min.js"></script>
</html>
