package com.clement.scoreboard.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clement.scoreboard.dto.Match;
import com.clement.scoreboard.dto.Team;
import com.clement.scoreboard.object.ChallengeScoreLine;
import com.clement.scoreboard.service.ChallengeDaoImpl;
import com.clement.scoreboard.service.MatchDaoImpl;

/**
 * 
 * @author Clement_Soullard
 *
 */
@RestController
public class ScoreBoardController {

	static final Logger LOG = LoggerFactory.getLogger(MatchDaoImpl.class);

	@Resource
	private MatchDaoImpl matchDaoImpl;

	@Resource
	private ChallengeDaoImpl challengeDaoImpl;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ws-match-sheet")
	public List<ChallengeScoreLine> getChallengeSheet() throws Exception {
		List<ChallengeScoreLine> challengeSheets = matchDaoImpl.getChallengeSheets();
		return challengeSheets;
	}

	/**
	 * 
	 * @param challengeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/open-match")
	public Match getOpenMatch(@RequestParam("id") String challengeId) throws Exception {
		Match match = matchDaoImpl.findOpenMatchForChallengeId(challengeId);
		return match;
	}

	/**
	 * 
	 * @param matchId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/close-match")
	public Match closeMatch(@RequestParam("id") String matchId) throws Exception {
		LOG.debug("Closing the match " + matchId);
		Match match = matchDaoImpl.closeMatch(matchId);
		matchDaoImpl.evaluateResult(matchId);
		return match;
	}

	/**
	 * 
	 * @param matchId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reset-score")
	public void resetScores() throws Exception {
		LOG.debug("Reset the scores ");
		challengeDaoImpl.resetScore();
	}

	/**
	 * 
	 * @param challengeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/closed-match")
	public List<Match> getClosedMatch(@RequestParam("id") String challengeId) throws Exception {
		LOG.debug("Finding the closed matches for challenge " + challengeId);
		List<Match> matches = matchDaoImpl.findCloseMatch(challengeId);
		LOG.debug("Found " + matches.size() + " closed match in DB");
		return matches;
	}

	/**
	 * 
	 * @param challengeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove-team")
	public void removeTeam(@RequestParam("teamId") String teamId, @RequestParam("matchId") String matchId)
			throws Exception {
		LOG.debug("Removing team " + teamId + " from " + matchId);
		matchDaoImpl.removeTeamFromMatch(matchId, teamId);
	}

	/**
	 * 
	 * @param challengeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reactivate-match")
	public Match reactivateMatch(@RequestParam("idReactivate") String matchIdToReactivate,
			@RequestParam(required = false, name = "idClose") String matchIdToClose) throws Exception {
		return matchDaoImpl.reactivateMatch(matchIdToReactivate, matchIdToClose);
	}

	/**
	 * 
	 * @param challengeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/team-notplayed")
	public List<Team> getTeamNotPlayed(@RequestParam("idChallenge") String challengeId) throws Exception {
		return matchDaoImpl.getTeamNotPlayed(challengeId);
	}

	/**
	 * 
	 * @param challengeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save-match")
	public Match saveMatch(@RequestBody Match match) throws Exception {
		return matchDaoImpl.saveMatch(match);
	}

}