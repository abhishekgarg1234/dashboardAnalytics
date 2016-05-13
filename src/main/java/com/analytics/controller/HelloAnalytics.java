package com.analytics.controller;

import java.io.File;
import java.io.IOException;

import com.analytics.sessions.hourly.todaySessionHourly;
import com.analytics.sessions.weekly.sessionsPresentWeek;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.Accounts;
import com.google.api.services.analytics.model.Profiles;
import com.google.api.services.analytics.model.Webproperties;

public class HelloAnalytics {

  private static final String APPLICATION_NAME = "Hello Analytics";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String KEY_FILE_LOCATION = "/home/abhishekgarg/Documents/com.analytics/My Project-0f271ca191f2.p12";
  private static final String SERVICE_ACCOUNT_EMAIL = "dashboardanalytics@thematic-center-130908.iam.gserviceaccount.com";
  
  public static void main(String[] args) {
    try {
//    	long unixTime = System.currentTimeMillis();
//    	System.out.println(unixTime);
    	
//    	System.out.println(getStartOfDayInMillis());
//      System.out.println("First Profile Id");
      Analytics analytics = initializeAnalytics();

//      String profile = getFirstProfileId(analytics);
      String profile = "119715910";
//      System.out.println("First Profile Id: "+ profile);
//      sessionsHourly s1=new sessionsHourly();
//      s1.calculation(s1.getResults(analytics, profile));
//      sessionsWeekly s2 = new sessionsWeekly(); 
//      s2.calculation(s2.getResults(analytics, profile));
    	
//    	todaySessionHourly s3 = new todaySessionHourly();
//    	s3.calculation(s3.getResults(analytics, profile));
      sessionsPresentWeek s4 = new sessionsPresentWeek();
      s4.calculation(s4.getResults(analytics, profile));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Analytics initializeAnalytics() throws Exception {
    // Initializes an authorized analytics service object.

    // Construct a GoogleCredential object with the service account email
    // and p12 file downloaded from the developer console.
    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    GoogleCredential credential = new GoogleCredential.Builder()
        .setTransport(httpTransport)
        .setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
        .setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
        .setServiceAccountScopes(AnalyticsScopes.all())
        .build();

    // Construct the Analytics service object.
    return new Analytics.Builder(httpTransport, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME).build();
  }

  private static String getFirstProfileId(Analytics analytics) throws IOException {
    // Get the first view (profile) ID for the authorized user.
    String profileId = null;

    // Query for the list of all accounts associated with the service account.
    Accounts accounts = analytics.management().accounts().list().execute();

    if (accounts.getItems().isEmpty()) {
      System.err.println("No accounts found");
    } else {
      String firstAccountId = accounts.getItems().get(0).getId();

      // Query for the list of properties associated with the first account.
      Webproperties properties = analytics.management().webproperties()
          .list(firstAccountId).execute();

      if (properties.getItems().isEmpty()) {
        System.err.println("No Webproperties found");
      } else {
        String firstWebpropertyId = properties.getItems().get(0).getId();

        // Query for the list views (profiles) associated with the property.
        Profiles profiles = analytics.management().profiles()
            .list(firstAccountId, firstWebpropertyId).execute();

        if (profiles.getItems().isEmpty()) {
          System.err.println("No views (profiles) found");
        } else {
          // Return the first (view) profile associated with the property.
          profileId = profiles.getItems().get(0).getId();
        }
      }
    }
    return profileId;
  }


}


