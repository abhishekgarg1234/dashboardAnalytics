package com.analytics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public abstract class utils {
	 public GaData getResults(Analytics analytics, String profileId) throws IOException{
		return null;}
	 public void calculation(GaData results) throws IOException{}
	 
	  public static void httpPostCall(String data) throws IOException {

	      URL url = new URL("https://www.hostedgraphite.com/api/v1/sink");
	      URLConnection conn = url.openConnection();
	      String key = "ca8af4c1-a025-4c5f-aa1d-663a345c7ce2";
	      String authHeader = Base64.encode(key.getBytes());
	      conn.setRequestProperty("Authorization", "Basic " + authHeader);
	      conn.setDoOutput(true);
	      OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
	      writer.write(data);
	      writer.flush();
	      String line2;
	      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      while ((line2 = reader.readLine()) != null) {
	          System.out.println(line2);
	      }
	      writer.close();
	      reader.close();
	      System.out.println("Done");
	  }
	  
	  public long getStartOfDayInSeconds() {
		    Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    calendar.set(Calendar.MILLISECOND, 0);
		    return calendar.getTimeInMillis()/1000;
		}
	}
