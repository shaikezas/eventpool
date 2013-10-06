var UserController = function($scope, $http,$rootScope,currentuser,resetSrv) {
	
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
    	if(newPassword !== confirmPassword){
    		alert("password mismatch");
    		return;
    	}
    	resetSrv.resetPassword(newPassword).success(function() {
        }).error(function() {
        	alert("Password not updated properly.");
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
    
    $scope.fetchUserDetails();
}