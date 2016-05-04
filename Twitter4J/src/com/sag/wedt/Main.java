package com.sag.wedt;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

    public static void main(String[] args) {

        // http://www.androidhive.info/2012/09/android-twitter-oauth-connect-tutorial/

        // Twitter configuration is in a file.
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("source:twitter4j yusukey");
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }

    }
}
