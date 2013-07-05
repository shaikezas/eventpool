function MainController($scope, $route,$rootScope, $routeParams,$location,signupSrv,search) {
	$scope.title = "Home";
	$scope.header = "home";
	$scope.newuser = "";
	
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
    	 if($scope.newuser== true){
    		 signupSrv.createUser($scope.username, $scope.password).success(function(data) {
        		 if(data.type=="success"){
        			 $scope.signin();
        		 }else{
        			 access = {
        		               text: data.text,
        		               type: data.type,
        		               show: true
        		           };
        		 }
             }).error(function() {
             });
    	 }else{
    		 $scope.signin();
    	 }
    	
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
}