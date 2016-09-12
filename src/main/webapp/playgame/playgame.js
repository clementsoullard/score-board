'use strict';

angular.module('myApp.playgame', ['ngRoute'])

.config(['$routeProvider' ,function($routeProvider) {
  $routeProvider.when('/playgame/challenge/:challengeId', {
    templateUrl: 'playgame/playgame.html',
    controller: 'PlayGameCtrl'
  });
}])

.controller('PlayGameCtrl', ['$scope','$http','$routeParams', function($scope,$http,$routeParams) {

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
		 console.log("Challenge Id = "+$routeParams.challengeId);
		 listTeam();		 

 
}]);