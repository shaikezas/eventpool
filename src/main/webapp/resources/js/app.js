'use strict';

var EventPool = {};

var App = angular.module('EventPool', ['EventPool.filters', 'EventPool.services', 'EventPool.directives','ui.bootstrap']);
App.factory('Data', function() {
	    var eventData = {};
	    var orderRegisterData = {};
	    return {
	    	getEventData: function() {
	    		return eventData;
	    	},
	    
	    	getOrderRegisterData: function(){
	    		return orderRegisterData;
	    	},
	    	setEventData: function(event) {
	    		 eventData = event;
	    	},
	    
	    	setOrderRegisterData: function(orderreg){
	    		 orderRegisterData=orderreg;
	    	}
	    };
	});

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/createevent',         {        templateUrl: 'html/event/createevent.html',          controller: CreateEventController              			});
    $routeProvider.when('/eventlist',           {        templateUrl: 'html/event/eventlist.html',            controller: CreateEventController              			});
    $routeProvider.when('/myevents',            {        templateUrl: 'html/event/myevents.html',             controller: MyEventsController   			          	});
    $routeProvider.when('/findevent',           {        templateUrl: 'html/event/findevent.html',            controller: CreateEventController    					    });
    $routeProvider.when('/mytickets',           {        templateUrl: 'html/ticket/mytickets.html',           controller: MyTicketsController    					    });
    $routeProvider.when('/home',                {        templateUrl: 'html/home.html',                       controller: MainController    					    });
    $routeProvider.when('/myevent/:eventid',	{        templateUrl: 'html/event/manageevent.html', 		  controller: CreateEventController                               });	
    $routeProvider.when('/event/:eventurl',	{        templateUrl: 'html/event/eventpage.html', 		  controller: EventPageController                               });
    $routeProvider.when('/order',	{        templateUrl: 'html/order/orderevent.html', 		  controller: EventPageController                               });
    $routeProvider.otherwise({redirectTo: '/home'});
}]);
