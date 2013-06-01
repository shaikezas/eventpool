function MainController($scope, $route, $routeParams,$location) {
	$scope.title = "Home";
	$scope.header = "home";
	 $scope.path = function () {
         return $location.url();
     };
     $scope.activeWhen = function (value) {
         return value ? 'active' : '';
     };
}