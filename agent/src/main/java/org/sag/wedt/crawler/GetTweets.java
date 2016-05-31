package org.sag.wedt.crawler;

import jade.util.Logger;
import twitter4j.*;

import java.io.IOException;
import java.util.logging.Level;


/**
 * Created by Michał Breiter.
 * Klasa wyszukująca Tweety bazująca na Twitter4j
 */
public class GetTweets {
    private static final Logger logger = Logger.getJADELogger(GetTweets.class.getName());

    private final String category;
    private String[] keywords = new String[2];

    private TwitterStream twitterStream = null;

    public GetTweets(String category, String[] keywords) {
        this.category = category;
        this.keywords = keywords;
    }

    /** Start crawling tweets in background thread.
     * Tweets will be filtered to category.
     *
     * @param listener listener which will be informed on new twetts @see {@link StatusListener}
     */
    void start(final StatusListener listener) {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        //twitterStream.sample();

        // changed to filter
        // https://github.com/kantega/storm-twitter-workshop/wiki/Basic-Twitter-stream-reading-using-Twitter4j
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track(keywords);
        // lokalizacja poniżej w przypadku, gdybysmy mysleli o lokalizacji
//        filterQuery.locations(new double[][]{new double[]{-126.562500,30.448674},
//                new double[]{-61.171875,44.087585
//                }}); // See https://dev.twitter.com/docs/streaming-apis/parameters#locations for proper location doc.
//        //Note that not all tweets have location metadata set.
        filterQuery.language("en");
        twitterStream.filter(filterQuery); // Start consuming public statuses that match one or more filter predicates.

    }

    public void stop() {
        if(twitterStream != null) {
            twitterStream.clearListeners();
            twitterStream.cleanUp(); // not sure if needed, documentation is sparse
        }

    }

    public static void main(String[] args) throws TwitterException, IOException, InterruptedException {
        String[] keywords = new String[2];
        keywords[0] = "car";
        keywords[1] = "crash";

        GetTweets getTweets = new GetTweets("crash", keywords);
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                logger.log(Level.INFO, status.getUser().getName() + " : " + status.getText() + "geo: " + status.getGeoLocation() +
                        "place: " + status.getPlace());
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                logger.log(Level.WARNING, "Limitation notice" + numberOfLimitedStatuses);
            }

            public void onScrubGeo(long userId, long upToStatusId) {
            }

            public void onStallWarning(StallWarning warning) {
                logger.log(Level.WARNING, "Stall warning" + warning.getMessage() + ":" + warning.getPercentFull());
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        getTweets.start(listener);
        Thread.sleep(10000);
    }
}
