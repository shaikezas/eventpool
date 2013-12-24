var MyEventsController = function($scope, $http,search,subcategories,categories,$rootScope,currentuser) {
    $scope.draftEvents = {};
    $scope.liveEvents = {};
    $scope.pastEvents = {};
    $scope.editMode = false;
    $scope.$parent.title="My Events";
    $scope.header = "myevents";
    $scope.fetchDraftEventsList = function() {
    	$('#loadingWidget').show();
        $http.get('event/myevent/draftEventList').success(function(draftevents){
            $scope.draftEvents = draftevents;            
            $('#loadingWidget').hide();
        });
    }
    $scope.fetchLiveEventsList = function() {
    	$('#loadingWidget').show();
        $http.get('event/myevent/liveEventList').success(function(liveevents){
            $scope.liveEvents = liveevents;
            $('#loadingWidget').hide();
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
    

    $scope.fetchPastEventsList = function() {
    	$('#loadingWidget').show();
        $http.get('event/myevent/pastEventList').success(function(pastevents){
            $scope.pastEvents = pastevents;           
            $('#loadingWidget').hide();
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

