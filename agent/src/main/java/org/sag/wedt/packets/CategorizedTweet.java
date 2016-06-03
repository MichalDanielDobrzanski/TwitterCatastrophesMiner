package org.sag.wedt.packets;

import org.sag.wedt.common.TweetCategory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Michał Breiter.
 */
public class CategorizedTweet implements Serializable {
    private final TweetCategory category; // category after classification
    private final CrawledTweet crawled; // tweet after crawling
    private ArrayList<String> locationNames;

    public CategorizedTweet(CrawledTweet crawledTweet, TweetCategory category) {
        this.crawled = crawledTweet;
        this.category = category;
    }

    public ArrayList<String> getLocationNames() {
        return locationNames;
    }

    public void setLocationNames(ArrayList<String> locationNames) {
        this.locationNames = locationNames;
    }

    public CrawledTweet getCrawled() {
        return crawled;
    }

    public TweetCategory getCategory() {
        return category;
    }
}
