
package com.clement.scoreboard.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.clement.scoreboard.dto.Team;

@RepositoryRestResource(collectionResourceRel = "team", path = "team")
public interface TeamRepository extends MongoRepository<Team, String> {

}
