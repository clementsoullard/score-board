'use strict';

angular.module('myApp.playgame', ['ngRoute'])

.config(['$routeProvider' ,function($routeProvider) {
  $routeProvider.when('/playgame/challenge/:challengeId', {
    templateUrl: 'playgame/playgame.html',
    controller: 'PlayGameCtrl'
  });
}])

.controller('PlayGameCtrl', ['$scope','$http','$routeParams', function($scope,$http,$routeParams) {

	 /** We use the tracer to identify if a registration has been done on the same computer */
	 var x = document.cookie;
	 
	 if(!x){
		 var d = new Date();
	 	d.setTime(d.getTime() + (30*24*60*60*1000));
	 	var expires = "expires="+ d.toUTCString();
	 	var rndNumber=Math.floor((Math.random() * 1000000) + 1);
	 	document.cookie =  "tracer="+rndNumber+" ; " + expires;
	 	x=document.cookie;
	 }

	 /** This is to identify the event it is related to */

	 var tracer=x.substring(7);

	
	var teamsInLice=[];
	$scope.teamsInLice=teamsInLice;
	var matchSelected;
	var matchPassed;
	/**
	 * List the teams that are displayed on the right to select for the match
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
			 matchSelected={'challengeId': $routeParams.challengeId,'close': false};
			 console.log("Creating a match "+$routeParams.challengeId);
		     teamsInLice=[];
		     $scope.teamsInLice=teamsInLice;
			 };

			 /**
			  * Get the information about the challenge
			  */
			 function saveMatch (){
				 console.log("Saving a match ");
				 matchSelected.tracer=tracer;
				 $http.post('save-match' , matchSelected).
			      success(function(data) {
			    	  matchSelected=data;
			    	  teamsInLice=matchSelected.scores;
					  if(teamsInLice==null){
						  teamsInLice=[];
					  }
					  $scope.teamsInLice=teamsInLice;
			    	console.log(JSON.stringify(data));
			      });
			 };
			 /**
			  * Get the information about the challenge
			  */
				 $scope.saveScore =function (){
					 saveMatch();
					 
				 };


		 /**
		  * Get the information about the challenge
		  */
		 $scope.closeMatch = function (){
				console.log("Closing the match");
				$http.get('close-match?id='+matchSelected.idr).
			      success(function(data) {
			    //	  createMatch();
			    	  getClosedMatch();
			    	  getMatch();
			    });
		 };
		 
		 /**
		  * Removing a team from the team en lice
		  */
		 $scope.removeTeam = function(teamId){
			 console.log("Remove team "+teamId);
		   var indexToRemove=-1;
			 for(var i in teamsInLice){
			 var team=teamsInLice[i];
			 console.log("Examinons "+ JSON.stringify(team) +" pour "+ teamId);
             if(teamId==team.idr){
            	 indexToRemove=i;
            }
			}
			 if(indexToRemove>-1){
			 teamsInLice.splice(indexToRemove,1);
			 }
			};

			/**
			  * Reactivate match
			  */
			 $scope.reactivateMatch = function(matchIdToReactivate){
				 var idMatchToClose=matchSelected.idr;
				 var url;
				 if(idMatchToClose!=undefined){
					 url="reactivate-match?idReactivate="+matchIdToReactivate+"&idClose="+idMatchToClose;
				 }
				 else{
					 url="reactivate-match?idReactivate="+matchIdToReactivate;
				 }
				 console.log("Reactivate match "+url);
					
				 $http.get(url).
				      success(function(data) {
						  matchSelected=data;
						  console.log("Match retour "+JSON.stringify(data));
						  teamsInLice=matchSelected.scores;
						  if(teamsInLice==null){
							  teamsInLice=[];
						  }
						  $scope.teamsInLice=teamsInLice;
				    	  getClosedMatch();

				      });							 
			 };

		 /**
		  * Get the match that is open for the challengeId, create one if no match is open.
		  */
		 function getMatch(challengeId){
			 // console.log("Finding matches1 for challenge "+challengeId);
			$http.get('open-match?id='+challengeId).
		      success(function(data) {
		    // console.log("Finding matches for challenge "+challengeId);
			  if(data=="" ){
		    		  createMatch(challengeId);
			  }else{
				  matchSelected=data;
				  teamsInLice=matchSelected.scores;
				  if(teamsInLice==null){
					  teamsInLice=[];
				  }
				  $scope.teamsInLice=teamsInLice;
			  }
			  console.log("MatchSelected " + JSON.stringify(matchSelected));
		    	  
		      });
		 };

		 
		 /**
		  * The closed match
		  */
		 function getClosedMatch(challengeId){
			 console.log("Closed matches for challenge "+challengeId);
			$http.get('closed-match?id='+$routeParams.challengeId).
		      success(function(data) {
		    // console.log("Finding matches for challenge "+challengeId);
		      var closedMatches=data;
		      console.log("Closed matches found "+ closedMatches);
			  $scope.closedMatches = closedMatches;

			  matchPassed=closedMatches;
			  $scope.matchPassed=matchPassed;
		    	  
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
			 for(var i in teamsInLice){
				 var teamInLice=teamsInLice[i];
				 if(team.idr==teamInLice.idr){
					 console.log("Preventing adding twice the team");
					 return; 
				 }
			 }
			 console.log("Ajout de la team = "+ team);
			 teamsInLice.push(team);
			 console.log("Team en lice= "+teamsInLice);
			 $scope.teamsInLice=teamsInLice;
			 matchSelected.scores=teamsInLice;
		 }
		 
		 getMatch(challengeId);
		 getClosedMatch(challengeId);
		 
 
}]);