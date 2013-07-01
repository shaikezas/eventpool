var EventPageController = function($scope, $http,$routeParams, srvevent,$location,Data,search) {
		$scope.event = Data.getEventData();
	    $scope.editMode = true;
	    $scope.orderRegister = Data.getOrderRegisterData();
	    $scope.status = {};
	    $scope.enableBookTicket = true;
                    
    $scope.eventpage = function() {
    	if(angular.isDefined($routeParams.eventurl)){
    	srvevent.eventpage($routeParams).success(function(data) {
		 $scope.event = data;
    }).error(function(data) {
    	
    });
    }
    };
    $scope.eventpage();
    
    $scope.cityFormatResult = function(cityinfo) {
    	cityinfo = cityinfo.text;
        var markup = "<table class='movie-result'><tr>";
        markup += "<td class='movie-image'><img src='images/india_preview.gif'/></td>";
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
    	$scope.validations();
        if($scope.stopSubmitAction === true){
        	$scope.stopSubmitAction = false;
         }
        else {
    	 $http.post('order/create',$scope.orderRegister).success(function(data) {
         	$scope.editMode = false;
         	$scope.status = data;
         }).error(function(error) {
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
}