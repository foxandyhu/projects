function initValidator() {
    $(".feedback").bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh',
        },
        fields: {
            title: {
                validators: {
                    notEmpty: {message: '主题不能为空!'},
                },
            },
            postUserName: {
                validators: {
                    notEmpty: {message: '姓名不能为空!'},
                },
            },
            content: {
                validators: {
                    notEmpty: {message: '留言内容不能为空!'},
                },
            },
        },
    });
}

function submitGuestBook() {
    $(".feedback").data('bootstrapValidator').validate();
    const flag = $(".feedback").data('bootstrapValidator').isValid();
    if (flag) {
        var title = $("#title").val();
        var postUserName = $("#postUserName").val();
        var email = $("#email").val();
        var phone = $("#phone").val();
        var content = $("#content").val();

        var data = {
            type: 2,
            postUserName: postUserName,
            ext: {title: title, content: content, email: email, phone: phone}
        };
        $.ajax({
            type: "POST",
            url: "/guestbook/post.html",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            dataType: "json",
            success: function (message) {
                document.forms[0].reset()
                alert("感谢您的宝贵建议!");
            },
            error: function (message) {
                alert("提交失败," + message.responseJSON.message);
            }
        });
    }
}

function tongJi() {
    $.get("/statistic.html?url=" + document.location.href+"&referer="+document.referrer, function () {});
}
$(function () {
    $("nav#mmenu").mmenu({
        slidingSubmenus: true, classes: 'mm-white', extensions: ["theme-white"],
        offCanvas: {position: "right", zposition: "front"}, searchfield: false,
        counters: false, navbar: {title: "网站导航"}, header: {add: true, update: true, title: "网站导航"}
    });
    $(".closemenu").click(function () {
        $("#mmenu").data("mmenu").close();
    });
    $(".navbar-toggle").click(function(){
        $("#mmenu").show();
    });
    tongJi();
})