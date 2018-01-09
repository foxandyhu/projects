define(["IotApp"],function(IotApp){
	IotApp.constant("MenuType",[{name:"系统菜单",value:1},{name:"功能菜单",value:2}])
	.controller("SysMenuController",function($http,$scope,Dialog,Util,Resource,MenuType){
		$scope.MenuType=MenuType;
		var rule={valid:"glyphicon glyphicon-ok",invalid:"glyphicon glyphicon-remove",validating:"glyphicon glyphicon-refresh"};
		$scope.initSysMenu=function()
		{
			Resource.init(["ztree"],function(){
				var tree=
					{
						beforeRemove:function(treeId, treeNode){$scope.delSysMenu(treeNode.id);return false;},
						showEditBtn:function(treeId, treeNode){return treeNode.id!=0;},
						removeHoverDom:function(treeId, treeNode){$("#addBtn_"+treeNode.tId).unbind().remove();},
						addHoverDom:function(treeId, treeNode)
						{
							if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0 || treeNode.type==MenuType[1].value) return;
							var sObj = $("#" + treeNode.tId + "_span");
							var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>";
							sObj.after(addStr);
							var btn = $("#addBtn_"+treeNode.tId);
							if (btn) btn.bind("click", function()
								{
									$scope.menu={id:0,parentId:treeNode.id,parentName:treeNode.name,enable:true};
									$("#menuForm").data("bootstrapValidator").resetForm();
									$scope.$apply();
									return false;
								});						
						},
						onClick:function(event,treeId,treeNode)
						{
							if(treeNode.level!=0){$scope.menu=treeNode;}else{$scope.menu=null;}
							$("#menuForm").data("bootstrapValidator").resetForm();
							$scope.$apply();
						}
				};
				var setting = {
						view:{selectedMulti:false,addHoverDom:tree.addHoverDom,removeHoverDom:tree.removeHoverDom},
						data: {simpleData:{pIdKey:"parentId",enable: true}},
						edit: {enable: true,showRemoveBtn:tree.showEditBtn,showRenameBtn:false},
						callback: {beforeRemove:tree.beforeRemove,onClick:tree.onClick}
						};
				$.fn.zTree.init($("#menuTree"), setting);
				$scope.loadSysMenu();
			});
		};
		$scope.loadSysMenu=function()
		{
			$http.get("manage/sys/menu.html",{cache:false}).success(function(data){
				var treeObj = $.fn.zTree.getZTreeObj("menuTree");
				var nodes = treeObj.getNodes();
				for (var i=0, l=nodes.length; i < l; i++) {
					treeObj.removeNode(nodes[i]);
				}
				treeObj.addNodes(null,data);
				$scope.menu=null;
			});
		};
		$scope.delSysMenu=function(nodeId)
		{
			Dialog.confirm("menuDialog","您确认要删除吗?",function(r){if(r)
			{
				$http.get("manage/sys/menu/del/"+nodeId+".html",{cache:false}).success(function(data){
					Dialog.successTip("删除成功!");
					$scope.loadSysMenu();
				});
			}
		  });
		};
		$scope.addSysMenu=function()
		{
			var params={
					name:$scope.menu.name,type:$scope.menu.type,seq:$scope.menu.seq,url:$scope.menu.url,
					enable:$scope.menu.enable,remark:$scope.menu.remark,parentId:$scope.menu.parentId
			};
			$("#menuForm").data("bootstrapValidator").validate();
			if($("#menuForm").data("bootstrapValidator").isValid())
				{
					$http.get("manage/sys/menu/post.html",{params:params},{cache:false}).success(function(data){
						Dialog.successTip("新增成功!");
						$scope.loadSysMenu();
					});
				}
		};
		$scope.editSysMenu=function()
		{
			var params={
					id:$scope.menu.id,name:$scope.menu.name,
					type:$scope.menu.type,seq:$scope.menu.seq,url:$scope.menu.url,
					enable:$scope.menu.enable,remark:$scope.menu.remark,parentId:$scope.menu.parentId
			};
			$("#menuForm").data("bootstrapValidator").validate();
			if($("#menuForm").data("bootstrapValidator").isValid())
				{
					$http.get("manage/sys/menu/edit.html",{params:params},{cache:false}).success(function(data){
						Dialog.successTip("保存成功!");
						$scope.loadSysMenu();
					});
				}
		};
		$scope.initSysMenuValidate=function()
		{
			Resource.init(["bootstrapValidator"],function(){
				$("#menuForm").bootstrapValidator({
					feedbackIcons:rule,
					fields:
					{
						name:{validators: {notEmpty:{message:"请输入菜单名称!"}}},
						url:{validators: {notEmpty:{message:"请输入URL地址!"}}},
						type:{validators: {notEmpty:{message:"请选择菜单类型!"}}}
					}
				});
			});
		};
	});
});