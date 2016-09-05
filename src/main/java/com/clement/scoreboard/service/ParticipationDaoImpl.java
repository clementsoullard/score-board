package com.clement.scoreboard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import com.clement.scoreboard.dto.Participation;

@Repository
public class ParticipationDaoImpl {

	final static float DISTRIBUTION_FACTOR = 2F;

	static final Logger LOG = LoggerFactory.getLogger(ParticipationDaoImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * Return the number of child
	 * 
	 * @return
	 */
	public Integer getChildNb() {
		try {
			BasicQuery query = new BasicQuery("{isChild: {$eq: true}}");
			LOG.debug("Construction de la requete effectuée");
			Long enfantCount = mongoTemplate.count(query, Participation.class);
			return enfantCount.intValue();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Return the number of vegatarians
	 * 
	 * @return
	 */
	public Integer getVegetarianNb() {
		try {
			BasicQuery query = new BasicQuery("{vegetarian: {$eq: true}}");
			LOG.debug("Construction de la requete effectuée");
			Long vegetarianCount = mongoTemplate.count(query, Participation.class);
			return vegetarianCount.intValue();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}	/**
	 * Return the number of vegatarians
	 * 
	 * @return
	 */
	public Integer getFemaleNb() {
		try {
			BasicQuery query = new BasicQuery("{gender: {$eq: female}}");
			LOG.debug("Construction de la requete effectuée");
			Long femaleCount = mongoTemplate.count(query, Participation.class);
			return femaleCount.intValue();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

}