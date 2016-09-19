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
		Map<String, Integer> totalScoreRank = new HashMap<String, Integer>();
		List<ChallengeScoreLine> matchEntries = new ArrayList<>();
		List<Challenge> challenges = challengeRepository.findAll();
		List<Team> teams = teamRepository.findAll();
		for (Challenge challenge : challenges) {
			ChallengeScoreLine challengeSheet = new ChallengeScoreLine();
			challengeSheet.setChallenge(challenge);
			for (Team team : teams) {
				ChallengeScore challengeScore = new ChallengeScore(team);
				Integer score = challenge.getScoreForTeam(team);
				Integer scoreRank = challenge.getRankingPointForTeam(team);
				challengeScore.setPoint(score);
				challengeScore.setPointRank(scoreRank);

				challengeSheet.addChallengeScore(challengeScore);
				/** Total des ponts gagnés */
				Integer totalscore = totalScores.get(team.getIdr());
				if (totalscore == null) {
					totalscore = 0;
				}
				totalscore += score;
				totalScores.put(team.getIdr(), totalscore);
				/** Total des pointsrank */

				Integer totalscoreRank = totalScoreRank.get(team.getIdr());
				if (totalscoreRank == null) {
					totalscoreRank = 0;
				}
				totalscoreRank += scoreRank;
				totalScoreRank.put(team.getIdr(), totalscoreRank);

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
			Integer scoreRank = totalScoreRank.get(team.getIdr());
			challengeScore.setPointRank(scoreRank);

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
	public Match evaluateResult(String matchId) {
		try {
			Match match = matchRepository.findOne(matchId);
			List<ScoreMatch> scoreMatchs = match.getScores();
			java.util.Collections.sort(scoreMatchs, new Comparator<ScoreMatch>() {
				@Override
				public int compare(ScoreMatch arg0, ScoreMatch arg1) {
					return arg1.getScore() - arg0.getScore();
				}
			});
			Challenge challenge = challengeRepository.findOne(match.getChallengeId());
			if (challenge.getScores() == null) {
				challenge.setScores(new ArrayList<>());
			}
			/**
			 * Pour les matchs à deux participants
			 */
			if (scoreMatchs.size() <= 2) {
				int i = 0;
				int previousScore = -1;
				for (ScoreMatch scoreMatch : scoreMatchs) {
					if (previousScore == scoreMatch.getScore()) {
						i--;
					}
					switch (i) {
					case 0:
						scoreMatch.setScoreRank(5);
									break;
					case 1:
						scoreMatch.setScoreRank(0);
						break;

					default:
						break;
					}
					challenge.getScores().add(scoreMatch);
					
					if (previousScore == scoreMatch.getScore()) {
						i++;
					}
					previousScore = scoreMatch.getScore();

					i++;
				}
			} else if (scoreMatchs.size() <= 4) {
				int i = 0;
				int previousScore = -1;
				
				for (ScoreMatch scoreMatch : scoreMatchs) {
					if (previousScore == scoreMatch.getScore()) {
						i--;
					}

					switch (i) {
					case 0:
						scoreMatch.setScoreRank(5);
						break;
					case 1:
						scoreMatch.setScoreRank(3);
						break;
					case 2:
						scoreMatch.setScoreRank(2);
						break;
					case 3:
						scoreMatch.setScoreRank(0);
						break;

					default:
						break;
					}
					challenge.getScores().add(scoreMatch);
					if (previousScore == scoreMatch.getScore()) {
						i++;
					}
					previousScore = scoreMatch.getScore();

					i++;
				}
			} else if (scoreMatchs.size() <= 8) {
				int previousScore = -1;
				
				int i = 0;
				for (ScoreMatch scoreMatch : scoreMatchs) {
					if (previousScore == scoreMatch.getScore()) {
						i--;
					}

					switch (i) {
					case 0:
						scoreMatch.setScoreRank(10);
						break;
					case 1:
						scoreMatch.setScoreRank(8);
						break;
					case 2:
						scoreMatch.setScoreRank(6);
						break;
					case 3:
						scoreMatch.setScoreRank(5);
						break;
					case 4:
						scoreMatch.setScoreRank(4);
						break;
					case 5:
						scoreMatch.setScoreRank(3);
						break;
					case 6:
						scoreMatch.setScoreRank(1);
						break;
					case 7:
						scoreMatch.setScoreRank(0);
						break;

					default:
						break;
					}
					challenge.getScores().add(scoreMatch);
					if (previousScore == scoreMatch.getScore()) {
						i++;
					}
					previousScore = scoreMatch.getScore();

					i++;
				}

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
		LOG.debug("Activating match " + matchToActivateId);
		match.setClose(false);
		matchRepository.save(match);
		if (matchToCloseId != null) {
			LOG.debug("Closing match " + matchToCloseId);
			match = matchRepository.findOne(matchToCloseId);
			LOG.debug("Closing match " + matchToCloseId);
			match.setClose(true);
			matchRepository.save(match);
		} else {
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
		if (match != null) {
			match.setClose(true);
			matchRepository.save(match);
			return findOpenMatchForChallengeId(match.getChallengeId());
		}
		return null;
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
