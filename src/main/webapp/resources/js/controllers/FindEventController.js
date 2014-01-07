var FindEventController = function($scope,$rootScope, $http,$routeParams, $location,currentuser) {
	
	$scope.searchResults = {};
	$scope.searchRecords = new Array();
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
        			'&cityId=',$routeParams.cityId,'&eventType=',$routeParams.eventType,'&countryId=',$routeParams.countryId,'&webinar=',$routeParams.webinar,'&eventDate=',$routeParams.eventDate,
        			'&q=',$routeParams.q,'&start=',$scope.page)).success(function(searchResults){
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
        	$http.get('search/getDefaultResults?subCategoryId='.concat($routeParams.subCategoryId,'&cityId=',$routeParams.cityId,'&eventType=',$routeParams.eventType,'&countryId=',$routeParams.countryId,
        			'&eventDate=',$routeParams.eventDate,'&q=',$routeParams.q,'&webinar=',$routeParams.webinar,'&start=',$scope.page)).success(function(searchResults){
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
    		if($routeParams.other){
    			$scope.othercountries=false;
    			$scope.countryname="Other Countries";
    		}
    		else
    			$scope.othercountries=true;
    	}
    
    	

         if(angular.isDefined($routeParams.q)){
    	     $scope.q=$routeParams.q;
    	  }
    	
    	if(angular.isDefined($scope.message()) && $scope.message() != null){
    		$scope.message().show = false;
    	}
        	$http.get('search/getDefaultResults?subCategoryId='.concat($routeParams.subCategoryId,'&cityId=',$routeParams.cityId,'&eventType=',$routeParams.eventType,
        			'&countryId=',$routeParams.countryId,'&eventDate=',$routeParams.eventDate,'&webinar=',$routeParams.webinar,'&q=',$routeParams.q)).success(function(searchResults){
    		for (var i=0;i<searchResults.eventSearchRecords.length;i++)
        	{ 
    			searchResults.eventSearchRecords[i].startDate = moment(searchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY HH:mm");
    			searchResults.eventSearchRecords[i].endDate = moment(searchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY HH:mm");
    			    			
        	}
        	$scope.searchResults = searchResults;
        	$scope.searchRecords = searchResults.eventSearchRecords;
        });
    	
        	if(angular.isDefined($routeParams.cname)){
        		$scope.countryname=$routeParams.cname;
        		$scope.countryid=$routeParams.countryId;
        	}
      }

    $scope.getcurrentuser = function(){
    	
    	if ($rootScope.user == undefined) {
    		currentuser.getcurrentuser().success(function(data) {
    			$rootScope.user = data;
        	});
        }
    	
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
    
    
    
    $scope.getActiveCountriesList = function(){   
    	 $http.get('search/getactivecountries').success(function(activeCountries) {     		 
    		$scope.activeCountries = activeCountries;
  	    }).error(function() {
  	    	
  	    });
  }
    
    $scope.getdefaultcountryname = function(){
    	if(angular.isUndefined($routeParams.countryId)){
   	 		$http.get('search/getdefaultcountrynameid').success(function(countrynameid) {
   	 			if(countrynameid!=null){
   	 				$scope.countryname = countrynameid['countryName'];
   	 				$scope.countryid = countrynameid['countryId'];
   	 			}
   	 		}).error(function() {
 	    	
   	 		});
    	}
 }
    
    $scope.findevents = function(){
   	 if(angular.isDefined($scope.q) && $scope.q!="" && $scope.q!=null && $scope.q!='undefined'){
   		 $location.url('findevent?q='+$scope.q + '&countryId=' + $routeParams.countryId + '&cname=' + $routeParams.cname);
   	 } else {
   		 $location.url('findevent?countryId=' + $routeParams.countryId+ '&cname=' + $routeParams.cname);
   	 }
    }
    
    $scope.setcountryname = function(id,name) {
    	if(angular.isDefined($scope.q) && $scope.q!="" && $scope.q!=null && $scope.q!='undefined'){
      		 $location.url('findevent?q='+$scope.q + '&countryId=' + id + '&cname=' + name);
      	 } else {
      		 $location.url('findevent?countryId=' + id + '&cname=' + name);
       	 }
    }
    
    
    $scope.fetchSearchResults($rootScope.user);
    $scope.getcurrentuser();
    $scope.getActiveCountriesList();
    $scope.getdefaultcountryname();
}