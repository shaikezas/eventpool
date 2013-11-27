var FindEventController = function($scope,$rootScope, $http,$routeParams, $location,currentuser) {
	
	$scope.searchResults = {};
	$scope.searchRecords = new Array();
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
	$scope.viewList=false;
	$scope.viewThumbs=true;
	/*$scope.eventType = new Array();*/
	$scope.thumbscrollPage = false;
	$scope.thumbpage = 0;
	$scope.listpage = 0;
	$scope.page = 0;
	$scope.listscrollPage = false;
	$scope.activeCountries = new Array();
	$scope.countryname = "Country";
	$scope.countryid = '';
	$scope.othercountries=true;
	
	$scope.isDateActive = function(filterItem){
		var query = filterItem.query;
		var index=query.indexOf("eventDate");
		if(index==-1){
			return "active";
		}
	}
	
	$scope.isPriceActive = function(filterItem){
		var query = filterItem.query;
		var index=query.indexOf("eventType");
		if(index==-1){
			return "active";
		}
	}
	

	$scope.isCategoryActive = function(filterItem){
		var query = filterItem.query;
		var index=query.indexOf("subCategoryId");
		if(index==-1){
			return "active";
		}
	}
	
	$scope.isCityActive = function(filterItem){
		var query = filterItem.query;
		var index=query.indexOf("cityId");
		if(index==-1){
			return "active";
		}
	}
	
	$scope.viewThumbsNextPage = function(){
		if ($scope.thumbscrollPage) return;
		if($scope.viewList) return ;
		if($scope.thumbpage == 0){
			$scope.thumbpage++;
			$scope.page++;
			return ;
		}
		$scope.thumbscrollPage = true;
//		yield to.sleep(.500);
		if(angular.isDefined($scope.message()) && $scope.message() != null){
    		$scope.message().show = false;
    	}
        	$http.get('search/getDefaultResults?subCategoryId='.concat($routeParams.subCategoryId,
        			'&cityId=',$routeParams.cityId,'&eventType=',$routeParams.eventType,'&countryId=',$routeParams.countryId,'&eventDate=',$routeParams.eventDate,'&q=',$routeParams.q,'&start=',$scope.page)).success(function(searchResults){
    		for (var i=0;i<searchResults.eventSearchRecords.length;i++)
        	{ 
    			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY HH:mm ");
    			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY HH:mm");
    			$scope.searchRecords.push(searchResults.eventSearchRecords[i]);
        	}
    		$scope.thumbscrollPage = false;
    		$scope.page++ ;
        });
	}
	
	$scope.viewListNextPage = function(){
		if ($scope.listscrollPage) return;
		if($scope.viewThumbs) return ;
		if($scope.listpage == 0){
			$scope.listpage++;
			$scope.page++;
			return ;
		}
		$scope.listscrollPage = true;
//		yield to.sleep(.500);
		if(angular.isDefined($scope.message()) && $scope.message() != null){
    		$scope.message().show = false;
    	}
        	$http.get('search/getDefaultResults?subCategoryId='.concat($routeParams.subCategoryId,'&cityId=',$routeParams.cityId,'&eventType=',$routeParams.eventType,'&countryId=',$routeParams.countryId,'&eventDate=',$routeParams.eventDate,'&q=',$routeParams.q,'&start=',$scope.page)).success(function(searchResults){
    		for (var i=0;i<searchResults.eventSearchRecords.length;i++)
        	{ 
    			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY HH:mm");
    			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY HH:mm");
    			$scope.searchRecords.push(searchResults.eventSearchRecords[i]);
        	}
    		$scope.listscrollPage = false;
    		$scope.page++ ;
        });
	}
    $scope.fetchSearchResults = function() {
    	
    	if(angular.isDefined($routeParams.other)){
    		if($routeParams.other)
    			$scope.othercountries=false;
    		else
    			$scope.othercountries=true;
    	}
    	if(angular.isDefined($routeParams.cname)){
    		$scope.countryname=$routeParams.cname;
    	}
    	
    	if(angular.isDefined($scope.message()) && $scope.message() != null){
    		$scope.message().show = false;
    	}
        	$http.get('search/getDefaultResults?subCategoryId='.concat($routeParams.subCategoryId,'&cityId=',$routeParams.cityId,'&eventType=',$routeParams.eventType,'&countryId=',$routeParams.countryId,'&eventDate=',$routeParams.eventDate,'&q=',$routeParams.q)).success(function(searchResults){
    		for (var i=0;i<searchResults.eventSearchRecords.length;i++)
        	{ 
    			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY HH:mm");
    			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY HH:mm");
    			    			
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
        			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY HH:mm");
        			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY HH:mm");        			
        			
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
    
    
/*    $scope.getcatvalue = function(id){   
    	categories.getcategories($scope.category).success(function(categories) {
      		$scope.categories = categories;
      		for(var i=0;i<$scope.categories.length;i++) {
      			  if(id===$scope.categories[i].value){         				  
      				 $scope.eventType.push($scope.categories[i].key);
      				break;
      			  }
      			}
  	    }).error(function() {
  	    	
  	    });
  }*/
    
    $scope.getActiveCountriesList = function(){   
    	 $http.get('search/getactivecountries').success(function(activeCountries) {     		 
    		$scope.activeCountries = activeCountries;
  	    }).error(function() {
  	    	
  	    });
  }
    
    $scope.setcountryname = function(id,name) {
    	$scope.countryname = name;
    	$scope.countryid = id;   
    	$location.url('findevent?q='+'&countryId=' + id + '&cname=' +name);
    }
    
    
    $scope.fetchSearchResults($rootScope.user);
    $scope.getcurrentuser();
    $scope.getActiveCountriesList();
}