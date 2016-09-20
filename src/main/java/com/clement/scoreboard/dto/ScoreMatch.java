package com.clement.scoreboard.dto;

/**
 * 
 * @author Clement_Soullard
 *
 */
public class ScoreMatch extends Team {

	private Integer score;
	
	private Integer scoreRank;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getScoreRank() {
		return scoreRank;
	}

	public void setScoreRank(Integer scoreRank) {
		this.scoreRank = scoreRank;
	}

}
