define(["App"],function(App){
	App.controller("MessageController",function($rootScope,$http,$scope,Dialog,Util,Resource){
		$scope.loadSms=function()
		{
			$http.get("manage/sms.html",{cache:false}).success(function(data){
				if(data){$scope.items=data.datas;$scope.pageCount = data.totalPage;}
			});
		};
		$scope.resendSms=function()
		{
			var smsId=this.item.id;
			$http.get("manage/sms/resend/"+smsId+".html",{cache:false}).success(function(data){
				Dialog.successTip("发送成功!");
				$scope.loadSms();
			});
		};		
	});
});