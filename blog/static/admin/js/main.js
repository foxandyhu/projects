requirejs.config({
    baseUrl: "/static/admin/plugins/",
    paths: {
        "jquery": "jquery/jquery.min",
        "jquery-ui": "jquery/jquery-ui.min.js",
        "angular": "angularjs/angular.min",
        "angular-route": "angularjs/angular-route.min",
        "pagination": "ng-pagination/ng-pagination.min",
        "bootstrap": "bootstrap/js/bootstrap.min",
        "bootstrapValidator": "bootstrapvalid/js/bootstrapValidator.min",
        "kindeditor": "kindeditor/kindeditor-min",
        "highchart": "highchart/js/highcharts",
        "3Dhighchart": "highchart/js/highcharts-3d",
        "fastclick": "fastclick/fastclick.min",
        "adminlte": "../dist/js/adminlte.min",

        "BlogApp": "../js/controllers/BlogApp",
        "BlogAppRoute": "../js/routes/BlogAppRoute",
        "CommonService": "../js/services/CommonService",
        "IndexController":"../js/controllers/IndexController",
        "MemberController":"../js/controllers/MemberController",
        "SystemController":"../js/controllers/SystemController",
        "ArticleController":"../js/controllers/ArticleController",
        "SeoController":"../js/controllers/SeoController",
        "UserController":"../js/controllers/UserController",
        "LogController":"../js/controllers/LogController"


    },
    shim: {
        "3Dhighchart": {
            deps: ["highchart"],
            exports: "3Dhighchart"
        },
        "highchart": {
            deps: ["jquery"],
            exports: "highchart"
        },
        "jquery-ui": {
            deps: ["jquery"],
            exports: "jquery-ui"
        },
        "kindeditor": {
            exports: "kindeditor"
        },
        "adminlte": {
            deps: ["jquery"],
            exports: "adminlte"
        },
        "fastclick": {
            deps: ["jquery"],
            exports: "fastclick"
        },
        "angular": {
            exports: "angular"
        },
        "angular-route": {
            deps: ["angular"],
            exports: "angular-route"
        },
        "pagination": {
            deps: ["angular"],
            exports: "ng-pagination"
        },
        "bootstrap": {
            deps: ["jquery"],
            exports: "bootstrap"
        },
        "bootstrapValidator": {
            deps: ["jquery", "bootstrap"],
            exports: "bootstrapValidator"
        }
    }
});


requirejs(["angular", "angular-route", "bootstrap", "pagination","fastclick", "BlogApp", "BlogAppRoute", "CommonService","IndexController","MemberController","SystemController","ArticleController","SeoController","UserController","LogController"], function (angular, BlogApp) {
    $(function () {
        angular.bootstrap(document, ["BlogApp"]);
    });
});
