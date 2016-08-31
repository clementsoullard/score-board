
package com.infosys.eventtracker.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.infosys.eventtracker.dto.Team;

@RepositoryRestResource(collectionResourceRel = "team", path = "team")
public interface TeamRepository extends MongoRepository<Team, String> {

}
