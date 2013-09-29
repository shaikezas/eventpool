'use strict';

/* Directives */

var AppDirectives = angular.module('EventPool.directives', ['infinite-scroll']);

AppDirectives.directive('appVersion', ['version', function (version) {
    return function (scope, elm, attrs) {
        elm.text(version);
    };
}]);

AppDirectives.directive('fileuploadPlugin', function($timeout) {
	var linkFn;
	linkFn = function(scope, element, attrs) {
		angular.element(element).ready(function() {
			jQuery('.fileupload').fileupload({
		        dataType: 'json',
		        limitMultiFileUploads: 1,
		        done: function (e, data) {
		        	scope.$apply(function(scope) {
		        		if(data.result.status == true) {
		        			scope.response = data.result.filesuploaded[0].uniqueid;
		        		} else {
		        			alert("Error in file upload "+data.result.error);
		        		}
		        	});
		        }
		    });
    	});
	};
	return {
		restrict	: 'A',
		link		: linkFn,
		scope: {
    		response: '=ngModel',
    	},
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

AppDirectives.directive('datetimePicker', function($timeout) {
    return {
        require: 'ngModel',
        link: function(scope, element, attrs, model) {
            $(".datetimepicker").datetimepicker({
            	autoclose: true,
                format: 'dd-M-yyyy HH:ii P',
                forceParse: false,
                todayBtn: true,
                todayHighlight : true,
                pickerPosition: "bottom-left",
                startDate : new Date(),
                weekStart : 1
            }).on('changeDate', function(ev){
            	scope.$apply(function() {
            		$timeout(function() {
            			model.$setViewValue(element.val());
            		}, 10);
            	});
            });
            //model.$setViewValue(model);
        }
    };
});

AppDirectives.directive('ckEditor',
        [ function() {
            return {
                require : '?ngModel',
                link : function($scope, elm, attr, ngModel) {

                    var ck = CKEDITOR.replace(elm[0]);

                    ck.on('instanceReady', function() {
                        ck.setData(ngModel.$viewValue);
                    });

                    ck.on('pasteState', function() {
                        $scope.$apply(function() {
                            ngModel.$setViewValue(ck.getData());
                        });
                    });

                    ngModel.$render = function(value) {
                        ck.setData(ngModel.$modelValue);
                    };
                }
            };
        } ]);
AppDirectives.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
});