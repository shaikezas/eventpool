var EventPageController = function($scope, $http,$routeParams, srvevent,$location,Data) {
	  $scope.event = Data.getEventData();
	    $scope.editMode = true;
	    $scope.orderRegister = Data.getOrderRegisterData();
    $scope.status = {};
    $scope.eventpage = function() {
    	if(angular.isDefined($routeParams.eventurl)){
    	srvevent.eventpage($routeParams).success(function(data) {
		 $scope.event = data;
    }).error(function(data) {
    	
    });
    }
    };
    $scope.eventpage();
    
    
    $scope.registerTicket = function() {
//        $scope.resetError();
    	$scope.editMode = false;
    	var tickets = $scope.event.tickets;
    	var eventRegister = new eventpool.eventRegister();
    	eventRegister.eventId = $scope.event.id;
    	eventRegister.subCategoryId = $scope.event.subCategoryId;
    	eventRegister.organizerName = $scope.event.organizerName;
    	eventRegister.registrationLimit = $scope.event.registrationLimit;
    	
    	for (var i=0;i<tickets.length;i++)
    	{ 
    		if(tickets[i].selectedQty > 0){
    		var ticketRegister = new eventpool.ticketRegister();
    		ticketRegister.qty = tickets[i].selectedQty;
    		ticketRegister.ticketId = tickets[i].id;
    		ticketRegister.price = tickets[i].price;
    		ticketRegister.ticketName = tickets[i].name;
    		
    	        eventRegister.ticketRegisterDTOs.push(ticketRegister);
    	}
    	        
    	}
    	
    	

        $http.post('order/register',eventRegister).success(function(data) {
        	$scope.orderRegister = data;
        	Data.setOrderRegisterData(data);
        	Data.setEventData($scope.event);
        	$location.url("/order");
        }).error(function(error) {
            alert(data);
        });
    }
    
    $scope.bookTicket = function() {
    	 $http.post('order/create',$scope.orderRegister).success(function(data) {
         	$scope.editMode = false;
         	$scope.status = data;
         }).error(function(error) {
             alert(error);
         });
}
}