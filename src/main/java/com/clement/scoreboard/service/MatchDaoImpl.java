package com.clement.scoreboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clement.scoreboard.dto.Challenge;
import com.clement.scoreboard.dto.Team;
import com.clement.scoreboard.object.ChallengeSheet;
import com.clement.scoreboard.object.ChallengeScore;

@Repository
public class MatchDaoImpl {
	@Autowired
	ChallengeRepository challengeRepository;
	@Autowired
	TeamRepository teamRepository;

	public List<ChallengeSheet> getChallengeSheets() {
		List<ChallengeSheet> matchEntries = new ArrayList<>();
		List<Challenge> challenges = challengeRepository.findAll();
		for (Challenge challenge : challenges) {
			ChallengeSheet matchEntry = new ChallengeSheet();
			matchEntry.setChallenge(challenge);
			List<Team> teams = teamRepository.findAll();
			for (Team team : teams) {
				matchEntry.addChallengeScore(new ChallengeScore(team));
			}
			matchEntries.add(matchEntry);
		}

		return matchEntries;

	}
}
