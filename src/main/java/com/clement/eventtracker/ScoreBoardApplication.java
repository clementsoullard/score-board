package com.infosys.eventtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * This is the main class that launch the spring application.
 * 
 * @author Clement_Soullard
 *
 */

@SpringBootApplication
public class EventTrackerApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(EventTrackerApplication.class, args);
	}

}