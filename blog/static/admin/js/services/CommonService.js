define(["jquery","BlogApp"],function($,BlogApp){
    BlogApp.factory("Dialog",function($rootScope,$timeout){
    	var message={
    			confirm:function(dialogId,content,fn)
    			{
    				var dialog="<div class='modal fade' id='"+dialogId+"' tabindex='-1' role='dialog' aria-labelledby='"+dialogId+"ModalLabel' aria-hidden='true'>";
    				dialog+="<div class='modal-dialog modal-sm' style='margin-top:10%;'><div class='modal-content'><div class='modal-header'>";
    				dialog+="<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>";
    				dialog+="<h4 class='modal-title' id='"+dialogId+"ModalLabel'>提示</h4></div>";
    				dialog+="<div class='modal-body' style='text-align:center;'>"+content+"</div>";
    				dialog+="<div class='modal-footer'>";
    				dialog+="<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>";
    				dialog+="<button type='button' class='btn btn-success' id='"+dialogId+"OK'>确定</button>";
    				dialog+="</div></div></div></div>";
    				$(dialog).appendTo(document.body);
    				$("#"+dialogId).modal("show");
    				$("#"+dialogId).on("shown.bs.modal", function (){$("#"+dialogId+"OK").focus();});
    				$("#"+dialogId).on("hidden.bs.modal",function (){$("#"+dialogId).remove();if(fn){fn(false);return false;}});
    				$("#"+dialogId+"OK").bind("click",function(){$("#"+dialogId).modal('hide');if(fn){fn(true);return false;}});
    			},
    			show:function(content)
    			{
    				var dialog="<div class='modal fade' id='alertDialog' tabindex='-1' role='dialog' aria-labelledby='alertDialogModalLabel' aria-hidden='true'>";
    				dialog+="<div class='modal-dialog modal-sm' style='margin-top:10%;'><div class='modal-content'><div class='modal-header'>";
    				dialog+="<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>";
    				dialog+="<h4 class='modal-title' id='alertDialogModalLabel'>提示</h4></div>";
    				dialog+="<div class='modal-body text-center'>"+content+"</div>";
    				dialog+="<div class='modal-footer'>";
    				dialog+="<button type='button' class='btn btn-info' data-dismiss='modal' id='alertDialogOK'>确定</button>";
    				dialog+="</div></div></div></div>";
    				$(dialog).appendTo(document.body);
    				$("#alertDialog").modal("show");
    				$("#alertDialogOK").on("shown.bs.modal", function (){$("#alertDialogOK").focus();});
    				$("#alertDialog").on('hide.bs.modal',function (){$("#alertDialog").remove();});
    			},
    			successTip:function(content)
    			{
    				$rootScope.tipSuccessMsg=content;
    				$(".tip_success").show();
    				$timeout(function(){$rootScope.tipSuccessMsg=null;$(".tip_success").hide();},3000);
    			}
    	};
    	return message;
    }).factory("Util",function(Resource,$http){
    	var util={
    			submitForm:function(formName){$("#"+formName).submit();},
    			createSimpleEditor:function(name)
    			{
    				requirejs.undef("kindeditor");
    				Resource.init(["kindeditor"],function(){
    					KindEditor.create("#"+name,{uploadJson:"/manage/file/upload/kindeditor",resizeType:1,formatUploadUrl:false,width:'100%',allowPreviewEmoticons : true,allowImageUpload : true,items : ['preview','source','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline','removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist','insertunorderedlist','link', '|', 'emoticons','image','baidumap','table'],autoHeightMode:true,afterChange:function(){this.sync();},afterBlur:function(){this.sync();}});
    				});
    			},
    			checkImage:function(file){if(/.(gif|jpg|jpeg|png|JPG|PNG|GIF)$/.test(file)){return true;}return false;},
    			uploadImg:function(imageFile)
    			{
    				var fd = new FormData();
    				fd.append("imgFile",imageFile);
    				var http=$http({url:"/manage/file/upload/img.html",data:fd,cache:false,method:"POST",headers:{"Content-Type":undefined},transformRequest:angular.identity});
    				return http;
    			}
    	};
    	return util;
    });
});