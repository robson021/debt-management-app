(function () {
  "use strict";
  angular.module("ngApp").controller('mutual-payments-ctrl', function ($scope, $http) {

    $scope.payments = null;
    $scope.newFee = {
      "amount": 100
    };

    $scope.newPayment = {
      "amount": '',
      "description": ''
    };

    $scope.submitNewPayment = function () {
      if ($scope.newPayment.amount > 0 && $scope.newPayment.description.length > 0) {
        $http.post('payments/add-mutual-payment/', $scope.newPayment)
          .then(function (response) {
            $scope.loadMutualPayments();
          });
      }
    };

    $scope.submitNewFee = function (paymentId) {
      $http.post('payments/add-fee/' + paymentId + '/' + $scope.newFee.amount + '/').then(function (r) {
        $scope.loadMutualPayments();
      });
    };

    $scope.loadMutualPayments = function () {
      $http.get('payments/mutual-payments/')
        .then(function (response) {
          if (response.data.length > 0) {
            $scope.payments = response.data;
          } else {
            $scope.payments = null;
          }
        });
    };

    $scope.cancelMyAllFees = function (paymentId) {
      $http.delete('payments/delete-my-fees/' + paymentId + '/').then(function (r) {
        $scope.loadMutualPayments();
      });
    };

    $scope.loadMutualPayments();

  }); // end of controller
})();