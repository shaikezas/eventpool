var UserController = function($scope, $http,$rootScope,currentuser,resetSrv) {
	
	$scope.userDetails = {};
	$scope.validaltemail=false;
	
    $scope.fetchUserDetails = function() {
    	$http.get('account/getuser').success(function(userDetails){
        	$scope.userDetails = userDetails;
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
    
    $scope.selectCity = function(region) {
    	if(angular.isUndefined(region) || region == null){
    		
    		return ;
    	}    	
        $scope.userDetails.homeAddress.cityId = region.text.cityId;
    }
    
    $scope.selectOfficeCity = function(region) {
    	if(angular.isUndefined(region) || region == null){
    		
    		return ;
    	}    	
        $scope.userDetails.officeAddress.cityId = region.text.cityId;
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