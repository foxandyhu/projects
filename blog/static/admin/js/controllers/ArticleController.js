define(["BlogApp"],function(BlogApp){
    BlogApp.controller("ArticleController",function($scope,$http,$location,$routeParams){
			$("#articlemenu").addClass("active");
		$scope.loadTags = function () {
				$http.get("/manage/blog/tags.html",{params:{pageNo:$scope.currentPage,name:$("#name").val()}},{cache:false}).success(function(data){
					if(data){$scope.items=data.items;$scope.pageCount = data.total;}
				});
		};
		$scope.addTag=function(){
            var tr=$("<tr><td><input name='name' type='text'/></td><td></td></tr>");
            var input=tr.find("input");
            input.blur(function(){
            	var name=input.val();
            	if(name){
                    $http.get("/manage/blog/tags/add.html",{params:{name:name}},{cache:false}).success(function(data){
                        $scope.loadTags();
                    });
				}
                tr.remove();
			});
            $("table tr:eq(0)").after(tr);
            input.trigger("focus");
		};
		$scope.editTag=function(e){
		 	var target=$(e.currentTarget);
		 	var item=this.item;
		 	var td=target.parent().prev();
		 	var input=$("<input name='name' type='text'/>");
		 	td.html(input);
		 	input.val(item.name);
		 	input.trigger("focus");
		 	input.blur(function(){
		 		if(input.val()==item.name){td.html(item.name);target.show();return;}
                $http.get("/manage/blog/tags/edit.html",{params:{id:item.id,name:input.val()}},{cache:false}).success(function(data){
                    $scope.loadTags();
                });
			});
		 	target.hide();
		};
	});
});