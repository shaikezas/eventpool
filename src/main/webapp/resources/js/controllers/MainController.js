function MainController($scope,$http, $route,$rootScope, $routeParams,$location,search,currentuser,categories) {
	$scope.title = "Home";
	$scope.header = "home";
	$scope.newuser = "";
	$scope.signupuserform = {};
	 $scope.shows = [
	                  {id:10, value:10},
	                  {id:20, value:20},
	                  {id:30, value:30},
	                  {id:40, value:40},
	                  {id:50, value:50},
	                  {id:100, value:100}
	                ];
	 $scope.path = function () {
         return $location.url();
     };
     $scope.activeWhen = function (value) {
         return value ? 'active' : '';
     };
     $scope.signin = function () {
         $scope.$emit('event:loginRequest', $scope.username, $scope.password);
         $('#login').modal('hide');
     };
     
     $scope.login = function () {
    		 $scope.signin();
     };
     $scope.signup = function () {
    	 $http.post('signupuser', $scope.signupuserform).success(function(data) {
        		 if(data.type=="success"){
        			 $scope.username = $scope.signupuserform.email;
        			 $scope.password = $scope.signupuserform.password;
        			 $scope.signin();
        		 }else{
        			 signupmessage = {
        		               text: data.text,
        		               type: data.type,
        		               show: true
        		           };
        		 }
             }).error(function() {
             });
    	
     };
     $scope.loginuser = function () {
    	 $scope.$emit('event:loginRequired');
     };
     $scope.logout = function () {
         $rootScope.user = null;
         $scope.username = $scope.password = null;
         $scope.$emit('event:logoutRequest');
         $location.url('home');
     };
     $scope.cancel = function (){
    	 access = "";
    	 signupmessage = "";
         message = "";
         $('#login').modal('hide');
    	 $location.url('home');
     }
     
     $scope.cityFormatResult = function(cityinfo) {
     	cityinfo = cityinfo.text;
         var markup = "<table class='movie-result'><tr>";
         markup += "<td class='movie-image'><div class='flag flag-"+cityinfo.flag+" '></div></td>";
         /*if (cityinfo.posters !== undefined && cityinfo.posters.thumbnail !== undefined) {
             markup += "<td class='movie-image'><img src='" + cityinfo.posters.thumbnail + "'/></td>";
         }*/
         markup += "<td class='movie-info'><div class='movie-title'>" + cityinfo.cityName + "</div>";
         if (cityinfo.stateName !== undefined) {
             markup += "<div class='movie-synopsis'>" + cityinfo.stateName + "," + cityinfo.countryName + "</div>";
         }
         markup += "</td></tr></table>"
         return markup;
     }

     $scope.cityFormatSelection = function(cityinfo) {
         return cityinfo.text.cityName;
     }
     
     $scope.getcityinfo = function(query) {
     	search.getbasicsearchresults(query.term).success(function(data) {
 			//$scope.cityResults = data;
             var result = {results: []};
 		    for (var i = 0; i < data.length; i++) {
 		        result.results.push({id: data[i].cityId, text: data[i]});
 		    }
 		    query.callback(result);

         }).error(function(data) {

         });
     };
     
     $scope.cityselect2options = {
     		query: $scope.getcityinfo, 
     		minimumInputLength: 3, 
     		placeholder: "City",
     		formatResult: $scope.cityFormatResult, 
     	    formatSelection: $scope.cityFormatSelection,
     	    dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
     };
     $scope.selectCity = function(region) {
     	if(angular.isUndefined(region) || region == null){
     		return ;
     	}
         
         
     };
     
     $scope.fetchCategories = function() {
     	categories.getcategories($scope.category).success(function(categories) {
     		$scope.categories = categories;
 	    }).error(function(data) {
 	    	
 	    });
     }
     
     $scope.getcurrentuser = function(){
      	
      	if ($rootScope.user == undefined) {
      		currentuser.getcurrentuser().success(function(data) {
      			$rootScope.user = data;
          	});
          }
      	
      }
      
      $scope.getcurrentuser();
      $scope.fetchCategories();
}