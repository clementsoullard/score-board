package com.clement.scoreboard.dto;

import org.springframework.data.annotation.Id;

public class Song {

	public String getIdr() {
		return id;
	}

	public void setIdr(String id) {
		this.id = id;
	}

	@Id
	private String id;

	private String song;

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

}
