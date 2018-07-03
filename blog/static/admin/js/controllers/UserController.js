define(["BlogApp"],function(BlogApp){
    BlogApp.controller("UserController",function($scope,$http,Dialog,Resource,SysInit){
		$("#usermenu").addClass("active");
		$scope.initMemberValidate=function(){
            Resource.init(["bootstrapValidator"],function(){
				$("#pwdForm").bootstrapValidator({
					feedbackIcons:SysInit.validateCss,
					fields:{
							password:{validators: {notEmpty:{message:"请输入您的您新密码!"},stringLength:{min:5,max:20,message:"密码在5-20个字符之间!"},identical:{field:"confirmPassword",message:"两次输入的密码不一致!"}}},
							confirmPassword:{validators: {notEmpty:{message:"请输入您的确认密码!"},stringLength:{min:5,max:20,message:"密码在5-20个字符之间!"},identical:{field:"password",message:"两次输入的密码不一致!"}}}
					}
				});
			});
        };
		$scope.editPwd=function(){
		    $("#pwdForm").data('bootstrapValidator').validate();
            if($("#pwdForm").data("bootstrapValidator").isValid()){
		        $http({url:"/manage/organize/users/pwd.html",method:"POST",data:$("#pwdForm").serialize(),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(data){
                    Dialog.successTip("保存成功!");
				});
            }
        };
	});
});