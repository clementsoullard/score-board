package com.clement.scoreboard.dto;

import org.springframework.data.annotation.Id;

public class MinutesPerChannel {
	Float totalMinutes;
	
	@Id private String channelName;


	public Float getTotalMinutes() {
		return totalMinutes;
	}

	public void setTotalMinutes(Float totalMinutes) {
		this.totalMinutes = totalMinutes;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channel) {
		this.channelName = channel;
	}
}
