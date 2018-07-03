define(["BlogApp"],function(BlogApp){
	BlogApp.controller("SystemController",function($scope,$http,Resource,Dialog,Util,SysInit,$location){
		$("#sysmenu").addClass("active");
		$scope.initSysTab=function(){
		    $('a[data-toggle="tab"]').on("shown.bs.tab", function (e) {
		        var index=e.target.tabIndex
                if(index==1){
		            $scope.initSysSettingValidate();
		            $scope.loadSysInfo();
                }else if(index==2){
                    $scope.loadSysCopyRight();
                }else if(index==3){

                }else if(index==4){

                }
            });
		     $(".nav-tabs li:eq(2) a").tab("show");
		};
		$scope.loadSysInfo=function () {
          $http.get("/manage/system/web_setting.html",{cache:false}).success(function(data){
                $scope.sysinfo=data;
                if($scope.sysinfo.logo && $scope.sysinfo.logo){
					$scope.imageResult={
						path:$scope.sysinfo.logo,
						fullUrl:$scope.sysinfo.logo
					};
                }
            });
        };
		$scope.initSysSettingValidate=function(){
		    Resource.init(["bootstrapValidator"],function(){
       			$("#sysinfoForm").bootstrapValidator({
					feedbackIcons:SysInit.validateCss,
					fields:{
					    name:{validators: {notEmpty:{message:"请输入网站名称!"},stringLength:{min:1,max:20,message:"网站名称在1-20个字符之间!"}}},
                        website:{validators: {notEmpty:{message:"请输入域名地址!"},uri:{message:"域名地址不正确!"}}}
					}
				});
			});
        };
		$scope.mergeSysInfo=function () {
          $("#sysinfoForm").data("bootstrapValidator").validate();
            if($("#sysinfoForm").data("bootstrapValidator").isValid())
			{
				$http({url:"/manage/system/web_setting.html",method:"POST",data:$("#sysinfoForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
					$location.path("/web_setting");
				});
			}
        };
		$scope.loadSysCopyRight=function () {
          $http.get("/manage/system/web_copyright.html",{cache:false}).success(function(data){
                $scope.copyright=data;
            });
        };
		$scope.mergeSysCopyRight=function () {
          $http({url:"/manage/system/web_copyright.html",method:"POST",data:$("#sysCopyRightForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
					$location.path("/web_setting");
				});
        };
		$scope.loadAbout=function(){
		    Resource.init(["kindeditor"],function () {
		        Util.createSimpleEditor("content");
                $http.get("/manage/system/about.html",{cache:false}).success(function(data){
                    $scope.content=data;
                });
            });
        };
		$scope.megerAbout=function(){
		    $http({url:"/manage/system/about.html",method:"POST",data:$("#aboutForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
				});
        };
		$scope.loadContact=function(){
		    Resource.init(["kindeditor"],function () {
		        Util.createSimpleEditor("content");
                $http.get("/manage/system/contact.html",{cache:false}).success(function(data){
                    $scope.content=data;
                });
            });
        };
		$scope.megerContact=function(){
		    $http({url:"/manage/system/contact.html",method:"POST",data:$("#contactForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
				});
        };
	});
});