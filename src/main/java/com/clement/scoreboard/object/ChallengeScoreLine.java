package com.clement.scoreboard.object;

import java.util.ArrayList;

import com.clement.scoreboard.dto.Challenge;

public class ChallengeScoreLine {

	private Challenge challenge;

	/**
	 * Is true if the challenge represent the total of the various challenge.
	 */
	boolean isTotal;
	/**
	 * A challenge score is representing a line of the general board.
	 */
	private java.util.List<ChallengeScore> challengeScores;

	public ChallengeScoreLine() {
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

	public void setChallengeScores(java.util.List<ChallengeScore> challengeScore) {
		this.challengeScores = challengeScore;
	}

	public void addChallengeScore(ChallengeScore challengeScore) {
		challengeScores.add(challengeScore);
	}

	public boolean isTotal() {
		return isTotal;
	}

	public void setTotal(boolean isTotal) {
		this.isTotal = isTotal;
	}
	

}
