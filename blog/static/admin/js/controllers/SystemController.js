define(["BlogApp"],function(BlogApp){
	BlogApp.controller("SystemController",function($scope,$http,Resource,Dialog,Util,SysInit,$location){
		$("#sysmenu").addClass("active");
		$scope.initSysTab=function(){
		    $('a[data-toggle="tab"]').on("shown.bs.tab", function (e) {
		        var index=e.target.tabIndex
                if(index==1){
		            $scope.initSysSettingValidate();
		            $scope.loadSysInfo();
                }else if(index==2){
                    $scope.loadSysCopyRight();
                }else if(index==3){
                	$scope.loadNavBar();
                }else if(index==4){
                    $scope.loadBanner();
                }
            });
		     $(".nav-tabs li:eq(2) a").tab("show");
		};
		$scope.loadNavBar=function () {
            $http.get("/manage/system/navbar.html",{cache:false}).success(function(data){
                $scope.navbars=data;
            });
        };
		$scope.show_new_navbar=true;
		$scope.newNavBar=function(){
            $scope.show_new_navbar=false;
		    var tr=$scope.createTr(undefined);
            $("#navbartable tr:eq(0)").after(tr);
        };
		$scope.createTr=function(data){
			var tr=document.createElement("tr");

		    var td=document.createElement("td");
		    var ele=document.createElement("input");
		    ele.type="text";
			ele.name="name";
			ele.id="nav_name";
			ele.className="form-control";
			if (data){ele.value=data.name;}
			td.appendChild(ele);
			tr.appendChild(td);

			td=document.createElement("td");
		    var ele=document.createElement("input");
		    ele.type="text";
			ele.name="link";
			ele.id="nav_link";
			ele.className="form-control";
			if (data){ele.value=data.link;}
			td.appendChild(ele);
			tr.appendChild(td);

			td=document.createElement("td");
			ele=document.createElement("select");
			ele.name="action";
			ele.id="nav_action";
			ele.className="form-control";
			if (data){ele.value=data.action;}
			var values=["_blank","_self"];
			values.forEach(function(item,index){
				var option=document.createElement("option");
				if(data && item == data.action){
					option.selected=true;
				}
				var text=document.createTextNode(item);
				option.appendChild(text);
				ele.appendChild(option)
			});
			td.appendChild(ele);
			tr.appendChild(td);

			td=document.createElement("td");
		    var ele=document.createElement("input");
		    ele.type="text";
			ele.name="seq";
			ele.id="nav_seq";
			ele.className="form-control";
			if (data){ele.value=data.seq;}
			td.appendChild(ele);
			tr.appendChild(td);

		    td=document.createElement("td");
		    addBtn=document.createElement("button");
            addBtn.className ="btn btn-xs btn-info";
		    $(addBtn).on("click",function(){
		        var params=["name="+$("#nav_name").val(),"link="+$("#nav_link").val(), "action="+$("#nav_action").val(), "seq="+$("#nav_seq").val()];
		        var url="/manage/system/navbar/add.html";
		        if(data){
		        	params.push("id="+data.id);
		        	url="/manage/system/navbar/edit.html";
				}
                $http({url:url,method:"POST",data:params.join("&"),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(){
                    Dialog.successTip("保存成功!");
                    $scope.loadNavBar();
                    tr.remove();
                    $scope.show_new_navbar=true;
                });
            });
		    i=document.createElement("i");
		    i.className="glyphicon glyphicon-plus";
		    text=document.createTextNode("保存");
            addBtn.appendChild(i);
            addBtn.appendChild(text);
		    td.appendChild(addBtn);

            removeBtn=document.createElement("button");
            removeBtn.className ="btn btn-xs btn-danger";
            removeBtn.style="margin-left:10px";
            $(removeBtn).on("click",function(){
                $scope.show_new_navbar=true;
                $scope.$apply();
                tr.remove();
                $scope.loadNavBar();
            });
            i=document.createElement("i");
            i.className="glyphicon glyphicon-minus";
            text=document.createTextNode("移除");
            removeBtn.appendChild(i);
            removeBtn.appendChild(text);
            td.appendChild(removeBtn);

		    tr.appendChild(td);
		    return tr;
		};
		$scope.editNavBar=function(e){
			$scope.show_new_navbar=false;
			var item =this.item;
			var currentTr=$(e.currentTarget.parentElement.parentElement);
			var index=currentTr.index();
			var tr=$scope.createTr(item);
			$("#navbartable tr:eq("+index+")").after(tr);
			currentTr.remove();
		};
		$scope.delNavBar=function(){
		    var navbarId=this.item.id;
		    Dialog.confirm("dialogId","您确认要删除该导航栏吗?",function (r) {
                if(r){
                    $http.get("/manage/system/navbar/del/"+navbarId+".html",{cache:false}).success(function(data){
                        $scope.copyright=data;
                        $scope.loadNavBar();
                    });
                }
            })
        };
		$scope.loadBanner=function(){

        };
		$scope.loadSysInfo=function () {
          $http.get("/manage/system/web_setting.html",{cache:false}).success(function(data){
                $scope.sysinfo=data;
                if($scope.sysinfo.logo && $scope.sysinfo.logo){
					$scope.imageResult={
						path:$scope.sysinfo.logo,
						fullUrl:$scope.sysinfo.logo
					};
                }
            });
        };
		$scope.initSysSettingValidate=function(){
		    Resource.init(["bootstrapValidator"],function(){
       			$("#sysinfoForm").bootstrapValidator({
					feedbackIcons:SysInit.validateCss,
					fields:{
					    name:{validators: {notEmpty:{message:"请输入网站名称!"},stringLength:{min:1,max:20,message:"网站名称在1-20个字符之间!"}}},
                        website:{validators: {notEmpty:{message:"请输入域名地址!"},uri:{message:"域名地址不正确!"}}}
					}
				});
			});
        };
		$scope.mergeSysInfo=function () {
          $("#sysinfoForm").data("bootstrapValidator").validate();
            if($("#sysinfoForm").data("bootstrapValidator").isValid())
			{
				$http({url:"/manage/system/web_setting.html",method:"POST",data:$("#sysinfoForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
					$location.path("/web_setting");
				});
			}
        };
		$scope.loadSysCopyRight=function () {
          $http.get("/manage/system/web_copyright.html",{cache:false}).success(function(data){
                $scope.copyright=data;
            });
        };
		$scope.mergeSysCopyRight=function () {
          $http({url:"/manage/system/web_copyright.html",method:"POST",data:$("#sysCopyRightForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
					$location.path("/web_setting");
				});
        };
		$scope.loadAbout=function(){
		    Resource.init(["kindeditor"],function () {
		        Util.createSimpleEditor("content");
                $http.get("/manage/system/about.html",{cache:false}).success(function(data){
                    $scope.content=data;
                });
            });
        };
		$scope.megerAbout=function(){
		    $http({url:"/manage/system/about.html",method:"POST",data:$("#aboutForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
				});
        };
		$scope.loadContact=function(){
		    Resource.init(["kindeditor"],function () {
		        Util.createSimpleEditor("content");
                $http.get("/manage/system/contact.html",{cache:false}).success(function(data){
                    $scope.content=data;
                });
            });
        };
		$scope.megerContact=function(){
		    $http({url:"/manage/system/contact.html",method:"POST",data:$("#contactForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
				});
        };
	});
});