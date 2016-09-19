
package com.clement.scoreboard.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.clement.scoreboard.dto.Challenge;

@RepositoryRestResource(collectionResourceRel = "challenge", path = "challenge")

public interface ChallengeRepository extends MongoRepository<Challenge, String> {

}
