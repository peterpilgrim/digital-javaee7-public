var newcaserecord = angular.module('newcaserecord', ['ui.bootstrap.modal', 'sharedService'])

newcaserecord.controller('NewCaseRecordModalController', function($scope, $modal, $http, $log, sharedService ) {
    $log.info("CaseRecord Modal Dialog. Yes!  $modal="+$modal+", $log="+$log);

    $scope.selected = false;
    $scope.caseRecord = {
        sex: "", firstName: "", lastName: "", country: "", passportNo: "", dateOfBirth: "",
        expirationDate: "", country: "", currentState: "", showTasks: false};
    $scope.returnedData = null;

    $scope.open = function () {

        var modalInstance = $modal.open({
            templateUrl: 'newCaseRecordContent.html',
            controller: newCaseRecordModalInstanceController,
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
            currentState: caseRecordItem.currentState
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
                data.showTasks = false;
                sharedService.setBroadcastMessage("editCaseRecord");
            });

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.showOrHideTasks = function(caseRecord) {
        caseRecord.showTasks = !caseRecord.showTasks;
    }

    $scope.getIconClass = function(caseRecord) {
        if ( caseRecord.showTasks)
            return "glyphicon-minus"
        else
            return "glyphicon-plus"
    }
});

var newCaseRecordModalInstanceController = function ($scope, $modalInstance, caseRecord) {
    caseRecord.showTasks = false;
    $scope.caseRecord = caseRecord;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};


var editCaseRecordModalInstanceController = function ($scope, $modalInstance, caseRecord) {
    $scope.caseRecord = caseRecord;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};

