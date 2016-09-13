package com.clement.scoreboard.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clement.scoreboard.dto.Match;
import com.clement.scoreboard.object.ChallengeSheet;
import com.clement.scoreboard.service.MatchDaoImpl;

@RestController
public class ScoreBoardController {

	@Resource
	private MatchDaoImpl matchDaoImpl;

	@RequestMapping("/ws-match-sheet")
	public List<ChallengeSheet> getChallengeSheet() throws Exception {
		List<ChallengeSheet> challengeSheets = matchDaoImpl.getChallengeSheets();
		return challengeSheets;
	}
	@RequestMapping("/open-match")
	public Match getOpenMatch(@RequestParam("id") String challengeId) throws Exception {
		Match match = matchDaoImpl.findMatchByChallengeIdAndClose(challengeId);
		return match;
	}

}