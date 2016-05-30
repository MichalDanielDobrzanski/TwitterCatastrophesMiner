package org.sag.wedt.crawler;

import jade.util.Logger;
import twitter4j.*;

import java.io.IOException;
import java.util.logging.Level;


/**
 * Created by Michał Breiter.
 */
public class GetTweets {
    private static final Logger logger = Logger.getJADELogger(GetTweets.class.getName());
    private final String category;
    private TwitterStream twitterStream = null;

    public GetTweets(String category) {
        this.category = category;
    }

    /** Start crawling tweets in background thread.
     * Tweets will be filtered to category.
     *
     * @param listener listener which will be informed on new twetts @see {@link StatusListener}
     */
    void start(final StatusListener listener) {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        twitterStream.sample();
        // change to filter
    }

    public void stop() {
        if(twitterStream != null) {
            twitterStream.clearListeners();
            twitterStream.cleanUp(); // not sure if needed, dockumentation is sparse
        }

    }

    public static void main(String[] args) throws TwitterException, IOException, InterruptedException {
        GetTweets getTweets = new GetTweets("crash");
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
