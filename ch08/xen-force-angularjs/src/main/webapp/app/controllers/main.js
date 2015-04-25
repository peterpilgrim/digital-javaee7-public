var myApp = angular.module('app', ['ui.bootstrap', 'newcaserecord','newtask', 'sharedService', 'isoCountries']);

myApp.factory('UpdateTaskStatusFactory', function( $log ) {
    var service = {};

    service.connect = function() {
        if (service.ws) { return; }

        var ws = new WebSocket("ws://localhost:8080/xen-force-angularjs-1.0-SNAPSHOT/update-task-status");

        ws.onopen = function() {
            console.log("WebSocket connect was opened");
        };

        ws.onclose = function() {
            console.log("WebSocket connection was closed");
        }

        ws.onerror = function() {
            console.log("WebSocket connection failure");
        }

        ws.onmessage = function(message) {
            console.log("message received ["+message+"]");
        };

        service.ws = ws;
    }

    service.send = function(message) {
        service.ws.send(message);
        service.ws.send(message);
    }

    service.subscribe = function(callback) {
        service.callback = callback;
    }

    return service;
});



myApp.controller('CaseRecordController',  function ($scope, $http, $log, UpdateTaskStatusFactory, sharedService, isoCountries ) {
    var self = this;
    $scope.caseRecords = [
        {sex: "F", firstName: "Angela", lastName: "Devonshire", dateOfBirth: "1982-04-15", expirationDate: "2018-11-21", country: "Australia", passportNo: "123456789012", currentState: "Start"},
    ];

    $scope.isoCountries = isoCountries;

    $scope.getCaseRecords = function () {
        $http.get('rest/caseworker/list').success(function(data) {
            console.log("data="+data);
            $scope.caseRecords = data;
        });
    }

    $scope.$on('handleBroadcastMessage', function() {
        var message = sharedService.getBroadcastMessage();
        if ( message !== "showTasksCaseRecord")  {
            $scope.getCaseRecords();
        }
    })

    // Retrieve the initial list of case records
    $scope.getCaseRecords();

//    UpdateTaskStatusFactory.subscribe(function(message) {
//        $scope.messages.push(message);
//        $scope.$apply();
//    });

    $scope.connect = function() {
        UpdateTaskStatusFactory.connect();
    }

    $scope.send = function( msg ) {
        UpdateTaskStatusFactory.send(msg);
    }

    $scope.updateProjectTaskCompleted = function( task ) {
        var message = { 'caseRecordId': task.caseRecordId, 'taskId': task.id, 'completed': task.completed }
        $scope.connect()
        var jsonMessage = JSON.stringify(message)
        console.log("jsonMessage = "+jsonMessage)
        $scope.send(jsonMessage)
    }
});


myApp.directive('iconExpandCollapseStatus', function() {
    return function(scope, elem, attrs) {
        scope.$watch(attrs.statusClass, function(value) {
            if(value == true) {
                elem.addClass('glyphicon-minus');
                elem.removeClass('glyphicon-plus');
            }
            else {
                elem.addClass('glyphicon-plus');
                elem.removeClass('glyphicon-minus');
            }
        }, true);
    };
});
