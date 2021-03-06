'use strict';

// Declare app level module which depends on views, and components
angular.module('managerApp', [
  'ngRoute',
  'managerApp.manager',
  'ng-fusioncharts',
  'colorpicker.module'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');

  $routeProvider.otherwise({redirectTo: '/manager'});
}]);
