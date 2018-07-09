define(["BlogApp"],function(BlogApp){
    BlogApp.controller("LogController",function($scope,$http){
		$("#logmenu").addClass("active");
		$scope.loadLoginLogs=function(){
			 $http.get("/manage/logs/login.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
                if(data){$scope.items=data.items;$scope.pageCount = data.page_count;}
            });
		};
	});
});