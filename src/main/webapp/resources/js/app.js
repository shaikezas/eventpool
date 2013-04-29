'use strict';

var EventPool = {};

var App = angular.module('EventPool', ['EventPool.filters', 'EventPool.services', 'EventPool.directives','ui.bootstrap']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
	alert("loading..");
    $routeProvider.when('/createevent', {
        templateUrl: 'events/createevent',
        controller: EventController
    });
    
    $routeProvider.when('/eventlist', {
        templateUrl: 'events/eventlist',
        controller: EventController
    });
    
    $routeProvider.when('/myevents', {
        templateUrl: 'events/myevents',
        controller: EventController
    });
    
    $routeProvider.when('/findevent', {
        templateUrl: 'events/findevent',
        controller: EventController
    });
    
    $routeProvider.when('/mytickets', {
        templateUrl: 'events/mytickets',
        controller: EventController
    });
    
    $routeProvider.when('/home', {
        templateUrl: 'home',
        controller: EventController
    });
    
    $routeProvider.otherwise({redirectTo: '/home'});
}]);
