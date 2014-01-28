function PackageController ($scope, $http,$rootScope,currentuser,resetSrv) {
	$scope.packages = {};
    
    $scope.getcurrentuser = function(){
       	
       	if ($rootScope.user == undefined) {
       		currentuser.getcurrentuser().success(function(data) {
       			$rootScope.user = data;
           	});
           }
       	
       }
    
    $http.get('pricing/package').success(function(data) {
      	$scope.packages = data;
      }).error(function(error) {
      });
       
       $scope.getcurrentuser();
    
       
}

function PricingController ($scope, $http,$rootScope,currentuser,resetSrv,$routeParams, srvevent,Data,$location) {
	
	 $scope.event = {};
	 $scope.totalAmount  = 0;
	 $scope.enableBookTicket = true;
	 $scope.atleastOneTktSelected = function() {
	    	var tickets = $scope.event.tickets;
	    	var totalTkts = 0;
	    	for (var i=0;i<tickets.length;i++) 	{    		
	    		totalTkts = parseInt(totalTkts) + parseInt(tickets[i].selectedQty);
	    	}
	    	if(totalTkts > 0){
	    	$scope.enableBookTicket = false;
	    	}
	    	else {
	    		$scope.enableBookTicket = true;
	    	}
	    }
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
    
    $scope.registerTicket = function() {
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
  	
  	

      $http.post('order/register',eventRegister).success(function(data) {
      	$scope.orderRegister = data;
      	Data.setOrderRegisterData(data);
      	Data.setEventData($scope.event);
      	$location.url("/orderpackage");
      }).error(function(error) {
          alert(data);
      });
   } else {
  	 alert("This event is not yet published.\n Ticket booking will allow after published.\n Redirecting to myevents page.");
  	 $location.url("/myevents");
   }
  }
       $scope.getcurrentuser();
       $scope.eventpage();
}

function OrderPackageController ($scope, $http,$rootScope,currentuser,resetSrv,$routeParams, srvevent,Data,$location) {
	$scope.event = Data.getEventData();
    $scope.orderRegister = Data.getOrderRegisterData();
    if(angular.isDefined($scope.orderRegister) && angular.isDefined($scope.orderRegister.billingAddress) && angular.isObject($scope.orderRegister.billingAddress) && $scope.orderRegister.billingAddress.cityId!=null){
	    $scope.cityinput = {id:$scope.orderRegister.billingAddress.cityId,text:{'cityId':$scope.orderRegister.billingAddress.cityId,'cityName':$scope.orderRegister.billingAddress.cityName,'countryName':$scope.orderRegister.billingAddress.countryName,'stateName':$scope.orderRegister.billingAddress.stateName}};
	    $scope.state = $scope.orderRegister.billingAddress.stateName;
	    $scope.country = $scope.orderRegister.billingAddress.countryName;
	    }
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
    
    $scope.bookTicket = function() {
    	$scope.disablePayment = true;
    	$scope.validations();
        if($scope.stopSubmitAction === true){
        	$scope.stopSubmitAction = false;
         }
        else {
    	 $http.post('order/create',$scope.orderRegister).success(function(data) {
    		 $scope.orderRegister = data;
    		 Data.setOrderRegisterData(data);
    		 Data.setEventData($scope.event);
    		 $scope.editMode = false;
    		 if(data.grossAmount==0){
    			 $location.url('order/success/?oid='+data.oid);
    		 }else{
    			 $location.url('order/pay');
    		 }
         }).error(function(error) {
        	 $scope.disablePayment = false;
             alert(error);
         });
        }
}
    $scope.validations = function() {
    	$scope.firstNameRequired = $scope.ticketInfoForm.firstName.$error.required;
    	$scope.lastNameRequired = $scope.ticketInfoForm.lastName.$error.required;
    	$scope.emailAddressRequired = $scope.ticketInfoForm.emailAddress.$error.required;
    	$scope.address1Required = $scope.ticketInfoForm.address1.$error.required;
    	$scope.cityRequired = $scope.ticketInfoForm.city.$error.required;
    	$scope.pincodeRequired = $scope.ticketInfoForm.pincode.$error.required;
    	$scope.pincodeMinLength = $scope.ticketInfoForm.pincode.$error.minlength;
    	$scope.pincodeMaxLength = $scope.ticketInfoForm.pincode.$error.maxlength;
    	if($scope.ticketInfoForm.$invalid){
    		$scope.stopSubmitAction=true;
    		$scope.disablePayment = false;
    	}
    }
    
	$scope.getcurrentuser();
    $scope.eventpage();	
}
