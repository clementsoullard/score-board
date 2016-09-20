'use strict';

angular.module('managerApp.manager', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/manager', {
    templateUrl: 'manager/manager_team.html',
    controller: 'teamCtrl'
  })
  .when('/challenge', {
	    templateUrl: 'manager/manager_challenge.html',
	    controller: 'challengeCtrl'
	  })
  ;
}])

.controller('teamCtrl',  ['$scope','$http', function($scope,$http) {
/**
 * Insert a new entry fonction
 */
 $scope.update = function (team) {
    $http.post('./rest/team',team).
        success(function(data) {
     	  	$scope.message='Thanks for submitting the idea.';
       	  	$scope.error=false;
            list();
        }).
		error(function(data) {
     	  	$scope.message='An issue occured';
       	  	$scope.error=true;
		})
		};
			
/**
 * List the entries
 */		
	 function list(){
		 $http.get('./rest/team').
	      success(function(data) {
	        	console.log(JSON.stringify(data._embedded));
	            $scope.teams = data._embedded.team;
	        });
		 }
	/**
	 * List the entries
	 */		
	$scope.remove = function(id){ $http.delete('./rest/team/'+id).
			success(function(data) {
		  	$scope.message='The entry has been removed.';
			list();
		});
	}
		
		list();		 
}])

.controller('challengeCtrl',  ['$scope','$http', function($scope,$http) {

	 
	/**
	 * Insert a new entry fonction
	 */
			
	 $scope.update = function (challenge) {
	    $http.post('./rest/challenge',challenge).
	        success(function(data) {
	     	  	$scope.message='Thanks for submitting challenge.';
	       	  	$scope.error=false;
	            list();
	        }).
			error(function(data) {
	     	  	$scope.message='An issue occured';
	       	  	$scope.error=true;
			})
			};
				
			/**
			 * List the entries
			 */		
				 function list(){
					 $http.get('./rest/challenge').
				      success(function(data) {
				        	console.log(JSON.stringify(data._embedded));
				            $scope.challenges = data._embedded.challenge;
				        });
					 }
		/**
		* List the entries
		*/		
				$scope.resetScore= function (){
					 $http.get('reset-score').
					      success(function(data) {
				        	console.log("Reset des scores");
					});
				}
		/**
		 * List the entries
		 */		
		$scope.remove = function(id){ $http.delete('./rest/challenge/'+id).
				success(function(data) {
			  	$scope.message='The entry has been removed.';
				list();
			});
		}
			list();		 
	}]);
