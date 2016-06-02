package com.sag.wedt;

import twitter4j.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Klasa wyszukująca Tweety
 */
public class TweetFinder implements Directories {


    private ArrayList<Category> categories;

    private static int twittsToFind = 100; // the amount of Twitts to get
    //private static String twittsSince = "2014-05-31"; // date from which we find twiits  YYYY-MM-DD
    private static String twittsUntil = "2016-06-02"; // date to which we find twiits  YYYY-MM-DD

    public TweetFinder(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public void getTwitts() {
        // Get API Handler:
        Twitter twitter = TwitterFactory.getSingleton();

        String noRetweets = "+exclude:retweets"; // http://stackoverflow.com/questions/7975866/how-to-exclude-retweets-from-my-search-query-results-options

        for (Category c : categories) {

            // concat key words:
            StringBuilder sb = new StringBuilder();
            for (String keyW : c.getKeyWords()) {
                sb.append(keyW + " ");
            }
            String searchQuery = sb.toString();

            // make a query:
            Query query = new Query(searchQuery + noRetweets);
            query.setLocale("en");
            query.setLang("en");
            query.setCount(twittsToFind);
            //query.setSince(twittsSince);
            query.setUntil(twittsUntil); // http://stackoverflow.com/questions/34633076/twitter4j-no-result-returns-if-i-use-since-until-restrictions

            QueryResult result = null;
            try {
                System.out.println("Getting tweets for " + c.getCategory()  + "...");
                result = twitter.search(query);
            } catch (TwitterException e) {
                System.out.println("here.");
                e.printStackTrace();
            }
            List<Status> results = result.getTweets();

            int locationCount = 0;
            for (Status status : results) {
                String data = status.getCreatedAt() + " @" + status.getUser().getScreenName() + ":" +
                        status.getText();
                System.out.println(data);
            }
            c.setStatuses(results);
        }

        for (Category c : categories) {
            System.out.println(c.getCategory() + " tweets: "
                    + c.getStatuses().size() + " geolocations: " + c.getGeoLocationsCount());
        }
    }


    public void saveTwitts() {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd_HH.mm_");
        File file = new File(trainingDir + sdf.format(now) + outFile);

        //File file = new File(trainingFile);
        //file.delete();
        BufferedWriter out;
        String tweetToWrite;
        int tweetsWritten = 0;
        try {
            for (Category c : categories) {
                List<String> statuses = c.getStatuses();
                for (String sta : statuses) {
                    out = new BufferedWriter(new FileWriter(file, true)); // append = true
                    tweetToWrite = sta.replace("\n","").replace("\r","");
                    tweetsWritten++;
                    // Feel free to optimize this code - it's not really good imho, but still serves its purpose.
                    // It checks whether the last tweet is being written to the file and - if no - it prevents the
                    // program from inserting the following "\n" which would crash the model training.
                    // (java.io.IOException: Empty lines, or lines with only a category string are not allowed!)
                    // MJK
                    if(tweetsWritten == categories.size()*twittsToFind)
                        out.write(c.getCategory() + " " + tweetToWrite);
                    else
                    if(tweetToWrite.length() > 0)
                        out.write(c.getCategory() + " " + tweetToWrite + "\n");
                    out.close();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
