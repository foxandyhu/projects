define(["App"],function(App){
	App.constant("RestaurantStatus",[{name:"营业中",value:1},{name:"打烊中",value:2},{name:"停业中",value:3}])
	.controller("RestaurantController",function($scope,$http,$location,$routeParams,Dialog,Resource,Util,RestaurantStatus){
		$scope.RestaurantStatus=RestaurantStatus;
		$scope.initRestaurantValidate=function(){
			Util.createSimpleEditor("restaurantRemark");
			Resource.init(["bootstrapValidator"],function(){
				$("#restaurantForm").bootstrapValidator({
					feedbackIcons:{valid:"glyphicon glyphicon-ok",invalid:"glyphicon glyphicon-remove",validating:"glyphicon glyphicon-refresh"},
					fields:
					{
						name:{validators: {notEmpty:{message:"请输入商户名称!"},stringLength:{min:1,max:20,message:"名称在1~20个字符之间!"}}},
						cellphone:{validators: {notEmpty:{message:"请输入商户联系电话!"}}},
						address:{validators: {notEmpty:{message:"请输入商户地址!"},stringLength:{min:1,max:100,message:"地址在1~100个字符之间!"}}},
						longitude:{validators: {notEmpty:{message:"请在地图上选项地址经度!"}}},
						latitude:{validators: {notEmpty:{message:"请在地图上选项地址纬度!"}}},
						authorKey:{validators: {notEmpty:{message:"请输入门店授权码!"},stringLength:{min:20,max:30,message:"授权码在20~30个字符之间!"}}}
					}
				});
			});
		};
		$scope.initRestaurantSafetyValidate=function()
		{
			Resource.init(["bootstrapValidator"],function(){
				$("#restaurantSafetyForm").bootstrapValidator({
					feedbackIcons:{valid:"glyphicon glyphicon-ok",invalid:"glyphicon glyphicon-remove",validating:"glyphicon glyphicon-refresh"},
					fields:
					{
						userName:{validators: {notEmpty:{message:"请输入用户名!"},stringLength:{min:6,max:20,message:"用户名在6~20个字符之间!"}}},
						password:{validators: {notEmpty:{message:"请输入您的您新密码!"},stringLength:{min:6,max:50,message:"密码在6~50个字符之间!"},identical:{field:"confirmPassword",message:"两次输入的密码不一致!"}}},
						confirmPassword:{validators: {notEmpty:{message:"请输入您的确认密码!"},stringLength:{min:6,max:50,message:"密码在6~50个字符之间!"},identical:{field:"password",message:"两次输入的密码不一致!"}}},
						name:{validators: {notEmpty:{message:"请输入您的姓名!"}}},
						email:{validators: {notEmpty:{message:"请输入您的邮件地址!"}}}
					}
				});
			});	
		};
		$scope.loadRestaurant=function()
		{
			$http.get("manage/restaurant.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
				if(data){$scope.picServer=data.picServer;$scope.items=data.datas;$scope.pageCount = data.totalPage;}
			});
		};
		$scope.delRestaurant=function(restaurantId)
		{
			Dialog.confirm("restaurantDialog","您确定要删除该商户吗?",function(r)
					{
						if(r)
						{
							$http.get("manage/restaurant/del/"+restaurantId+".html",{cache:false}).success(function(){
								Dialog.successTip("删除成功!");
								$scope.loadRestaurant();
							});	
						}
					});
		};
		$scope.detailRestaurant=function()
		{
			Util.createSimpleEditor("restaurantRemark");
			var restaurantId=$routeParams.restaurantId;
			$http.get("manage/restaurant/"+restaurantId+".html",{cache:false}).success(function(data){
				$scope.restaurant=data;
				$scope.picServer=data.picServer;
				$scope.detailRestaurateur($scope.restaurant.id);
			});
		};
		$scope.detailRestaurateur=function(restaurantId)
		{
			$http.get("manage/restaurateur/"+restaurantId+".html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
				$scope.flag=data?true:false;
				if(data){$scope.restaurateur=data;}
			});
		};
		$scope.editRestaurant=function()
		{
			$("#restaurantForm").data('bootstrapValidator').validate();
			if($("#restaurantForm").data("bootstrapValidator").isValid())
			{	
				var params={id:$scope.restaurant.id,name:$scope.restaurant.name,address:$scope.restaurant.address,
						cellphone:$scope.restaurant.cellphone,logo:$scope.restaurant.logo,longitude:$scope.restaurant.longitude,
						authorKey:$scope.restaurant.authorKey,status:$scope.restaurant.status,
						latitude:$scope.restaurant.latitude,remark:$("#restaurantRemark").val(),authorKey:$scope.restaurant.authorKey};
				$http.get("manage/restaurant/edit.html",{params:params},{cache:false}).success(function(data){
					Dialog.successTip("修改成功!");
					$location.path("/restaurant/");
				});
			}
		};
		$scope.editRestaurateur=function()
		{
			$("#restaurantSafetyForm").data('bootstrapValidator').validate();
			if($("#restaurantSafetyForm").data("bootstrapValidator").isValid())
			{	
				var params={id:$scope.restaurateur.id,name:$scope.restaurateur.name,email:$scope.restaurateur.email,enable:$scope.restaurateur.enable};
				$http.get("manage/restaurateur/edit.html",{params:params},{cache:false}).success(function(data){
					Dialog.successTip("修改成功!");
					$location.path("/restaurant/");
				});
			}
		};
		$scope.addRestaurant=function()
		{
			$("#restaurantForm").data('bootstrapValidator').validate();
			if($("#restaurantForm").data("bootstrapValidator").isValid())
			{	
				$http.get("manage/restaurant/post.html?status="+$scope.restaurant.status+"&"+$("#restaurantForm").serialize(),{cache:false}).success(function(restaurantId){
					Dialog.successTip("新增成功,请继续为该商户设置账户!");
					$location.path("/restaurant/"+restaurantId);
				});
			}
		};
		$scope.addRestaurateur=function()
		{
			$("#restaurantSafetyForm").data('bootstrapValidator').validate();
			if($("#restaurantSafetyForm").data("bootstrapValidator").isValid())
			{	
				$http.get("manage/restaurateur/post.html?"+$("#restaurantSafetyForm").serialize(),{cache:false}).success(function(restaurantId){
					Dialog.successTip("新增成功!");
					$location.path("/restaurant/");
				});
			}
		};
		$scope.uploadRestaurantLogo=function(obj)
		{
			var img=$(obj).siblings("img.preImg");var path=$(obj).siblings(".prePath");
			Resource.init(["ajaxupload"],function(){Util.uploadImg(obj,function(data,status)
					{
						img.attr("src",data.fullUrl);path.val(data.url);
						$scope.restaurant.logo=data.url;
					});
			});
		};
		$scope.restaurant={address:null,latitude:"",longitude:""};
		$scope.initRestaurantMap=function(){
			window.soso=window.soso||{};soso.maps=soso.maps||{};
			var url="http://open.map.qq.com/apifiles/v1.0";var verson="v1.0.160113.1";
			soso.maps.__load=function(apiLoad){delete soso.maps.__load;apiLoad([url,"",verson]);};var loadScriptTime=(new Date).getTime();
			$.getScript(url+"/app.js?v="+verson,function(){
				var latLng = new soso.maps.LatLng($scope.restaurant.latitude, $scope.restaurant.longitude);var geocoder=new soso.maps.Geocoder();
			    var map = new soso.maps.Map(document.getElementById("mapcontainer"),{center:latLng,zoomLevel:12});
				var infoWin = new soso.maps.InfoWindow({map: map});infoWin.open($scope.restaurant.address, latLng);
			    soso.maps.Event.addListener(map,"click", function(event) {$scope.restaurant.longitude=event.latLng.getLng();$scope.restaurant.latitude=event.latLng.getLat();$scope.$apply();});
			    var navControl = new soso.maps.NavigationControl({align: soso.maps.ALIGN.TOP_RIGHT,margin: new soso.maps.Size(5, 15),map: map});
			    $("#btnSearch").bind("click",function()
			    {
			    	var marker=null;geocoder.geocode({"address": $scope.restaurant.address}, function(results, status) {
			    		if (status == soso.maps.GeocoderStatus.OK){map.setCenter(results.location);if (marker != null) {marker.setMap(null);}
			    		 marker = new soso.maps.Marker({map: map,position:results.location});}else{Dialog.show("检索没有结果，原因: " + status);}});
			    });
			});
		};
	});
});