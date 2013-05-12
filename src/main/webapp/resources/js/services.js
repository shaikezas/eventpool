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
        url: "search/citysearch"
    };
	  return $http(config);
}
    
    }
});


AppServices.service('subcategories', function ($http) {

    return {
getsubcategories: function(categoryid) {
	  var datatosend = {
			  "categoryid": categoryid
	  };
	  var config = {
        method: "GET",
        params: datatosend,
        url: "dropdown/subcategories"
    };
	  return $http(config);
}
    
    }
});