
package com.clement.scoreboard.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.clement.scoreboard.dto.Match;

@RepositoryRestResource(collectionResourceRel = "match", path = "match")
public interface MatchRepository extends MongoRepository<Match, String> {
	List<Match> findMatchByChallengeId(@Param("id") String id);
}
