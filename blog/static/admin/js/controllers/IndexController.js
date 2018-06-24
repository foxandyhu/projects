define(["BlogApp"],function(BlogApp){
    BlogApp.controller("IndexController",function($scope,$http,$location,$routeParams,Dialog,Resource){
		$("#homemanager").addClass("active");
		$scope.loadOrderReport=function(){

			$scope.name='zhangsan';
		};
	});
});