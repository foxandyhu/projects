define(["BlogApp"], function (BlogApp) {
    BlogApp.controller("MemberController", function ($scope, $http,Dialog,SysInit,Resource,$location) {
        $("#membermenu").addClass("active");
        $scope.loadMembers = function () {
		    $http.get("/manage/members/list.html",{params:{pageNo:$scope.currentPage,name:$("#name").val()}},{cache:false}).success(function(data){
				if(data){$scope.items=data.items;$scope.pageCount = data.total;}
			});
        };
        $scope.loadVerifyMembers = function () {
            $http.get("/manage/members/unverify.html",{params:{pageNo:$scope.currentPage,name:$("#name").val()}},{cache:false}).success(function(data){
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
        $scope.verifyMember=function(){
        	var memberId=this.item.id;
        	Dialog.confirm("dialog","您确定要审核通过该会员吗?",function(r){if(r){
				$http.get("/manage/members/verify-"+memberId+".html",{cache:false}).success(function(data){
				    if(data.status){
				        $scope.loadVerifyMembers();
                    }else{
				        Dialog.show(data.message)
                    }
				});
			}});
		};
        $scope.initMemberValidate=function(){
            Resource.init(["bootstrapValidator"],function(){
				$("#memberForm").bootstrapValidator({
					feedbackIcons:SysInit.validateCss,
					fields:{
							username:{validators: {notEmpty:{message:"请输入用户名!"}}},
							password:{validators: {notEmpty:{message:"请输入您的您新密码!"},stringLength:{min:6,max:50,message:"密码在6~50个字符之间!"},identical:{field:"confirmPassword",message:"两次输入的密码不一致!"}}},
							confirmPassword:{validators: {notEmpty:{message:"请输入您的确认密码!"},stringLength:{min:6,max:50,message:"密码在6~50个字符之间!"},identical:{field:"password",message:"两次输入的密码不一致!"}}},
							nickname:{validators: {notEmpty:{message:"请输入昵称!"}}}
					}
				});
			});
        };
        $scope.addMember=function () {
        	$("#memberForm").data('bootstrapValidator').validate();
            if($("#memberForm").data("bootstrapValidator").isValid())
			{
				$http({url:"/manage/members/add.html",method:"POST",data:$("#memberForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
					Dialog.successTip("保存成功!");
					$location.path("/members");
				});
			}
        };
    });
});