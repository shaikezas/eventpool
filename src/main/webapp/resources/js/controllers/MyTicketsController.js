var MyTicketsController = function($scope, $http) {
	$scope.header = "mytickets";
	
	$scope.myTicketsList = {};
	
    $scope.fetchMyTicketsList = function() {
    	$http.get('event/myevent/myTickets').success(function(myTicketsList){
        	$scope.myTicketsList = myTicketsList;
        });
        
      }
    $scope.fetchMyTicketsList();
	
}