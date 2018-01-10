define(["App"],function(App){
	App.controller("SysRoleController",function($http,$scope,Dialog,Util,Resource){
		var rule={valid:"glyphicon glyphicon-ok",invalid:"glyphicon glyphicon-remove",validating:"glyphicon glyphicon-refresh"};
		$scope.initSysRole=function()
		{
			Resource.init(["ztree","bootstrapValidator"],function(){
				var tree=
					{
						beforeRemove:function(treeId, treeNode){$scope.delSysRole(treeNode.id);return false;},
						showEditBtn:function(treeId, treeNode){return treeNode.id!=0;},
						removeHoverDom:function(treeId, treeNode){$("#addBtn_"+treeNode.tId).unbind().remove();},
						addHoverDom:function(treeId, treeNode)
						{
							if ((treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) || treeNode.level>0) return;
							var sObj = $("#" + treeNode.tId + "_span");
							var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>";
							sObj.after(addStr);
							var btn = $("#addBtn_"+treeNode.tId);
							if (btn) btn.bind("click", function()
								{
									$scope.addSysRole();
									return false;
								});						
						},
						onClick:function(event,treeId,treeNode)
						{
							if(treeNode.level!=0){
								$scope.role=treeNode;
								$scope.loadSysMenuFun(treeNode.id);
								$scope.loadSysUsers(treeNode.id);
							}
							else{$scope.role=null;}
							$("#roleForm").data("bootstrapValidator").resetForm();
							$scope.$apply();
						}
				};
				var setting={
						view:{selectedMulti:false,addHoverDom:tree.addHoverDom,removeHoverDom:tree.removeHoverDom},
						data: {simpleData:{pIdKey:"parentId",enable: true}},
						edit: {enable: true,showRemoveBtn:tree.showEditBtn,showRenameBtn:false},
						callback: {beforeRemove:tree.beforeRemove,onClick:tree.onClick}
						};
				$.fn.zTree.init($("#roleTree"), setting);
				$scope.loadSysRole();
			});
		};
		$scope.loadSysMenuFun=function(roleId)
		{
			$http.get("manage/sys/menu/role/"+roleId+".html",{cache:false}).success(function(data){
				var setting ={data:{simpleData:{pIdKey:"parentId",enable: true}},callback:{onCheck:$scope.bindMenuRole},view:{selectedMulti:false},check:{enable:true,autoCheckTrigger: true}};
				$.fn.zTree.init($("#funTree"), setting,data);
			});
		};
		$scope.loadSysUsers=function(roleId)
		{
			$http.get("manage/user/role/"+roleId+".html",{cache:false}).success(function(data){
				$scope.users=data;
			});
		};
		$scope.loadUnassignSysUsers=function()
		{
			$("#assignUsersModal").modal("show");
			$scope.assignUsers=null;
			$http.get("manage/user/role/unassign.html?pageNo="+$scope.currentPage,{cache:false}).success(function(data){
				if(data){$scope.assignUsers=data.datas;$scope.pageCount = data.totalPage;}
			});
		};
		$scope.loadSysRole=function()
		{
			$http.get("manage/sys/role.html",{cache:false}).success(function(data){
				var treeObj = $.fn.zTree.getZTreeObj("roleTree");
				var nodes = treeObj.getNodes();
				for (var i=0, l=nodes.length; i < l; i++) {
					treeObj.removeNode(nodes[i]);
				}
				
				treeObj.addNodes(null,data);
				$scope.role=null;
			});
		};
		$scope.delSysRole=function(nodeId)
		{
			Dialog.confirm("roleDialog","删除角色对导致分配的用户权限失效,您确认要删除吗?",function(r){if(r)
			{
				$http.get("manage/sys/role/del/"+nodeId+".html",{cache:false}).success(function(data){
					Dialog.successTip("删除成功!");
					$scope.loadSysRole();
				});
			}
		  });
		};
		$scope.addSysRole=function()
		{
			$http.get("manage/sys/role/post.html",{cache:false}).success(function(data){
				Dialog.successTip("新增成功!");
				$scope.loadSysRole();
			});
		};
		$scope.editSysRole=function()
		{
			var params={
					id:$scope.role.id,name:$scope.role.name,seq:$scope.role.seq,
					enable:$scope.role.enable,remark:$scope.role.remark
			};
			$("#roleForm").data("bootstrapValidator").validate();
			if($("#roleForm").data("bootstrapValidator").isValid())
				{
					$http.get("manage/sys/role/edit.html",{params:params},{cache:false}).success(function(data){
						Dialog.successTip("保存成功!");
					});
				}
		};
		$scope.initSysRoleValidate=function()
		{
			Resource.init(["bootstrapValidator"],function(){
				$("#roleForm").bootstrapValidator({
					feedbackIcons:rule,
					fields:
					{
						name:{validators: {notEmpty:{message:"请输入角色名称!"}}},
					}
				});
			});
		};
		$scope.bindUserRole=function(userId,roleId)
		{
			$http.get("manage/sys/role/bind/users/"+userId+"-"+roleId+".html",{cache:false}).success(function(data){
				Dialog.successTip("操作成功!");
				$scope.loadSysUsers(roleId);
				$scope.loadUnassignSysUsers();
			});
		};
		$scope.unbindUserRole=function(userId,roleId)
		{
			Dialog.confirm("roleDialog","回收用户角色权限将会影响该用户的操作使用,您确认要继续吗?",function(r){if(r){
					$http.get("manage/sys/role/unbind/users/"+userId+"-"+roleId+".html",{cache:false}).success(function(data){
						Dialog.successTip("操作成功!");
						$scope.loadSysUsers(roleId);
					});
				}
			});
		};
		$scope.bindMenuRole=function(event, treeId, treeNode)
		{
			var flag=treeNode.checked?"bind":"unbind";
			$http.get("manage/sys/role/"+flag+"/menus/"+treeNode.id+"-"+treeNode.roleId+".html",{cache:false}).success(function(data){
				Dialog.successTip("操作成功!");
			});
		};
	});
});