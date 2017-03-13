const JWT = 'jwt';
(function () {
  "use strict";
  angular.module("ngApp").controller('login-ctrl', function ($rootScope, $scope, $http, $state) {

    $scope.email = '';
    $scope.password = '';

    $scope.login = function () {
      let data = {
        "email": $scope.email,
        "password": $scope.password
      };

      $http.post('auth/login/', data)
        .then(function (response) {
            $scope.email = $scope.password = '';
            let token = response.data.message;
            $scope.saveJWT(token);
            $state.go('my-debts');
            $scope.checkForPrivileges();
          },
          function (error) {
            console.info(error);
          });
    };

    $scope.saveJWT = function (token) {
      $http.defaults.headers.common.Authorization = 'Bearer ' + token;
      $rootScope.loggedIn = true;
      localStorage.setItem(JWT, token);
      console.info('saved token: ' + token);
    };

    $scope.clearToken = function () {
      $http.defaults.headers.common.Authorization = undefined;
    };

    $scope.checkForPrivileges = function () {
      $http.get('credentials/is-admin/')
        .then(function (response) {
          $rootScope.isAdmin = response.data;
          console.info('Admin privileges: ' + $rootScope.isAdmin);
        });
    };

    $scope.checkOldToken = function () {
      let token = localStorage.getItem(JWT);
      if (token == undefined) return;

      console.info('auto-login try');
      $scope.saveJWT(token);
      $http.get('auth/am-i-logged-in/')
        .then(function (response) {
          $state.go('my-debts');
          console.info(response);
        }, function (error) {
          $scope.clearToken();
          console.info('JWT clear');
        });
    };

    $scope.checkOldToken();

  }); // end of controller
})();