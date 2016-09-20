package com.clement.scoreboard.dto;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author Clement_Soullard
 *
 */
public class Challenge {

	@Id
	private String id;

	private String name;

	private List<ScoreMatch> scores;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdr() {
		return id;
	}

	public void setIdr(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ScoreMatch> getScores() {
		return scores;
	}

	public void setScores(List<ScoreMatch> scores) {
		this.scores = scores;
	}

	public Integer getScoreForTeam(Team team) {
		if (scores != null) {
			for (ScoreMatch scoreMatch : scores) {
				if (scoreMatch.getIdr().equals(team.getIdr())) {
					return scoreMatch.getScore();
				}
			}
		}
		return 0;
	}

	public Integer getRankingPointForTeam(Team team) {
		if (scores != null) {
			for (ScoreMatch scoreMatch : scores) {
				if (scoreMatch.getIdr().equals(team.getIdr())) {
					return scoreMatch.getScoreRank();
				}
			}
		}
		return 0;
	}

}
