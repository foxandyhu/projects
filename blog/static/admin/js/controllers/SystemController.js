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
		     $(".nav-tabs li:eq(3) a").tab("show");
		};
		$scope.loadNavBar=function () {
            $http.get("/manage/system/navbar.html",{cache:false}).success(function(data){
                $scope.navbars=data;
            });
        };
		$scope.show_new_navbar=true;
		$scope.newNavBar=function(){
            $scope.show_new_navbar=false;
		    var tr=$scope.createNavBarTr(undefined);
            $("#navbartable tr:eq(0)").after(tr);
        };
		$scope.createNavBarTr=function(data){
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
		    ele=document.createElement("input");
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
		    ele=document.createElement("input");
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
			var tr=$scope.createNavBarTr(item);
			$("#navbartable tr:eq("+index+")").after(tr);
			currentTr.remove();
		};
		$scope.delNavBar=function(){
		    var navbarId=this.item.id;
		    Dialog.confirm("dialogId","您确认要删除该导航栏吗?",function (r) {
                if(r){
                    $http.get("/manage/system/navbar/del/"+navbarId+".html",{cache:false}).success(function(data){
                        $scope.loadNavBar();
                    });
                }
            })
        };
		$scope.createNavBarHtml=function(){
			$http.get("/manage/system/navbar/tohtml.html",{cache:false}).success(function(data){
				Dialog.successTip("操作成功!");
			});
		};
		$scope.loadBanner=function(){
			$http.get("/manage/system/banner.html",{cache:false}).success(function(data){
                $scope.banners=data;
            });
        };
		$scope.show_new_banner=true;
		$scope.newBanner=function(){
            $scope.show_new_banner=false;
		    var tr=$scope.createBannerTr(undefined);
            $("#bannertable tr:eq(0)").after(tr);
        };
		$scope.createBannerTr=function(data){
			var tr=document.createElement("tr");

		    var td=document.createElement("td");
		    var ele=document.createElement("input");
		    ele.type="text";
			ele.name="title";
			ele.id="banner_title";
			ele.className="form-control";
			if (data){ele.value=data.title;}
			td.appendChild(ele);
			tr.appendChild(td);

			td=document.createElement("td");
		    ele=document.createElement("input");
		    ele.type="text";
			ele.name="link";
			ele.id="banner_link";
			ele.className="form-control";
			if (data){ele.value=data.link;}
			td.appendChild(ele);
			tr.appendChild(td);

			td=document.createElement("td");
			ele=document.createElement("select");
			ele.name="action";
			ele.id="banner_action";
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
		    ele=document.createElement("input");
		    ele.type="text";
			ele.name="seq";
			ele.id="banner_seq";
			ele.className="form-control";
			if (data){ele.value=data.seq;}
			td.appendChild(ele);
			tr.appendChild(td);

		    td=document.createElement("td");
		    addBtn=document.createElement("button");
            addBtn.className ="btn btn-xs btn-info";
		    $(addBtn).on("click",function(){
		        var params=["title="+$("#banner_title").val(),"link="+$("#banner_link").val(), "action="+$("#banner_action").val(), "seq="+$("#banner_seq").val()];
		        var url="/manage/system/banner/add.html";
		        if(data){
		        	params.push("id="+data.id);
		        	if(data.logo){
		        		params.push("logo="+data.logo);
		        	}
		        	url="/manage/system/banner/edit.html";
				}
                $http({url:url,method:"POST",data:params.join("&"),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(){
                    Dialog.successTip("保存成功!");
                    $scope.loadBanner();
                    tr.remove();
                    $scope.show_new_banner=true;
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
                $scope.show_new_banner=true;
                $scope.$apply();
                tr.remove();
                $scope.loadBanner();
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
		$scope.showBannerImg=function(){
			var item = this.item;
			$scope.bannerItem=item;
			$scope.imageResult={
				path:$scope.bannerItem.logo,
				fullUrl:$scope.bannerItem.logo
			};
			$("#bannerModal").modal("show");
			$("#bannerModal").on("hide.bs.modal", function() {
        		$scope.bannerItem=null;
        		$scope.imageResult=null;
    		});
		};
		$scope.addBannerLogo=function(){
			var params=["banner_id="+$scope.bannerItem.id,"logo="+$scope.imageResult.path];
			$http({url:"/manage/system/banner/upload.html",method:"POST",data:params.join("&"),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(){
                    Dialog.successTip("保存成功!");
                    $("#bannerModal").modal("hide");
                    $scope.loadBanner();
                });
		};
		$scope.editBanner=function(e){
			$scope.show_new_banner=false;
			var item =this.item;
			var currentTr=$(e.currentTarget.parentElement.parentElement);
			var index=currentTr.index();
			var tr=$scope.createBannerTr(item);
			$("#bannertable tr:eq("+index+")").after(tr);
			currentTr.remove();
		};
		$scope.delBanner=function(){
		    var bannerId=this.item.id;
		    Dialog.confirm("dialogId","您确认要删除该Banner吗?",function (r) {
                if(r){
                    $http.get("/manage/system/banner/del/"+bannerId+".html",{cache:false}).success(function(data){
                        $scope.loadBanner();
                    });
                }
            })
        };
		$scope.createBannerHtml=function(){
			$http.get("/manage/system/banner/tohtml.html",{cache:false}).success(function(data){
				Dialog.successTip("操作成功!");
			});
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
                        website:{validators: {notEmpty:{message:"请输入域名地址!"}}}
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
			$http.get("/manage/system/about.html",{cache:false}).success(function(data){
				$scope.content=data;
				Util.createSimpleEditor("content");
			});
        };
		$scope.megerAbout=function(){
		    $http({url:"/manage/system/about.html",method:"POST",data:$("#aboutForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
				});
        };
		$scope.loadContact=function(){
			$http.get("/manage/system/contact.html",{cache:false}).success(function(data){
				$scope.content=data;
				Util.createSimpleEditor("content");
			});
        };
		$scope.megerContact=function(){
		    $http({url:"/manage/system/contact.html",method:"POST",data:$("#contactForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
				});
        };
		$scope.initTemplateTree=function(){
			Resource.init(["ztree"],function(){
				$scope.loadTemplateDir();
			});
		};
		$scope.loadTemplateDir=function () {
			var root=[{path:"/",name:"根目录",isParent:true,open:true}];
			var treeObj=null;$scope.rel_path=root[0].path;
			var setting = {
				async: {
					enable:true,type:"GET",url:"/manage/system/page/res.html",autoParam:["path"],otherParam:{"flag":$scope.typeflag},
					dataFilter: function(treeId, parentNode, childNodes){
						if (!childNodes) return null;
						$scope.items = childNodes;$scope.$apply();var nodes=[];
						for (var i=0, l=childNodes.length; i<l; i++) {
							nodes.push({path:childNodes[i].path,name:childNodes[i].name,isParent:childNodes[i].type==1});
						}
						return nodes;
					}
				},
				callback:{
					onClick:function(event,treeId, treeNode){
						$scope.rel_path=treeNode.path;
						if(treeNode){treeObj.reAsyncChildNodes(treeNode, "refresh");}
                        $scope.show_edit_file=false;
					}
				}
			};
			$.fn.zTree.init($("#templateTree"), setting, root);
			treeObj = $.fn.zTree.getZTreeObj("templateTree");
			var node = treeObj.getNodeByTId("templateTree_1");
			treeObj.reAsyncChildNodes(node, "refresh");
        };
		$scope.loadFiles=function(path){
				$http.get("/manage/system/page/res.html",{params:{path:path,flag:$scope.typeflag}},{cache:false}).success(function(data){
					$scope.items=data;
				});
		};
		$scope.clickFile=function(){
		    var item=this.item;
			if(item.type ==1){
				$scope.rel_path=item.path;
				$scope.loadFiles($scope.rel_path);
			}else{
			    var path =item.path;
                if(/.(html|txt|tpl|css|js|TXT)$/.test(item.name)){
                    $scope.show_edit_file=true;
                    $http.get("/manage/system/page/get.html?"+new Date(),{params:{path:path,flag:$scope.typeflag}},{cache:false}).success(function(data){
                    	$scope.pageFile={path:item.path,name:item.name,content:data};
                    });
                }else{
                    window.open("/manage/system/page/get.html?path="+path+"&flag="+$scope.typeflag+"&="+new Date());
                }
            }
		};
		$scope.canclePageFile=function(){
		    $scope.pageFile={path:"",name:"",content:""};
            $scope.show_edit_file=false;
        };
		$scope.modifyPageFile=function () {
            var params=["flag="+$scope.typeflag,"path="+$scope.pageFile.path,"content="+encodeURIComponent($scope.pageFile.content)];
            $http({url:"/manage/system/page/edit.html",method:"POST",data:params.join("&"),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(){
                Dialog.successTip("修改成功!");
                $scope.canclePageFile();
            });
        };
		$scope.renameFile=function(e){
			var btn =$(e.currentTarget);
			var index=$(e.currentTarget.parentElement.parentElement).index();
			var td=$("table tr:eq("+index+") td:eq(0) div");
			var item=this.item;
			var input=document.createElement("input")
			input.className="form-control"
			input.value=item.name;
			$(input).on("blur",function(){
				if (item.name==input.value){td.html(item.name);btn.attr("disabled",false);return;}
				$http.get("/manage/system/page/rename.html",{params:{flag:$scope.typeflag,path:item.path,nname:input.value}},{cache:false}).success(function(data){
					Dialog.successTip("修改成功!")
					btn.attr("disabled",false);
					item.path=item.path.replace(item.name,input.value);
					item.name=input.value;
					if(item.type==1){
						td.html("<i class='fa fa-folder text-yellow'></i> "+input.value);
					}else{
						td.html(input.value);
					}
				});
			});
			td.html(input);
			btn.attr("disabled",true);
			input.focus();
		};
		$scope.deleteFile=function(){
			var item=this.item;
			Dialog.confirm("dialog","您确定要删除文件 <b class='red'>"+item.name+"</b> 吗?",function(r){
				if(r){
						$http.get("/manage/system/page/del.html",{params:{flag:$scope.typeflag,path:item.path}},{cache:false}).success(function(data){
							Dialog.successTip("删除成功!")
							var index=$scope.items.indexOf(item);
							$scope.items.splice(index,1);
					});
				}
			});
		};
		$scope.create_dir=function(){
			var name =$("#newdir").val();
			if(name){
				$http.get("/manage/system/page/mkdir.html",{params:{dirname:name,flag:$scope.typeflag,path:$scope.rel_path}},{cache:false}).success(function(data){
						Dialog.successTip("创建成功!");
						$("#newdir").val("");
						$scope.loadFiles($scope.rel_path);
				});
			}else{
				Dialog.show("请输入要创建的文件夹名称!")
			}
		};
		$scope.bindUploadPageFile=function(){
            $("#file").on("change",function(){
                $scope.uploadPageFile();
            });
            $("#file").click();
        };
		$scope.uploadPageFile=function(){
            var fd = new FormData();
            fd.append("file",document.getElementById("file").files[0]);
            fd.append("path",$scope.rel_path);
            fd.append("flag",$scope.typeflag);
            $http({url:"/manage/system/page/upload.html",data:fd,cache:false,method:"POST",headers:{"Content-Type":undefined},transformRequest:angular.identity}).success(function(data){
                Dialog.successTip("上传成功!")
                $scope.loadFiles($scope.rel_path);
            });
		};
	});
});