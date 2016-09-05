package com.infosys.eventtracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyManager {

	
	@Value("${production.mode}")
	Boolean productionMode;

	
	public Boolean getProductionMode() {
		return productionMode;
	}
}
