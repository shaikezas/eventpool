var UserController = function($scope, $http) {
	
	$scope.userDetails = {};
	
    $scope.fetchUserDetails = function() {
    	$http.get('getuser').success(function(userDetails){
        	$scope.userDetails = userDetails;
        });
        
      }
    
    $scope.updateUser = function() {        	
        $http.post('updateuser', $scope.userDetails).success(function() {
       
        }).error(function() {
        	alert("User not updated properly check console for errors.");
        });
       
        
    }
    
    $scope.fetchUserDetails();
}