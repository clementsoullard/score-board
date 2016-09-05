package com.clement.scoreboard.object;

import com.clement.scoreboard.dto.Team;

public class ChallengeScore {
	Team team;

	public ChallengeScore(Team team) {
		this.team = team;
	}

	Integer point = 0;

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

}
