var PrintTicketController = function($scope, $http,$routeParams,currentuser) {
	$scope.header = "mytickets";
	$scope.invoice = {};
	$scope.myTicketsList = {};
	
    $scope.printTicket = function() {
    	if(angular.isDefined($routeParams.suborderid)){
    		$http.get('event/myevent/print/'+$routeParams.suborderid).success(function(invoice){
            	$scope.invoice = invoice;
            });	
    	}
      }
    
      $scope.getcurrentuser();
      $scope.printTicket();
	
}