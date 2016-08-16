package com.infosys.eventtracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyManager {
	@Value("${scheduler.work.path}")
	String workPath;

	@Value("${livebox.urlPrefix}")
	String liveboxUrlPrefix;

	@Value("${production.mode}")
	Boolean productionMode;

	public String getPathCountDown() {
		return workPath + "/CD";
	}

	public String getPathStandby() {
		return workPath + "/SB";
	}

	public String getPathStatus() {
		return workPath + "/ST";
	}

	public String getPathRemaining() {
		return workPath + "/REM";
	}

	public String getLiveboxUrlPrefix() {
		return liveboxUrlPrefix;
	}
	
	public Boolean getProductionMode() {
		return productionMode;
	}
}
