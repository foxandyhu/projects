require.config({
    baseUrl: "resources/plugin/",
    paths: {
      "jquery": "jquery/jquery.min",
      "angular" : "angularjs/angular.min",
      "angular-route" : "angularjs/angular-route.min",
      "pagination":"ng-pagination/ng-pagination.min",
      "bootstrap":"bootstrap3.3.2/js/bootstrap.min",
      "bootstrapValidator":"bootstrapvalid/js/bootstrapValidator.min",
      "framework":"framework/js/framework.min",
      "ztree":"ztree/jquery.ztree.all-3.5.min",
      "My97DatePicker":"My97DatePicker/WdatePicker",
      "kindeditor":"kindeditor/kindeditor-min",
      
      "App" : "../js/controllers/App",
      "AppRoute":"../js/routes/AppRoute",
      "CommonService":"../js/services/CommonService",
      
      "SysController":"../js/controllers/SysController",
      "SysMenuController":"../js/controllers/SysMenuController",
      "SysRoleController":"../js/controllers/SysRoleController",
      "UserController":"../js/controllers/UserController",
      "MemberController":"../js/controllers/MemberController",
      "LogsController":"../js/controllers/LogsController",
      "RestaurantController":"../js/controllers/RestaurantController"
    },
    shim: {
    	"kindeditor":{
            exports:"kindeditor"
    	},
    	"ztree":{
    		deps: ["jquery"],
            exports:"ztree"
    	},
    	"framework":{
    		 deps: ["jquery"],
             exports:"framework"
    	},
       "angular": {
          exports:"angular"
       },
       "angular-route":{
           deps: ["angular"],
           exports:"angular-route"
        },
        "pagination":{
            deps: ["angular"],
            exports:"ng-pagination"
         },
       "bootstrap":{
    	   deps: ["jquery"],
    	   exports:"bootstrap"
       },
       "bootstrapValidator":{
    	   deps: ["jquery","bootstrap"],
           exports:"bootstrapValidator"
       }
    }
});


require(["angular","angular-route","bootstrap","pagination","framework","App","AppRoute","CommonService","UserController","SysController","SysMenuController","SysRoleController","MemberController","LogsController","RestaurantController"],function (angular,App){
	$(function(){
		$(document.body).addClass("hold-transition skin-red sidebar-mini");
		angular.bootstrap(document,["App"]);
	});
});
