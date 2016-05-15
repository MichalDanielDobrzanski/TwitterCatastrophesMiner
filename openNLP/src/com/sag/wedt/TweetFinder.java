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
public class TweetFinder {

    private static final String dir = "openNLP/input/";
    private static final String trainingDir = "openNLP/input_training/";
    private static final String outFile = "tweets.txt";

    private ArrayList<Category> categories;

    private static int twittsToFind = 100; // the amount of Twitts to get
    //private static String twittsSince = "2014-01-01"; // date from which we find twiits  YYYY-MM-DD
    private static String twittsUntil = "2016-05-11"; // date to which we find twiits  YYYY-MM-DD

    public TweetFinder(ArrayList<Category> categories) {
        this.categories = categories;
    }



    public void saveTwitts() {
        //Date now = new Date();
        //SimpleDateFormat sdf = new SimpleDateFormat("MM.dd_HH.mm_");

        //File file = new File(dir + sdf.format(now) + outFile);
        File file = new File(trainingDir + outFile);
        file.delete();
        BufferedWriter out = null;
        String tweetToWrite = null;
        int tweetsWritten = 0;

        try {
            for (Category c : categories) {
                List<String> statuses = c.getStatuses();
                for (String sta : statuses) {
                    out = new BufferedWriter(new FileWriter(file, true));
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
                    if (out != null)
                        out.close();
                }

            }

        } catch (IOException e) {
                e.printStackTrace();
        }

    }

    public void getTwitts() {
        // Get API Handler:
        Twitter twitter = TwitterFactory.getSingleton();

        for (Category c : categories) {
            // concat key words:
            StringBuilder sb = new StringBuilder();
            for (String keyW : c.getKeyWords()) {
                sb.append(keyW + " ");
            }
            String searchQuery = sb.toString();

            // make a query:

            // http://stackoverflow.com/questions/7975866/how-to-exclude-retweets-from-my-search-query-results-options
            Query query = new Query(searchQuery + "+exclude:retweets");
            query.setCount(twittsToFind);

            // Z oficjalnej dokumentacji:
            // http://stackoverflow.com/questions/34633076/twitter4j-no-result-returns-if-i-use-since-until-restrictions
            query.setUntil(twittsUntil);

            QueryResult result = null;
            try {
                System.out.println("Getting tweets for " + c.getCategory()  + "...");
                result = twitter.search(query);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            List<Status> results = result.getTweets();
            for (Status status : results) {
                System.out.println(status.getCreatedAt() + " @" + status.getUser().getScreenName() + ":" +
                        status.getText());
            }
            c.setStatuses(results);
        }

        for (Category c : categories) {
            System.out.println(c.getCategory() + " tweets: " + c.getStatuses().size());
        }
    }
}
