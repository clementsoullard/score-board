package com.clement.scoreboard.object;

import java.util.ArrayList;

import com.clement.scoreboard.dto.Challenge;

public class ChallengeSheet {

	private Challenge challenge;

	private java.util.List<ChallengeScore> challengeScores;

	public ChallengeSheet() {

		challengeScores = new ArrayList<ChallengeScore>();
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public java.util.List<ChallengeScore> getChallengeScores() {
		return challengeScores;
	}

	public void setChallengeScores(java.util.List<ChallengeScore> challengeStatus) {
		this.challengeScores = challengeStatus;
	}

	public void addChallengeScore(ChallengeScore challengeStatus) {
		challengeScores.add(challengeStatus);
	}

}
