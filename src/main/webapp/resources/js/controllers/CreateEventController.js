'use strict';

/**
 * CreateEventController
 * @constructor
 */
var CreateEventController = function($scope, $http,search,subcategories,categories, $routeParams, $timeout, srvevent,eventsettings) {
    $scope.event = {};
    $scope.editMode = false;
    $scope.$parent.title="Create Event";
    $scope.isCollapsed = true;
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
        	alert("success");
        $http.post('event/addEvent', $scope.event).success(function() {
//            $scope.fetchEventsList();
        }).error(function() {
            $scope.setError('Could not add a new event');
        });
        }
        
    }
    
    $scope.validations = function() {
    	
    	$scope.nameRequired = $scope.eventForm.eName.$error.required;
    	$scope.descRequired = $scope.eventForm.description.$error.required;
    	$scope.startDateRequired = $scope.eventForm.startDate.$error.required;
    	$scope.endDateRequired = $scope.eventForm.endDate.$error.required;
    	if($scope.nameRequired || $scope.endDateRequired ||$scope.descRequired ||$scope.startDateRequired){
    		$scope.stopSubmitAction=true;
    	}
    }
    
    $scope.addNewFreeTicket = function() {
        $scope.resetError();
        if(angular.isUndefined($scope.event.tickets)) {	
        	$scope.event.tickets = [];
        }
      
        var ticket = new eventpool.ticket();
        ticket.ticketType = "FREE";
        ticket.showFree = true;

        var now = new Date();
        ticket.saleStart = now;
        if(angular.isDefined($scope.event.startDate)){
        var sEnd = $scope.event.startDate;
        sEnd = new Date(sEnd);
        var millSecs = sEnd.getTime();
        millSecs = millSecs - 3600000;
        sEnd = new Date(millSecs);
        ticket.saleEnd = sEnd;
        }
        ticket.minQty = 1;
        ticket.maxQty = 5;
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
        var now = new Date();
        ticket.saleStart = now;
        if(angular.isDefined($scope.event.startDate)){
        var sEnd = $scope.event.startDate;
        sEnd = new Date(sEnd);
        var millSecs = sEnd.getTime();
        millSecs = millSecs - 3600000;
        sEnd = new Date(millSecs);
        ticket.saleEnd = sEnd;
        }
        ticket.minQty = 1;
        ticket.maxQty = 5;
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
    
    $scope.selectMe = function(item) {
    	$scope.searchText = item
    	var n = item.split("-");
        $scope.city =  n[0]
        $scope.state =  n[1];
        $scope.country = n[2];
        $scope.event.cityId = n[3];
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

