
<!DOCTYPE html>
<html lang="en" ng-app="EventPool">
  <head>
    <meta charset="utf-8">
    <title ng-Bind-Template="Event Pool : {{title}}"></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="resources/bootstrap/css/style.css" rel="stylesheet">
	<link href="css/datetimepicker.css" rel="stylesheet" media="screen">

  </head>

  <body >

    <!-- Navbar
    ================================================== -->
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
         
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active">
                <a href="#/home">Home</a>
              </li>
              <li class="">
                <a href="#/findevent">Find Event</a>
              </li>
              <li class="">
                <a href="#/createevent">Create Event</a>
              </li>
              <li class="">
                <a href="#/myevents">My Events</a>
              </li>
              <li class="">
                <a href="#/mytickets">My Tickets</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>




  <div class="container">

   
<div ng-view></div>
      </div>



    <!-- Footer
    ================================================== -->
    <footer class="footer">
      <div class="container">
        © 2009-2013, Copyright @ Company Name. All Rights Reserved.
      </div>
    </footer>

	<script src="lib/jquery/jquery.js" type="text/javascript"></script>
	<script src="lib/jquery/jquery-ui.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"> </script>
    <script type="text/javascript" src="lib/bootstrap/bootstrap-datetimepicker.js"> </script>
    <script src="resources/bootstrap/js/bootstrap-tab.js"></script>

	<script src="resources/js/lib/angular/angular.js"></script>
	<script src="resources/js/app.js"></script>
	<script src="resources/js/services.js"></script>
	<script src="js/objects.js"></script>
	<script src="resources/js/controllers/CreateEventController.js"></script>
	<script src="resources/js/filters.js"></script>
	<script src="resources/js/directives.js"></script>
	<script src="resources/js/ui-bootstrap-tpls-0.2.0.js"></script>
	
	<script src="lib/fileupload/jquery.iframe-transport.js"></script>
	<script src="lib/fileupload/jquery.fileupload.js"></script>
	<script src="lib/fileupload/load-image.min.js"></script>    
	    


  </body>
</html>
