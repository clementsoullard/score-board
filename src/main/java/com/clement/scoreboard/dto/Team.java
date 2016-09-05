package com.clement.scoreboard.dto;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author Clement_Soullard
 *
 */
public class Team {
	
	@Id
	private String id;

	private String name;

	private String color;

	public String getIdr() {
		return id;
	}

	public void setIdr(String id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
