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
    
/*    $scope.executeSearch = function() {
    	 	$http.get('search/executeSearch/'+$scope.searchType+'/'+$scope.cityId).success(function(searchResults){
    		$scope.searchResults = searchResults;
        });
        
      }*/
    $scope.fetchResultsByFilterType = function(key) {
    	
    	if(angular.isUndefined($scope.searchType) || $scope.searchType == null){
    		$scope.searchType = null;
    	}
    	if(angular.isUndefined($scope.cityId) || $scope.cityId == null){
    		$scope.cityId = 0;
    	}
    	 	$http.get('search/fetchResultsByFilterType/'+key+'/'+$scope.searchType+'/'+$scope.cityId).success(function(searchResults){
    	 	$scope.searchResults = searchResults;
        });
        
      }
    
    $scope.selectCity = function(region) {
    	if(angular.isUndefined(region) || region == null){
    		return ;
    	}
       $scope.cityId = region.text.cityId;
        
    };
    $scope.fetchEventsByNumberResults();
    $scope.fetchSearchResults();
}