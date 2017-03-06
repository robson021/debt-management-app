(function () {
  "use strict";
  angular.module("ngApp").controller('my-debtors-ctrl', function ($scope, $http) {

    $scope.debtors = null;

    $scope.loadDebtors = function () {
      $http.get('/payments/my-debtors/')
        .then(function (response) {
          if (response.data.length > 0)
            $scope.debtors = response.data;
        });
    };

    $scope.loadDebtors();

  }); // end of controller
})();