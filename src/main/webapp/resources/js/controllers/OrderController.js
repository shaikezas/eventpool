var OrderController = function($scope, $http,$rootScope,$routeParams,currentuser,resetSrv) {
	
	 $scope.getcurrentuser = function(){
       	if ($rootScope.user == undefined) {
       		currentuser.getcurrentuser().success(function(data) {
       			$rootScope.user = data;
           	});
           }
	 }
       	$scope.ordersuccess = function(){
       	$http.get('order/success?oid='.concat($routeParams.oid,'&token=',$routeParams.token,'&PayerID=',$routeParams.PlayerID)).success(function(){
    			});
       	}
       $scope.getcurrentuser();
       $scope.ordersuccess();
    
}