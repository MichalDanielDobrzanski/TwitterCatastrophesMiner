package com.sag.wedt;

import twitter4j.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dobi on 06.05.16.
 */
public class DisasterMiner {

    private static final String dir = "input/";
    private static final String outFile = "tweets.txt";
    private static final String catFile = "categories.txt";

    private ArrayList<Category> categories;

    private static int twittsToFind = 100; // the amount of Twitts to get
    //private static String twittsSince = "2014-01-01"; // date from which we find twiits  YYYY-MM-DD
    private static String twittsUntil = "2016-05-05"; // date to which we find twiits  YYYY-MM-DD

    public DisasterMiner() {

        initCategories();

        getTwitts();

        saveTwitts();

    }

    private void saveTwitts() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd_HH.mm_");

        File file = new File(dir + sdf.format(now) + outFile);
        BufferedWriter out = null;
        try {
            for (Category c : categories) {
                out = new BufferedWriter(new FileWriter(file, true));
                out.write(c.toString());
            }
            if (out != null)
                out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initCategories() {
        categories = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(dir + catFile));
            while (sc.hasNext()) {
                Category c = new Category(sc.next());
                c.addKeyWord(sc.next());
                c.addKeyWord(sc.next());
                categories.add(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getTwitts() {
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
                System.out.println(status.getCreatedAt() + " @" + status.getUser().getScreenName() + ":" + status.getText());
            }
            c.setStatuses(results);
        }

        for (Category c : categories) {
            System.out.println(c.getCategory() + " tweets: " + c.getStatuses().size());
        }
    }
}
