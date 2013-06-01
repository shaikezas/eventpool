var MyEventsController = function($scope, $http,search,subcategories,categories) {
    $scope.draftEvents = {};
    $scope.liveEvents = {};
    $scope.pastEvents = {};
    $scope.editMode = false;
    $scope.$parent.title="My Events";
    $scope.header = "myevents";
    $scope.fetchDraftEventsList = function() {
        $http.get('event/draftEventList').success(function(draftevents){
            $scope.draftEvents = draftevents;
        });
    }
    $scope.fetchLiveEventsList = function() {
        $http.get('event/liveEventList').success(function(liveevents){
            $scope.liveEvents = liveevents;
        });
    }
    $scope.fetchPastEventsList = function() {
        $http.get('event/pastEventList').success(function(pastevents){
            $scope.pastEvents = pastevents;
        });
    }
    
    $scope.fetchLiveEventsList();
}

