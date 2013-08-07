var MyTicketsController = function($scope, $http) {
	$scope.header = "mytickets";
	$scope.invoice = {};
	$scope.myTicketsList = {};
	
    $scope.fetchMyTicketsList = function() {
    	$http.get('event/myevent/myTickets').success(function(myTicketsList){
        	$scope.myTicketsList = myTicketsList;
        });
        
      }
    
    $scope.openPrintTicket = function(suborderId){
    	$scope.shouldBeOpen = true;
    	$http.get('event/myevent/print/'+suborderId).success(function(data){
    		$scope.invoice = data;
        });
    }
    $scope.closePrintTicket = function () {
        $scope.shouldBeOpen = false;
        $scope.invoice = {};
      };
      $scope.opts = {
    		    backdropFade: true,
    		    dialogFade:true,
    		  };
    $scope.fetchMyTicketsList();
	
}