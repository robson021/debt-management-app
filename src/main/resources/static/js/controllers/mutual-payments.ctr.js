(function () {
  "use strict";
  angular.module("ngApp").controller('mutual-payments-ctrl', function ($scope, $http) {

    $scope.payments = null;

    $scope.newPayment = {
      "amount": '',
      "description": ''
    };

    $scope.submitNewPayment = function () {
      if ($scope.newPayment.amount > 0 && $scope.newPayment.description.length > 0) {
        $http.post('/payments/add-mutual-payment/', $scope.newPayment)
          .then(function (response) {
            $scope.loadMutualPayments();
          });
      }
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

    $scope.loadMutualPayments();

  }); // end of controller
})();