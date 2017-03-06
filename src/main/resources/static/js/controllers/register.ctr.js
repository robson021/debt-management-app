(function () {
  "use strict";
  angular.module("ngApp").controller('register-ctrl', function ($scope, $http, $state) {

    $scope.name = '';
    $scope.surname = '';
    $scope.email = '';
    $scope.password = '';
    $scope.repassword = '';

    $scope.register = function () {
      if ($scope.password !== $scope.repassword) {
        // TODO: toast
        return;
      }
      let json = {
        "name": $scope.name,
        "surname": $scope.surname,
        "email": $scope.email,
        "password": $scope.password
      };
      $http.post('auth/register', json)
        .then(function (response) {
          $scope.name = $scope.surname = $scope.password = $scope.email = $scope.repassword = '';
          $state.go('default');
        }, function (error) {
          // TODO: add toast
          console.info('error');
        });
    }

  }); // end of controller
})();