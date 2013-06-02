'use strict';

/**
 * CreateEventController
 * @constructor
 */
var CreateEventController = function($scope, $http,search,subcategories,categories, $routeParams, $timeout, srvevent,eventsettings, $dialog) {
    $scope.event = {};
    $scope.editMode = false;
    $scope.$parent.title="Create Event";
    $scope.isCollapsed = true;
    $scope.isWebinarChecked = false;
    $scope.isWebinar = false;
    $scope.questionForm = {};
    $scope.eventFormSettings = {};
      $scope.template = "html/event/editevent.html";
      $scope.templateSelect = "edit";
      $scope.header = "create";
      
      $scope.updateUrl = function(){
    	  $scope.event.eventUrl = $scope.event.title;
      }
      
      $scope.updateQuestions = function(){
    	  $http.post('event/updatesettings', $scope.eventFormSettings).success(function(data) {
//            $scope.fetchEventsList();
        }).error(function() {
            $scope.setError('Could not add a new event');
        });
      }
      
    $scope.openDialog = function () {
    	$scope.shouldBeOpen = true;
    	  };

  $scope.opts = {
    backdrop: true,
    keyboard: true,
    controller: 'CreateEventController'
  };

   $scope.saveAndClose = function () {
	   $scope.shouldBeOpen = false; 
	   alert($scope.questionForm.question + $scope.questionForm.questionType['questionTypeName']);
	 /*  $http.post('event/addQuestion', $scope.questionForm).success(function() {
	   $scope.shouldBeOpen = false; 
	   alert($scope.questionForm.question + $scope.questionForm.questionType['questionTypeName']);
	   $http.post('event/addQuestion', $scope.questionForm).success(function() {
     }).error(function() {
         $scope.setError('Could not add a Question ');
     });*/
	   
   };  
    

    $scope.close = function(){
    	$scope.shouldBeOpen = false;
  	  };
      
   $scope.questionType = [  {questionTypeName : 'Text' },      
    	                    {questionTypeName : 'Dropdown' },
    	                    {questionTypeName : 'Radio Buttons' }, 
    	                    {questionTypeName : 'Checkboxes' }];
    $scope.myevent = function() {
    	if(angular.isDefined($routeParams.eventid)){
    	srvevent.myevent($routeParams).success(function(data) {
		 $scope.event = data;
    }).error(function(data) {
    	
    });
    }
    };
    $scope.myevent();
    
    $scope.updateUrl = function() {
    	$scope.event.eventUrl = $scope.event.title;
    	};
    
   
   /* $scope.getsearchResults = function(query) {
		search.getbasicsearchresults(query).success(function(data) {
			$scope.searchResults = data;
	    }).error(function(data) {
	    	
	    });
	};*/
	
	
    $scope.fetchEventsList = function() {
        $http.get('event/eventslist.json').success(function(event){
            $scope.event = event;
        });
    }
    
    $scope.fetchEventSettings = function() {
    	eventsettings.geteventsettings($scope.event.id).success(function(eventFormSettings) {
			$scope.eventFormSettings = eventFormSettings;
	    }).error(function(data) {
	    	
	    });
    }


    $scope.addNewEvent = function() {
        $scope.resetError();
        $scope.validations();
        if($scope.stopSubmitAction === true){
        	$scope.stopSubmitAction = false;
         }
        else {
        	
        $http.post('event/addEvent', $scope.event).success(function() {
        	$scope.success=true;
//            $scope.fetchEventsList();
        }).error(function() {
        	$scope.error=true;
            $scope.setError('Could not add a new event');
        });
        }
        
    }
    $scope.setRequiredFields = function() {
    	$scope.isWebinarChecked=!$scope.isWebinarChecked;
    	$scope.venueForm.venueName.$error.required = !$scope.isWebinarChecked;
    	$scope.venueForm.venueAddress1.$error.required = !$scope.isWebinarChecked;
    	$scope.venueForm.venueAddress2.$error.required = !$scope.isWebinarChecked;
    	$scope.venueForm.$invalid = !$scope.isWebinarChecked;
    }
    $scope.validations = function() {
    	$scope.nameRequired = $scope.eventForm.eName.$error.required;
    	$scope.descRequired = $scope.eventForm.description.$error.required;
    	$scope.startDateRequired = $scope.eventForm.startDate.$error.required;
    	$scope.endDateRequired = $scope.eventForm.endDate.$error.required;
    	$scope.venueNameReq = $scope.venueForm.venueName.$error.required;
    	$scope.venueAdd1Req = $scope.venueForm.venueAddress1.$error.required;
    	$scope.venueAdd2Req = $scope.venueForm.venueAddress2.$error.required;
    	$scope.orgNameReq = $scope.orgForm.orgName.$error.required;
    	
    	if($scope.eventForm.$invalid || $scope.venueForm.$invalid || $scope.orgForm.$invalid){
    		$scope.stopSubmitAction=true;
    	}
    }
    
    $scope.cityselect2options = {
    		
    };
    
    $scope.getcityinfo = function(query) {
        //customersrv.getcustomersbymobile(query.term).success(function(data) {
            //$scope.customersavailable = data;
            var result = {results: []};
            result.results.push({id: 1, text: "Hyderabad"});
            result.results.push({id: 1, text: "Bangalore"});
            result.results.push({id: 1, text: "Chennai"});
            result.results.push({id: 1, text: "Mumbai"});
		    /*for (var i = 0; i < data.length; i++) {
		        result.results.push({id: data[i].mobile, text: data[i].mobile});
		    }*/
		    query.callback(result);

        /*}).error(function(data) {

        });*/
    };
    $scope.addNewFreeTicket = function() {
        $scope.resetError();
        if(angular.isUndefined($scope.event.tickets)) {	
        	$scope.event.tickets = [];
        }
      
        var ticket = new eventpool.ticket();
        ticket.ticketType = "FREE";
        ticket.showFree = true;

        if(angular.isDefined($scope.event.startDate)){
        	var sEnd = $scope.event.startDate;
        	sEnd = new Date(sEnd);
        	var millSecs = sEnd.getTime();
        	millSecs = millSecs - 3600000;
        	sEnd = new Date(millSecs);
        	ticket.saleEnd = moment(sEnd).format("DD-MMM-YYYY hh:mm A");
        }
        
        $scope.event.tickets.push(ticket);
    }
    
    $scope.addNewPaidTicket = function() {
        $scope.resetError();
        if(angular.isUndefined($scope.event.tickets)) {
        	$scope.event.tickets = [];
        }
        var ticket = new eventpool.ticket();
        ticket.ticketType = "PAID";
        ticket.showPrice = true;
        if(angular.isDefined($scope.event.startDate)){
	        var sEnd = $scope.event.startDate;
	        sEnd = new Date(sEnd);
	        var millSecs = sEnd.getTime();
	        millSecs = millSecs - 3600000;
	        sEnd = new Date(millSecs);
	        ticket.saleEnd = moment(sEnd).format("DD-MMM-YYYY hh:mm A");
        }
        $scope.event.tickets.push(ticket);
    }
    
    $scope.removeTicket = function(index) {
        $scope.resetError();
        $scope.event.tickets.splice(index, 1);
    }

    $scope.updateEvent = function(event) {
        $scope.resetError();

        $http.put('event/updateEvent', event).success(function() {
            $scope.fetchEventsList();
            $scope.event.name = '';
            $scope.event.speed = '';
            $scope.event.diesel = false;
            $scope.editMode = false;
        }).error(function() {
            $scope.setError('Could not update the event');
        });
    }

    $scope.editEvent = function(event) {
        $scope.resetError();
        $scope.event = event;
        $scope.editMode = true;
        
    }

    $scope.removeEvent = function(id) {
        $scope.resetError();

        $http.delete('event/removeEvent/' + id).success(function() {
            $scope.fetchEventsList();
        }).error(function() {
            $scope.setError('Could not remove event');
        });
    }
    

    $scope.removeAllEvents = function() {
        $scope.resetError();

        $http.delete('event/removeAllEvents').success(function() {
            $scope.fetchEventsList();
        }).error(function() {
            $scope.setError('Could not remove all events');
        });

    };
    
    $scope.getsearchResults = function(query) {
		search.getbasicsearchresults(query).success(function(data) {
			$scope.searchResults = data;
	    }).error(function(data) {
	    	
	    });
	};

    $scope.resetEventForm = function() {
        $scope.resetError();
        $scope.event = {};
        $scope.editMode = false;
    }

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    }

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    }
    
    $scope.showticketsettings = function(ticket, show) {
    	ticket.showsettings = show;
    }
    
    $scope.selectCity = function(region) {
    	$scope.searchText = region.cityName;
        $scope.city =  region.cityName;
        $scope.state =  region.stateName;
        $scope.country = region.countryName;
        $scope.event.cityId = region.cityId;
        $scope.searchResults = null;
        
        
    };
   /* $scope.profilepic;
    $scope.$watch("profilepic", function() {
    	if(!angular.isUndefined($scope.profilepic)) {
//    		$scope.event.banner = $scope.profilepic.uniqueid;
    	}
    })
    */
     $scope.fetchCategories = function() {
    	categories.getcategories($scope.category).success(function(categories) {
    		$scope.categories = categories;
	    }).error(function(data) {
	    	
	    });
    }
    
    $scope.fetchSubCategories = function() {
    	subcategories.getsubcategories($scope.category).success(function(subcategories) {
			$scope.subcategories = subcategories;
	    }).error(function(data) {
	    	
	    });
    }
    $scope.fetchCategories();
    
    $scope.predicate = 'id'
}

