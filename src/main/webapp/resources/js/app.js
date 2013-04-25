'use strict';

var EventPool = {};

var App = angular.module('EventPool', ['EventPool.filters', 'EventPool.services', 'EventPool.directives','ui.bootstrap']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/createevent', {
        templateUrl: 'events/createevent',
        controller: EventController
    });
    
    $routeProvider.when('/eventlist', {
        templateUrl: 'events/eventlist',
        controller: EventController
    });

    $routeProvider.otherwise({redirectTo: '/eventlist'});
}]);
