'use strict';

/**
 * EventController
 * @constructor
 */
var EventController = function($scope, $http) {
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
        alert("Hello"+event);
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

    $scope.fetchEventsList();

    $scope.predicate = 'id'
}