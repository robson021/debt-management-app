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
            let token = response.data.message;
            $scope.email = $scope.password = '';
            $http.defaults.headers.common.Authorization = 'Bearer ' + token;
            $rootScope.loggedIn = true;
            $state.go('my-debts');
            $scope.checkForPrivileges();
            console.info('token: ' + token);
          },
          function (error) {
            console.info(error);
          });
    };

    $scope.checkForPrivileges = function () {
      $http.get('credentials/is-admin/').then(function (response) {
        $rootScope.isAdmin = response.data;
        console.info('Admin privileges: ' + $rootScope.isAdmin);
      });
    };

  }); // end of controller
})();