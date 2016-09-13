package com.clement.scoreboard.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.clement.scoreboard.dto.Match;
import com.clement.scoreboard.service.MatchDaoImpl;
import com.clement.scoreboard.service.MatchRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class ParticipationTest {

	@Autowired
	MatchDaoImpl MatchDaoImpl;

	@Autowired
	MatchRepository matchRepository;

	@Test
	public void testCloseMatch() {
		List<Match> matches = matchRepository.findAll();
		for (Match match : matches) {
			MatchDaoImpl.closeMatch(match.getIdr());
		}
	}

}
