package com.clement.scoreboard.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import com.clement.scoreboard.dto.Challenge;
import com.clement.scoreboard.dto.Match;
import com.clement.scoreboard.dto.ScoreMatch;
import com.clement.scoreboard.dto.Team;
import com.clement.scoreboard.object.ChallengeScore;
import com.clement.scoreboard.object.ChallengeSheet;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

@Repository
public class MatchDaoImpl {
	@Autowired
	private ChallengeRepository challengeRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private MatchRepository matchRepository;

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
			ChallengeSheet challengeSheet = new ChallengeSheet();
			challengeSheet.setChallenge(challenge);
			List<Team> teams = teamRepository.findAll();
			for (Team team : teams) {
				ChallengeScore challengeScore = new ChallengeScore(team);
				Integer score = challenge.getScoreForTeam(team);
				challengeScore.setPoint(score);
				challengeSheet.addChallengeScore(challengeScore);

			}
			matchEntries.add(challengeSheet);
		}

		return matchEntries;

	}

	/**
	 * The list of the matches that are started
	 * 
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

	/**
	 * The list of the matches that are started
	 * 
	 * @param id
	 * @return
	 */
	public Match closeMatch(String id) {
		try {
			Match match = matchRepository.findOne(id);
			List<ScoreMatch> scoreMatchs = match.getScores();
			java.util.Collections.sort(scoreMatchs, new Comparator<ScoreMatch>() {
				@Override
				public int compare(ScoreMatch arg0, ScoreMatch arg1) {
					return arg0.getScore() - arg1.getScore();
				}
			});
			Challenge challenge = challengeRepository.findOne(match.getChallengeId());
			challenge.setScores(new ArrayList<>());
			/**
			 * Pour les matchs à deux participants
			 */
			if (scoreMatchs.size() == 2) {
				int i = 0;
				for (ScoreMatch scoreMatch : scoreMatchs) {
					switch (i) {
					case 0:
						scoreMatch.setScore(5);
						break;
					case 1:
						scoreMatch.setScore(0);
						break;

					default:
						break;
					}
					challenge.getScores().add(scoreMatch);
					i++;
				}
			} else if (scoreMatchs.size() == 4) {
				int i = 0;
				for (ScoreMatch scoreMatch : scoreMatchs) {
					switch (i) {
					case 0:
						scoreMatch.setScore(5);
						break;
					case 1:
						scoreMatch.setScore(3);
						break;
					case 2:
						scoreMatch.setScore(2);
						break;
					case 3:
						scoreMatch.setScore(0);
						break;

					default:
						break;
					}
					challenge.getScores().add(scoreMatch);
					i++;
				}
			} else if (scoreMatchs.size() == 8) {

			} else {
				LOG.error("Not a recognized size of match");
			}

			challengeRepository.save(challenge);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

}
