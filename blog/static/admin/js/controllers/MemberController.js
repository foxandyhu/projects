define(["BlogApp"],function(BlogApp){
	BlogApp.controller("MemberController",function($scope,$http,$location,$routeParams,Dialog,Resource){
		$("#vipmanager").addClass("active");
		$scope.loadMembers=function()
		{
			$http.get("manage/member/list.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
				if(data){$scope.items=data.datas;$scope.pageCount = data.totalPage;}
			});
		};

	});
});