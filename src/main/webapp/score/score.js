'use strict';

angular.module('myApp.score', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/score', {
    templateUrl: 'score/score.html',
    controller: 'scoreCtrl'
  });
}])

.controller('scoreCtrl',  ['$scope','$http', function($scope,$http) {
			
/**
 * List the entries
 */		
	 function listTeam(){
		 $http.get('rest/team').
	      success(function(data) {
	        //	console.log(JSON.stringify(data._embedded));
	            $scope.teams = data._embedded.team;
	        });
	 };
	 function listChallenge(){
		 $http.get('ws-match-sheet').
	      success(function(data) {
	        //	console.log(JSON.stringify(data));
	            $scope.challengesScore = data;
	        });
	 };
		 
		
		listTeam();		 
		listChallenge();		 
}]);
