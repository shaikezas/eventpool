var MyEventsController = function($scope, $http,search,subcategories,categories) {
    $scope.myevents = {};
    $scope.editMode = false;
    $scope.fetchEventsList = function() {
        $http.get('event/eventlist').success(function(myevents){
            $scope.myevents = myevents;
        });
    }
    
    $scope.fetchEventsList();
}