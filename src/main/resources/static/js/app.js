var app = angular
  .module("ngApp", [ 'ui.router', 'ngDialog' ])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('default', {
        url: '/',
        templateUrl: 'partials/login-page.html',
        controller: 'login-ctrl'
      });

    $urlRouterProvider.otherwise("/");

  }); //end of config

// global data
angular.module('ngApp')
  .run(function ($rootScope) {
    $rootScope.loggedIn = false;
    $rootScope.isAdmin = false;
  });
