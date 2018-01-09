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
	<title>记步记录概览</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" href="<%=basePath%>resources/plugin/bootstrap3.3.2/css/bootstrap.min.css">
	<style>
		.container{width:1000;margin:auto; } 
		.qrcode{padding:1rem;text-align: center;}
		.col-xs-2{width:20%}
	</style>
  </head> 
  <body>
    <div class="container">
    	<div class="row" style="height:5rem;">
    		
    	</div>
	    <div class="row content">
			<c:forEach var="item" items="${list}" varStatus="c"> 
		      		<div class="col-xs-2 qrcode">  
		      			<span  id="${item.serialNo }" lang="${item.serialNo }"></span><br> 
		      			<span>${item.step }</span>
					</div>
			</c:forEach>
		</div>
	</div>
  </body>
  <script src="resources/plugin/jquery/jquery.min.js"></script>
  <script src="resources/plugin/jquery/jquery.qrcode.min.js"></script>
  <script src="resources/plugin/jquery/jquery.animateNumber.min.js"></script>
  <script type="text/javascript"> 
  	var h=screen.height;
  	var serialNo = ${serialNos};
  	if(h<=600){ 
  		for(var i in serialNo ){
	  		$('#'+serialNo[i]).qrcode({width:50,height:50,text:'http://pbj.lwintel.com/qrcode/'+$('#'+serialNo[i]).attr('lang')+'.html'})
	  	}
  	}
  	else if(h<=800){
  		for(var i in serialNo ){
	  		$('#'+serialNo[i]).qrcode({width:90,height:90,text:'http://pbj.lwintel.com/qrcode/'+$('#'+serialNo[i]).attr('lang')+'.html'})
	  	}
  	}else if(h<=1000){
  		for(var i in serialNo ){
  			$('#'+serialNo[i]).qrcode({width:110,height:110,text:'http://pbj.lwintel.com/qrcode/'+$('#'+serialNo[i]).attr('lang')+'.html'})
	  	}
  	}else{
  		for(var i in serialNo ){ 
  			$('#'+serialNo[i]).qrcode({width:150,height:150,text:'http://pbj.lwintel.com/qrcode/'+$('#'+serialNo[i]).attr('lang')+'.html'})
	  	}
  	} 
  	setInterval(function(){
  		$.ajax({
			url : "qrcodes/dynamic.html",
			dataType : "json",
			success : function(msg) {
				for(var i in msg ){ 
		  			var $$ = $('#'+msg[i].serialNo).next().next();
		  			//var $$ = $('#'+msg[i].serialNo).next().next().text(msg[i].step);
		  			$$.prop('number',$$.text()).animateNumber(
							  {
							    number: msg[i].step,
							    color: 'green', // require jquery.color
							    easing: 'linear' // require jquery.easing
							  },
							  500 
						); 
			  	}
			}
		});
  	}, 2000);
  </script>
</html>
