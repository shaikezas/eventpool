var EventPageController = function($scope, $http,$routeParams, srvevent) {
    $scope.event = {};
    $scope.editMode = false;
    $scope.eventpage = function() {
    	if(angular.isDefined($routeParams.eventurl)){
    	srvevent.eventpage($routeParams).success(function(data) {
		 $scope.event = data;
    }).error(function(data) {
    	
    });
    }
    };
    $scope.eventpage();
    
}