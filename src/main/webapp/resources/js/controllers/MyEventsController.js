var MyEventsController = function($scope, $http,search,subcategories,categories,$rootScope,currentuser) {
    $scope.draftEvents = {};
    $scope.liveEvents = {};
    $scope.pastEvents = {};
    $scope.editMode = false;
    $scope.$parent.title="My Events";
    $scope.header = "myevents";
    $scope.fetchDraftEventsList = function() {
        $http.get('event/myevent/draftEventList').success(function(draftevents){
            $scope.draftEvents = draftevents;            
        });
    }
    $scope.fetchLiveEventsList = function() {
        $http.get('event/myevent/liveEventList').success(function(liveevents){
            $scope.liveEvents = liveevents;
        });
    }
    
    $scope.publishEvent = function(eventid) {
        $http.get('event/myevent/publishevent/'+eventid).success(function(){
        $scope.fetchLiveEventsList();        
        });
    }
    $scope.fetchPastEventsList = function() {
        $http.get('event/myevent/pastEventList').success(function(pastevents){
            $scope.pastEvents = pastevents;           
        });
    }
    
 $scope.getcurrentuser = function(){
     	
     	if ($rootScope.user == undefined) {
     		currentuser.getcurrentuser().success(function(data) {
     			$rootScope.user = data;
         	});
         }
     	
     }
 
 $scope.changeDateFormat = function(eventlists) {
	 for (var i=0;i<eventlists.length;i++)
	 	{ 
		 	eventlists[i].startDate = moment(eventlists[i].startDate).format("DD-MMM-YYYY HH:mm");
		 	eventlists[i].endDate = moment(eventlists[i].endDate).format("DD-MMM-YYYY HH:MM");
		    			
	 	}
	 	return eventlists;
 }
     
    $scope.getcurrentuser();
    $scope.fetchLiveEventsList();
}

