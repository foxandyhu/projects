define(["BlogApp"],function(BlogApp){
    BlogApp.controller("SeoController",function($scope,$http,Dialog,Resource,$routeParams){
        $("#seomenu").addClass("active");

        $scope.loadFriendlinks=function(){
            $http.get("/manage/seo/friendlink.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
                if(data){
                    if(data){$scope.items=data.items;$scope.pageCount = data.page_count;}
                }
            });
        };

        $scope.show_new_friendlink=true;
        $scope.createFriendlinkTr=function(data){
            var tr =document.createElement("tr");

            var td =document.createElement("td")
            var input = document.createElement("input");
            input.type="text";
            input.name="name";
            input.id="friend_name";
            input.className="form-control";
            input.placeholder="请输入链接网站名称"
            if(data){
                input.value=data.name;
            }
            td.appendChild(input);
            tr.appendChild(td);

            td =document.createElement("td")
            input = document.createElement("input");
            input.type="text";
            input.name="link";
            input.id="friend_link";
            input.className="form-control";
            input.placeholder="请输入链接网站地址"
            if(data){
                input.value=data.link;
            }
            td.appendChild(input);
            tr.appendChild(td);

            td =document.createElement("td")
            input = document.createElement("input");
            input.type="text";
            input.name="seq";
            input.id="friend_seq";
            input.className="form-control";
            input.placeholder="请输入排序序号"
            if(data){
                input.value=data.seq;
            }else{
                input.value=1;
            }
            td.appendChild(input);
            tr.appendChild(td);

            td =document.createElement("td")

            button = document.createElement("button");
            button.className="btn btn-xs btn-info";

            i = document.createElement("i");
            i.className="glyphicon glyphicon-plus";
            button.appendChild(i);
            text =document.createTextNode("保存");
            button.appendChild(text);
            $(button).on("click",function(){
		        var params=["name="+$("#friend_name").val(),"link="+$("#friend_link").val(),"seq="+$("#friend_seq").val()];
		        var url="/manage/seo/friendlink/add.html";
		        if(data){
		        	params.push("id="+data.id);
		        	url="/manage/seo/friendlink/edit.html";
				}
                $http({url:url,method:"POST",data:params.join("&"),cache:false,headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).success(function(){
                    Dialog.successTip("保存成功!");
                    $scope.loadFriendlinks();
                    tr.remove();
                    $scope.show_new_friendlink=true;
                });
            });
            td.appendChild(button);
            td.appendChild(document.createTextNode("  "));

            button = document.createElement("button");
            button.className="btn btn-xs btn-danger";

            i = document.createElement("i");
            i.className="glyphicon glyphicon-plus";
            button.appendChild(i);
            text =document.createTextNode("移除");
            button.appendChild(text);
            $(button).on("click",function(){
                $scope.show_new_friendlink=true;
                $scope.$apply();
                tr.remove();
                $scope.loadFriendlinks();
            });

            td.appendChild(button);
            tr.appendChild(td);
            return tr;
        };
        $scope.addFriendlink=function(){
            $scope.show_new_friendlink=false;
            var tr =$scope.createFriendlinkTr(null);
            $("table tr:eq(0)").after(tr);
		};
        $scope.delFriendlink=function(){
            var friendlink_id = this.item.id;
            Dialog.confirm("dialog","您确定要删除该链接吗?",function(r){
                if(r){
                    $http.get("/manage/seo/friendlink/del/"+friendlink_id+".html",{cache:false}).success(function(){
                       $scope.loadFriendlinks();
                       Dialog.successTip("操作成功!");
                    });
                }
            });
        };
        $scope.editFriendlink=function(e){
			$scope.show_new_friendlink=false;
			var currentTr=$(e.currentTarget.parentElement.parentElement);
			console.log(currentTr);
			var index=currentTr.index();
            var data =this.item;
            var tr =$scope.createFriendlinkTr(data);
            $("table tr:eq("+index+")").after(tr);
            currentTr.remove();
        };

        $scope.initPvReport=function(){
            Highcharts.setOptions({ global: { useUTC: false } });
            var pv_chart = Highcharts.chart('pv_report', {credits: {enabled: false},tooltip:{shared: true, crosshairs: true},
                title: {text:""},yAxis: {title: {text: "访问量"}}, xAxis: {title: {text: "时间段"}},
                legend:{layout: "horizontal", align: "center", verticalAlign: "top",series:[]}
            });
            $scope.pv_chart=pv_chart;
        };
        $scope.statistic={ips:0,uvs:0,pvs:0,list:[]};
        $scope.loadPv=function (type,begin,end) {
            while($scope.pv_chart.series.length){
                    $scope.pv_chart.series[0].remove(true);
            }
            $http.get("/manage/seo/pv.html",{params:{type:type,begin:begin,end:end}},{cache:false}).success(function(data){
                var ips=new Array(),uvs=new Array(),pvs= new Array(),x_points=new Array();
                $(data.list).each(function(index,item){
                    for(var key in item){
                        x_points.push(key);
                        var ip=item[key][0],uv=item[key][1],pv=item[key][2],hour =item[key][3];
                        ips.push({name:hour,y:ip});
                        uvs.push({name:hour,y:uv});
                        pvs.push({name:hour,y:pv});
                    }
                });

                $scope.statistic={ips:data.ipTotal,uvs:data.visitorTotal,pvs:data.pvTotal,list:data.list,period:data.period};
                $scope.pv_chart.xAxis[0].update({categories: x_points})
                $scope.pv_chart.addSeries({name:"独立IP统计",data:ips,color:"#40ff3b"});
                $scope.pv_chart.addSeries({name:"UV统计",data:uvs,color:"#8c2d7e"});
                $scope.pv_chart.addSeries({name:"PV统计",data:pvs,color:"#15ccad"});
            });
        };
        $scope.enable_btn_flow=true;
        $scope.initSeoFlow=function(){
          Resource.init(["daterangepicker","solid-gauge"],function(){
              $("#reservation").on("focus",function(){
                  $(".btn-group button").removeClass("btn-info");
                 $("#reservation").daterangepicker(
                     {"locale":{separator: " 至 ", format: "YYYY-MM-DD"},autoApply:true,autoUpdateInput:false
                },function(){
                         $scope.enable_btn_flow=false;$scope.$apply();
                         var begin =this.startDate.format(this.locale.format);
                         var end = this.endDate.format(this.locale.format);
                         this.element.val(begin + this.locale.separator + end);
                         $scope.loadPv(5,begin,end);
                     });
                 $("#reservation").val("");
              });
              $("#btn_clearn_calendar").on("click",function(){
                  $(".btn-group button").removeClass("btn-info");
                  $("#reservation").val("");
                  $scope.enable_btn_flow=true;$scope.$apply();
              });
              $(".btn-group button").on("click",function(){
                  $(".btn-group button").removeClass("btn-info");
                  $(this).addClass("btn-info");$(this).blur();
                  var type =$(this).attr("data");
                  $scope.loadPv(type,null,null);
              });
              $scope.initPvReport();
              $(".btn-group button:eq(0)").click();
          });
        };


        $scope.initPvSourceReport=function(){
            Highcharts.setOptions({ global: { useUTC: false } });
            var source_chart_line = Highcharts.chart("source_report_line",{chart: {type: "spline"},
                plotOptions:{spline:{marker:{enabled: true}}},credits: {enabled: false},tooltip:{shared: true, crosshairs: true},
                title: {text:""},yAxis: {title: {text: "访问量"}}, xAxis: {title: {text: "时间段"}},
                legend:{layout: "horizontal", align: "center", verticalAlign: "top",series:[]}
            });
            var source_chart_pie = Highcharts.chart("source_report_pie", {
                credits:{enabled: false},title:{text:""},chart:{type:"pie", options3d: {enabled: true,beta: 0, alpha: 45}},
                plotOptions: {pie:{allowPointSelect: true, cursor: 'pointer', depth: 35, slicedOffset: 20, point: {events: {mouseOver: function() {this.slice();}, mouseOut: function() {this.slice();}, click: function() {return false;}}}}
                },colors:["#77a1e5", "#c42525", "#a6c96a"],series: [{data: []}]
            });
            $scope.source_chart_line=source_chart_line;
            $scope.source_chart_line=source_chart_line;
            $scope.source_chart_pie=source_chart_pie;
        };
        $scope.loadPvSource=function (type,category,begin,end) {
            while($scope.source_chart_line.series.length){
                    $scope.source_chart_line.series[0].remove(true);
            }
            while($scope.source_chart_pie.series.length){
                $scope.source_chart_pie.series[0].remove(true);
            }
            $http.get("/manage/seo/source.html",{params:{type:type,category:category,begin:begin,end:end}},{cache:false}).success(function(data){
                $scope.statistic={ips:data.ip_total,uvs:data.uv_total,pvs:data.pv_total,list:data.list,period:data.period,items:data.items,c_name:data.c_name};
                var pie_date=new Array();
                $(data.items).each(function(index,item){
                    for(var key in item){
                     pie_date.push([key,item[key][2]]);
                    }
                });
                var series_map={},x_points=new Array();
                $(data.list).each(function(index,item){
                    for(var key in item){
                        x_points.push(key);
                        var obj=item[key];
                        for(var type in obj){
                            if (series_map[type]){
                                series_map[type].push(obj[type][2]);
                            }else{
                                series_map[type]=[obj[type][2]];
                            }
                        }
                    }
                });
                $scope.source_chart_pie.addSeries({name:"访问来源PV:",data:pie_date,color:"#8c2d7e"});
                $scope.source_chart_line.xAxis[0].update({categories: x_points});
                for(var key in series_map){
                        $scope.source_chart_line.addSeries({name:key,data:series_map[key]});
                    }
            });
        };
        $scope.initSeoSource=function(){
            var category =$routeParams.category;
          Resource.init(["daterangepicker","solid-gauge"],function(){
              $("#reservation").on("focus",function(){
                  $(".btn-group button").removeClass("btn-info");
                 $("#reservation").daterangepicker(
                     {"locale":{separator: " 至 ", format: "YYYY-MM-DD"},autoApply:true,autoUpdateInput:false
                },function(){
                         $scope.enable_btn_flow=false;$scope.$apply();
                         var begin =this.startDate.format(this.locale.format);
                         var end = this.endDate.format(this.locale.format);
                         this.element.val(begin + this.locale.separator + end);
                         $scope.loadPvSource(5,category,begin,end);
                     });
                 $("#reservation").val("");
              });
              $("#btn_clearn_calendar").on("click",function(){
                  $(".btn-group button").removeClass("btn-info");
                  $("#reservation").val("");
                  $scope.enable_btn_flow=true;$scope.$apply();
              });
              $(".btn-group button").on("click",function(){
                  $(".btn-group button").removeClass("btn-info");
                  $(this).addClass("btn-info");$(this).blur();
                  var type =$(this).attr("data");
                  $scope.loadPvSource(type,category,null,null);
              });
              $scope.initPvSourceReport();
              $(".btn-group button:eq(0)").click();
          });
        };
	});
});