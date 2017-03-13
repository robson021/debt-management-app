angular.module("ngApp", [ 'ui.router' ])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('default', {
        url: '/',
        templateUrl: 'partials/login-page.html',
        controller: 'login-ctrl'
      })
      .state('register', {
        url: '/register',
        templateUrl: 'partials/register-page.html',
        controller: 'register-ctrl'
      })
      .state('my-debts', {
        url: '/my-debts',
        templateUrl: 'partials/my-debts.html',
        controller: 'my-debts-ctrl'
      })
      .state('my-debtors', {
        url: '/my-debtors',
        templateUrl: 'partials/my-debtors.html',
        controller: 'my-debtors-ctrl'
      })
      .state('mutual-payments', {
        url: '/mutual-payments',
        templateUrl: 'partials/mutual-payment.html',
        controller: 'mutual-payments-ctrl'
      });

    $urlRouterProvider.otherwise("/");

  }); //end of config

// global data init
angular.module('ngApp')
  .run(function ($rootScope) {
    $rootScope.loggedIn = false;
    $rootScope.isAdmin = false;
  });
