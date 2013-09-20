var FindEventController = function($scope,$rootScope, $http,$routeParams, $location,currentuser) {
	
	$scope.searchResults = {};
	$scope.searchRecords = {};
	$scope.filters = '';
	$scope.category = "category";
	$scope.date = "date";
	$scope.eventType = "type";
	$scope.location = "location";
	$scope.empty = "null";
	$scope.filterType = new Object();
	$scope.filterType['cat']=null;
	$scope.filterType['type']=null;
	$scope.filterType['loc']=null;
	$scope.filterType['date']=null;
	$scope.filterText = "";
	$scope.viewMode=true;
	$scope.viewModeBtnText="List";
		
	$scope.toggleViewMode = function() {
	if($scope.viewMode){
		$scope.viewModeBtnText="Thumbnail";
		$scope.viewMode = false;
	}
	else {
		$scope.viewModeBtnText="List";
		$scope.viewMode = true;
	}
	
	}
	
    $scope.fetchSearchResults = function() {
    	$http.get('search/getDefaultResults?subCategoryId='.concat($routeParams.subCategoryId,'&cityId=',$routeParams.cityId,'&eventType=',$routeParams.eventType,'&eventDate=',$routeParams.eventDate,'&q=',$routeParams.q)).success(function(searchResults){
    		for (var i=0;i<searchResults.eventSearchRecords.length;i++)
        	{ 
    			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY hh:mm A");
    			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY hh:mm A");
    			    			
        	}
        	$scope.searchResults = searchResults;
        	$scope.searchRecords = searchResults.eventSearchRecords;
        });
    	
        
      }
    
    $scope.getcurrentuser = function(){
    	
    	if ($rootScope.user == undefined) {
    		currentuser.getcurrentuser().success(function(data) {
    			$rootScope.user = data;
        	});
        }
    	
    }
    
    $scope.fetchResultsByFilterType = function(filterText,filterType) {
    	    	
    	if(angular.isUndefined($scope.searchText) || $scope.searchText == null){
    		$scope.searchText = null;
    	}
    	if(angular.isUndefined($scope.cityId) || $scope.cityId == null){
    		$scope.loc = 0;
    	}
 
    		if(filterType == 'category'){
    			$scope.filterType['cat'] = filterText;
    			}
    		if(filterType == 'date'){
    			$scope.filterType['date'] = filterText;
    			}
			if(filterType == 'type'){
				$scope.filterType['type'] =  filterText;
				}
			if(filterType == 'location'){
				$scope.filterType['loc'] =  filterText;
				}
		
			if($scope.searchText != null && $scope.loc != null && $scope.filterType['type'] != null && $scope.filterType['cat'] != null){
				$location.search({text: $scope.searchText, location: $scope.loc, type: $scope.filterType['type'], category: $scope.filterType['cat']});
			} 
			else if($scope.searchText != null && $scope.loc != null && $scope.filterType['type'] != null){
				$location.search({text: $scope.searchText, location: $scope.loc, type: $scope.filterType['type']});
			}
			else if($scope.searchText != null && $scope.loc != null){
				$location.search({text: $scope.searchText, location: $scope.loc});
			}
			else if($scope.searchText != null){
				$location.search({text: $scope.searchText});
			}
		
		
		
			
			$scope.filterText = 'null';
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
       $scope.loc = $scope.cityId+","+$scope.state+","+$scope.country;
    };
    
    
    $scope.fetchSearchResults($rootScope.user);
    $scope.getcurrentuser();
}