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
    
    $scope.publishEvent = function(eventid,sold) {
        if(sold!="0/0"){
        $http.get('event/myevent/publishevent/'+eventid).success(function(){
        	$scope.fetchDraftEventsList();        
        });
        } else {
            $.bootstrapGrowl("You cannot publish without tickets.", {
                type: 'error',
                align: 'center',
                width: 'auto',
                delay: 10000,
                allow_dismiss: true
            });
        }
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
 
     
    $scope.getcurrentuser();
    $scope.fetchLiveEventsList();
}

