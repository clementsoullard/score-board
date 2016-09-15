package com.clement.scoreboard.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.clement.scoreboard.object.ChallengeScoreLine;
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
	public List<ChallengeScoreLine> getChallengeSheets() {
		Map<String, Integer> totalScores = new HashMap<String, Integer>();
		List<ChallengeScoreLine> matchEntries = new ArrayList<>();
		List<Challenge> challenges = challengeRepository.findAll();
		List<Team> teams = teamRepository.findAll();
		for (Challenge challenge : challenges) {
			ChallengeScoreLine challengeSheet = new ChallengeScoreLine();
			challengeSheet.setChallenge(challenge);
			for (Team team : teams) {
				ChallengeScore challengeScore = new ChallengeScore(team);
				Integer score = challenge.getScoreForTeam(team);
				challengeScore.setPoint(score);
				challengeSheet.addChallengeScore(challengeScore);
				Integer totalscore = totalScores.get(team.getIdr());
				if (totalscore == null) {
					totalscore = 0;
				}
				totalscore += score;
				totalScores.put(team.getIdr(), totalscore);
			}
			matchEntries.add(challengeSheet);
		}
		/**
		 * This is the line for the total score.
		 */
		ChallengeScoreLine challengeScoreLine = new ChallengeScoreLine();
		for (Team team : teams) {
			Challenge challenge = new Challenge();
			challenge.setName("Total");
			challengeScoreLine.setTotal(true);
			challengeScoreLine.setChallenge(challenge);
			ChallengeScore challengeScore = new ChallengeScore(team);
			Integer score = totalScores.get(team.getIdr());
			challengeScore.setPoint(score);
			challengeScoreLine.addChallengeScore(challengeScore);
			Integer totalscore = totalScores.get(team.getIdr());
			if (totalscore == null) {
				totalscore = 0;
			}
			totalscore += score;
			totalScores.put(team.getIdr(), totalscore);
		}
		matchEntries.add(challengeScoreLine);
		return matchEntries;

	}

	/**
	 * The list of the matches that are started
	 * 
	 * @param challengeId
	 * @return
	 */
	public Match findOpenMatchForChallengeId(String challengeId) {
		try {
			BasicQuery query = new BasicQuery("{$and: [ {close: {$ne: true}},{challengeId:\"" + challengeId + "\"}]}");
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
	public List<Match> findCloseMatch(String id) {
		try {
			BasicQuery query = new BasicQuery("{$and: [ {close: true},{challengeId:\"" + id + "\"}]}");
			List<Match> matchs = mongoTemplate.find(query, Match.class);
			return matchs;
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
	public Match evaluateResult(String id) {
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

	/**
	 * This reactivate a match
	 * 
	 * @param matchToActivateId
	 */
	public Match reactivateMatch(String matchToActivateId, String matchToCloseId) {
		Match match = matchRepository.findOne(matchToActivateId);
		String challengeId = match.getChallengeId();
		LOG.debug("Activating match "+matchToActivateId); 
		match.setClose(false);
		matchRepository.save(match);
		if (matchToCloseId != null) {
			LOG.debug("Closing match "+matchToCloseId); 
			match = matchRepository.findOne(matchToCloseId);
			LOG.debug("Closing match "+matchToCloseId); 
			match.setClose(true);
			matchRepository.save(match);
		}
		else{
			LOG.debug("There is no match to close"); 
		}
		match = findOpenMatchForChallengeId(challengeId);
	return match;
	}

	/**
	 * This reactivate a match
	 * 
	 * @param matchId
	 */
	public Match closeMatch(String matchId) {
		Match match = matchRepository.findOne(matchId);
		match.setClose(true);
		matchRepository.save(match);
		return findOpenMatchForChallengeId(match.getChallengeId());
	}

	/**
	 * A match is only saved if there are some score (otherwise it does not make
	 * sense)
	 * 
	 * @param match
	 * @return
	 */
	public Match saveMatch(Match match) {
		if (match.getScores() != null && match.getScores().size() > 0) {
			LOG.debug("Save Match " + match.getIdr());
			return matchRepository.save(match);
		}
		return match;
	}

}
