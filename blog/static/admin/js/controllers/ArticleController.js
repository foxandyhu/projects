define(["BlogApp"],function(BlogApp){
    BlogApp.controller("ArticleController",function($scope,$http,$location,$routeParams){
		$("#articlemenu").addClass("active");
		 $scope.loadTags = function () {
		    $http.get("/manage/blog/tags.html",{params:{pageNo:$scope.currentPage,name:$("#name").val()}},{cache:false}).success(function(data){
		    	console.log(data)
				if(data){$scope.items=data.items;$scope.pageCount = data.total;}
			});
        };
	});
});