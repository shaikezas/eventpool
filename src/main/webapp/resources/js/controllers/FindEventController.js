var FindEventController = function($scope, $http) {
	
	$scope.searchResults = {};
	
	
    $scope.fetchSearchResults = function() {
    	$http.get('search/getDefaultResults').success(function(searchResults){
    		for (var i=0;i<searchResults.eventSearchRecords.length;i++)
        	{ 
    			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY hh:mm A");
    			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY hh:mm A");
    			    			
        	}
        	$scope.searchResults = searchResults;
        });
        
      }
    
    $scope.fetchResultsByFilterType = function(key) {
    	
    	if(angular.isUndefined($scope.searchType) || $scope.searchType == null){
    		$scope.searchType = null;
    	}
    	if(angular.isUndefined($scope.cityId) || $scope.cityId == null){
    		$scope.cityId = 0;
    	}
    	 	$http.get('search/fetchResultsByFilterType/'+key+'/'+$scope.searchType+'/'+$scope.cityId).success(function(searchResults){
        		for (var i=0;i<searchResults.eventSearchRecords.length;i++)
            	{ 
        			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY hh:mm A");
        			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY hh:mm A");        			
        			
            	}
    	 	$scope.searchResults = searchResults;
        });
        
      }
    
    $scope.selectCity = function(region) {
    	if(angular.isUndefined(region) || region == null){
    		return ;
    	}
       $scope.cityId = region.text.cityId;
        
    };
    
    $scope.fetchSearchResults();
}