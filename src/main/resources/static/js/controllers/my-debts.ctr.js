(function () {
  "use strict";
  angular.module("ngApp").controller('my-debts-ctrl', function ($scope, $http) {

    $scope.debts = null;

    $scope.loadDebts = function () {
      $http.get('/payments/my-debts/')
        .then(function (response) {
          if (response.data.length > 0)
            $scope.debts = response.data;
        });
    };

    $scope.loadDebts();

  }); // end of controller
})();