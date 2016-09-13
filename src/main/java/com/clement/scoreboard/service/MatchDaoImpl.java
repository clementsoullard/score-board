package com.clement.scoreboard.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import com.clement.scoreboard.dto.Challenge;
import com.clement.scoreboard.dto.Match;
import com.clement.scoreboard.dto.Team;
import com.clement.scoreboard.object.ChallengeScore;
import com.clement.scoreboard.object.ChallengeSheet;

@Repository
public class MatchDaoImpl {
	@Autowired
	private ChallengeRepository challengeRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	static final Logger LOG = LoggerFactory.getLogger(MatchDaoImpl.class);

	/**
	 * 
	 * @return
	 */
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
/**
 * The list of the matches that are started
 * @param id
 * @return
 */
	public Match findMatchByChallengeIdAndClose(String id) {
		try {
			BasicQuery query = new BasicQuery("{$and: [ {close: {$ne: true}},{challengeId:\"" + id + "\"}]}");
			List<Match> matchs = mongoTemplate.find(query, Match.class);
			if (matchs.size() == 1) {
				return matchs.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
}
