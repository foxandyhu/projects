define(["BlogApp"],function(BlogApp){
    BlogApp.controller("IndexController",function($scope,$http,$location,$routeParams,Dialog,Resource){
        $scope.initReport=function(){
            var hours=["00:00-00:59","01:00-01:59","02:00-02:59","03:00-03:59","04:00-04:59","05:00-05:59","06:00-06:59", "07:00-07:59","08:00-08:59","09:00-09:59","10:00-10:59","11:00-11:59","12:00-12:59", "13:00-13:59","14:00-14:59","15:00-15:59","16:00-16:59","17:00-17:59","18:00-18:59", "19:00-19:59","20:00-20:59","21:00-21:59","22:00-22:59","23:00-23:59"];
            var pv_chart = Highcharts.chart('pv_report', {credits: {enabled: false},
                title: {text:"今日网站流量统计"},subtitle:{text:"当日流量"}, yAxis: {title: {text: "访问量"}}, xAxis: {categories:hours,title: {text: "时间段"}},
                legend:{layout: "vertical", align: "right", verticalAlign: "middle"}
            });

            var shource_chart = Highcharts.chart("source_report", {
                credits:{enabled: false},title:{text:"网站访问统计"},subtitle:{text:"数据截至今日"},chart:{type:"pie", options3d: {enabled: true,beta: 0, alpha: 45}},
                plotOptions: {pie:{allowPointSelect: true, cursor: 'pointer', depth: 35, slicedOffset: 20, point: {events: {mouseOver: function() {this.slice();}, mouseOut: function() {this.slice();}, click: function() {return false;}}}}
                },colors:["#77a1e5", "#c42525", "#a6c96a"],series: [{data: []}]
            });
            var keywords_chart = Highcharts.chart("keywords_report", {
                chart:{type:"column"},credits:{enabled: false},title:{text:"搜索关键词"}, subtitle:{text:"数据截至今日"},
                xAxis:{type:"category", labels:{rotation: -45}},yAxis:{min:0,title:{text:"PV"}}, legend:{enabled: false},
                tooltip:{pointFormat: "PV: <b>{point.y}</b>"},series:[{name:"搜索关键词", data: []}]
            });

            $scope.hours=hours;
            $scope.pv_chart=pv_chart;
            $scope.shource_chart=shource_chart;
            $scope.keywords_chart= keywords_chart;
        };
        $scope.loadPv=function (type) {
            $http.get("/manage/seo/pv.html",{params:{type:type}},{cache:false}).success(function(data){
                var ips=new Array(),uvs=new Array(),pvs= new Array();
                for(var i=0;i<24;i++){
                    ips.push({name:$scope.hours[i],y:0});
                    uvs.push({name:$scope.hours[i],y:0});
                    pvs.push({name:$scope.hours[i],y:0});
                }
                $(data.list).each(function(index,item){
                    var ip=item[0],uv=item[1],pv=item[2],hour =item[4];
                    ips[hour]={name:$scope.hours[hour],y:ip};
                    uvs[hour]={name:$scope.hours[hour],y:uv};
                    pvs[hour]={name:$scope.hours[hour],y:pv};
                });
                $scope.pv_chart.addSeries({name:"独立IP统计",data:ips,color:"#40ff3b"});
                $scope.pv_chart.addSeries({name:"UV统计",data:uvs,color:"#8c2d7e"});
                $scope.pv_chart.addSeries({name:"PV统计",data:pvs,color:"#15ccad"});
            });
        };
        $scope.loadSource=function () {
            $http.get("/manage/seo/source.html",{cache:false}).success(function(data){
                $scope.shource_chart.addSeries({name:"访问来源PV:",data:data,color:"#8c2d7e"});
            });
        };
        $scope.loadKeywords=function(){
            $http.get("/manage/seo/keywords.html",{cache:false}).success(function(data){
                $scope.keywords_chart.addSeries({name:"关键字来源PV:",data:data,color:"#2eff7b"});
            });
        };
        $scope.loadLink=function(){
            $http.get("/manage/seo/link.html",{cache:false}).success(function(data){
                $scope.links=data;
            });
        };
        $scope.loadData=function(){
            Resource.init(["3Dhighchart"],function(){
                $scope.initReport();
                $scope.loadPv();
                $scope.loadSource();
                $scope.loadKeywords();
                $scope.loadLink();
            });
        };
    });
});