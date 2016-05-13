package com.analytics.sessions.hourly;

import java.io.IOException;
import java.util.List;

import com.analytics.utils;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;

public class todaySessionHourly extends utils {
	
	  public GaData getResults(Analytics analytics, String profileId) throws IOException {
		    return analytics.data().ga()
		        .get("ga:" + profileId, "today", "today", "ga:sessions")
		        .setDimensions("ga:hour")
		        .execute();
		  }
	  
	  public void calculation(GaData results) throws IOException {
		  long startTime=getStartOfDayInSeconds();

		    if (results != null && !results.getRows().isEmpty()) {

		          for(List<String> session : results.getRows()){
		              startTime = startTime + 3600;
		              String data="todaySessionsHourly"+ " " + session.get(1) + " " + startTime;
		              System.out.println(data);
//		              httpPostCall(data);
		          }
		    } else {
		      System.out.println("No results found");
		    }
	  }
	 

}
