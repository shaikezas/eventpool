function MainController($scope, $route,$rootScope, $routeParams,$location,signupSrv) {
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
    	 $location.url('home');
     }
}