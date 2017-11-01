define(["IotApp"],function(IotApp){
	IotApp.controller("SysController",function($http,$scope,Dialog,Util,Resource){
		$scope.loadSysConfig=function()
		{
			$http.get("manage/system/config/load.html",{cache:false}).success(function(data){
				$scope.config=data;
			});
		};
		$scope.editSysConfig=function()
		{
			$http.get("manage/system/config/post.html?"+$("#sysConfigForm").serialize(),{cache:false}).success(function(data){
				Dialog.successTip("新增成功!");
			});
		};
		$scope.loadWxConfig=function()
		{
			$http.get("manage/weixin/config.html",{cache:false}).success(function(data){
				$scope.wxparam=data.param;
				console.log($scope.wxparam);
				$scope.menus=data.menu;
				$scope.picServer=data.picServer;
				$scope.menuItem=null;
				$scope.initWxMenu();
			});
		};
		$scope.initWxMenu=function()
		{
			Resource.init(["ztree"],function(){
				var tree=
				{
						addHoverDom:function(treeId, treeNode){
							var sObj = $("#" + treeNode.tId + "_span");
							if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
							var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' onfocus='this.blur();'></span>";
							sObj.after(addStr);
							var btn = $("#addBtn_"+treeNode.tId);
							if (btn) btn.bind("click", function(){
								$http.get("manage/weixin/menu/post.html",{params:{name:"新节点",parentId:treeNode.id}},{cache:false}).success(function(data){
									Dialog.successTip("保存成功!");
									$scope.loadWxConfig();
								});
								return false;
							});
						},
						removeHoverDom:function(treeId, treeNode) {
							$("#addBtn_"+treeNode.tId).unbind().remove();
						},
						beforeRemove:function(treeId, treeNode) {
							Dialog.confirm("wxDialog","您确定要删除该菜单吗?",function(r){
								if(r)
									{$http.get("manage/weixin/menu/del/"+treeNode.id+".html",{cache:false}).success(function(data){
										Dialog.successTip("删除成功!");
										$scope.loadWxConfig();
									});}
							});
							return false;
						},
						onClick:function(event, treeId, treeNode)
						{
							$scope.menuItem=treeNode;
							$scope.$apply();
						}
				};
				var setting = {
		    			data: {simpleData: {enable: true}},
		    			view: {selectedMulti: false,addHoverDom:tree.addHoverDom,removeHoverDom:tree.removeHoverDom},
		    			edit: {enable:true,editNameSelectAll:true,showRemoveBtn:true,showRenameBtn:false},
		    			callback:{beforeRemove:tree.beforeRemove,onClick:tree.onClick}
		    		};
				$.fn.zTree.init($("#weixinMenuTree"), setting,$scope.menus);
			});
		};
		$scope.syncWxMenu=function()
		{
			$http.get("manage/weixin/menu/sync.html",{cache:false}).success(function(data){
				Dialog.successTip("同步成功,请稍后打开微信查看!");
			});
		};
		$scope.editWxMenu=function()
		{
			$http.get("manage/weixin/menu/edit.html?"+$("#menuForm").serialize(),{cache:false}).success(function(data){
				Dialog.successTip("保存成功!");
				$scope.loadWxConfig();
			});
		};
		$scope.editWxParam=function()
		{
			$http.get("manage/weixin/param/post.html?"+$("#paramForm").serialize(),{cache:false}).success(function(data){
				Dialog.successTip("保存成功!");
			});
		};
		$scope.editWxBanner=function()
		{
			$http.get("manage/weixin/banner/post.html?"+$("#bannerForm").serialize(),{cache:false}).success(function(data){
				Dialog.successTip("保存成功!");
			});
		};
		$scope.uploadWxBanner=function(obj,flag)
		{
			Resource.init(["ajaxupload"],function(){
				var id=$(obj).attr("id");var f=document.getElementById(id).value;
				if(!Util.checkImage(f)){Dialog.show("亲,您上传的文件非图片类型哦!");return;}
				$.ajaxFileUpload({url:"manage/weixin/banner/upload.html",fileElementId:id,dataType:"json",async:false,success:function (data,status){
					$("#bannerPicImg"+flag).attr("src",data.fullUrl);
					$("#bannerPicPath"+flag).val(data.url);
					}
				});
			});
		};
	});
});