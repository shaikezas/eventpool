var PricingController = function($scope, $http,$rootScope,currentuser,resetSrv) {
	
    
    $scope.getcurrentuser = function(){
       	
       	if ($rootScope.user == undefined) {
       		currentuser.getcurrentuser().success(function(data) {
       			$rootScope.user = data;
           	});
           }
       	
       }
       
       $scope.getcurrentuser();
    
}