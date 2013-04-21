'use strict';

var EventPool = {};

var App = angular.module('EventPool', ['EventPool.filters', 'EventPool.services', 'EventPool.directives']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/events', {
        templateUrl: 'events/layout',
        controller: EventController
    });

    $routeProvider.otherwise({redirectTo: '/events'});
}]);
