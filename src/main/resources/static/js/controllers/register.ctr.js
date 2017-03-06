(function () {
  "use strict";
  angular.module("ngApp").controller('register-ctrl', function ($scope, $http) {

    $scope.name = '';
    $scope.surname = '';
    $scope.email = '';
    $scope.password = '';
    $scope.repassword = '';

    $scope.register = function () {
      if ($scope.password !== $scope.repassword) {
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
          console.info(response);
        }, function (error) {
          // TODO: add toast
          console.info('error');
        });
    }

  }); // end of controller
})();