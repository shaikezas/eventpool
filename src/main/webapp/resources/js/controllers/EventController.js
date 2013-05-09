'use strict';

/**
 * EventController
 * @constructor
 */
var EventController = function($scope, $http,search) {
    $scope.event = {};
    $scope.editMode = false;
    $scope.fetchEventsList = function() {
        $http.get('events/eventslist.json').success(function(event){
            $scope.event = event;
        });
    }

    $scope.addNewEvent = function(event) {
        $scope.resetError();

        $http.post('events/addEvent', event).success(function() {
            $scope.fetchEventsList();
            $scope.event.title = '';
            $scope.event.description = '';
            $scope.event.venueName = '';
        }).error(function() {
            $scope.setError('Could not add a new event');
        });
    }
    
    $scope.addNewTicket = function(event) {
        $scope.resetError();
        $http.post('events/addTicket', event).success(function() {
            $scope.fetchEventsList();
            $scope.event.title = '';
            $scope.event.description = '';
            $scope.event.venueName = '';
        }).error(function() {
            $scope.setError('Could not add a new event');
        });
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
    
    $scope.selectMe = function(item) {
    	$scope.searchText = item
    	var n = item.split("-");
        $scope.city =  n[0]
        $scope.state =  n[1];
        $scope.country = n[2];
        $scope.event.cityId = n[3];
        $scope.searchResults = null;
        
        
    };

    $scope.fetchEventsList();

    $scope.predicate = 'id'
}

/*function TypeaheadCtrl($scope,$http) {

	  $scope.selected = undefined;
	   $scope.states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Dakota', 'North Carolina', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming']; 
	   $scope.fetchStates = function() {
 		 		$http.get('events/states').success(function(stateList){
      $scope.states = stateList;
  });
  }
	   $scope.fetchStates();
	}*/