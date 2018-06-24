define(["BlogApp"],function(BlogApp){
    BlogApp.config(function($routeProvider){
		$routeProvider.when("/home",{
			 templateUrl:"/templates/admin/index.html",
			  controller:"IndexController"
		}).when("/error",{
			templateUrl:"error.html"
		}).when("/noright",{
			templateUrl:"noright.html"
		}).otherwise({
			redirectTo:"/home"
		});
	});

    BlogApp.config(function($routeProvider){
		$routeProvider.when("/members",{
			templateUrl:"/templates/admin/members/member_list.html",
			controller:"MemberController"
		}).when("/members/unverify",{
            templateUrl:"/templates/admin/members/member_unverify.html",
            controller:"MemberController"
        });
	});

    BlogApp.config(function($routeProvider){
        $routeProvider.when("/friendlink",{
            templateUrl:"/templates/admin/seo/friendlink_list.html",
            controller:"SeoController"
        });
    });

    BlogApp.config(function($routeProvider){
        $routeProvider.when("/users",{
            templateUrl:"/templates/admin/users/user_list.html",
            controller:"UserController"
        }).when("/menus",{
            templateUrl:"/templates/admin/users/menu_list.html",
            controller:"UserController"
        }).when("/roles",{
            templateUrl:"/templates/admin/users/role_list.html",
            controller:"UserController"
        });
    });

    BlogApp.config(function($routeProvider){
        $routeProvider.when("/loginlogs",{
            templateUrl:"/templates/admin/logs/loginlog_list.html",
            controller:"LogController"
        }).when("/oplogs",{
            templateUrl:"/templates/admin/logs/oplog_list.html",
            controller:"LogController"
        }).when("/logfiles",{
            templateUrl:"/templates/admin/logs/logfile_list.html",
            controller:"LogController	"
        });
    });

    BlogApp.config(function($routeProvider){
        $routeProvider.when("/articles",{
            templateUrl:"/templates/admin/articles/article_list.html",
            controller:"ArticleController"
        }).when("/tags",{
            templateUrl:"/templates/admin/articles/tags_list.html",
            controller:"ArticleController"
        }).when("/categorys",{
            templateUrl:"/templates/admin/articles/category_list.html",
            controller:"ArticleController"
        }).when("/comments",{
            templateUrl:"/templates/admin/articles/comment_list.html",
            controller:"ArticleController"
        });
    });

    BlogApp.config(function($routeProvider){
        $routeProvider.when("/web_setting",{
            templateUrl:"/templates/admin/system/web_setting.html",
            controller:"SystemController"
        }).when("/web_template",{
            templateUrl:"/templates/admin/system/web_template.html",
            controller:"SystemController"
        }).when("/tasks",{
            templateUrl:"/templates/admin/system/tasks.html",
            controller:"SystemController"
        }).when("/about",{
            templateUrl:"/templates/admin/system/about.html",
            controller:"SystemController"
        }).when("/contact",{
            templateUrl:"/templates/admin/system/contact.html",
            controller:"SystemController"
        });
    });
});