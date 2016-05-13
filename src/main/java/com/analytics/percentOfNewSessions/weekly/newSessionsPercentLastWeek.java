package com.analytics.percentOfNewSessions.weekly;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.analytics.utils;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;

public class newSessionsPercentLastWeek extends utils {
	 public GaData getResults(Analytics analytics, String profileId) throws IOException {
		  String startDate=(getDayOfWeek()+7) + "daysAgo";
		  String endDate =getDayOfWeek() +"daysAgo";
		    return analytics.data().ga()
		        .get("ga:" + profileId, startDate, endDate, "ga:percentNewSessions")
		        .setDimensions("ga:day")
		        .execute();
		  }
	  
	  public void calculation(GaData results) throws IOException {
		    if (results != null && !results.getRows().isEmpty()) {
		    	System.out.println(results);
				int days =getDayOfWeek()+7;
		    	long startTime=getStartOfDayInSeconds() - (days*86400);
		    	
		          for(List<String> session : results.getRows()){
		              String data="lastWeekNewSessionsPercentData"+ " " + session.get(1) + " " + startTime;
		              System.out.println(data);
		              startTime = startTime + 86400;
//		              httpPostCall(data);
		          }
		    } else {
		      System.out.println("No results found");
		    }
		  }
	  
	  private static int getDayOfWeek(){
		  Calendar cal = Calendar.getInstance();
			return (cal.get(Calendar.DAY_OF_WEEK)-1);
	  }
}
