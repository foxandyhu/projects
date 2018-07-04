define(["BlogApp"],function(BlogApp){
    BlogApp.controller("ArticleController",function($scope,$http,$location,$routeParams,Resource,Dialog,Util,SysInit){
			$("#articlemenu").addClass("active");
		$scope.loadTags = function () {
				$http.get("/manage/blog/tags.html",{params:{pageNo:$scope.currentPage,name:$("#name").val()}},{cache:false}).success(function(data){
					if(data){$scope.items=data.items;$scope.pageCount = data.page_count;}
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
					target.find("i").removeClass("fa-folder");
					target.find("i").addClass(" fa-folder-open");
				}else{
					target.find("i").removeClass("fa-folder-open");
					target.find("i").addClass("fa-folder");
				}
			});
		};

       $scope.loadArticles = function () {
       		$scope.checked = [];
       		$scope.is_disabled=true;
       		$scope.selected_all=false;
       		var cid=undefined;
       		if($scope.cid){
       			cid=$scope.cid;
			}
		    $http.get("/manage/blog/articles.html",{params:{cid:cid,pageNo:$scope.currentPage,title:$("#title").val()}},{cache:false}).success(function(data){
				if(data){$scope.items=data.items;$scope.pageCount = data.page_count;}
			});
        };
       $scope.searchArticle=function(){
       		$scope.currentPage=1;
       		$scope.loadArticles();
	   };
       $scope.chooseCategory=function(e){
		   $(".tree_choose").removeClass("tree_choose");
		   $(e.currentTarget).addClass("tree_choose");
		   var cid=undefined;
		   $scope.currentPage=1;
		   if(this.item){cid=this.item.id;}else{cid=this.c.id;}
		   $scope.cid=cid;
           $scope.loadArticles();
	   };
       $scope.checked = [];
       $scope.is_disabled=true;
       $scope.selectChkAll=function () {
       		if($scope.selected_all){
       			$scope.checked = [];
                angular.forEach($scope.items, function (i) {
                    i.checked = true;
                    $scope.checked.push(i.id);
                });
			}else{
                angular.forEach($scope.items, function (i) {
                    i.checked = false;
                    $scope.checked = [];
                });
			}
           $scope.is_disabled=!$scope.checked.length>0
       };
       $scope.selectOne=function () {
           angular.forEach($scope.items , function (i) {
               var index = $.inArray(i.id,$scope.checked);
               if(i.checked && index == -1) {
                   $scope.checked.push(i.id);
               } else if (!i.checked && index != -1){
                   $scope.checked.splice(index, 1);
               };
           });
           $scope.selected_all=$scope.items.length == $scope.checked.length;
           $scope.is_disabled=!$scope.checked.length>0;
       };
       $scope.toSetTop=function (flag) {
       		if($scope.checked.length<=0){
       			Dialog.show("请选中要操作的文章!");
       			return;
			}
			 $http.get("/manage/blog/articles/settop.html",{params:{flag:flag,ids:$scope.checked.join(",")}},{cache:false}).success(function(data){
			 	$scope.loadArticles();
			});
       };
       $scope.toSetRecommend=function (flag) {
       		if($scope.checked.length<=0){
       			Dialog.show("请选中要操作的文章!");
       			return;
			}
			 $http.get("/manage/blog/articles/setrecommend.html",{params:{flag:flag,ids:$scope.checked.join(",")}},{cache:false}).success(function(data){
			 	$scope.loadArticles();
			});
       };
       $scope.toSetVerify=function (flag) {
       		if($scope.checked.length<=0){
       			Dialog.show("请选中要操作的文章!");
       			return;
			}
			 $http.get("/manage/blog/articles/setverify.html",{params:{flag:flag,ids:$scope.checked.join(",")}},{cache:false}).success(function(data){
			 	$scope.loadArticles();
			});
       };
       $scope.initArticleValidate=function(){
       		Resource.init(["bootstrapValidator"],function(){
       			$("#articleForm").bootstrapValidator({
					feedbackIcons:SysInit.validateCss,
					fields:{
							category_id:{validators: {notEmpty:{message:"请选择类别!"},callback:{message:"请选择类别!",callback:function(value,validator){return parseInt(value)>0;}}}},
							title:{validators: {notEmpty:{message:"请输入标题!"},stringLength:{min:1,max:50,message:"标题在1-50个字符之间!"}}},
							summary:{validators: {notEmpty:{message:"请输入摘要!"},stringLength:{min:1,max:200,message:"摘要在1-200个字符之间!"}}},
							seq:{validators: {notEmpty:{message:"请输入排序序号!"},integer:{message:"请输入有效整数数字的序号!"}}},
							content:{validators: {notEmpty:{message:"请输入内容!"},callback:{message:"请输入内容!",callback:function(value,validator){return value.length>0;}}}}
					}
				});
			});
	   };
       $scope.initArticleAdd=function(){
       		Resource.init(["select2"],function () {
       			Util.createSimpleEditor("content");
       			$('.select2').select2();
       			$scope.loadCategorys();
       			$scope.loadTags();
            });
	   };
       $scope.addArticle=function(){
       		$("#articleForm").data("bootstrapValidator").validate();
            if($("#articleForm").data("bootstrapValidator").isValid())
			{
				$("#tag_ids").val($("#tags").val().join(","));
				$http({url:"/manage/blog/articles/add.html",method:"POST",data:$("#articleForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
					$location.path("/articles");
				});
			}
	   };
       $scope.detailArticle=function(){
       		var articleId=$routeParams.articleId;
       		$http.get("/manage/blog/articles/"+articleId+".html",{cache:false}).success(function (data) {
       			$scope.article=data;
       			if($scope.article && $scope.article.logo){
					$scope.imageResult={
						path:$scope.article.logo,
						fullUrl:$scope.article.logo
					};
                }
       			$scope.initArticleAdd();
                $scope.ss=$scope.article.category_id
            });
	   };
       $scope.checkExistTagChoose=function(tags,id){
		   	if(!tags) {return false;}
			var ids=[];
		   tags.forEach(function (item) {
			  ids.push(item.id);
		   });
           var flag=$.inArray(id,ids);
           return flag>=0;
	   };
       $scope.editArticle=function () {
           $("#articleForm").data("bootstrapValidator").validate();
           if($("#articleForm").data("bootstrapValidator").isValid())
           {
               $("#tag_ids").val($("#tags").val().join(","));
               $http({url:"/manage/blog/articles/edit.html",method:"POST",data:$("#articleForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
                   Dialog.successTip("保存成功!");
                   $location.path("/articles");
               });
           }
       };
	});
});