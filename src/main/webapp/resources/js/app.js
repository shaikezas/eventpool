'use strict';

var EventPool = {};
var httpHeaders;
var message;
var access;
var signupmessage;
var App = angular.module('EventPool', 
		['EventPool.filters', 'EventPool.services', 'EventPool.directives','ui.bootstrap', 'ui.utils', 'ui.select2']);
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
App.config(['$routeProvider','$httpProvider', function ($routeProvider,$httpProvider) {
    $routeProvider.when('/createevent',         {        templateUrl: 'html/event/myevent/createevent.html',  controller: CreateEventController              			});
    $routeProvider.when('/eventlist',           {        templateUrl: 'html/event/eventlist.html',            controller: CreateEventController              			});
    $routeProvider.when('/myevents',            {        templateUrl: 'html/event/myevents.html',             controller: MyEventsController   			          	});
    $routeProvider.when('/findevent',           {        templateUrl: 'html/event/findevent.html',            controller: FindEventController    					    });
    $routeProvider.when('/mytickets',           {        templateUrl: 'html/ticket/mytickets.html',           controller: MyTicketsController    					    });
    $routeProvider.when('/home',                {        templateUrl: 'html/home.html',                       controller: MainController    					    });
    $routeProvider.when('/',                {        templateUrl: 'html/home.html',                       controller: MainController    					    });    
    $routeProvider.when('/myevent/:eventid',	{        templateUrl: 'html/event/manageevent.html', 		  controller: CreateEventController                               });	
    $routeProvider.when('/event/:eventurl',	{        templateUrl: 'html/event/eventpage.html', 		  controller: EventPageController                               });
    $routeProvider.when('/order',	{        templateUrl: 'html/order/orderevent.html', 		  controller: EventPageController                               });
    $routeProvider.when('/pricing',	{        templateUrl: 'html/ticket/pricing.html', 		  controller: PricingController                               });
    $routeProvider.when('/userSettings',           {        templateUrl: 'html/user/MyAccount.html',           controller: UserController    					    });
    $routeProvider.when('/printticket/:suborderid',           {        templateUrl: 'html/ticket/printticket.html',           controller: PrintTicketController    					    });
    $routeProvider.otherwise({redirectTo: '/home'});
  //configure $http to catch message responses and show them
    $httpProvider.responseInterceptors.push(function ($q) {
        var setMessage = function (response) {
            //if the response has a text and a type property, it is a message to be shown
            if (response.data.text && response.data.type) {
                    $.bootstrapGrowl(response.data.text, {
                        type: response.data.type,
                        align: 'center',
                        stackup_spacing: 30,
                        delay: 10000,
                        allow_dismiss: true
                    });
                message = {
                    text: response.data.text,
                    type: response.data.type,
                    show: true
                };
            }
        };
        return function (promise) {
            return promise.then(
                //this is called after each successful server request
                function (response) {
                    setMessage(response);
                    return response;
                },
                //this is called after each unsuccessful server request
                function (response) {
                    setMessage(response);
                    return $q.reject(response);
                }
            );
        };
    });

    //configure $http to show a login dialog whenever a 401 unauthorized response arrives
    $httpProvider.responseInterceptors.push(function ($rootScope, $q) {
        return function (promise) {
            return promise.then(
                //success -> don't intercept
                function (response) {
                    return response;
                },
                //error -> if 401 save the request and broadcast an event
                function (response) {
                    if (response.status === 401) {
                        var deferred = $q.defer(),
                            req = {
                                config: response.config,
                                deferred: deferred
                            };
                        $rootScope.requests401.push(req);
                        $rootScope.$broadcast('event:loginRequired');
                        return deferred.promise;
                    }
                    return $q.reject(response);
                }
            );
        };
    });
    httpHeaders = $httpProvider.defaults.headers;
}]);

App.run(function ($rootScope, $http, base64) {
    //make current message accessible to root scope and therefore all scopes
    $rootScope.message = function () {
        return message;
    };
    
    $rootScope.access = function () {
        return access;
    };
    
    $rootScope.signupmessage = function () {
        return signupmessage;
    };

    /**
     * Holds all the requests which failed due to 401 response.
     */
    $rootScope.requests401 = [];

    $rootScope.$on('event:loginRequired', function () {
        $('#login').modal('show');
    });

    /**
     * On 'event:loginConfirmed', resend all the 401 requests.
     */
    $rootScope.$on('event:loginConfirmed', function () {
        var i,
            requests = $rootScope.requests401,
            retry = function (req) {
                $http(req.config).then(function (response) {
                    req.deferred.resolve(response);
                });
            };

        for (i = 0; i < requests.length; i += 1) {
            retry(requests[i]);
        }
        $rootScope.requests401 = [];
    });

    /**
     * On 'event:loginRequest' send credentials to the server.
     */
    $rootScope.$on('event:loginRequest', function (event, username, password) {
        httpHeaders.common['Authorization'] = 'Basic ' + base64.encode(username + ':' + password);
        $http.get('user').success(function (data) {
            $rootScope.user = data;
            access = "";
            message = "";
            signupmessage = "";
            $rootScope.$broadcast('event:loginConfirmed');
        });
       access = {
               text: "Invalid credentails",
               type: "error",
               show: true
           };
    });

    /**
     * On 'logoutRequest' invoke logout on the server and broadcast 'event:loginRequired'.
     */
    $rootScope.$on('event:logoutRequest', function () {
        httpHeaders.common['Authorization'] = null;
        $http.get('signout').success(function () {
        });
    });
});
