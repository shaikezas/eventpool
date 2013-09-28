
<!DOCTYPE html>
<html lang="en" ng-app="EventPool" ng-controller="MainController">
  <head>
    <meta charset="utf-8">
    <title ng-Bind-Template="Event Pool : {{title}}"></title>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	
	
	
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Coda">
    
    
    <link href="resources/bootstrap/css/feature-carousel.css" rel="stylesheet">
	<link href="css/datetimepicker.css" rel="stylesheet" media="screen">
	<link href="lib/select2/select2.css" rel="stylesheet" media="screen">
	<link href="resources/bootstrap/css/ui.multiselect.css" rel="stylesheet" type="text/css"/>
	<link href="css/wysiwyg.css" rel="stylesheet" media="screen">
	
	<link rel="stylesheet" href="lib/ckeditor/skins/moono/editor.css">	
	
	<link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    
    <link href="resources/bootstrap/css/style.css" rel="stylesheet">
    
    
	<!-- This styles are being added for city search dropdown. remove it when proper css are written -->
	<style type="text/css">
        .movie-result td {vertical-align: top }
        .movie-image { width: 20px; }
        .movie-image img { height: 20px; width: 20px;  }
        .movie-info { padding-left: 10px; vertical-align: top; }
        .movie-title { font-size: 1.0em; padding-bottom: 2px; }
        .movie-synopsis { font-size: .8em; color: #888; }
        .select2-highlighted .movie-synopsis { font-size: .8em; color: #eee; }
        .bigdrop.select2-container .select2-results {max-height: 100px;}
        .bigdrop .select2-results {max-height: 300px;}
      </style>
  </head>

  <body >

<div ng-view></div>

 

	<div >
		    <div class="modal newModel" style="display: none" id="login">
				        
				     	<div class="modal-header">
				            <a class="close" data-dismiss="modal" ng-click="cancel()">x</a>
				            <h3>Login</h3>
				        </div>
				     
				     <!-- Old User Login Start -->
				  		<div class="userLogin"> 
							<div class="modal-body">
				        		 <div ng-class="'alert alert-'+access().type" ng-show="access().show">
				        		 <button type="button" class="close" ng-click="access().show=false;usernameReq=false;passwordRequired=false;validEmail=false">x</button>
				        		 <msg>{{access().text}}</msg>
				    			</div>
							<form class="form-horizontal" name="userForm">
				         
								  <fieldset>
								   		<legend ng-style="{color:'blue'}">Login User</legend>
								    	<label for="username">Email</label>
								    	<input id="username" ng-model="username" type="email" name="username" required/>
				                         <div  ng-style="{color:'red'}">
											<span ng-show="usernameReq" ng-style="{color:'red'}">Email is required.</span>
											<span ng-show="validEmail" ng-style="{color:'red'}">Enter valid email address.</span>
				                		</div>
								   
								   		<label for="password">Password</label>
								 		<input type="password" id="password" name="password" ng-model="password" required ng-enter="login()"/>
					                     <div  ng-show="passwordRequired" ng-style="{color:'red'}">Password is Required.
											<!-- <span ng-show="userForm.password.$error.required" ng-style="{color:'red'}">Password is required.</span> -->
					               		 </div>
								
								  </fieldset>

							</form>
						
							 <div class="modal-footer">
			        			<a ng-click="login()" class="btn " >Sign In</a>
			        			
			    			</div>
						 <br><br>
						 <a herf="#">I cannot access my account. </a>
						</div></div> 
						<!-- Old User Login End -->
					
					
						<!-- New User Login End -->
						<div class="newUserLogin"> 
							
							<div class="modal-body">
				        		 <div ng-class="'alert alert-'+signupmessage().type" ng-show="signupmessage().show">
				        		 <button type="button" class="close" ng-click="signupmessage().show=false;newUserEmailReq=false;newuserValidEmail=false;newUserPasswordReq=false;newUserPasswordCFReq=false">x</button>
				        		 <msg>{{signupmessage().text}}</msg>
				    		</div>
							
							<form name="signupForm">
							  <fieldset>
							    <legend ng-style="{color:'blue'}">New User Sign Up Here</legend>
							    <label for="firstName">First Name</label>
								<input id="firstName" type="text" ng-model="signupuserform.fname" name="firstName"/>
								
								<label for="lastName">Last Name</label>
								<input id="lastName" type="text" ng-model="signupuserform.lname" name="lastName"/>
								
							    <label for="newUserEmail">Email</label>
								<input id="newUserEmail" type="email" ng-model="signupuserform.email" name="newUserEmail" required/>
								 <div  ng-style="{color:'red'}">
									<span ng-show="newUserEmailReq" ng-style="{color:'red'}">Email is required.</span>
									<span ng-show="newuserValidEmail" ng-style="{color:'red'}">Enter valid email address.</span>
				                </div>
								
								<label for="newUserPassword">Password</label>
								<input id="newUserPassword" type="password" ng-model="signupuserform.password" name="newUserPassword" required/>
								<div  ng-show="newUserPasswordReq" ng-style="{color:'red'}">Password is Required.</div>
								
								<label for="newUserPasswordCF">Confirm Password</label>
								<input id="newUserPasswordCF" type="password" ng-model="confirmpassword" name="newUserPasswordCF" required/>
								<div  ng-show="newUserPasswordCFReq" ng-style="{color:'red'}">Confirm Password is Required.</div>
								
								
								<div class="modal-footer">
				        			<a class="btn" ng-click="signup()">Sign Up</a>
			    				</div>
							  </fieldset>
							</form>
						</div>
				     
				        <!-- New User Login End -->
				       
				        
				       
				        
			
						
			
			
		  
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			</div>
		   
	</div>

	
 
      



   

	<script src="lib/jquery/jquery.js" type="text/javascript"></script>
	<script src="lib/jquery/jquery-ui.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"> </script>
    <!-- check this for datetime picker documentation http://www.malot.fr/bootstrap-datetimepicker -->
    <script type="text/javascript" src="lib/bootstrap/bootstrap-datetimepicker.js"> </script>
    <script src="resources/bootstrap/js/bootstrap-tab.js"></script>
    <script src="resources/bootstrap/js/jquery.featureCarousel.min.js"></script>
	<script src="lib/moment.js"></script>
	<script src="lib/select2/select2.js"></script>
	<script src="lib/angular/angular.min.js"></script>
	<script src="lib/angular/angular-ui-utils.js"></script>
	<script src="lib/angular/angular-select2.js"></script>
<!-- 	<script  src="lib/wysiwyg.js"></script>
	<script  src="lib/wysiwyg-settings.js"></script> -->
	
	<!-- Javascript files related to project -->
	<script src="resources/js/app.js"></script>
	<script  src="js/base64.js"></script>
	<script src="resources/js/services.js"></script>
	<script src="js/objects.js"></script>
	<script src="resources/js/controllers/MainController.js"></script>
	<script src="resources/js/controllers/CreateEventController.js"></script>
	<script src="resources/js/controllers/EventPageController.js"></script>
	<script src="resources/js/controllers/MyEventsController.js"></script>
	<script src="resources/js/controllers/FindEventController.js"></script>
	<script src="resources/js/controllers/MyTicketsController.js"></script>
	<script src="resources/js/controllers/UserController.js"></script>
	<script src="resources/js/filters.js"></script>
	<script src="resources/js/directives.js"></script>
	<script src="resources/js/ui-bootstrap-tpls-0.2.0.js"></script>
	
	<script src="lib/fileupload/jquery.iframe-transport.js"></script>
	<script src="lib/fileupload/jquery.fileupload.js"></script>
	<script src="lib/fileupload/load-image.min.js"></script>    
	<script  src="resources/bootstrap/js/ui.multiselect.js"></script>
	
	<script type="text/javascript" src="js/jquery.ocupload-1.1.4.js"></script>
	<script type="text/javascript" src="lib/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="lib/ckeditor/build-config.js"></script>
	<script type="text/javascript" src="lib/ckeditor/config.js"></script>
	<script type="text/javascript" src="lib/ckeditor/styles.js"></script>
	<script type="text/javascript" src="lib/ckeditor/lang/en.js"></script>


	
  </body>
</html>
