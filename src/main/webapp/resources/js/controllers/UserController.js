var UserController = function($scope, $http) {
	
	$scope.userDetails = {};
	
    $scope.fetchUserDetails = function() {
    	$http.get('account/getuser').success(function(userDetails){
        	$scope.userDetails = userDetails;
        });
        
      }
    
    $scope.updateUser = function() {        	
        $http.post('account/updateuser', $scope.userDetails).success(function() {
        	$scope.fetchUserDetails();
        }).error(function() {
        	alert("User not updated properly check console for errors.");
        });
       
        
    }
    
    $scope.resetPassword = function(newPassword, confirmPassword) {
    	$http.post('account/resetpassword/'+ newPassword +'/'+ confirmPassword).success(function() {
        	
        }).error(function() {
        	alert("Password not updated properly.");
        });
    	
        
    }
    
    $scope.fetchUserDetails();
}