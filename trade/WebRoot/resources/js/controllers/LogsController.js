define(["App"],function(App){
	App.controller("LogsController",function($scope,$http){
		$scope.loadSysLogs=function()
		{
			$http.get("manage/logs.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
				if(data){$scope.items=data.datas;$scope.pageCount = data.totalPage;}
			});
		};
		$scope.loadSysLoginLogs=function()
		{
			$http.get("manage/login/logs.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
				if(data){$scope.items=data.datas;$scope.pageCount = data.totalPage;}
			});
		}
	});
});