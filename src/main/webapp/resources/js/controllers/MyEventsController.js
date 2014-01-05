var MyEventsController = function($scope, $http,search,subcategories,categories,$rootScope,currentuser) {
    $scope.draftEvents = {};
    $scope.liveEvents = {};
    $scope.pastEvents = {};
    $scope.cancelledevents = {};
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
    

    $scope.fetchPastEventsList = function() {
        $http.get('event/myevent/pastEventList').success(function(pastevents){
            $scope.pastEvents = pastevents;           
        });
    }
    
    $scope.fetchCancelledEventsList = function() {
        $http.get('event/myevent/cancelledEventList').success(function(cancelledevents){
            $scope.cancelledevents = cancelledevents;           
        });
    }
    
    $scope.publishEvent = function(eventid,sold,description) {
        if(description==""){
            $.bootstrapGrowl("You cannot publish without event description.", {
                type: 'error',
                align: 'center',
                width: 'auto',
                delay: 10000,
                allow_dismiss: true
            });
        }
    	else if(sold=="0/0"){
            $.bootstrapGrowl("You cannot publish without tickets.", {
                type: 'error',
                align: 'center',
                width: 'auto',
                delay: 10000,
                allow_dismiss: true
            });
        } else {        
            $http.get('event/myevent/publishevent/'+eventid).success(function(){
            	$scope.fetchDraftEventsList();        
            });
        }
    }
    
    $scope.closeEvent = function(eventid) {           
            $http.get('event/myevent/closeevent/'+eventid).success(function(){
            	alert("Event successfully closed.");
            });      
    }
    
    $scope.copyEvent = function(eventid) {           
        $http.get('event/myevent/copyevent/'+eventid).success(function(){
        	alert("Event successfully copied.");
        });      
    }
    
    $scope.cancelEvent = function(eventid) {           
        $http.get('event/myevent/cancelevent/'+eventid).success(function(){
        	alert("Event successfully cancelled.");   
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

