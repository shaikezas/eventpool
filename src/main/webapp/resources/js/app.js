'use strict';

var EventPool = {};

var App = angular.module('EventPool', ['EventPool.filters', 'EventPool.services', 'EventPool.directives','ui.bootstrap']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/createevent', {
        templateUrl: 'html/events/createevent.html',
        controller: EventController
    });
    
    $routeProvider.when('/eventlist', {
        templateUrl: 'html/events/eventlist.html',
        controller: EventController
    });
    
    $routeProvider.when('/myevents', {
        templateUrl: 'html/events/myevents.html',
        controller: EventController
    });
    
    $routeProvider.when('/findevent', {
        templateUrl: 'html/events/findevent.html',
        controller: EventController
    });
    
    $routeProvider.when('/mytickets', {
        templateUrl: 'html/ticket/mytickets.html',
        controller: EventController
    });
    
    $routeProvider.when('/home', {
        templateUrl: 'html/home.html',
        controller: EventController
    });
    
    $routeProvider.otherwise({redirectTo: '/home'});
}]);
