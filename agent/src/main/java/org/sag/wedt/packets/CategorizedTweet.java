package org.sag.wedt.packets;

import org.sag.wedt.common.TweetCategory;

import java.io.Serializable;

/**
 * Created by Michał Breiter.
 */
public class CategorizedTweet implements Serializable {
    private final TweetCategory category; // category after classification
    private final CrawledTweet crawled; // tweet after crawling

    public CategorizedTweet(CrawledTweet crawledTweet, TweetCategory category) {
        this.crawled = crawledTweet;
        this.category = category;
    }

    public CrawledTweet getCrawled() {
        return crawled;
    }

    public TweetCategory getCategory() {
        return category;
    }
}
