
<!DOCTYPE html>
<html lang="en" ng-app="EventPool" ng-controller="MainController">
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
	<link href="lib/select2/select2.css" rel="stylesheet" media="screen">

	<!-- This styles are being added for city search dropdown. remove it when proper css are written -->
	<style type="text/css">
        .movie-result td {vertical-align: top }
        .movie-image { width: 20px; }
        .movie-image img { height: 20px; width: 20px;  }
        .movie-info { padding-left: 10px; vertical-align: top; }
        .movie-title { font-size: 1.2em; padding-bottom: 15px; }
        .movie-synopsis { font-size: .8em; color: #888; }
        .select2-highlighted .movie-synopsis { font-size: .8em; color: #eee; }
        .bigdrop.select2-container .select2-results {max-height: 300px;}
        .bigdrop .select2-results {max-height: 300px;}
      </style>
  </head>

  <body >



 

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
    <!-- check this for datetime picker documentation http://www.malot.fr/bootstrap-datetimepicker -->
    <script type="text/javascript" src="lib/bootstrap/bootstrap-datetimepicker.js"> </script>
    <script src="resources/bootstrap/js/bootstrap-tab.js"></script>
	<script src="lib/moment.js"></script>
	<script src="lib/select2/select2.js"></script>
	<script src="lib/angular/angular.js"></script>
	<script src="lib/angular/angular-ui-utils.js"></script>
	<script src="lib/angular/angular-select2.js"></script>
	
	<!-- Javascript files related to project -->
	<script src="resources/js/app.js"></script>
	<script src="resources/js/services.js"></script>
	<script src="js/objects.js"></script>
	<script src="resources/js/controllers/MainController.js"></script>
	<script src="resources/js/controllers/CreateEventController.js"></script>
	<script src="resources/js/controllers/EventPageController.js"></script>
	<script src="resources/js/controllers/MyEventsController.js"></script>
	<script src="resources/js/controllers/MyTicketsController.js"></script>
	<script src="resources/js/filters.js"></script>
	<script src="resources/js/directives.js"></script>
	<script src="resources/js/ui-bootstrap-tpls-0.2.0.js"></script>
	
	<script src="lib/fileupload/jquery.iframe-transport.js"></script>
	<script src="lib/fileupload/jquery.fileupload.js"></script>
	<script src="lib/fileupload/load-image.min.js"></script>    
	    

  </body>
</html>
