function PackageController ($scope, $http,$rootScope,currentuser,resetSrv) {
	
    
    $scope.getcurrentuser = function(){
       	
       	if ($rootScope.user == undefined) {
       		currentuser.getcurrentuser().success(function(data) {
       			$rootScope.user = data;
           	});
           }
       	
       }
       
       $scope.getcurrentuser();
    
}

function PricingController ($scope, $http,$rootScope,currentuser,resetSrv,$routeParams, srvevent) {
	
	 $scope.event = {};
    $scope.getcurrentuser = function(){
       	
       	if ($rootScope.user == undefined) {
       		currentuser.getcurrentuser().success(function(data) {
       			$rootScope.user = data;
           	});
           }
       	
       }
    $scope.eventpage = function() {
    	if(angular.isDefined($routeParams.eventurl)){
    	srvevent.eventpage($routeParams).success(function(data) {
		 $scope.event = data;
    }).error(function(data) {
    	
    });
    }
    };
    $scope.calcTotalAmount = function(){
    	var tickets = $scope.event.tickets;
    	var amount = 0;
    	for (var i=0;i<tickets.length;i++) 	{    		
    		amount = amount + parseFloat(tickets[i].price) * parseInt(tickets[i].selectedQty);
    	}
    	$scope.totalAmount = amount;
    }
    $scope.buyPoints = function() {
//      $scope.resetError();
  	if($scope.event.isPublish){
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
  	
  	

      $http.post('pricing/buy',eventRegister).success(function(data) {
      	$scope.orderRegister = data;
      }).error(function(error) {
          alert(data);
      });
   } 
  }
       $scope.getcurrentuser();
       $scope.eventpage();
}