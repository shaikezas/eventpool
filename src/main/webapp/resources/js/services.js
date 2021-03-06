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
                  url: "event/id/" + eventid
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
              url: "event/url/" + eventurl
          };
          return $http(config);
      }
      };
      
});


AppServices.service('eventsettings', function ($http) {

    return {
geteventsettings: function(eventid) {
	  var config = {
        method: "GET",
        url: "event/myevent/settings/"+eventid
    };
	  return $http(config);
}
    
    }
});

AppServices.service('currentuser', function ($http) {

    return {
getcurrentuser: function() {
	  var config = {
        method: "GET",
        url: "user"
    };
	  return $http(config);
}
    
    }
});


AppServices.service('resetSrv', function ($http) {

    return {
resetPassword: function(password) {
	  var datatosend = {
			  "password":password
	  };
	  var config = {
        method: "POST",
        params: datatosend,
        url: "account/resetpassword"
    };
	  return $http(config);
    
    },
    
    	forgotPassword: function(email) {
    		  var datatosend = {
    				  "email":email
    		  };
    		  var config = {
    	        method: "POST",
    	        params: datatosend,
    	        url: "forgotpassword"
    	    };
    		  return $http(config);
    	}
    	    
    	    };
});


