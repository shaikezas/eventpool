'use strict';

var EventPool = {};

var App = angular.module('EventPool', ['EventPool.filters', 'EventPool.services', 'EventPool.directives','ui.bootstrap']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/createevent',         {        templateUrl: 'html/event/createevent.html',          controller: CreateEventController              			});
    $routeProvider.when('/eventlist',           {        templateUrl: 'html/event/eventlist.html',            controller: CreateEventController              			});
    $routeProvider.when('/myevents',            {        templateUrl: 'html/event/myevents.html',             controller: CreateEventController   			          	});
    $routeProvider.when('/findevent',           {        templateUrl: 'html/event/findevent.html',            controller: CreateEventController    					    });
    $routeProvider.when('/mytickets',           {        templateUrl: 'html/ticket/mytickets.html',           controller: CreateEventController    					    });
    $routeProvider.when('/home',                {        templateUrl: 'html/home.html',                       controller: CreateEventController    					    });
    $routeProvider.otherwise({redirectTo: '/home'});
}]);
