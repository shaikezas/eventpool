var FindEventController = function($scope, $http) {
	
	$scope.searchResults = {};
	$scope.searchRecords = {};
	$scope.filters = '';
	$scope.category = "category";
	$scope.date = "date";
	$scope.eventType = "type";
	$scope.location = "location";
	$scope.empty = "null";
	$scope.filterType = new Object();
	$scope.filterType['cat']="null";
	$scope.filterType['type']="null";
	$scope.filterType['loc']="null";
	$scope.filterType['date']="null";
	$scope.filterText = "";
		
	
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
    	
    	if(angular.isUndefined($scope.searchText) || $scope.searchText == null){
    		$scope.searchText = null;
    	}
    	if(angular.isUndefined($scope.cityId) || $scope.cityId == null){
    		$scope.loc = 0;
    	}
    	/*$scope.filterType = "null";*/
    		if(filterType == 'category'){
    			$scope.filterType['cat'] = "cat:" + filterText;
    			}
    		if(filterType == 'date'){
    			$scope.filterType['date'] = "date:" + filterText;
    			}
			if(filterType == 'type'){
				$scope.filterType['type'] = "type:" + filterText;
				}
			if(filterType == 'location'){
				$scope.filterType['loc'] = "loc:" + filterText;
				}
			$scope.filterText = "";
			if($scope.filterType['cat'] != 'null'){
			$scope.filterText = $scope.filterText + $scope.filterType['cat'] + ",";
			}
			if($scope.filterType['date'] != 'null'){
				$scope.filterText = $scope.filterText +  $scope.filterType['date'] + ",";
				}
			if($scope.filterType['loc'] != 'null'){
				$scope.filterText = $scope.filterText + $scope.filterType['loc'] + ",";
				}
			if($scope.filterType['type'] != 'null'){
				$scope.filterText = $scope.filterText + $scope.filterType['type'];
				}
			if($scope.filterText == "" || filterText == 'null' && filterType == 'null'){
				$scope.filterText = "null";				
			}
			$http.get('search/fetchResultsByFilterType/'+$scope.filterText+'/'+$scope.searchText+'/'+$scope.loc).success(function(searchResults){
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