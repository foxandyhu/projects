define(["BlogApp"],function(BlogApp){
    BlogApp.controller("ArticleController",function($scope,$http,$location,$routeParams,Resource,Dialog){
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
            	var name=$.trim(input.val());
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
		$scope.loadCategorys=function(){
			$http.get("/manage/blog/categorys.html",{cache:false}).success(function(data){
                    $scope.categorys=data;
                });
		};
		$scope.editCategory=function(e){
			var name="";
			var id=0;
			if(this.sub){name=this.sub.name;id=this.sub.id;}else {name=this.item.name;id=this.item.id;}
			var target=$(e.currentTarget);
			var label=target.parent().find("label");
			var input=$("<input name='name' type='text'/>");
			input.val(name);
			label.html(input)
			input.trigger("focus");
			input.blur(function(){
		 		if(input.val()==name){label.html(name);target.show();return;}
                $http.get("/manage/blog/categorys/edit.html",{params:{id:id,name:input.val()}},{cache:false}).success(function(data){
                    $scope.loadCategorys();
                });
			});
		 	target.hide();
		};
        $scope.addCategory=function(e){
        	var parent_id=0;
        	if(this.item){
        		parent_id=this.item.id;
            }
            var label=$("<label class='list-group-item'><input name='name' type='text'/></label>");
            var input=label.find("input");
            input.blur(function(){
                var name=$.trim(input.val());
                if(name){
                    $http.get("/manage/blog/categorys/add.html",{params:{name:name,parent_id:parent_id}},{cache:false}).success(function(data){
                        $scope.loadCategorys();
                    });
                }
                label.remove();
            });
            $(e.currentTarget).parent().parent().next().before(label);
            input.trigger("focus");
        };
        $scope.delCategory=function(){
            var id=0;
            if(this.sub){id=this.sub.id;}else {id=this.item.id;}
            Dialog.confirm("dialog","将会删除该类别下的子类别，确定吗?",function(r){if(r){
                $http.get("/manage/blog/categorys/del-"+id+".html",{cache:false}).success(function(data){
                    if(data.status){
                        $scope.loadCategorys();
                    }else{
                        Dialog.show(data.message)
                    }
                });
            }});
		};
       $scope.categoryTreeChange=function (e) {
    		var target=$(e.currentTarget);
			var content =target.next();
			content.fadeToggle(function(){
				if(content.is(":visible")){
					target.prev().removeClass("fa-folder");
					target.prev().addClass(" fa-folder-open");
				}else{
					target.prev().removeClass("fa-folder-open");
					target.prev().addClass("fa-folder");
				}
			});
		};

       $scope.loadArticls = function () {
		    $http.get("/manage/blog/articles.html",{params:{pageNo:$scope.currentPage,title:$("#title").val()}},{cache:false}).success(function(data){
				if(data){$scope.items=data.items;$scope.pageCount = data.total;}
			});
        };
       $scope.searchArticle=function(){
       		$scope.currentPage=1;
       		$scope.loadArticls();
	   };
	});
});