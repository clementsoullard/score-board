package com.clement.scoreboard.dto;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author Clement_Soullard
 *
 */
public class Challenge {

	@Id
	private String id;

	private String name;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdr() {
		return id;
	}

	public void setIdr(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
