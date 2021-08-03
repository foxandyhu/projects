$(function(){
    $('#pull').click(function () {
        if (parseInt($("#side-menu").css("right")) <= -165) {
            $("#side-menu").animate({right: 0}, "fast").css("display", "block");
            $("body").animate({right: 165}, "fast");
            $('#menu_bg').addClass('menu_bg_active');
        }
    });
    $('#menu_bg').click(function () {
        $("#side-menu").animate({right: -165}, "fast").css("display", "none");
        $("body").animate({right: 0}, "fast");
        $('#menu_bg').removeClass('menu_bg_active');
    });
    $('.nav li').hover(function () {
        $('span', this).stop().css('height', '2px');
        $('span', this).animate({
            left: '0',
            width: '100%',
            right: '0'
        }, 200);
    }, function () {
        $('span', this).stop().animate({
            left: '50%',
            width: '0'
        }, 200);
    });
    $('.nav li').click(function () {
        $(this).addClass("current").siblings().removeClass("current");
    });
    // 悬浮窗口
    $(".yb_conct").hover(function () {
        $(".yb_conct").css("right", "5px");
        $(".yb_bar .yb_ercode").css('height', '190px');
    }, function () {
        $(".yb_conct").css("right", "-127px");
        $(".yb_bar .yb_ercode").css('height', '53px');
    });
    // 返回顶部
    $(".yb_top").click(function () {
        $("html,body").animate({
            'scrollTop': '0px'
        }, 300)
    });
});