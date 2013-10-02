var MyTicketsController = function($scope, $http,$rootScope,currentuser) {
	$scope.header = "mytickets";
	$scope.invoice = {};
	$scope.myTicketsList = {};
	
    $scope.fetchMyTicketsList = function() {
    	$http.get('event/myevent/myTickets').success(function(myTicketsList){
        	$scope.myTicketsList = myTicketsList;
        });
        
      }
    
    $scope.openPrintTicket = function(suborderId){
//    	$scope.shouldBeOpen = true;
//    	$http.get('event/myevent/print/'+suborderId).success(function(data){
//    		$scope.invoice = data;
//        });
    	window.open("#/printticket/"+suborderId);
    }
    $scope.sendTicket = function(suborderId){
    	$http.get('event/myevent/send/'+suborderId).success(function(){
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
      
      $scope.getcurrentuser = function(){
       	
       	if ($rootScope.user == undefined) {
       		currentuser.getcurrentuser().success(function(data) {
       			$rootScope.user = data;
           	});
           }
       	
       }
       
       $scope.getcurrentuser();
    $scope.fetchMyTicketsList();
	
}