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
//    $scope.startDateBeforeEndDate = false;
    $scope.questionForm = {};
    $scope.eventFormSettings = {};
    $scope.tktId = "";
    $scope.subject = "";
    $scope.toValue = "";
    $scope.message = "";
    $scope.citySelect = {};
    $scope.timezoneSelect = {};
    $scope.eventFormOptions = {};
    var map = [];
    var classificationTypes1 = [];
    var upgrades = [];
    var i;
    $scope.from="admin@eventhut.com";
    $scope.disabled = false;
    $scope.stopSubmitAction = false;
   
      $scope.template = "html/event/editevent.html";
      $scope.templateSelect = "edit";
      $scope.header = "create";
      
      $scope.updateUrl = function(){
    	  $scope.event.eventUrl = $scope.event.title;
      }
      
      $scope.updateQuestions = function(){
//    	  if(!$scope.event.isPublish){
    	  $http.post('event/myevent/updatesettings', $scope.eventFormSettings).success(function(data) {
        }).error(function() {
            $scope.setError('Could not update settings.');
        });
    	/*  }
    	  else {
      		alert("Published events cannot be modified.\n Review event details.");
      	}*/
      }
      
      $scope.updateEventOptions = function(){
//    	  if(!$scope.event.isPublish){
    	  $http.post('event/myevent/updateoptions', $scope.event).success(function(data) {
        }).error(function() {
            $scope.setError('Could not update settings.');
        });
//    	  }
//    	  else {
//        		alert("Published events cannot be modified.\n Review event details.");
//        	}
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
    	$scope.createorupdate = false;
    	srvevent.myevent($routeParams).success(function(data) {
		$scope.event = data;
		$scope.showOrgName = $scope.event.userEventSettingDTO.showOrgName;
	    $scope.showOrgDesc = $scope.event.userEventSettingDTO.showOrgDesc;
	    $scope.contactDetails = $scope.event.userEventSettingDTO.contactDetails;
	    $scope.showHostWebsite = $scope.event.userEventSettingDTO.showHostWebsite;
	    $scope.showAttendeDetails = $scope.event.userEventSettingDTO.showAttendeDetails;
	    $scope.citySelect = {id:$scope.event.cityId,text:{'cityId':$scope.event.cityId,'cityName':$scope.event.cityName,'countryName':$scope.event.countryName,'stateName':$scope.event.stateName,'timeZone':$scope.event.timeZone}};
	    $scope.timezoneSelect = {id:$scope.event.cityId,text:{'cityId':$scope.event.cityId,'cityName':$scope.event.cityName,'countryName':$scope.event.countryName,'stateName':$scope.event.stateName,'timeZone':$scope.event.timeZone}};
//	    $scope.event.startDate = moment($scope.event.startDate).format("DD-MMM-YYYY hh:mm A");
//	    $scope.event.endDate = moment($scope.event.endDate).format("DD-MMM-YYYY hh:mm A");
	    /*for (var i=0;i<$scope.event.tickets.length;i++)
	 	{ 
	    	$scope.event.tickets[i].saleStart = moment($scope.event.tickets[i].saleStart).format("DD-MMM-YYYY hh:mm A");
	    	$scope.event.tickets[i].saleEnd = moment($scope.event.tickets[i].saleEnd).format("DD-MMM-YYYY hh:mm A");
		    			
	 	}*/
	    $scope.eventpageurl = $location.absUrl();
	    var array = $scope.eventpageurl.split("#");
	    $scope.eventpageurl = array[0].concat("#/event/",$scope.event.eventUrl);

	    $scope.setRequiredFields();
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
    	$scope.createorupdate = true;
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
    
    $scope.fetchEventOptions = function() {
    	$http.get('event/myevent/options/'+$scope.event.id).success(function(data) {
			$scope.eventFormOptions = data;
			alert($scope.eventFormOptions.faceBookUrl);
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

/////// start of file uploads
    
    $scope.chooseBanner = function(element) {
        $scope.$apply(function(scope) {
          console.log('files:', element.files);
          // Turn the FileList object into an Array
          $scope.files = [];
            $scope.files[0]=element.files[0];
           
          });
        };
        
    $scope.cancelBanner = function() {
    	$scope.files[0]=null;
    	$scope.apply();
    };

    $scope.uploadBanner = function() {
     	   var formData = new FormData();
     	   for (var i in $scope.files) {
     		   formData.append("banner", $scope.files[0])
            }
     	  $scope.event.upload=true;
 		   $http.post('upload/image', formData,{headers: {'Content-Type': undefined },data: formData,
 		        transformRequest: angular.identity}).success(function(data){
 		        	if(data.status == true ) {
 		        		$scope.event.bannerFile= data.filesuploaded[0].uniqueid;
 	        		} else {
 	        			alert(data.error);
 	        		}
 		        	
 		        }
 		        
 		        ).error();
 		  $scope.event.upload=false;
 		  $scope.apply();
 	};

    $scope.uploadWaitBanner = function(){
    	$scope.event.upload=true;
 	   $scope.apply();
    };
    
    $scope.removeBanner = function() {
    	$scope.event.bannerFile="";
    	$scope.files[0]=null;
    	$scope.apply();
    };

    
    $scope.choosePromotion = function(element) {
        $scope.$apply(function(scope) {
          console.log('files:', element.files);
          // Turn the FileList object into an Array
            $scope.files = [];
            $scope.files[1]=element.files[0];
          });
        };
        
    $scope.cancelPromotion = function() {
    	$scope.files[1]=null;
    	$scope.apply();
    };

    $scope.uploadPromotion = function() {
        	
     	   var formData = new FormData();
     	   for (var i in $scope.files) {
     		   formData.append("promotion", $scope.files[1])
            }
 		   $http.post('upload/promo', formData,{headers: {'Content-Type': undefined },data: formData,
 		        transformRequest: angular.identity}).success(function(data){
 		        	if(data.status == true) {
 		        		$scope.event.promotionFile= data.filesuploaded[0].uniqueid;
 	        		} else {
 	        			alert("Error in file upload "+data.error);
 	        		}
 		        	
 		        }
 		        
 		        ).error();
 		   $scope.apply();
 	};

    $scope.removePromotion = function() {
    	$scope.event.promotionFile="";
    	$scope.files[1]=null;
    	$scope.apply();
    };

/////// end of file uploads
    
    $scope.addNewEvent = function() {
    	$scope.disabled = true;
    	$scope.resetError();
        $scope.validations();
        $scope.setticketsaleendandsalestartdates();
        if($scope.stopSubmitAction === true){
        	$scope.stopSubmitAction = false;        	
         }
        else {        
        $http.post('event/myevent/addevent', $scope.event).success(function(response) {
        	if(response.type=='success')
        		{
        			$location.url('myevents');
        		}
        	else {
        		$scope.disabled = false;
        	}
        }).error(function() {
        	$scope.disabled = false;
        });
        }
    }
    
    $scope.addNewEventAndPublish = function() {
    	$scope.disabled = true;
    	$scope.resetError();
    	$scope.validations();
        var tktqty = $scope.atleastoneticketrequired();
        $scope.setticketsaleendandsalestartdates();
    	       
    	if(tktqty==0){        	
    		$scope.stopSubmitAction=true;    
    		$("#ticketId").addClass("activeAlert");
    		$scope.disabled = false;
                $.bootstrapGrowl("You cannot save and publish without tickets. You can do Save & Exit.", {
                    type: 'error',
                    align: 'center',
                    width: 'auto',
                    delay: 10000,
                    allow_dismiss: true
                });
        }
        if($scope.stopSubmitAction === true){
        	$scope.stopSubmitAction = false;
         }
        else {
        $http.post('event/myevent/addEventAndPublish', $scope.event).success(function() {
        	$location.url('myevents');
        }).error(function() {
        	$scope.disabled = false;
        });
        }
        
    }
    $scope.atleastoneticketrequired = function() {
    	var tktqty = 0;
    	if(angular.isDefined($scope.event.tickets)){ 
    	var tickets = $scope.event.tickets;
    	for (var i=0;i<tickets.length;i++) 	{    		
    		tktqty = tktqty + parseInt(tickets[i].quantity);
    	}
    	}
    	return tktqty;
    }
    $scope.setRequiredFields = function() {
    	$scope.timezoneReq = false;
    	$scope.pinReq = false;
    	$scope.pinReqmin = false;
    	$scope.venueNameReq=false;
    	$scope.venueAdd1Req=false;
    	$scope.venueForm.$invalid=false;
        if($scope.event.isWebinar){
        	if(angular.isUndefined($scope.event.timeZone) || $scope.event.timeZone==null){
        		$scope.timezoneReq = true;
        		$scope.venueForm.$invalid=true;
        	}
        } else {  
        	if(angular.isUndefined($scope.event.venueName) || $scope.event.venueName==null)
        		$scope.venueNameReq=true;
        	if(angular.isUndefined($scope.event.address1) || $scope.event.address1==null)
        		$scope.venueAdd1Req=true;
        	if(angular.isUndefined($scope.event.zipCode) || $scope.event.zipCode==null)
        		$scope.pinReq=true;
        	
        	if(angular.isDefined($scope.event.zipCode) && $scope.event.zipCode.length < 4){
        		$scope.pinReqmin = true;
        	}
        }
     	if($scope.timezoneReq || $scope.venueNameReq || $scope.venueAdd1Req || $scope.pinReq || $scope.pinReqmin){
    		$scope.venueForm.$invalid = true;
    	}
    }
    
    function convertDate(dateValue){
    	var startDateArr = dateValue.split(" ");
    	var temp = startDateArr[0].replace(/-/g,"/");
    	var evntDate = temp + " " + startDateArr[1];
    	return evntDate;
    }
    $scope.validations = function() {	   
    	$("#eventId").removeClass("activeAlert");
    	$("#venueId").removeClass("activeAlert");
    	$("#ticketId").removeClass("activeAlert");
  	    $scope.setRequiredFields();
    	$scope.nameRequired = $scope.eventForm.eName.$error.required;
/*    	alert('xyz'+JSON.stringify($scope.eventForm.description));
*/    	$scope.descRequired = $scope.eventForm.description.$error.required;
    	$scope.startDateRequired = $scope.eventForm.startDate.$error.required;
    	$scope.endDateRequired = $scope.eventForm.endDate.$error.required;
    	$scope.catRequired = $scope.eventForm.category.$error.required;
    	$scope.curRequired = $scope.eventForm.currency.$error.required;

    	$scope.citryReq = false;
    	if(angular.isUndefined($scope.event.cityName) || $scope.event.cityName==null){
    		$scope.citryReq = true;
    	}
    	if(angular.isDefined($scope.event.startDate)&&angular.isDefined($scope.event.endDate)){    	    	
      	var startDate = new Date(convertDate($scope.event.startDate));
    	var endDate = new Date(convertDate($scope.event.endDate));
    	if(startDate.getTime()>endDate.getTime()){
    		$scope.stopSubmitAction=true;
//    		$scope.startDateBeforeEndDate = true;
    	      $.bootstrapGrowl("Start date & time should not be after End date & time.", {
                  type: 'error',
                  align: 'center',
                  width: 'auto',
                  delay: 10000,
                  allow_dismiss: true
              });
    		$scope.disabled = false;
    	}
    	else {
//    		$scope.startDateBeforeEndDate = false;
    	}
    	}
      	if($scope.eventForm.$invalid || $scope.venueForm.$invalid || $scope.orgForm.$invalid || $scope.tktForm.$invalid){
    		$scope.stopSubmitAction=true;   
    		if($scope.eventForm.$invalid){
    			$("#eventId").addClass("activeAlert");
    		}
    		
    		if( $scope.venueForm.$invalid){
    			$("#venueId").addClass("activeAlert");
    		}
    		
    		if($scope.tktForm.$invalid){
    			$("#ticketId").addClass("activeAlert");
    		}
    		$scope.disabled = false;
                $.bootstrapGrowl("Please fill required fields in one of the tabs.", {
                    type: 'error',
                    align: 'center',
                    width: 'auto',
                    delay: 100000,
                    allow_dismiss: true
                });
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
    	if(angular.isUndefined(cityinfo.text)) {	
    		return;
    	}
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

  /*      if(angular.isDefined($scope.event.startDate)){
        	ticket.saleEnd= $scope.event.startDate;
        	sEnd = new Date(sEnd);
        	var millSecs = sEnd.getTime();
        	millSecs = millSecs - 3600000;
        	sEnd = new Date(millSecs);
        	ticket.saleEnd = moment(sEnd).format("DD-MMM-YYYY HH:mm");
        } */       
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
/*        if(angular.isDefined($scope.event.startDate)){
        	ticket.saleEnd = $scope.event.startDate;
	        sEnd = new Date(sEnd);
	        var millSecs = sEnd.getTime();
	        millSecs = millSecs - 3600000;
	        sEnd = new Date(millSecs);
	        ticket.saleEnd = moment(sEnd).format("DD-MMM-YYYY HH:mm");
        }*/
        $scope.event.tickets.push(ticket);
    }
    
    $scope.removeTicket = function(index) {
        $scope.resetError();
        var   rmTicket =  $scope.event.tickets[index];
        if(!angular.isUndefined(rmTicket.id)){
        	rmTicket.deleted = true;
        }else{
        $scope.event.tickets.splice(index, 1);
        }
    }
    
    $scope.setticketsaleendandsalestartdates = function(){
    	$scope.saleendaftereventstartdate=false;
    	$scope.saleendafterstartdate=false;
    	if(angular.isDefined($scope.event.tickets)) {
    	var tickets = $scope.event.tickets;
    	if(angular.isDefined($scope.event.endDate)){
    		var millSecs = (new Date(convertDate($scope.event.endDate))).getTime();
			millSecs = millSecs - 3600000;
			var eventEndDate = new Date(millSecs);
    		for (var i=0;i<tickets.length;i++) 	{
    			if(angular.isUndefined(tickets[i].saleStart)){
                  	 tickets[i].saleStart= moment().format("DD-MMM-YYYY HH:mm");
          			}
    			if(angular.isUndefined(tickets[i].saleEnd)){
            	 tickets[i].saleEnd= moment(eventEndDate).format("DD-MMM-YYYY HH:mm"); 
    			}
    		}
    	}
    }
        if(angular.isDefined($scope.event.tickets)){
        	var tickets = $scope.event.tickets;
        	var eventEndDate = new Date(convertDate($scope.event.endDate));
        	for (var i=0;i<tickets.length;i++) 	{
        	if(angular.isDefined(tickets[i].saleEnd)){              	
            	var saleEnd = new Date(convertDate(tickets[i].saleEnd));
            	if(eventEndDate.getTime()<saleEnd.getTime()){
            		$scope.saleendaftereventstartdate=true;
            	}
            }
        }
        	if($scope.saleendaftereventstartdate){
      	      $.bootstrapGrowl("Ticket Sale End On should not be after Event End date & time.", {
                    type: 'error',
                    align: 'center',
                    width: 'auto',
                    delay: 10000,
                    allow_dismiss: true
                });
      	    $("#ticketId").addClass("activeAlert");
      		$scope.disabled = false;
      		$scope.stopSubmitAction=true;
        	}
        }
      if($scope.saleendaftereventstartdate===false){  
    if(angular.isDefined($scope.event.tickets)){
    	var tickets = $scope.event.tickets;
    	for (var i=0;i<tickets.length;i++) 	{
    	if(angular.isDefined(tickets[i].saleEnd)&&angular.isDefined(tickets[i].saleStart)){
          	var saleStart = new Date(convertDate(tickets[i].saleStart));
        	var saleEnd = new Date(convertDate(tickets[i].saleEnd));
        	if(saleStart.getTime()>saleEnd.getTime()){
        		$scope.saleendafterstartdate=true;
        	}
        }
    }
    	if($scope.saleendafterstartdate){
  	      $.bootstrapGrowl("Ticket Sale Start On should not be after Ticket Sale End On.", {
                type: 'error',
                align: 'center',
                width: 'auto',
                delay: 10000,
                allow_dismiss: true
            });
  	    $("#ticketId").addClass("activeAlert");
  		$scope.disabled = false;
  		$scope.stopSubmitAction=true;
    	}
    }
    }
      if(angular.isDefined($scope.event.tickets)){
        var isvalid = true;
        var tkts = [];
    	var tickets = $scope.event.tickets;
      	for (var i=0;i<tickets.length;i++) 	{
      		if(angular.isDefined(tickets[i].minQty)&&angular.isDefined(tickets[i].maxQty)){
      			var min = tickets[i].minQty;
      			var max = tickets[i].maxQty;
      			if(min > max){
      				isvalid = false;
      				tkts.push(tickets[i].name);
      			}
      		}
      		
      	}
      }
      if(isvalid == false){
  	      $.bootstrapGrowl("Ticket max qty should not be less than min qty for tickets : " + tkts, {
              type: 'error',
              align: 'center',
              width: 'auto',
              delay: 10000,
              allow_dismiss: true
          });
	    $("#ticketId").addClass("activeAlert");
		$scope.disabled = false;
		$scope.stopSubmitAction=true;
      }
      
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
//        message().show = false;
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
    	$scope.event.cityName = region.text.cityName;
    	$scope.event.stateName =  region.text.stateName;
    	$scope.event.countryName = region.text.countryName;
        $scope.event.cityId = region.text.cityId;
        $scope.event.timeZone = 'GMT'+region.text.timeZone;
        
    };
    
    $scope.selectTimeZone = function(region) {
    	if(angular.isUndefined(region) || region == null){
    		return ;
    	}
    	$scope.event.cityName = region.text.cityName;
    	$scope.event.stateName =  region.text.stateName;
    	$scope.event.countryName = region.text.countryName;
        $scope.event.cityId = region.text.cityId;
        $scope.event.timeZone = 'GMT'+region.text.timeZone;
        
        
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

