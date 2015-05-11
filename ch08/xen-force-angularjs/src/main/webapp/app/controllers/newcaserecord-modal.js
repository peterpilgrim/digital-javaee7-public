var newcaserecord = angular.module('newcaserecord', ['ui.bootstrap.modal', 'sharedService','isoCountries'])

newcaserecord.controller('NewCaseRecordModalController', function($rootScope, $scope, $modal, $http, $log, sharedService, isoCountries ) {
    $log.info("CaseRecord Modal Dialog. Yes!  $modal="+$modal+", $log="+$log);

    $scope.selected = false;
    $scope.caseRecord = {
        sex: "F", firstName: "", lastName: "", country: "", passportNo: "", dateOfBirth: "",
        expirationDate: "", country: "", currentState: "", showTasks: false};
    $scope.returnedData = null;
    $scope.isoCountries = isoCountries;
    $rootScope.isoCountries = isoCountries;

    $scope.openCreateCaseRecordDialog = function () {
        var modalInstance = $modal.open({
            templateUrl: 'newCaseRecordContent.html',
            controller: newCaseRecordModalInstanceController,
            isoCountries: isoCountries,
            resolve: {
                caseRecord: function () {
                    return $scope.caseRecord;
                }
            }
        });

        modalInstance.result.then(function (data) {
            $scope.selected = data;
            $http.post('rest/caseworker/item', $scope.caseRecord).success(function(data) {
                $log.info("data="+data);
                $scope.returnedData = data;
                sharedService.setBroadcastMessage("newCaseRecord");
            });

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.openEditCaseRecordDialog = function (caseRecordItem) {
        // Deep copy of the caseRecord!
        $scope.caseRecord = {
            id: caseRecordItem.id,
            sex: caseRecordItem.sex,
            firstName: caseRecordItem.firstName,
            lastName: caseRecordItem.lastName,
            dateOfBirth: caseRecordItem.dateOfBirth,
            country: caseRecordItem.country,
            passportNo: caseRecordItem.passportNo,
            expirationDate: caseRecordItem.expirationDate,
            currentState: caseRecordItem.currentState,
            showTasks: caseRecordItem.showTasks
        };

        var modalInstance = $modal.open({
            templateUrl: 'editCaseRecordContent.html',
            controller: editCaseRecordModalInstanceController,
            resolve: {
                caseRecord: function () {
                    return $scope.caseRecord;
                }
            }
        });

        modalInstance.result.then(function (data) {
            $scope.selected = data;
            $http.put('rest/caseworker/item/'+$scope.caseRecord.id, $scope.caseRecord).success(function(data) {
                $log.info("data="+data);
                $scope.returnedData = data;
                sharedService.setBroadcastMessage("editCaseRecord");
            });

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };


    $scope.changeStateCaseRecordDialog = function (caseRecordItem) {
        // Deep copy of the caseRecord!
        $scope.caseRecord = {
            id: caseRecordItem.id,
            firstName: caseRecordItem.firstName,
            lastName: caseRecordItem.lastName,
            dateOfBirth: caseRecordItem.dateOfBirth,
            country: caseRecordItem.country,
            passportNo: caseRecordItem.passportNo,
            expirationDate: caseRecordItem.expirationDate,
            currentState: caseRecordItem.currentState,
            nextStates: caseRecordItem.nextStates,
            showTask: caseRecordItem.showTasks
        };

        // NOTE: Append the current state to the list of next possible states in order to avoid an empty select option
        // See Stack overflow http://stackoverflow.com/questions/12654631/why-does-angularjs-include-an-empty-option-in-select
        $scope.caseRecord.nextStates.push( caseRecordItem.currentState );
        // Save the current state
        $scope.saveCurrentState = caseRecordItem.currentState;

        var modalInstance = $modal.open({
            templateUrl: 'changeStateCaseRecordContent.html',
            controller: moveStateRecordModalInstanceController,
            resolve: {
                caseRecord: function () {
                    return $scope.caseRecord;
                }
            }
        });

        modalInstance.result.then(function (data) {
            $scope.selected = data;
            if ( $scope.saveCurrentState !== $scope.caseRecord.currentState ) {
                $http.put('rest/caseworker/state/'+$scope.caseRecord.id, $scope.caseRecord).success(function(data) {
                    $log.info("data="+data);
                    $scope.returnedData = data;
                    sharedService.setBroadcastMessage("editCaseRecord");
                });
            }
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.showOrHideTasks = function(caseRecord) {
        caseRecord.showTasks = !caseRecord.showTasks;
        $http.put('rest/caseworker/showtasks/'+caseRecord.id, caseRecord).success(function(data) {
            $log.info("data="+data);
            sharedService.setBroadcastMessage("showTasksCaseRecord");
        });
    }

    $scope.getIconClass = function(caseRecord) {
        if ( caseRecord.showTasks)
            return "glyphicon-minus"
        else
            return "glyphicon-plus"
    }
});

var newCaseRecordModalInstanceController = function ($scope, $modalInstance, caseRecord, isoCountries) {
    caseRecord.showTasks = true; // Convenience for the user
    $scope.caseRecord = caseRecord;
    $scope.isoCountries = isoCountries;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};


var editCaseRecordModalInstanceController = function ($scope, $modalInstance, caseRecord, isoCountries ) {
    $scope.caseRecord = caseRecord;
    $scope.isoCountries = isoCountries;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};


var moveStateRecordModalInstanceController = function ($scope, $modalInstance, caseRecord) {
    $scope.caseRecord = caseRecord;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};
