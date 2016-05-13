package com.analytics.sessionDuration.weekly;

import java.io.IOException;
import java.util.List;

import com.analytics.utils;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;

public class sessionDurationPresentWeek extends utils {
	  public GaData getResults(Analytics analytics, String profileId) throws IOException {
		  String startDate =getDayOfWeek() +"daysAgo";
		    return analytics.data().ga()
		        .get("ga:" + profileId, startDate, "today", "ga:sessionDuration")
		        .setDimensions("ga:day")
		        .execute();
		  }
	  
	  public void calculation(GaData results) throws IOException {
		    if (results != null && !results.getRows().isEmpty()) {
		    	System.out.println(results);
				int days =getDayOfWeek();
		    	long startTime=getStartOfDayInSeconds() - (days*86400);
		    	
		          for(List<String> session : results.getRows()){
		              String data="presentWeekSessionDurationData"+ " " + session.get(1) + " " + startTime;
		              System.out.println(data);
		              startTime = startTime + 86400;
//		              httpPostCall(data);
		          }
		    } else {
		      System.out.println("No results found");
		    }
		  }

}
