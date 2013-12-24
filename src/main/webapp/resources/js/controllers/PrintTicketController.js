var PrintTicketController = function($scope, $http,$routeParams,currentuser) {
	$scope.header = "mytickets";
	$scope.invoice = {};
	$scope.myTicketsList = {};
	
    $scope.printTicket = function() {
    	if(angular.isDefined($routeParams.suborderid)){
    		$('#loadingWidget').show();
    		$http.get('event/myevent/print/'+$routeParams.suborderid).success(function(invoice){
            	$scope.invoice = invoice;
            	$('#loadingWidget').hide();
            });	
    	}
      }
    
      $scope.getcurrentuser();
      $scope.printTicket();
	
}