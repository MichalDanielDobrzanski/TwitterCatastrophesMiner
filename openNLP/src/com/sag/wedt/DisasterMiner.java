package com.sag.wedt;

import twitter4j.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dobi on 06.05.16.
 */
public class DisasterMiner {

    private static final String outFile = "input/tweets.txt";
    private static final String catFile = "input/categories.txt";

    private ArrayList<Category> categories;

    private static int twittsToFind = 100; // the amount of Twitts to get
    //private static String twittsSince = "2014-01-01"; // date from which we find twiits  YYYY-MM-DD
    //private static String twittsUntil = "2014-12-01"; // date to which we find twiits  YYYY-MM-DD

    public DisasterMiner() {

        initCategories();

        getTwitts();

        saveTwitts();

    }

    private void saveTwitts() {
        BufferedWriter out = null;
        try {
            for (Category c : categories) {
                    out = new BufferedWriter(new FileWriter(outFile, true));
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
            Scanner sc = new Scanner(new File(catFile));
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
            Query query = new Query(searchQuery);
            query.setCount(twittsToFind);
            //query.setSince(twittsSince);
            //query.setUntil(twittsUntil);

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
    }
}
