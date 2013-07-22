var FindEventController = function($scope, $http) {
	
	$scope.searchResults = {};
	$scope.eventsByNumberResults = {};
	
    $scope.fetchSearchResults = function() {
    	$http.get('search/getDefaultResults').success(function(searchResults){
        	$scope.searchResults = searchResults;
        });
        
      }
    
    $scope.fetchEventsByNumberResults = function() {
    	$http.get('search/getEventsByNumberResults').success(function(eventsByNumberResults){
        	$scope.eventsByNumberResults = eventsByNumberResults;
        });
        
      }
    $scope.fetchEventsByNumberResults();
    $scope.fetchSearchResults();
}