package org.sag.wedt.packets;

import twitter4j.Status;

import java.io.Serializable;

/**
 * Created by Michał Breiter.
 * Message send between Crawler and(->) Categorizer.
 */
public class CrawledTweet implements Serializable {
    private final Status status;
    private final String crawlerCategory;

    public CrawledTweet(Status status, String crawlerCategory) {
        this.status = status;
        this.crawlerCategory = crawlerCategory;
    }

    public Status getStatus() {
        return status;
    }

    public String getCrawlerCategory() {
        return crawlerCategory;
    }
}
