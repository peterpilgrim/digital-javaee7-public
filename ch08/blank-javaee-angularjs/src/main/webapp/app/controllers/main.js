var myApp = angular.module('app', ['ui.bootstrap']);

myApp.controller('AcmeController',  function ($scope, $http, $log  ) {
    $scope.employee = {
        title: "Mr",
        firstName: "Anthony Norris",
        lastName: "Other",
        employeeId: "123456789",
        comment: "Great worker!"
    }
} );


