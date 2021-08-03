$(function(){
	try {
		$(".news-container .owl-carousel").owlCarousel({autoPlay: 5000,lazyLoad: true,autoHeight: true, transitionStyle: 'fade', items : 4});
	} catch(err) {}

	try {
		$(".case-container .owl-carousel").owlCarousel({navigation : false, autoPlay: 4000,lazyLoad: true,autoHeight: true, transitionStyle: 'fade', items : 4});
	} catch(err) {}

	try{
		$('.triggerAnimation').waypoint(function() {var animation = $(this).attr('data-animate');$(this).css('opacity', '');$(this).addClass("animate__animated animate__" + animation);}, {offset: '75%', triggerOnce: true});
	} catch(err) {}

	try{
		$(".banner-container").revolution({delay:3000, startwidth:940, startheight:540, hideThumbs:10, fullWidth:"on", forceFullWidth:"on", onHoverStop:"off", navigationVOffset:80, navigationHOffset:0, soloArrowLeftHOffset:50, soloArrowRightHOffset:50});
	}catch(err){}

	try{
		$(document).on("mouseenter", ".kefu-container .a", function(){
			var _this = $(this);
			var s = $(".kefu-container");
			var isService = _this.hasClass("a-service");
			var isServicePhone = _this.hasClass("a-service-phone");
			var isQrcode = _this.hasClass("a-qrcode");
			if(isService){ s.find(".d-service").show().siblings(".d").hide();}
			if(isServicePhone){ s.find(".d-service-phone").show().siblings(".d").hide();}
			if(isQrcode){ s.find(".d-qrcode").show().siblings(".d").hide();}
		});
		$(document).on("mouseleave", ".kefu-container, .kefu-container .a-top", function(){
			$(".kefu-container").find(".d").hide();
		});
		$(document).on("mouseenter", ".kefu-container .a-top", function(){
			$(".kefu-container").find(".d").hide();
		});
		$(document).on("click", ".kefu-container .a-top", function(){
			$("html,body").animate({scrollTop: 0});
		});
		$(window).scroll(function(){
			var st = $(document).scrollTop();
			var $top = $(".kefu-container .a-top");
			if(st > 400){
				$top.css({display: 'block'});
			}else{
				if ($top.is(":visible")) {
					$top.hide();
				}
			}
		});
	}catch(e){}
})