var UserController = function($scope, $http,$rootScope,currentuser,resetSrv) {
	
	$scope.userDetails = {};
	$scope.validaltemail=false;
	
    $scope.fetchUserDetails = function() {
    	$('#loadingWidget').show();
    	$http.get('account/getuser').success(function(userDetails){
        	$scope.userDetails = userDetails;
        	$('#loadingWidget').hide();
        });
        
      }
 
    
    $scope.updateUser = function() {
    	if($scope.companyform.coymanyurl.$error.url){
    		alert("Please enter valid url http://zeoevent.com");
    		return;
    	}
    	$scope.validaltemail=false;
    	if($scope.personaldetails.altemailaddress.$error.email){
    		$scope.validaltemail=true;
    		return;
    	}
    	
        $http.post('account/updateuser', $scope.userDetails).success(function() {
        	$scope.fetchUserDetails();
        }).error(function() {
        	alert("User not updated properly check console for errors.");
        });
       
        
    }
    
    $scope.resetPassword = function(newPassword, confirmPassword) {
    	$scope.newpwReq = $scope.resetform.newpw.$error.required;
    	$scope.confirmpwReq = $scope.resetform.confirmpw.$error.required;
    	if($scope.resetform.$invalid){
    		return;
    	}
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