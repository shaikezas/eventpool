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


/*service('srvprofile', function ($http, myprofile, mynetwork) {
	var profileinfo = new netvogue.profile(); //This must be converted to hashtable if we want to store localdata
      
      *//*********************************//*
      return {
          profileinfo: function (routeparams) {
              var profileid = "";
              if (!angular.isUndefined(routeparams.profileid)) {
                  profileid = routeparams.profileid;
              }
              var config = {
                  method: "GET",
                  url: "profile/" + profileid
              };
              return $http(config);
          };
      });*/