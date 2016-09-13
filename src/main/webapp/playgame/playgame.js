'use strict';

angular.module('myApp.playgame', ['ngRoute'])

.config(['$routeProvider' ,function($routeProvider) {
  $routeProvider.when('/playgame/challenge/:challengeId', {
    templateUrl: 'playgame/playgame.html',
    controller: 'PlayGameCtrl'
  });
}])

.controller('PlayGameCtrl', ['$scope','$http','$routeParams', function($scope,$http,$routeParams) {

	var teamsInLice=[];
	$scope.teamsInLice=teamsInLice;
	var matchSelected;
	/**
	 * List the teams
	 */		
		 function listTeam(){
			 $http.get('rest/team').
		      success(function(data) {
		        //	console.log(JSON.stringify(data._embedded));
		            $scope.teams = data._embedded.team;
		        });
		 };
			/**
			 * Get the information about the current challenge
			 */		
		 function getChallenge(challengeId){
			 $http.get('rest/challenge/'+challengeId).
		      success(function(data) {
		       $scope.challenge = data;
		      //console.log(JSON.stringify(data));
		      });
		 };
		 
		 /**
		  * Get the information about the challenge
		  */
		 function createMatch(challengeId){
			 var match={'challengeId': challengeId};
			 console.log("Creating a match ");
			matchSelected=match;			 
			 $http.post('rest/match',match).
		      success(function(data) {
		      console.log(JSON.stringify(data));
		      });
		 };
		 
		 /**
		  * Get the information about the challenge
		  */
		 function createMatch(challengeId){
			 var match={'challengeId': challengeId,'close': false};
			  console.log("Creating a match ");
			 $http.post('rest/match',match).
		      success(function(data) {
		      match=data;
		      console.log(JSON.stringify(data));
		      teamsInLice=[];
		      $scope.teamsInLice=teamsInLice;
		      matchSelected=match;
		      });
		 };

		 /**
		  * Get the information about the challenge
		  */
		 function saveMatch(match){
			  console.log("Saving a match ");
			 $http.post('rest/match',match).
		      success(function(data) {
		     console.log(JSON.stringify(data));
		      });
		 };
		 /**
		  * Get the information about the challenge
		  */
		 $scope.saveScore = function (){
			console.log("Saving the current score");
			saveMatch(matchSelected);
		 };

		 /**
		  * Get the information about the challenge
		  */
		 $scope.closeMatch = function (){
			console.log("Closing the match");
			matchSelected.close=true;
			saveMatch(matchSelected);
			getMatch(challengeId);
		 };

		 /**
		  * Get the information about the challenge
		  */
		 function getMatch(challengeId){
			 // console.log("Finding matches1 for challenge "+challengeId);
			$http.get('open-match?id='+challengeId).
		      success(function(data) {
		    // console.log("Finding matches for challenge "+challengeId);
		      var match=data;
		      console.log("Matches found "+ match);
			  $scope.matches = match;

			  if(match==""){
		    		  createMatch(challengeId);
			  }else{
				  matchSelected=match;
				  teamsInLice=matchSelected.scores;
				  if(teamsInLice==null){
					  teamsInLice=[];
				  }
				  $scope.teamsInLice=teamsInLice;
			  }
		    	  
		      });
		 };
		 
/**
 * Getting the challenge id
 */		 
		 var challengeId=$routeParams.challengeId
		 console.log("Challenge Id = "+challengeId);
		 listTeam();	
		 getChallenge(challengeId);

		 /**
		  * This add a team to a match
		  */
		 $scope.addTeam = function addTeam(team){
			 console.log("Ajout de la team = "+ team);
			 teamsInLice.push(team);
			 console.log("Team en lice= "+teamsInLice);
			 $scope.teamsInLice=teamsInLice;
			 matchSelected.scores=teamsInLice;
		 }
		 
		 getMatch(challengeId);
		 
 
}]);