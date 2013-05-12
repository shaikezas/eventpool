'use strict';

/**
 * EventController
 * @constructor
 */
var EventController = function($scope, $http,search,subcategories) {
    $scope.event = {};
    $scope.editMode = false;
    $scope.fetchEventsList = function() {
        $http.get('events/eventslist.json').success(function(event){
            $scope.event = event;
        });
    }

    $scope.addNewEvent = function() {
        $scope.resetError();

        $http.post('events/addEvent', $scope.event).success(function() {
//            $scope.fetchEventsList();
        }).error(function() {
            $scope.setError('Could not add a new event');
        });
    }
    
    $scope.addNewTicket = function() {
        $scope.resetError();
        if(angular.isUndefined($scope.event.tickets)) {
        	$scope.event.tickets = [];
        }
        $scope.event.tickets.push(new eventpool.ticket());
    }
    
    $scope.removeTicket = function(index) {
        $scope.resetError();
        $scope.event.tickets.splice(index, 1);
    }

    $scope.updateEvent = function(event) {
        $scope.resetError();

        $http.put('events/updateEvent', event).success(function() {
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

        $http.delete('events/removeEvent/' + id).success(function() {
            $scope.fetchEventsList();
        }).error(function() {
            $scope.setError('Could not remove event');
        });
    }

    $scope.removeAllEvents = function() {
        $scope.resetError();

        $http.delete('events/removeAllEvents').success(function() {
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
    $scope.profilepic;
    $scope.$watch("profilepic", function() {
    	if(!angular.isUndefined($scope.profilepic)) {
    		$scope.event.banner = $scope.profilepic.uniqueid;
    	}
    })
//    $scope.fetchEventsList();
    
     $scope.fetchCategories = function() {
        $http.get('dropdown/categories').success(function(categories){
            $scope.categories = categories;
        });
    }
    
    $scope.fetchSubCategories = function() {
    	alert("foo");
      /*  $http.get('dropdown/subcategories',$scope.category).success(function(subcategories){
            $scope.subcategories = subcategories;
        });*/
        
    	subcategories.getsubcategories($scope.category).success(function(subcategories) {
			$scope.subcategories = subcategories;
	    }).error(function(data) {
	    	
	    });
    }
    $scope.fetchCategories();
    $scope.predicate = 'id'
}

