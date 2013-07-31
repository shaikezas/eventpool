var FindEventController = function($scope, $http) {
	
	$scope.searchResults = {};
	$scope.searchRecords = {};
	$scope.filters = '';
	$scope.category = "category";
	$scope.date = "date";
	$scope.eventType = "type";
	$scope.location = "location";
	$scope.empty = "null";
	$scope.query = "";
		
	
    $scope.fetchSearchResults = function() {
    	$http.get('search/getDefaultResults').success(function(searchResults){
    		for (var i=0;i<searchResults.eventSearchRecords.length;i++)
        	{ 
    			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY hh:mm A");
    			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY hh:mm A");
    			    			
        	}
        	$scope.searchResults = searchResults;
        	$scope.searchRecords = searchResults.eventSearchRecords;
        });
        
      }
    
    $scope.fetchResultsByFilterType = function(filterText,filterType) {
    	
    	if(angular.isUndefined($scope.searchType) || $scope.searchType == null){
    		$scope.searchType = null;
    	}
    	if(angular.isUndefined($scope.cityId) || $scope.cityId == null){
    		$scope.loc = 0;
    	}
    	
    		if(filterType == 'category'){
    			$scope.query = $scope.query + "cat:" + filterText;
    			}
    		if(filterType == 'date'){
    			$scope.query = $scope.query + "date:" + filterText;
    			}
			if(filterType == 'type'){
				$scope.query = $scope.query + "type:" + filterText;
				}
			if(filterType == 'location'){
				$scope.query = $scope.query + "loc:" + filterText;
				}
		  $http.get('search/fetchResultsByFilterType/'+$scope.query+'/'+$scope.searchType+'/'+$scope.loc).success(function(searchResults){
        		for (var i=0;i<searchResults.eventSearchRecords.length;i++)
            	{ 
        			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY hh:mm A");
        			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY hh:mm A");        			
        			
            	}
        	if(filterText == 'null' && filterType == 'null'){
        	    $scope.searchResults = searchResults;
        		$scope.searchRecords = searchResults.eventSearchRecords;
        	} else {
        		$scope.searchRecords = searchResults.eventSearchRecords;        		
        	}
        });
        
      }
    
    $scope.selectCity = function(region) {
    	if(angular.isUndefined(region) || region == null){
    		return ;
    	}
       $scope.cityId = region.text.cityId;
       $scope.state =  region.text.stateName;
       $scope.country = region.text.countryName;
       $scope.loc = "loc:"+$scope.cityId+","+$scope.state+","+$scope.country;
    };
    
    $scope.fetchSearchResults();
}