define(["BlogApp"],function(BlogApp){
    BlogApp.controller("SeoController",function($scope,$http,Dialog){
        $("#seomenu").addClass("active");

        $scope.loadFriendlinks=function(){
            $http.get("/manage/seo/friendlink.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
                if(data){
                    if(data){$scope.items=data.items;$scope.pageCount = data.page_count;}
                }
            });
        };

        $scope.show_new_friendlink=true;
        $scope.createFriendlinkTr=function(data){
            var tr =document.createElement("tr");

            var td =document.createElement("td")
            var input = document.createElement("input");
            input.type="text";
            input.name="name";
            input.id="friend_name";
            input.className="form-control";
            input.placeholder="请输入链接网站名称"
            if(data){
                input.value=data.name;
            }
            td.appendChild(input);
            tr.appendChild(td);

            td =document.createElement("td")
            input = document.createElement("input");
            input.type="text";
            input.name="link";
            input.id="friend_link";
            input.className="form-control";
            input.placeholder="请输入链接网站地址"
            if(data){
                input.value=data.link;
            }
            td.appendChild(input);
            tr.appendChild(td);

            td =document.createElement("td")
            input = document.createElement("input");
            input.type="text";
            input.name="seq";
            input.id="friend_seq";
            input.className="form-control";
            input.placeholder="请输入排序序号"
            if(data){
                input.value=data.seq;
            }else{
                input.value=1;
            }
            td.appendChild(input);
            tr.appendChild(td);

            td =document.createElement("td")

            button = document.createElement("button");
            button.className="btn btn-xs btn-info";

            i = document.createElement("i");
            i.className="glyphicon glyphicon-plus";
            button.appendChild(i);
            text =document.createTextNode("保存");
            button.appendChild(text);
            $(button).on("click",function(){
		        var params=["name="+$("#friend_name").val(),"link="+$("#friend_link").val(),"seq="+$("#friend_seq").val()];
		        var url="/manage/seo/friendlink/add.html";
		        if(data){
		        	params.push("id="+data.id);
		        	url="/manage/seo/friendlink/edit.html";
				}
                $http({url:url,method:"POST",data:params.join("&"),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(){
                    Dialog.successTip("保存成功!");
                    $scope.loadFriendlinks();
                    tr.remove();
                    $scope.show_new_friendlink=true;
                });
            });
            td.appendChild(button);
            td.appendChild(document.createTextNode("  "));

            button = document.createElement("button");
            button.className="btn btn-xs btn-danger";

            i = document.createElement("i");
            i.className="glyphicon glyphicon-plus";
            button.appendChild(i);
            text =document.createTextNode("移除");
            button.appendChild(text);
            $(button).on("click",function(){
                $scope.show_new_friendlink=true;
                $scope.$apply();
                tr.remove();
                $scope.loadFriendlinks();
            });

            td.appendChild(button);
            tr.appendChild(td);
            return tr;
        };
        $scope.addFriendlink=function(){
            $scope.show_new_friendlink=false;
            var tr =$scope.createFriendlinkTr(null);
            $("table tr:eq(0)").after(tr);
		};
        $scope.delFriendlink=function(){
            var friendlink_id = this.item.id;
            Dialog.confirm("dialog","您确定要删除该链接吗?",function(r){
                if(r){
                    $http.get("/manage/seo/friendlink/del/"+friendlink_id+".html",{cache:false}).success(function(){
                       $scope.loadFriendlinks();
                       Dialog.successTip("操作成功!");
                    });
                }
            });
        };
        $scope.editFriendlink=function(e){
			$scope.show_new_friendlink=false;
			var currentTr=$(e.currentTarget.parentElement.parentElement);
			console.log(currentTr);
			var index=currentTr.index();
            var data =this.item;
            var tr =$scope.createFriendlinkTr(data);
            $("table tr:eq("+index+")").after(tr);
            currentTr.remove();
        };
	});
});