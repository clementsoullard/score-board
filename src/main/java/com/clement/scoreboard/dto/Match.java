package com.clement.scoreboard.dto;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author Clement_Soullard
 *
 */
public class Match {

	@Id
	private String id;

	private List<ScoreMatch> scores;

	private String challengeId;

	public String getIdr() {
		return id;
	}

	public void setIdr(String id) {
		this.id = id;
	}

	public List<ScoreMatch> getScores() {
		return scores;
	}

	public void setScores(List<ScoreMatch> scores) {
		this.scores = scores;
	}

	public String getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}



}
