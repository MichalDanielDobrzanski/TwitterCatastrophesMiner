package org.sag.wedt.crawler;

import jade.core.behaviours.CyclicBehaviour;
import jade.util.Logger;
import org.sag.wedt.packets.CrawledTweet;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import java.util.logging.Level;

/**
 * Created by Michał Breiter.
 * Klasa nasłuchująca na przyjście nowych Tweetów (implementująca StatusListener)
 */
public class CrawlTwitterBehaviour extends CyclicBehaviour implements StatusListener {

    private static final Logger logger = Logger.getJADELogger(CrawlTwitterBehaviour.class.getName());

    private final String category;
    private final String categorizerAgentName;
    private final String[] keywords;

    private final CrawlerAgent crawlerAgent;
    GetTweets getTweets = null;

    public CrawlTwitterBehaviour(CrawlerAgent crawlerAgent, String category, String categorizerAgentName, String[] keywords) {
        super(crawlerAgent);
        this.crawlerAgent = crawlerAgent;
        this.category = category;
        this.categorizerAgentName = categorizerAgentName;
        this.keywords = keywords;
    }

    // Jade
    @Override
    public void action() {
        if(getTweets == null) {
            getTweets = new GetTweets(category, keywords);
            getTweets.start(this);
            logger.log(Level.INFO, "starting tweet crawling, sending to:" + categorizerAgentName);
        }
        block();
    }

    @Override
    public int onEnd() {
        if(getTweets != null) {
            getTweets.stop();
        }
        return super.onEnd();
    }

    // twitter
    // note called from other thread
    public void onStatus(Status status) {
//        logger.log(Level.INFO, "got message " + status.getUser().getName() + " : " + status.getText() + "geo: " + status.getGeoLocation() +
//                "place: " + status.getPlace());

        logger.log(Level.INFO, "got message from " + status.getUser().getName());
        // addBehaviour seems to be thread safe
        // or add to queue and change to periodic behaviour
        // or post message to agent's queue
        myAgent.addBehaviour(new SendTweetsBehaviour(new CrawledTweet(status, category), categorizerAgentName));
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
}
