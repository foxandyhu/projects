define(["BlogApp"], function (BlogApp) {
    BlogApp.controller("MemberController", function ($scope, $http, $location, $routeParams, Dialog, Resource) {
        $("#membermenu").addClass("active");
        $scope.loadMembers = function () {
		    $http.get("/manage/members/list.html",{params:{pageNo:$scope.currentPage,name:$("#name").val()}},{cache:false}).success(function(data){
				if(data){$scope.items=data.items;$scope.pageCount = data.total;}
			});
        };
        $scope.enableMember=function(){
            var label =this.item.is_enable?"禁用":"启用";
            var memberId=this.item.id;
            Dialog.confirm("dialog","您确定要"+label+"该会员吗?",function(r){if(r){
				$http.get("/manage/members/enable-"+memberId+".html",{cache:false}).success(function(data){
				    if(data.status){
				        $scope.loadMembers();
                    }else{
				        Dialog.show(data.message)
                    }
				});
			}});
        };
    });
});