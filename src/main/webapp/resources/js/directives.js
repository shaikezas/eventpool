'use strict';

/* Directives */

var AppDirectives = angular.module('EventPool.directives', []);

AppDirectives.directive('appVersion', ['version', function (version) {
    return function (scope, elm, attrs) {
        elm.text(version);
    };
}]);


AppDirectives.directive('multiplefileuploadPlugin', function($timeout) {
	var linkFn;
	linkFn = function(scope, element, attrs) {
		var addfiles = function(files) {
			scope.newfiles = [];
			scope.newfiles.push("Uploading images");
			/*for (var i = 0; i < files.length; i++) {
		    	var loadingImage = window.loadImage(
		    			files[i],
		    	        function (img) {
		    	            
		    	        },
		    	        {maxWidth: 600}
		    	    );
		    	if (!loadingImage) {
		    		loadingImage.src = "http://placehold.it/210x150";
		    	}
		    	scope.newfiles.push(new netvogue.photo(files[i].name, "UNTITLED", loadingImage.src));
		    }*/
		};
		scope.isEditMode = false;
		scope.$on('filesadded', function(e, files) {
			addfiles(files);
			jQuery.blockUI({ css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .5, 
	            color: '#fff' 
	        } }); 
		});
		var galleryid = {
				"galleryid" : scope.galleryid
		};
		scope.errorCallback = function(event, src, index) {
			var imgElement = event.srcElement;
			angular.element(imgElement).attr('src', 'img/ajax-loader1.gif');
			$timeout(function() {
				angular.element(imgElement).attr('src', scope.existingfiles[index].add_url);
			}, 1000);
		};
		angular.element(element).ready(function() {
			jQuery('#fileupload').fileupload({
		        dataType: 'json',
		        singleFileUploads: false,
		        formData: galleryid,
		        progressall: function (e, data) {
		        	var progtemp = parseInt(data.loaded / data.total * 100, 10);
		        	scope.$apply(function(scope) {
		        		scope.progress = progtemp;
		        	});
		        },
		        drop: function(e, data) {
		        	scope.$apply(function(scope) {
		        		addfiles(data.files);
		        	});
		        },
		        done: function (e, data) {
		        	scope.$apply(function(scope) {		        		
		        		if(data.result.status == true) {
		        			if(true == scope.filesadded) {
		        				//Worst logic ever -- revisit it once again -- Azeez
		        				var replyFromServer = [];
			        			for(var i=0; i < data.result.filesuploaded.length; i++){
			        				replyFromServer.push(data.result.filesuploaded[i]);
			        			};
			        			for(var i=0; i < scope.existingfiles.length; i++){
			        				replyFromServer.push(scope.existingfiles[i]);
			        			};
			        			scope.existingfiles = angular.copy(replyFromServer);
			        			scope.newfiles = [];
			        			scope.filesadded = false;
			        			//scope.$emit('filesuploaded');
		        			}
		        			jQuery.unblockUI(); 
		        		} else {
		        			alert("error");
		        		}
		        	});
		        }
		    });
    	});
		scope.orderchangedNew = function() {
			console.log("order changed");
		};
		scope.setprofilepicToParent = function(uniqueid) {
			scope.setprofilepic({uniqueid:uniqueid});
		};
		scope.updatedataToParent = function(label, seasonname, photoid) {
			scope.updatedata({label:label, seasonname:seasonname, photoid:photoid});
		};
		scope.deletedataToParent = function(photoid) {
			scope.deletedata({photoid:photoid});
		};
		scope.progressVisible = true;
		scope.progress = 0;
	};
	return {
		templateUrl	: 'templates/fileupload_plugin.htm',
		restrict	: 'A',
		scope		: {
			newfiles		: '=newFiles',
			existingfiles	: '=ngModel',
			maxheight		: '=maxHeight',
			minheight		: '=minHeight',
			galleryid		: '=galleryId',
			setprofilepic	: '&setProfilepic',
			updatedata		: '&updateData',
			deletedata		: '&deleteData',
			filesadded		: '=filesAdded'
		},
		link		: linkFn
	};
});

AppDirectives.directive('fileuploadPlugin', function($timeout) {
	var linkFn;
	linkFn = function(scope, element, attrs, ngModel) {
		scope.errorCallback = function(event, src) {
			var imgElement = event.srcElement;
			angular.element(imgElement).attr('src', '/img/loading.gif');
			$timeout(function() {
				angular.element(imgElement).attr('src', scope.profilepic.thumbnail_url);
			}, 1000);
		};
		scope.loadCallback = function() {
			scope.$emit('profilepicchanged', scope.profilepic);
		};
		angular.element(element).ready(function() {
			jQuery('#fileupload').fileupload({
		        dataType: 'json',
		        limitMultiFileUploads: 1,
		        done: function (e, data) {
		        	scope.$apply(function(scope) {
		        		if(data.result.status == true) {
		        			scope.profilepic = data.result.filesuploaded[0];
		        		} else {
		        			alert("error");
		        		}
		        	});
		        }
		    });
    	});
	};
	return {
		restrict	: 'A',
		link		: linkFn
	};		
});

AppDirectives.directive("focused", function($timeout) {
    return function(scope, element, attrs) {
        element[0].focus();
        element.bind('focus', function() {
            scope.$apply(attrs.focused + '=true');
        });
        element.bind('blur', function() {
            $timeout(function() {
                scope.$eval(attrs.focused + '=false');
            }, 200);
        });
        scope.$eval(attrs.focused + '=true')
    }
});