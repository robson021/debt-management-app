(function () {
  "use strict";
  angular.module("ngApp").controller('my-debtors-ctrl', function ($scope, $http) {

    $scope.debtors = null;
    $scope.otherUsers = null;
    $scope.selectedUser = null;

    $scope.newAsset = {
      "description": '',
      "amount": '',
      "borrowerName": '',
      "borrowerSurname": '',
      "borrowerId": ''
    };

    $scope.loadDebtors = function () {
      $http.get('/payments/my-debtors/')
        .then(function (response) {
          if (response.data.length > 0) {
            $scope.debtors = response.data;
          } else {
            $scope.debtors = null;
          }
        });
    };

    $scope.cancelDebt = function (debtId) {
      $http.delete('/payments/cancel-debt/' + debtId + '/')
        .then(function () {
          $scope.loadDebtors();
        });
    };

    $scope.getOtherUsers = function () {
      $http.get('/credentials/other-users/')
        .then(function (response) {
          if (response.data.length > 0)
            $scope.otherUsers = response.data;
        });
    };

    $scope.submitNewAsset = function () {
      if ($scope.selectedUser == null) return;
      $scope.prepareNewAsset();
      $http.post('payments/add-assets-to-user/', $scope.newAsset)
        .then(function () {
          $scope.loadDebtors();
        });
    };

    $scope.prepareNewAsset = function () {
      let user = $scope.selectedUser.split(' ');
      $scope.newAsset.borrowerName = user[ 0 ];
      $scope.newAsset.borrowerSurname = user[ 1 ];
      $scope.newAsset.borrowerId = user[ 2 ];
    };

    $scope.loadDebtors();
    $scope.getOtherUsers();

  }); // end of controller
})();