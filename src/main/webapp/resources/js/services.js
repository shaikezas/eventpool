'use strict';

/* Services */

var AppServices = angular.module('EventPool.services', []);

AppServices.value('version', '0.1');

AppServices.service('search', function ($http) {

    return {
getbasicsearchresults: function(query) {
	  var datatosend = {
			  "query": query
	  };
	  var config = {
        method: "GET",
        params: datatosend,
        url: "events/search"
    };
	  return $http(config);
}
    
    }
});