define(["angular","jquery"], function (angular,$) {
    var BlogApp = angular.module("BlogApp", ["ngRoute","ng-pagination"]);
    
    var initData={};
    initData.validateCss={valid:"glyphicon glyphicon-ok",invalid:"glyphicon glyphicon-remove",validating:"glyphicon glyphicon-refresh"};
    BlogApp.constant("SysInit",initData);

    BlogApp.config(["$httpProvider",function($httpProvider){
    	$httpProvider.defaults.headers.common["X-Requested-With"] ="XMLHttpRequest";
    	$httpProvider.interceptors.push("httpInterceptor");
    }]).directive("tipsuccess",function(){
    	return{
			replace:true,restrict:"AE",templateUrl:"/static/admin/js/views/tip_success.html"
    		};
     }).directive("loading",function(){
    	return {
    		replace:true,restrict:"AE",templateUrl:"/static/admin/js/views/loading.html",
    		link:function(){
    			var top=$(window).height()/2;var left=$(window).width()/2;
    			$(".load_layer").css({top:top,left:left,position:"fixed","z-index":1000});
    		}
    	};
    }).directive("fileUpload",["$parse","fileReader","Util","Dialog",function($parse,fileReader,Util,Dialog){
    	return {
    		restrict:"A",
    		link:function(scope,element,attrs,ngModel){
    			var model=$parse(attrs.fileUpload);
    			var modelSetter=model.assign;
    			element.bind("change",function(event){
    				scope.$apply(function(){modelSetter(scope,element[0].files[0]);});
    				scope.imageFile=(event.srcElement||event.target).files[0];
    				if(!scope.imageFile){
    					return;
					}
    				if(!Util.checkImage(scope.imageFile.name))
    				{
    					Dialog.show("亲,您上传的文件非图片类型哦!");
    					return;
					}
    				fileReader.readAsDataUrl(scope.imageFile,scope).then(function(result){
    					scope.imageSrc = result;
    					var http=Util.uploadImg(scope.imageFile);
    					http.success(function(data){
    						scope.imageResult=data;
    						Dialog.successTip("上传成功!");
    					});
					});
    			});
    		}
    	};
    }]).factory("fileReader",["$q", "$log",function($q,$log){
    	var onLoad = function(reader, deferred, scope){return function(){scope.$apply(function(){deferred.resolve(reader.result);});};};
    	var onError = function(reader,deferred,scope){return function () {scope.$apply(function(){deferred.reject(reader.result);});};};
    	var getReader = function(deferred, scope) {var reader = new FileReader();reader.onload = onLoad(reader, deferred, scope);reader.onerror = onError(reader, deferred, scope);return reader;};
    	var readAsDataURL = function (file, scope) {var deferred = $q.defer();var reader = getReader(deferred, scope);reader.readAsDataURL(file);return deferred.promise;};
    	return {
    		readAsDataUrl: readAsDataURL  
    	};
    }])
    .factory("httpInterceptor",function($q,Dialog,$rootScope,$location){
    	$rootScope.$on("loginEvent",function(errorType){window.location.href="manage/login.html";});
    	$rootScope.$on("norightEvent",function(errorType){$location.path("/noright");});
    	return {
    				request:function(config){if((config.url.indexOf("views/loading.html")<0)&&config.url.indexOf("views/tip_success.html")<0){$(".load_layer").show();}return config || $q.when(config);},
    				response:function(response){$(".load_layer").hide();return response || $q.when(response);},
		    		responseError:function(rejection)
			    		{
			    			$(".load_layer").hide();var title="错误代码："+rejection.status+"<br>"+rejection.data;
			    			if(-1==rejection.status){title="网络连接失败,服务器无响应!";}
			    			else if(404==rejection.status){title="错误代码："+rejection.status+"<br>找不到对应的资源!";}
			    			else if(403==rejection.status){
			    				$rootScope.$emit("norightEvent","needRight",rejection);
			    				return;
			    			}
			    			else if(401==rejection.status)
			    			{
			    				$rootScope.$emit("loginEvent","needLogin",rejection);
			    				return;
			    			}
			    			Dialog.show(title);
			    			return $q.reject(rejection);
			    		}
    			};
    })
    .factory("Resource",function(){
    	return {
    		init:function(resourceNames,fn){
    			requirejs(resourceNames,function(){fn();});
    		}
    	};
    });
    return BlogApp;
});
