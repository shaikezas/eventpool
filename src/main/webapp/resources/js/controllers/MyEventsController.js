var MyEventsController = function($scope, $http,search,subcategories,categories,$rootScope,currentuser) {
    $scope.draftEvents = {};
    $scope.liveEvents = {};
    $scope.pastEvents = {};
    $scope.editMode = false;
    $scope.$parent.title="My Events";
    $scope.header = "myevents";
    $scope.fetchDraftEventsList = function() {
        $http.get('event/myevent/draftEventList').success(function(draftevents){
            $scope.draftEvents = $scope.changeDateFormat(draftevents);            
        });
    }
    $scope.fetchLiveEventsList = function() {
        $http.get('event/myevent/liveEventList').success(function(liveevents){
            $scope.liveEvents = $scope.changeDateFormat(liveevents);
            /*$scope.hideInfoMessage();*/
        });
    }
    
    $scope.publishEvent = function(eventid) {
        $http.get('event/myevent/publishevent/'+eventid).success(function(){
        $scope.fetchDraftEventsList();        
        });
    }
    $scope.fetchPastEventsList = function() {
        $http.get('event/myevent/pastEventList').success(function(pastevents){
            $scope.pastEvents = $scope.changeDateFormat(pastevents);
           /* $scope.hideInfoMessage();*/
        });
    }
    
 $scope.getcurrentuser = function(){
     	
     	if ($rootScope.user == undefined) {
     		currentuser.getcurrentuser().success(function(data) {
     			$rootScope.user = data;
         	});
         }
     	
     }
 
 $scope.hideInfoMessage = function() {
 if(angular.isDefined($scope.message()) && $scope.message() != null){
		$scope.message().show = false;
	}
 }
 
 $scope.changeDateFormat = function(eventlists) {
	 for (var i=0;i<eventlists.length;i++)
	 	{ 
		 	eventlists[i].startDate = moment(eventlists[i].startDate).format("DD-MMM-YYYY hh:mm A");
		 	eventlists[i].endDate = moment(eventlists[i].endDate).format("DD-MMM-YYYY hh:mm A");
		    			
	 	}
	 	return eventlists;
 }
     
    $scope.getcurrentuser();
    $scope.fetchLiveEventsList();
}

