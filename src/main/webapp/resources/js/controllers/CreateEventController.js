'use strict';

/**
 * CreateEventController
 * @constructor
 */
var CreateEventController = function($scope, $http,search,subcategories,categories, $routeParams, $timeout, srvevent,eventsettings, $dialog,$location,$rootScope,currentuser) {
    $scope.event = {};
    $scope.editMode = false;
    $scope.$parent.title="Create Event";
    $scope.isCollapsed = true;
    $scope.isWebinarChecked = false;
    $scope.isWebinar = false;
    $scope.startDateBeforeEndDate = false;
    $scope.questionForm = {};
    $scope.eventFormSettings = {};
    $scope.tktId = "";
    $scope.subject = "";
    $scope.toValue = "";
    $scope.message = "";
    var map = [];
    var classificationTypes1 = [];
    var upgrades = [];
    var i;
    $scope.from="admin@eventhut.com";
    $scope.disabled = false;
   
      $scope.template = "html/event/editevent.html";
      $scope.templateSelect = "edit";
      $scope.header = "create";
      
      $scope.updateUrl = function(){
    	  $scope.event.eventUrl = $scope.event.title;
      }
      
      $scope.updateQuestions = function(){
    	  $http.post('event/myevent/updatesettings', $scope.eventFormSettings).success(function(data) {
//            $scope.fetchEventsList();
        }).error(function() {
            $scope.setError('Could not update settings.');
        });
      }

  $scope.validateeventname = function(eventname) {
	  if(angular.isUndefined(eventname))
		  return true;
	  if(eventname == "success")
		  return true;
	  return true; 
  }
  
   $scope.saveAndClose = function () {
	  alert($scope.questionForm.question + $scope.questionForm.questionType['questionTypeName']);
	 /*  $http.post('event/addQuestion', $scope.questionForm).success(function() {
	   $scope.shouldBeOpen = false; 
	   alert($scope.questionForm.question + $scope.questionForm.questionType['questionTypeName']);
	   $http.post('event/addQuestion', $scope.questionForm).success(function() {
     }).error(function() {
         $scope.setError('Could not add a Question ');
     });*/
	   
   };  
    

   $scope.createevent = function(){
	   $http.post('event/myevent/createevent').success(function() {
     }).error(function() {
     });
   }
   
   $scope.getMembershipId = function(){
	   $http.get('event/myevent/userMembershipId').success(function(membership) {
		   $scope.membership = membership;
     }).error(function() {
     });
   }

   
   
    $scope.close = function(){
    	$scope.shouldBeOpen = false;
  	  };
      
   $scope.questionType = [  {questionTypeName : 'Text' },      
    	                    {questionTypeName : 'Dropdown' },
    	                    {questionTypeName : 'Radio Buttons' }, 
    	                    {questionTypeName : 'Checkboxes' }];
   		  
   $scope.classificationTypes = 		[{id : 1 , classificationType : 'CLASSIC'},      
                                		 {id : 2 , classificationType : 'SILVER'},
                                		 {id : 3 , classificationType : 'GOLD'}, 
                                		 {id : 4 , classificationType : 'PLATINUM'},
                                		 {id : 5 , classificationType : 'DIAMOND'}];
   
    $scope.getMembershipId();
    $scope.myevent = function() {
    	if(angular.isDefined($routeParams.eventid)){
    	srvevent.myevent($routeParams).success(function(data) {
		$scope.event = data;
		$scope.showOrgName = $scope.event.userEventSettingDTO.showOrgName;
	    $scope.showOrgDesc = $scope.event.userEventSettingDTO.showOrgDesc;
	    $scope.contactDetails = $scope.event.userEventSettingDTO.contactDetails;
	    $scope.showHostWebsite = $scope.event.userEventSettingDTO.showHostWebsite;
	    $scope.showAttendeDetails = $scope.event.userEventSettingDTO.showAttendeDetails;
	   
	    if($scope.event.isWebinar){
	    	$scope.setRequiredFields();
	    }
	    i = $scope.event.classificationType;
	    upgrades = $scope.classificationTypes;
	    while(i <= $scope.membership){
	    	classificationTypes1.push(upgrades[i-1]);
	    	i = i + 1;
	    }
	    
	    $scope.classificationTypes = classificationTypes1;
	    $scope.isWebinar = $scope.event.isWebinar;
		 
    }).error(function(data) {
    	
    });
    } else {
    	$scope.showOrgName = true;
    	$scope.showOrgDesc = true;
    	$scope.contactDetails = true;
    	$scope.showHostWebsite = true;
    	$scope.showAttendeDetails = true;
    }
    };
    
    
    $scope.updateUrl = function() {
    	$scope.event.eventUrl = $scope.event.title;
    	};
    
  
    	     	
    
   
   /* $scope.getsearchResults = function(query) {
		search.getbasicsearchresults(query).success(function(data) {
			$scope.searchResults = data;
	    }).error(function(data) {
	    	
	    });
	};*/
	
	
    $scope.fetchEventsList = function() {
        $http.get('event/eventslist.json').success(function(event){
            $scope.event = event;
        });
    }
    
    
    $scope.fetchEventSettings = function() {
    	eventsettings.geteventsettings($scope.event.id).success(function(eventFormSettings) {
			$scope.eventFormSettings = eventFormSettings;
	    }).error(function(data) {
	    	
	    });
    }
    
    $scope.fetchTicketsHistory = function(eventid) {
        $http.get('event/myevent/getTicketHistory/'+eventid).success(function(subOrdersList){
        	$scope.subOrdersList = subOrdersList;
        });
    }
    
    $scope.fetchBuyers = function(ticketId) {
    	  $http.get('event/myevent/buyers/'+ticketId).success(function(buyersList){
        	$scope.buyersList = buyersList;
         });
    }
    
    $scope.storeTicketId = function(ticketId) {
    	$scope.tktId = ticketId;
  }
    
    $scope.sendMail = function(mail) {
    	$scope.mailString = $scope.tktId;
    	$scope.mailString  = $scope.mailString + "~" + mail.subject;
    	$scope.mailString  = $scope.mailString + "~" + mail.toValue;
    	$scope.mailString  = $scope.mailString + "~" + mail.message;
    	
  	  $http.post('event/myevent/sendmail/'+$scope.mailString).success(function(){
  		 
       });
  }
    
    $scope.fetchAttendees = function(ticketId) {
    	$http.get('event/myevent/attendees/'+ticketId).success(function(attendeesList){
        	$scope.attendeesList = attendeesList;
        });
    }


    $scope.addNewEvent = function() {
    	/*$('#loadingPopup').modal('toggle');*/
    	$scope.disabled = true;
    	$scope.resetError();
        $scope.validations();
        if($scope.stopSubmitAction === true){
        	$scope.stopSubmitAction = false;        	
         }
        else {        	
        $http.post('event/myevent/addevent', $scope.event).success(function() {
        	$location.url('myevents');
        }).error(function() {
        	$scope.disabled = false;
        	$('#loadingPopup').modal('toggle');
        });
        }
        
    }
    
    $scope.addNewEventAndPublish = function() {
    	$scope.resetError();
        $scope.validations();
        if($scope.stopSubmitAction === true){
        	$scope.stopSubmitAction = false;
         }
        else {        	
        $http.post('event/myevent/addEventAndPublish', $scope.event).success(function() {
        	$location.url('myevents');
        }).error(function() {
        });
        }
        
    }
    $scope.setRequiredFields = function() {
    	$scope.isWebinarChecked=!$scope.isWebinarChecked;
    	$scope.venueForm.venueName.$error.required = !$scope.isWebinarChecked;
    	$scope.venueForm.venueAddress1.$error.required = !$scope.isWebinarChecked;
    	$scope.venueForm.venueAddress2.$error.required = !$scope.isWebinarChecked;
    	$scope.venueForm.$invalid = !$scope.isWebinarChecked;
    }
    $scope.validations = function() {
    	$scope.nameRequired = $scope.eventForm.eName.$error.required;
/*    	alert('xyz'+JSON.stringify($scope.eventForm.description));
*/    	$scope.descRequired = $scope.eventForm.description.$error.required;
    	$scope.startDateRequired = $scope.eventForm.startDate.$error.required;
    	$scope.endDateRequired = $scope.eventForm.endDate.$error.required;
    	$scope.catRequired = $scope.eventForm.category.$error.required;
    	/*$scope.subCatRequired = $scope.eventForm.subCategory.$error.required;*/
    	$scope.venueNameReq = $scope.venueForm.venueName.$error.required;
    	$scope.venueAdd1Req = $scope.venueForm.venueAddress1.$error.required;
    	if(angular.isUndefined($scope.cityinput) || $scope.cityinput==null){
    		$scope.citryReq = true;
    	}
    	$scope.pinReq= $scope.venueForm.pincode.$error.required;
//    	$scope.citryReq = $scope.cityinput.$error.required;
    	$scope.orgNameReq = $scope.orgForm.orgName.$error.required;
    	if(angular.isDefined($scope.event.startDate)&&angular.isDefined($scope.event.endDate)){
      	var startDate = new Date($scope.event.startDate);
    	var endDate = new Date($scope.event.endDate);
    	if(startDate.getTime()>endDate.getTime()){
    		$scope.stopSubmitAction=true;
    		$scope.startDateBeforeEndDate = true;
    	}
    	else {
    		$scope.startDateBeforeEndDate = false;
    	}
    	}
      	if($scope.eventForm.$invalid || $scope.venueForm.$invalid || $scope.orgForm.$invalid || $scope.tktForm.$invalid){
    		$scope.stopSubmitAction=true;    		
    		
    	}
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
    
   
    $scope.addNewFreeTicket = function() {
        $scope.resetError();
        if(angular.isUndefined($scope.event.tickets)) {	
        	$scope.event.tickets = [];
        }
      
        var ticket = new eventpool.ticket();
        ticket.ticketType = "FREE";
        ticket.showFree = true;

        if(angular.isDefined($scope.event.startDate)){
        	var sEnd = $scope.event.startDate;
        	sEnd = new Date(sEnd);
        	var millSecs = sEnd.getTime();
        	millSecs = millSecs - 3600000;
        	sEnd = new Date(millSecs);
        	ticket.saleEnd = moment(sEnd).format("DD-MMM-YYYY hh:mm A");
        }
        
        $scope.event.tickets.push(ticket);
    }
    
    $scope.addNewPaidTicket = function() {
        $scope.resetError();
        if(angular.isUndefined($scope.event.tickets)) {
        	$scope.event.tickets = [];
        }
        var ticket = new eventpool.ticket();
        ticket.ticketType = "PAID";
        ticket.showPrice = true;
        if(angular.isDefined($scope.event.startDate)){
	        var sEnd = $scope.event.startDate;
	        sEnd = new Date(sEnd);
	        var millSecs = sEnd.getTime();
	        millSecs = millSecs - 3600000;
	        sEnd = new Date(millSecs);
	        ticket.saleEnd = moment(sEnd).format("DD-MMM-YYYY hh:mm A");
        }
        $scope.event.tickets.push(ticket);
    }
    
    $scope.removeTicket = function(index) {
        $scope.resetError();
        $scope.event.tickets.splice(index, 1);
    }

    $scope.updateEvent = function(event) {
        $scope.resetError();

        $http.put('event/myevent/updateEvent', event).success(function() {
            $scope.fetchEventsList();
            $scope.event.name = '';
            $scope.event.speed = '';
            $scope.event.diesel = false;
            $scope.editMode = false;
        }).error(function() {
            $scope.setError('Could not update the event');
        });
    }

    $scope.editEvent = function(event) {
        $scope.resetError();
        $scope.event = event;
        $scope.editMode = true;
        
    }

/*    $scope.removeEvent = function(id) {
        $scope.resetError();

        $http.delete('event/myevent/removeEvent/' + id).success(function() {
            $scope.fetchEventsList();
        }).error(function() {
            $scope.setError('Could not remove event');
        });
    };
    

    $scope.removeAllEvents = function() {
        $scope.resetError();

        $http.delete('event/myevent/removeAllEvents').success(function() {
            $scope.fetchEventsList();
        }).error(function() {
            $scope.setError('Could not remove all events');
        });

    };*/
    
    $scope.getsearchResults = function(query) {
		search.getbasicsearchresults(query).success(function(data) {
			$scope.searchResults = data;
	    }).error(function(data) {
	    	
	    });
	};

    $scope.resetEventForm = function() {
        $scope.resetError();
        $scope.event = {};
        $scope.editMode = false;
    }

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
        message().show = false;
    }

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    }
    
    $scope.showticketsettings = function(ticket, show) {
    	ticket.showsettings = show;
    }
    
    $scope.selectCity = function(region) {
    	if(angular.isUndefined(region) || region == null){
    		return ;
    	}
        $scope.state =  region.text.stateName;
        $scope.country = region.text.countryName;
        $scope.event.cityId = region.text.cityId;
        
    };
   /* $scope.profilepic;
    $scope.$watch("profilepic", function() {
    	if(!angular.isUndefined($scope.profilepic)) {
//    		$scope.event.banner = $scope.profilepic.uniqueid;
    	}
    })
    */
     $scope.fetchCategories = function() {
    	categories.getcategories($scope.category).success(function(categories) {
    		$scope.categories = categories;
	    }).error(function(data) {
	    	
	    });
    }
    
/*    $scope.fetchSubCategories = function() {
    	subcategories.getsubcategories($scope.category).success(function(subcategories) {
			$scope.subcategories = subcategories;
	    }).error(function(data) {
	    	
	    });
    }*/
     
     $scope.getcurrentuser = function(){
     	
     	if ($rootScope.user == undefined) {
     		currentuser.getcurrentuser().success(function(data) {
     			$rootScope.user = data;
         	});
         }
     	
     }
     
     $scope.getcurrentuser();
     
    $scope.fetchCategories();
    
//    $scope.createevent();
    
    $scope.myevent();
    
    $scope.predicate = 'id'
}

