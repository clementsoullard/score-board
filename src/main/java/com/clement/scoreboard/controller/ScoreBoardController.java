package com.clement.scoreboard.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clement.scoreboard.dto.Match;
import com.clement.scoreboard.object.ChallengeScoreLine;
import com.clement.scoreboard.service.MatchDaoImpl;

/**
 * 
 * @author Clement_Soullard
 *
 */
@RestController
public class ScoreBoardController {

	@Resource
	private MatchDaoImpl matchDaoImpl;

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
		Match match = matchDaoImpl.findOpenMatch(challengeId);
		return match;
	}

	/**
	 * 
	 * @param challengeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/closed-match")
	public List<Match> getClosedMatch(@RequestParam("id") String challengeId) throws Exception {
		List<Match> matches = matchDaoImpl.findCloseMatch(challengeId);
		return matches;
	}

}