package com.herakles.ratelimiter;

import java.util.Calendar;
import java.util.Date;

public class QueueElement {
	private String	apiId;
	private Date	timestamp;
	private static 		Calendar cal = Calendar.getInstance(); 

	QueueElement(String api) {
		this.apiId = api;		
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
	    this.timestamp = cal.getTime();
	}

	public String getApiId() {
		return apiId;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
