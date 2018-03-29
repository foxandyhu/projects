define(["App"],function(App){
	App.controller("MemberController",function($scope,$http,$routeParams,Dialog){
		$scope.loadMembers=function()
		{
			$http.get("manage/member.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
				if(data){$scope.items=data.datas;$scope.pageCount=data.totalPage;}
			});
		};
		$scope.enableMember=function(memberId)
		{
			$http.get("manage/member/editstatus/"+memberId+".html",{cache:false}).success(function(data){
				Dialog.successTip("操作成功!");
				$scope.loadMembers();
			});
		};
		$scope.detailMember=function()
		{
			var memberId=$routeParams.memberId;
			$http.get("manage/member/"+memberId+".html",{cache:false}).success(function(data){
				$scope.member=data;
			});
		};
		$scope.loadSellers=function()
		{
			$http.get("manage/seller.html",{params:{pageNo:$scope.currentPage}},{cache:false}).success(function(data){
				if(data){$scope.items=data.datas;$scope.pageCount=data.totalPage;}
			});
		};
		$scope.enableSeller=function()
		{
			var sellerId=this.item.id;
			$http.get("manage/seller/editstatus/"+sellerId+".html",{cache:false}).success(function(data){
				Dialog.successTip("操作成功!");
				$scope.loadSellers();
			});
		};
		$scope.detailSeller=function()
		{
			var sellerId=$routeParams.sellerId;
			$http.get("manage/seller/"+sellerId+".html",{cache:false}).success(function(data){
				$scope.seller=data;
			});
		};
		$scope.loadSellerPics=function()
		{
			var sellerId=$routeParams.sellerId;
			$http.get("manage/seller/"+sellerId+"/pics.html",{cache:false}).success(function(data){
				$scope.items=data;
			});
		};
		$scope.delSellerPics=function()
		{
			var sellerId=$routeParams.sellerId;
			var picId=this.item.id;
			Dialog.confirm("picDialog","您确认要删除吗该图片?",function(r){if(r)
			{
				$http.get("manage/seller/del/pics/"+sellerId+"-"+picId+".html",{cache:false}).success(function(data){
					Dialog.successTip("操作成功!");
					$scope.loadSellerPics();
				});
			}
		  });
		};		
	});
});