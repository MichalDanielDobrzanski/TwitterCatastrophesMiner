package org.sag.wedt.packets;

import org.sag.wedt.common.TweetCategories;

import java.io.Serializable;

/**
 * Created by Michał Breiter.
 */
public class CategorizedTweet implements Serializable {
    private final TweetCategories category; // category after classification
    private final CrawledTweet crawled; // tweet after crawling

    public CategorizedTweet(CrawledTweet crawledTweet, TweetCategories category) {
        this.crawled = crawledTweet;
        this.category = category;
    }

    public CrawledTweet getCrawled() {
        return crawled;
    }

    public TweetCategories getCategory() {
        return category;
    }
}
