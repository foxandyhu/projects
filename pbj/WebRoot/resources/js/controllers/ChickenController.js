define(["IotApp"],function(IotApp){	
	IotApp.controller("ChickenController",function($scope,$http,$location,$filter,$routeParams,Dialog,Resource,Util,DateFormat){
		var rule={valid:"glyphicon glyphicon-ok",invalid:"glyphicon glyphicon-remove",validating:"glyphicon glyphicon-refresh"};
		
		$scope.loadChicken=function()
		{
			$http.get("manage/chicken/list.html?&pageNo="+$scope.currentPage,{cache:false}).success(function(data){
				if(data){$scope.items=data.datas;$scope.pageCount = data.totalPage;}
			});
		};
		
		$scope.delChicken=function()
		{
			var serialNo=this.item.serialNo;
			Dialog.confirm("dialog","您确定要删除该鸡只吗?",function(r){
				if(r){
					$http.get("manage/chicken/del/"+serialNo+".html",{cache:false}).success(function(data){
						Dialog.successTip("删除成功!");
						$scope.loadChicken();
					});
				}
			});
		};
		
		$scope.initChickenValidate=function(){
			Resource.init(["bootstrapValidator","datepicker-zh"],function(){
				$('input[name="wearTime"]').datepicker({
					language : 'zh-CN',
					format : 'yyyy-mm-dd',
					autoclose : true
				})

				$("#chickenForm").bootstrapValidator({
					feedbackIcons:rule,
					fields:
					{
						seriesNo:{validators: {notEmpty:{message:"请选择设备序编号!"},stringLength:{min:1,max:50,message:"编号在1~50个字符之间!"}}},
						birthDays:{validators: {notEmpty:{message:"请输入出生日龄!"},stringLength:{min:1,max:3,message:"日龄在1~3个字符之间!"}}},
						wearTime:{validators: {notEmpty:{message:"请选择佩戴日期!"}}},
						farm:{validators: {notEmpty:{message:"请输入鸡只养殖场!"},stringLength:{min:1,max:20,message:"养殖场在1~20个字符之间!"}}},
						breeds:{validators: {notEmpty:{message:"请输入鸡只品种!"},stringLength:{min:1,max:10,message:"描述在1~10个字符之间!"}}},
						remark:{validators: {notEmpty:{message:"请输入鸡只品种特点!"},stringLength:{min:1,max:500,message:"描述在1~500个字符之间!"}}}
					}
				});
			});
		};

		$scope.addChicken=function()
		{
			$("#chickenForm").data("bootstrapValidator").validate();
			if($("#chickenForm").data("bootstrapValidator").isValid())
			{	
				$http.get("manage/chicken/post.html?"+$("#chickenForm").serialize(),{cache:false}).success(function(data){
					Dialog.successTip("新增成功!");
					$location.path("/chicken/add");
				});
			}
		};	
		
	});
});