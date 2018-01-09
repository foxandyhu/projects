require.config({
    baseUrl: "resources/plugin/",
    paths: {
      "jquery": "jquery/jquery.min",
      "qrcode":"jquery/jquery.qrcode.min",
      "angular" : "angularjs/angular.min",
      "angular-route" : "angularjs/angular-route.min",
      "pagination":"ng-pagination/ng-pagination.min",
      "bootstrap":"bootstrap3.3.2/js/bootstrap.min",
      "bootstrapValidator":"bootstrapvalid/js/bootstrapValidator.min",
      "framework":"framework/js/framework.min",
      "ztree":"ztree/jquery.ztree.all-3.5.min",
      "My97DatePicker":"My97DatePicker/WdatePicker",
      "kindeditor":"kindeditor/kindeditor-min",
      
      "IotApp" : "../js/controllers/IotApp",
      "IotAppRoute":"../js/routes/IotAppRoute",
      "CommonService":"../js/services/CommonService",
      
      "SysController":"../js/controllers/SysController",
      "SysMenuController":"../js/controllers/SysMenuController",
      "SysRoleController":"../js/controllers/SysRoleController",
      "UserController":"../js/controllers/UserController",
      "LogsController":"../js/controllers/LogsController",
      "EquipmentController":"../js/controllers/EquipmentController"
    },
    shim: {
    	"kindeditor":{
            exports:"kindeditor"
    	},
    	"qrcode":{
    		deps: ["jquery"],
            exports:"qrcode"
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


require(["angular","angular-route","bootstrap","pagination","framework","IotApp","IotAppRoute","CommonService","UserController","SysController","SysMenuController","SysRoleController","LogsController","EquipmentController"],function (angular,IotApp){
	$(function(){
		$(document.body).addClass("hold-transition skin-red sidebar-mini");
		angular.bootstrap(document,["IotApp"]);
	});
});
