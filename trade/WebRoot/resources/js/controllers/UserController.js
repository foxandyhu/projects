define(["App"],function(App){
	App.controller("UserController",function($scope,$http,$location,$routeParams,Dialog,Resource){
		$scope.initPwdValidate=function(){
			Resource.init(["bootstrapValidator"],function(){
				$("#pwdForm").bootstrapValidator({
					feedbackIcons:{valid:"glyphicon glyphicon-ok",invalid:"glyphicon glyphicon-remove",validating:"glyphicon glyphicon-refresh"},
					fields:
					{
						oldPassword:{validators: {notEmpty:{message:"请输入您的原始密码!"},stringLength:{min:6,max:50,message:"密码在6~50个字符之间!"}}},
						password:{validators: {notEmpty:{message:"请输入您的您新密码!"},stringLength:{min:6,max:50,message:"密码在6~50个字符之间!"},identical:{field:"confirmPassword",message:"两次输入的密码不一致!"}}},
						confirmPassword:{validators: {notEmpty:{message:"请输入您的确认密码!"},stringLength:{min:6,max:50,message:"密码在6~50个字符之间!"},identical:{field:"password",message:"两次输入的密码不一致!"}}},
					}
				});
			});
		};
		$scope.admin={oldPassword:"",password:""};
		$scope.editPwd=function()
		{
			$("#pwdForm").data('bootstrapValidator').validate();
			if($("#pwdForm").data("bootstrapValidator").isValid())
			{
				$http.get("manage/user/editpwd.html",{params:{oldPassword:$scope.admin.oldPassword,password:$scope.admin.password}}).success(function(data){
					Dialog.successTip("密码修改成功!");
				});
			}
		};
		
		$scope.initUserValidate=function(){
			Resource.init(["bootstrapValidator"],function(){
				$("#userForm").bootstrapValidator({
					feedbackIcons:{valid:"glyphicon glyphicon-ok",invalid:"glyphicon glyphicon-remove",validating:"glyphicon glyphicon-refresh"},
					fields:
					{
						name:{validators: {notEmpty:{message:"请输入名称!"},stringLength:{min:1,max:10,message:"名称在1~10个字符之间!"}}},
						email:{validators: {notEmpty:{message:"请输入您的您邮箱!"},emailAddress:{message:"邮箱格式不正确!"}}},
						userName:{validators: {notEmpty:{message:"请输入用户名!"},stringLength:{min:6,max:30,message:"用户名在6~30个字符之间!"}}},
						password:{validators: {notEmpty:{message:"请输入您的您新密码!"},stringLength:{min:6,max:50,message:"密码在6~50个字符之间!"}}},
						confirmPassword:{validators: {notEmpty:{message:"请输入您的确认密码!"},stringLength:{min:6,max:50,message:"密码在6~50个字符之间!"},identical:{field:"password",message:"两次输入的密码不一致!"}}}					
					}
				});
			});
		};
		$scope.loadUsers=function()
		{
			$http.get("manage/user.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
				if(data){$scope.items=data.datas;$scope.pageCount = data.totalPage;}
			});
		};
		$scope.enableUser=function()
		{
			var userId=this.item.id;
			$http.get("manage/user/editenable/"+userId+".html",{cache:false}).success(function(data){
				Dialog.successTip("操作成功!");
				$scope.loadUsers();
			});
		};
		$scope.detailUser=function()
		{
			var userId=$routeParams.userId;
			$http.get("manage/user/"+userId+".html",{cache:false}).success(function(data){
				$scope.user=data;
			});
		};
		$scope.editUser=function()
		{
			$("#userForm").data('bootstrapValidator').validate();
			if($("#userForm").data("bootstrapValidator").isValid())
			{	
				$http.get("manage/user/edit.html",{params:{id:$scope.user.id,enable:$scope.user.enable,name:$scope.user.name,email:$scope.user.email}},{cache:false}).success(function(data){
					Dialog.successTip("修改成功!");
					$location.path("/users/");
				});
			}
		};
		$scope.addUser=function()
		{
			$("#userForm").data('bootstrapValidator').validate();
			if($("#userForm").data("bootstrapValidator").isValid())
			{	
				$http.get("manage/user/post.html",{params:{userName:$scope.user.userName,password:$scope.user.password,enable:$scope.user.enable,name:$scope.user.name,email:$scope.user.email}},{cache:false}).success(function(data){
					Dialog.successTip("新增成功!");
					$location.path("/users/");
				});
			}
		};
	});
});