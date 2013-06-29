function MainController($scope, $route,$rootScope, $routeParams,$location) {
	$scope.title = "Home";
	$scope.header = "home";
	
	 $scope.path = function () {
         return $location.url();
     };
     $scope.activeWhen = function (value) {
         return value ? 'active' : '';
     };
     $scope.login = function () {
         $scope.$emit('event:loginRequest', $scope.username, $scope.password);
         $('#login').modal('hide');
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
    	 $location.url('home');
     }
}