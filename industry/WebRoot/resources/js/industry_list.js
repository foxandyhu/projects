var web={
		initGotop:function()
		{
			var obj=this;
			$(window).scroll(function () {
				var scrollHeight = $(document).height();
				var scrollTop = $(window).scrollTop();
				var windowHeight = $(window).innerHeight();
				scrollTop > 100 ? $(".gotop").fadeIn("slow"): $(".gotop").fadeOut("slow");
				if(scrollTop + windowHeight +200>= scrollHeight){
				　　　　obj.loadSellersByType();
				　　}
			});
			$(".backtop").click(function (e) {
				$("html,body").animate({ scrollTop:0});
			});
			var ev=$._data($(".bl_more"),"events")
			if(!(ev && ev["click"])){
				$(".bl_more").bind("click",function(){
					obj.loadSellersByType();
				});
			}
			$(".refresh").click(function(){
				window.lock=false;
				window.pageNo=undefined;
				$(".renqi_list").html("")
				obj.loadSellersByType();
			});
		},
		loadSellersByType:function()
		{
			if(window.lock==undefined){
				window.lock=false;
			}
			if(!lock){
				if(!window.pageNo){
					window.pageNo=1;
				}else{
					++pageNo;
				}
				lock=true;var obj=this;
				$.ajax({
					url:"load/"+window.typeId+"-"+pageNo+".html",
					success:function(data){
						lock=data.length>0?false:true;
						if(lock){
							$(".bl_more").hide();
						}
						obj.renderSeller(data);
					}
				});
			}
		},
		renderSeller:function(data)
		{
			$(data).each(function(index,item){
				var html="<a href='"+item.id+".html'><div class='renqi_content'>"+
			        "<div class='bl_img'><img src='"+item.logo+"' /></div>"+
			        "<div class='bl_right'><div class='bl_title'>"+item.name+"</div>"+
			            "<div class='bl_note'><span class='left sa_star_1'><i class='sa_d10'></i></span><span class='left'>&nbsp;&nbsp;5.0</span></div>"+
			            "<div class='bl_tag'><div class='bl_clickrate'>41051浏览</div>"+
			                "<div class='bl_juli'>611km</div></div></div></div></a>";
				$(".renqi_list").append($(html));
			});
		}
};

web.initGotop();
$(".bl_more").click();