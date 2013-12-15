var EventPageController = function($scope, $http,$routeParams, srvevent,$location,Data,search,$rootScope,currentuser) {
		$scope.event = Data.getEventData();
	    $scope.editMode = true;
	    $scope.orderRegister = Data.getOrderRegisterData();
	    $scope.status = {};
	    $scope.enableBookTicket = true;
	    $scope.totalAmount=0;
	    $scope.disablePayment = false;
                    
	    if(angular.isDefined($scope.orderRegister) && angular.isDefined($scope.orderRegister.billingAddress)){
	    $scope.cityinput = {id:$scope.orderRegister.billingAddress.cityId,text:{'cityId':$scope.orderRegister.billingAddress.cityId,'cityName':$scope.orderRegister.billingAddress.cityName,'countryName':$scope.orderRegister.billingAddress.countryName,'stateName':$scope.orderRegister.billingAddress.stateName}};
	    $scope.state = $scope.orderRegister.billingAddress.stateName;
	    $scope.country = $scope.orderRegister.billingAddress.countryName;
	    }
    $scope.eventpage = function() {
    	if(angular.isDefined($routeParams.eventurl)){
    	srvevent.eventpage($routeParams).success(function(data) {
		 $scope.event = data;
	/*	 $scope.event.startDate = moment($scope.event.startDate).format("DD-MMM-YYYY hh:mm A");
		 $scope.event.endDate = moment($scope.event.endDate).format("DD-MMM-YYYY hh:mm A");
		 for (var i=0;i<$scope.event.tickets.length;i++)
		 	{ 
		    	$scope.event.tickets[i].saleStart = moment($scope.event.tickets[i].saleStart).format("DD-MMM-YYYY hh:mm A");
		    	$scope.event.tickets[i].saleEnd = moment($scope.event.tickets[i].saleEnd).format("DD-MMM-YYYY hh:mm A");
			    			
		 	}*/
    }).error(function(data) {
    	
    });
    }
    };
    
	 $scope.$on('timer-stopped', function (event, data){
		 alert("Registration time limit reached.");
		 $location.url('/home');         
     });
    $scope.calcTotalAmount = function(){
    	var tickets = $scope.event.tickets;
    	var amount = 0;
    	for (var i=0;i<tickets.length;i++) 	{    		
    		amount = amount + parseInt(tickets[i].price) * parseInt(tickets[i].selectedQty);
    	}
    	$scope.totalAmount = amount;
    }
    
    $scope.cityFormatResult = function(cityinfo) {
    	cityinfo = cityinfo.text;
        var markup = "<table class='movie-result'><tr>";
        markup += "<td class='movie-image'><div class='flag flag-"+cityinfo.flag+" '></div></td>";
        /*if (cityinfo.posters !== undefined && cityinfo.posters.thumbnail !== undefined) {
            markup += "<td class='movie-image'><img src='" + cityinfo.posters.thumbnail + "'/></td>";
        }*/
        markup += "<td class='movie-info'><div class='movie-title'>" + cityinfo.cityName + "</div>";
        if (cityinfo.stateName !== undefined) {
            markup += "<div class='movie-synopsis'>" + cityinfo.stateName + "," + cityinfo.countryName + "</div>";
        }
        markup += "</td></tr></table>"
        return markup;
    }

    $scope.cityFormatSelection = function(cityinfo) {
        return cityinfo.text.cityName;
    }
    
    $scope.getcityinfo = function(query) {
    	search.getbasicsearchresults(query.term).success(function(data) {
			//$scope.cityResults = data;
            var result = {results: []};
		    for (var i = 0; i < data.length; i++) {
		        result.results.push({id: data[i].cityId, text: data[i]});
		    }
		    query.callback(result);

        }).error(function(data) {

        });
    };
    
    $scope.cityselect2options = {
    		query: $scope.getcityinfo, 
    		minimumInputLength: 3, 
    		placeholder: "City",
    		formatResult: $scope.cityFormatResult, 
    	    formatSelection: $scope.cityFormatSelection,
    	    dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
    };
    $scope.selectCity = function(region) {
    	if(angular.isUndefined(region) || region == null){
    		return ;
    	}
        $scope.state =  region.text.stateName;
        $scope.country = region.text.countryName;
        $scope.orderRegister.billingAddress.cityId = region.text.cityId;
        
    };
    $scope.registerTicket = function() {
//        $scope.resetError();
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
        	$location.url("/order");
        }).error(function(error) {
            alert(data);
        });
     } else {
    	 alert("This event is not yet published.\n Ticket booking will allow after published.\n Redirecting to myevents page.");
    	 $location.url("/myevents");
     }
    }
    
    $scope.bookTicket = function() {
    	$scope.disablePayment = true;
    	$scope.validations();
        if($scope.stopSubmitAction === true){
        	$scope.stopSubmitAction = false;
         }
        else {
    	 $http.post('order/create',$scope.orderRegister).success(function(data) {
         	$scope.editMode = false;
         	$scope.status = data;
         	//$location.url('/mytickets');
        	$location.href('https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-1DC284005R366174D');
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
     
     $scope.getcurrentuser();
     $scope.eventpage();
}