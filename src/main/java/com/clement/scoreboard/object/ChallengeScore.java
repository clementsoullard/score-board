package com.clement.scoreboard.object;

import com.clement.scoreboard.dto.Team;

public class ChallengeScore {

	private Team team;

	private Integer point = 0;

	private Integer pointRank = 0;

	public ChallengeScore(Team team) {
		this.team = team;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Integer getPointRank() {
		return pointRank;
	}

	public void setPointRank(Integer pointRank) {
		this.pointRank = pointRank;
	}

}
