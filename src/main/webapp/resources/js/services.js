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


AppServices.service('categories', function ($http) {

    return {
getcategories: function(categoryid) {
	  var datatosend = {
	  };
	  var config = {
        method: "GET",
        params: datatosend,
        url: "dropdown/categories"
    };
	  return $http(config);
}
    
    }
});


AppServices.service('srvevent', function ($http) {
      return {
    	  myevent: function (routeparams) {
              var eventid = "";
              if (!angular.isUndefined(routeparams.eventid)) {
            	  eventid = routeparams.eventid;
              }
              var config = {
                  method: "GET",
                  url: "event/myevent/" + eventid
              };
              return $http(config);
          },
      
     eventpage: function (routeparams) {
          var eventurl = "";
          if (!angular.isUndefined(routeparams.eventurl)) {
        	  eventurl = routeparams.eventurl;
          }
          var config = {
              method: "GET",
              url: "event/myeventurl/" + eventurl
          };
          return $http(config);
      }
      };
      
});