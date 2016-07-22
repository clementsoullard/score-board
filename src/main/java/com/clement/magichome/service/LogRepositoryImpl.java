package com.clement.magichome.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.clement.magichome.dto.MinutesPerChannel;

@Repository
public class LogRepositoryImpl {

	static final Logger LOG = LoggerFactory.getLogger(LogRepositoryImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;

	public AggregationResults<MinutesPerChannel> getMinutesPerChannel() {
		try {
			Aggregation aggregation = newAggregation(match(Criteria.where("metricName").in("TV")),
					project("channel", "minutes"), group("channel").sum("minutes").as("totalMinutes"));
			LOG.debug("Construction de la requete effectuée");
			AggregationResults<MinutesPerChannel> minutesPerChannelAgg = mongoTemplate.aggregate(aggregation, "log",
					MinutesPerChannel.class);
			LOG.debug("Requete effectue");
			for (MinutesPerChannel minutesPerChannel : minutesPerChannelAgg) {
				LOG.debug(minutesPerChannel.getChannel().toString() + " " + minutesPerChannel.getTotalMinutes());
			}
			return minutesPerChannelAgg;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

}