var FindEventController = function($scope, $http) {
	
	$scope.searchResults = {};
	
    $scope.fetchSearchResults = function() {
    	$http.get('search/getDefaultResults').success(function(searchResults){
        	$scope.searchResults = searchResults;
        });
        
      }
    $scope.fetchSearchResults();
}