var web={
		initBanner:function()
		{
			$("#owl").owlCarousel({items: true,autoPlay: true});
		},
		initGotop:function()
		{
			$(window).scroll(function () {
				var scrollHeight = $(document).height();
				var scrollTop = $(window).scrollTop();
				var $windowHeight = $(window).innerHeight();
				scrollTop > 100 ? $(".gotop").fadeIn("slow"): $(".gotop").fadeOut("slow");
			});
			$(".backtop").click(function (e) {
				$("html,body").animate({ scrollTop:0});
			});
		}
};

web.initBanner();
web.initGotop();