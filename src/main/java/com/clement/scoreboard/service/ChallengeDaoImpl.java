package com.clement.scoreboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clement.scoreboard.dto.Challenge;

@Component
public class ChallengeDaoImpl {

	@Autowired
	private ChallengeRepository challengeRepository;

	@Autowired
	private MatchRepository matchRepository;

	public void resetScore() {
		List<Challenge> challenges = challengeRepository.findAll();
		for (Challenge challenge : challenges) {
			challenge.setScores(null);
			challengeRepository.save(challenge);
		}
		matchRepository.deleteAll();
	}

}
