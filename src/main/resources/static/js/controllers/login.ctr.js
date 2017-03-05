(function () {
  "use strict";
  angular.module("ngApp").controller('login-ctrl', function ($scope, $http) {

    $scope.login = function () {
      $scope.error = null;
      let promise = $http.post(); // todo
      promise.then(function (token) {
          $scope.token = token;
          $http.defaults.headers.common.Authorization = 'Bearer ' + token;
        },
        function (error) {
          $scope.error = error;
          $scope.userName = '';
        });
    }

  }); // end of controller
})();