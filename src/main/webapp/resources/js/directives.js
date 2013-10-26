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
                format: 'dd-M-yyyy hh:ii ',
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


AppDirectives.directive('timer', ['$compile', function ($compile) {
    return  {
      restrict: 'E',
      replace: false,
      scope: {
        interval: '=interval',
        startTimeAttr: '=startTime',
        countdownattr: '=countdown',
        autoStart: '&autoStart'
      },
      controller: ['$scope', '$element', '$attrs', function ($scope, $element, $attrs) {
        //angular 1.2 doesn't support attributes ending in "-start", so we're
        //supporting both "autostart" and "auto-start" as a solution for
        //backward and forward compatibility.
        $scope.autoStart = $attrs.autoStart || $attrs.autostart;

        if ($element.html().trim().length === 0) {
          $element.append($compile('<span>{{millis}}</span>')($scope));
        }

        $scope.startTime = null;
        $scope.timeoutId = null;
        $scope.countdown = $scope.countdownattr && parseInt($scope.countdownattr, 10) >= 0 ? parseInt($scope.countdownattr, 10) : undefined;
        $scope.isRunning = false;

        $scope.$on('timer-start', function () {
          $scope.start();
        });

        $scope.$on('timer-resume', function () {
          $scope.resume();
        });

        $scope.$on('timer-stop', function () {
          $scope.stop();
        });

        function resetTimeout() {
          if ($scope.timeoutId) {
            clearTimeout($scope.timeoutId);
          }
        }

        $scope.start = $element[0].start = function () {
          $scope.startTime = $scope.startTimeAttr ? new Date($scope.startTimeAttr) : new Date();
          $scope.countdown = $scope.countdownattr && parseInt($scope.countdownattr, 10) > 0 ? parseInt($scope.countdownattr, 10) : undefined;
          resetTimeout();
          tick();
        };

        $scope.resume = $element[0].resume = function () {
          resetTimeout();
          if ($scope.countdownattr) {
            $scope.countdown += 1;
          }
          $scope.startTime = new Date() - ($scope.stoppedTime - $scope.startTime);
          tick();
        };

        $scope.stop = $scope.pause = $element[0].stop = $element[0].pause = function () {
          $scope.stoppedTime = new Date();
          resetTimeout();
          $scope.$emit('timer-stopped', {millis: $scope.millis, seconds: $scope.seconds, minutes: $scope.minutes, hours: $scope.hours, days: $scope.days});
          $scope.timeoutId = null;
        };

        $element.bind('$destroy', function () {
          resetTimeout();
        });

        function calculateTimeUnits() {
          $scope.seconds = Math.floor(($scope.millis / 1000) % 60);
          $scope.minutes = Math.floor((($scope.millis / (60000)) % 60));
          $scope.hours = Math.floor((($scope.millis / (3600000)) % 24));
          $scope.days = Math.floor((($scope.millis / (3600000)) / 24));
        }

        //determine initial values of time units
        if ($scope.countdownattr) {
          $scope.millis = $scope.countdownattr * 1000;
        } else {
          $scope.millis = 0;
        }
        calculateTimeUnits();

        var tick = function () {

          $scope.millis = new Date() - $scope.startTime;
          var adjustment = $scope.millis % 1000;

          if ($scope.countdownattr) {
            $scope.millis = $scope.countdown * 1000;
          }

          calculateTimeUnits();
          if ($scope.countdown > 0) {
            $scope.countdown--;
          }
          else if ($scope.countdown <= 0) {
            $scope.stop();
            return;
          }

          //We are not using $timeout for a reason. Please read here - https://github.com/siddii/angular-timer/pull/5
          $scope.timeoutId = setTimeout(function () {
            tick();
            $scope.$apply();
          }, $scope.interval - adjustment);

          $scope.$emit('timer-tick', {timeoutId: $scope.timeoutId, millis: $scope.millis});
        };

        if ($scope.autoStart === undefined || $scope.autoStart === true) {
          $scope.start();
        }
      }]
    };
  }]);