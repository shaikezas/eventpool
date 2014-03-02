function MainController($scope,$http, $route,$rootScope, $routeParams,$location,search,currentuser,categories,resetSrv) {
	$scope.title = "Home";
	$scope.header = "home";
	$scope.newuser = "";
	$scope.myInterval = 4000;
	$scope.selectedCat = "All Categories";
/*	$scope.eventType = new Array();*/
	$scope.signupuserform = {};
	$scope.slides = [{img:'resources/bootstrap/img/Banner1.jpg'},
	                 {img:'resources/bootstrap/img/Banner2.jpg'},
	                 {img:'resources/bootstrap/img/Banner3.jpg'},
	                 {img:'resources/bootstrap/img/banner_4.jpg'},
	                 {img:'resources/bootstrap/img/banner_5.jpg'},
	                 {img:'resources/bootstrap/img/banner_6.jpg'}];
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
         $('#signup').modal('hide');
     };
     
  
     
     $scope.login = function () {
    	 $scope.username = $("#username").val();
    	 if($scope.username !=null || $$scope.username != '')
    		 $scope.usernameReq = false;
    	 else
    		 $scope.usernameReq = $scope.userForm.username.$error.required;
    	 $scope.password = $("#password").val();
    	 if($scope.password !=null || $scope.password != '')
    		 $scope.passwordRequired = false;
    	 else
    		 $scope.passwordRequired = $scope.userForm.password.$error.required;
    	 $scope.validEmail = $scope.userForm.username.$error.email; 
    	 if($scope.userForm.$invalid){
    		 return ;
    	 }
    	 $scope.signin();
    	 
     };
     $scope.signup = function () {
    	 $scope.newUserEmailReq = $scope.signupForm.newUserEmail.$error.required;    	 
    	 $scope.newuserValidEmail = $scope.signupForm.newUserEmail.$error.email; 
    	 $scope.newUserPasswordReq = $scope.signupForm.newUserPassword.$error.required;
    	 $scope.passlength = $scope.signupForm.newUserPassword.$error.minlength;
    	 $scope.newUserPasswordCFReq = $scope.signupForm.newUserPasswordCF.$error.required;
    	 if($scope.signupForm.$valid){
    		 if($scope.signupuserform.password == $scope.confirmpassword){
    			 $http.defaults.headers.post.Authorization='';
    			 
    			 $http.post('signupuser', $scope.signupuserform).success(function(data) {
        		 if(data.type=="success"){
        			 $scope.username = $scope.signupuserform.email;
        			 $scope.password = $scope.signupuserform.password;
        			 $scope.signin();
        			$scope.resetsignform();
        		 }else{
        			 signupmessage = {
        		               text: data.text,
        		               type: data.type,
        		               show: true
        		           };
        		 }        		 
             }).error(function() {
             });
    	 }
    	 else {
    		 alert("Password and Confirm password are not matched.");
    	 }
    	}
     };
     
     $scope.forgotpwd = function () {
    	 $scope.usermailReq = $scope.fogotForm.usermail.$error.required;
    	 $scope.validuserMail = $scope.fogotForm.usermail.$error.email; 
    	 if($scope.fogotForm.$invalid){
    		 return;
    	 }
    	 if($scope.usermailReq)
    	 $('#forgot').modal('hide');
    			 resetSrv.forgotPassword($scope.usermail).success(function() {
    				 $('#login').modal('hide');
    				 $('#signup').modal('hide');
    		        }).error(function() {
    		        	alert("Password not reset properly.");
    		        });
     };
     
     $scope.loginuser = function () {
    	 $scope.resetsignform();
    	 $scope.$emit('event:loginRequired');
     };
     
     $scope.signupuser = function () {
    	 $scope.resetsignform();
    	 $scope.$emit('event:signupRequired');
     };
     
     $scope.resetsignform = function(){
    	 $scope.signupuserform.fname="";    	 
    	 $scope.signupuserform.lname=""; 
    	 $scope.signupuserform.email="";
    	 $scope.signupuserform.password="";
    	 $scope.confirmpassword="";
    	 $scope.username="";
    	 $scope.password="";
     }
     
     $scope.logout = function () {
         $rootScope.user = null;
         $scope.username = $scope.password = null;
         $scope.$emit('event:logoutRequest');
         $location.url('home');
     };
     $scope.cancel = function (){
    	 $scope.resetsignform();
    	 $scope.usernameReq=false;
    	 $scope.passwordRequired=false;
    	 $scope.passlength=false;
    	 $scope.validEmail=false;
    	 $scope.newUserEmailReq=false;
    	 $scope.newuserValidEmail=false;
    	 $scope.newUserPasswordReq=false;
    	 $scope.newUserPasswordCFReq=false;
    	 
    	 access = "";
    	 signupmessage = "";
         message = "";
         $('#login').modal('hide');
         $('#signup').modal('hide');
    	 $location.url('home');
     }
     
     $scope.cancelForgot = function (){
         $('#forgot').modal('hide');
     }
     
     $scope.openForgot = function (){
         $('#forgot').modal('show');
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
     
/*     $scope.getcatvalue = function(id){    	
    	   categories.getcategories($scope.category).success(function(categories) {
         		$scope.categories = categories;
         		for(var i=0;i<$scope.categories.length;i++) {
         			  if(id===$scope.categories[i].value){         				  
         				 $scope.eventType.push($scope.categories[i].key);
         				break;
         			  }
         			}
     	    }).error(function() {
     	    	
     	    });
     }*/
     
     $scope.getcurrentuser = function(){
      	
      	if ($rootScope.user == undefined) {
      		currentuser.getcurrentuser().success(function(data) {
      			$rootScope.user = data;
          	});
          }
      	
      }
     
     
     $scope.setCatValue = function(catId, catValue){
      	 $scope.selectedCat = catValue;
      	 $scope.catId = catId;
     }
     
      $scope.findevents = function(){
     	 if(angular.isDefined($scope.q) && $scope.q!="" && $scope.q!=null){
    		 $location.url('findevent?q='+$scope.q);
    	 } else {
    		 $location.url('findevent');
    	 }
      }
      
  	$scope.byCategory = function(catId, catName){
  		$location.url('findevent?subCategoryId='+catId + '&catname=' + catName);
	}
     
     $scope.fetchhomepagerecords = function(){
    	 if(angular.isUndefined($scope.homePageResults) && $scope.homePageResults == null){    		 
    	 $http.get('search/getSearchResults').success(function(homePageSearchResults) {    		 
    		
    			for (var i=0;i<homePageSearchResults.eventSearchRecords.length;i++)
            	{ 
    				homePageSearchResults.eventSearchRecords[i].startDate = moment(homePageSearchResults.eventSearchRecords[i].startDate).format("DD-MMM-YYYY HH:mm");
    				homePageSearchResults.eventSearchRecords[i].endDate = moment(homePageSearchResults.eventSearchRecords[i].endDate).format("DD-MMM-YYYY HH:mm");
        			    			
            	}
            $scope.homePageResults = homePageSearchResults.eventSearchRecords;
        }).error(function() {
//            $scope.setError('Home page results are not correct.');
        });
      }
     }
     
      $scope.getcurrentuser();
      $scope.fetchCategories();
      $scope.fetchhomepagerecords();
}